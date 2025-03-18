package com.vlad.exchangerate.service;

import com.vlad.exchangerate.dto.CurrencyDto;
import com.vlad.exchangerate.entity.Currency;
import com.vlad.exchangerate.mapper.CurrencyCreateMapper;
import com.vlad.exchangerate.mapper.CurrencyReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyFacade {

    private final CurrencyReadService currencyReadService;
    private final CurrencyWriteService currencyWriteService;
    private final CurrencyReadMapper currencyReadMapper;
    private final CurrencyCreateMapper currencyCreateMapper;

    public List<CurrencyDto> getAllCurrencies() {
        log.info("Fetching all currencies...");
        List<CurrencyDto> currencies = currencyReadService.getAllCurrencies().stream()
                .map(currencyReadMapper::map)
                .toList();
        log.info("Fetched {} currencies", currencies.size());
        return currencies;
    }

    public Optional<CurrencyDto> getCurrencyByCode(String code) {
        log.info("Fetching currency by code: {}", code);
        Optional<CurrencyDto> currency = currencyReadService.getCurrencyByCode(code)
                .map(currencyReadMapper::map);

        currency.ifPresentOrElse(
                c -> log.info("Currency with code {} found", code),
                () -> log.warn("Currency with code {} not found", code)
        );
        return currency;
    }

    @Transactional
    public CurrencyDto saveCurrency(CurrencyDto currencyDto) {
        log.info("Saving currency with code: {}", currencyDto.getCode());
        Currency currency = currencyCreateMapper.map(currencyDto);
        try {
            Currency savedCurrency = currencyWriteService.save(currency);
            log.info("Currency with code {} saved successfully", currencyDto.getCode());
            return currencyReadMapper.map(savedCurrency);
        } catch (Exception e) {
            log.error("Failed to save currency with code {}: {}", currencyDto.getCode(), e.getMessage());
            throw e;
        }
    }
}
