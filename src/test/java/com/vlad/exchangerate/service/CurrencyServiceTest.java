package com.vlad.exchangerate.service;

import com.vlad.exchangerate.dto.CurrencyDto;
import com.vlad.exchangerate.entity.Currency;
import com.vlad.exchangerate.mapper.CurrencyCreateMapper;
import com.vlad.exchangerate.mapper.CurrencyReadMapper;
import com.vlad.exchangerate.repository.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private CurrencyReadMapper currencyReadMapper;
    @Mock
    private CurrencyCreateMapper currencyCreateMapper;
    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void getAllCurrencies() {
        Currency usd = new Currency(1L, "USD", "Dollar", BigDecimal.valueOf(1.23), LocalDateTime.of(2024, 3, 1, 12, 0));
        Currency eur = new Currency(2L, "EUR", "Euro", BigDecimal.valueOf(1.00), LocalDateTime.of(2024, 3, 1, 12, 0));
        Currency pln = new Currency(3L, "PLN", "Zloty", BigDecimal.valueOf(4.19), LocalDateTime.of(2024, 3, 1, 12, 0));
        CurrencyDto usdDto = new CurrencyDto("USD", "Dollar", BigDecimal.valueOf(1.23));
        CurrencyDto eurDto = new CurrencyDto("EUR", "Euro", BigDecimal.valueOf(1.00));
        CurrencyDto plnDto = new CurrencyDto("PLN", "Zloty", BigDecimal.valueOf(4.19));
        List<Currency> currencies = List.of(usd, eur, pln);
        when(currencyRepository.findAll()).thenReturn(currencies);
        when(currencyReadMapper.map(usd)).thenReturn(usdDto);
        when(currencyReadMapper.map(eur)).thenReturn(eurDto);
        when(currencyReadMapper.map(pln)).thenReturn(plnDto);

        List<CurrencyDto> actualResult = currencyService.getAllCurrencies();

        assertThat(actualResult).containsExactly(usdDto, eurDto, plnDto);
        verify(currencyRepository, times(1)).findAll();
        verify(currencyReadMapper, times(3)).map(any(Currency.class));
    }

    @Test
    void getRate() {
        Currency usd = new Currency(1L, "USD", "Dollar", BigDecimal.valueOf(1.23), LocalDateTime.of(2024, 3, 1, 12, 0));
        CurrencyDto usdDto = new CurrencyDto("USD", "Dollar", BigDecimal.valueOf(1.23));
        when(currencyRepository.findByCode("USD")).thenReturn(Optional.of(usd));
        when(currencyReadMapper.map(usd)).thenReturn(usdDto);

        Optional<CurrencyDto> actualResult = currencyService.getRate("USD");

        assertThat(actualResult).isPresent();
        verify(currencyRepository, times(1)).findByCode("USD");
        verify(currencyReadMapper, times(1)).map(any(Currency.class));
    }

    @Test
    void addCurrency() {
        CurrencyDto usdDto = new CurrencyDto("USD", "Dollar", BigDecimal.valueOf(1.23));
        Currency usd = new Currency(1L, "USD", "Dollar", BigDecimal.valueOf(1.23), LocalDateTime.of(2024, 3, 1, 12, 0));
        when(currencyCreateMapper.map(usdDto)).thenReturn(usd);
        when(currencyRepository.save(usd)).thenReturn(usd);
        when(currencyReadMapper.map(usd)).thenReturn(usdDto);

        CurrencyDto actualResult = currencyService.addCurrency(usdDto);

        assertThat(actualResult).isNotNull();
        assertEquals(actualResult, usdDto);
        verify(currencyCreateMapper, times(1)).map(usdDto);
        verify(currencyRepository, times(1)).save(usd);
        verify(currencyReadMapper, times(1)).map(any(Currency.class));
    }
}
