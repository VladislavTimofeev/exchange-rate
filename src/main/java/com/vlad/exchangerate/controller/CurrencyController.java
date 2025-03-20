package com.vlad.exchangerate.controller;

import com.vlad.exchangerate.dto.CurrencyDto;
import com.vlad.exchangerate.dto.ExchangeRateResponse;
import com.vlad.exchangerate.service.impl.CurrencyFacadeImpl;
import com.vlad.exchangerate.service.impl.ExchangeRateServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
@Slf4j
public class CurrencyController {

    private final ExchangeRateServiceImpl exchangeRateServiceImpl;
    private final CurrencyFacadeImpl currencyFacadeImpl;

    @GetMapping
    public ResponseEntity<List<CurrencyDto>> getAllCurrencies() {
        List<CurrencyDto> currencies = currencyFacadeImpl.getAllCurrencies();
        if (currencies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/rate")
    public ResponseEntity<CurrencyDto> getCurrencyByCode(@RequestParam("code") String code) {
        CurrencyDto currencyDto = currencyFacadeImpl.getCurrencyByCode(code);
        return ResponseEntity.ok(currencyDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CurrencyDto createCurrency(@Valid @RequestBody CurrencyDto currencyDto) {
        return currencyFacadeImpl.saveCurrency(currencyDto);
    }

    @GetMapping("/fetch-rates")
    public ResponseEntity<ExchangeRateResponse> fetchRates() {
        ExchangeRateResponse response = exchangeRateServiceImpl.fetchExchangeRate();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current-exchange-rates")
    public ResponseEntity<Map<String, BigDecimal>> getCurrentRates() {
        Map<String, BigDecimal> rates = exchangeRateServiceImpl.getExchangeRatesMap();
        if (rates.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rates);
    }
}
