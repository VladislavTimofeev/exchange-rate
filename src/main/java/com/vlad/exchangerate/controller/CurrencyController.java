package com.vlad.exchangerate.controller;

import com.vlad.exchangerate.dto.CurrencyRequest;
import com.vlad.exchangerate.dto.CurrencyResponse;
import com.vlad.exchangerate.dto.ExchangeRateResponse;
import com.vlad.exchangerate.service.CurrencyFacade;
import com.vlad.exchangerate.service.ExchangeRateService;
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

    private final ExchangeRateService exchangeRateService;
    private final CurrencyFacade currencyFacade;

    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getAllCurrencies() {
        List<CurrencyResponse> currencies = currencyFacade.getAllCurrencies();
        if (currencies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/rate")
    public ResponseEntity<CurrencyResponse> getCurrencyByCode(@RequestParam("code") String code) {
        CurrencyResponse currencyResponse = currencyFacade.getCurrencyByCode(code);
        return ResponseEntity.ok(currencyResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CurrencyResponse createCurrency(@Valid @RequestBody CurrencyRequest currencyRequest) {
        return currencyFacade.saveCurrency(currencyRequest);
    }

    @GetMapping("/fetch-rates")
    public ResponseEntity<ExchangeRateResponse> fetchRates() {
        ExchangeRateResponse response = exchangeRateService.fetchExchangeRate();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current-exchange-rates")
    public ResponseEntity<Map<String, BigDecimal>> getCurrentRates() {
        Map<String, BigDecimal> rates = exchangeRateService.getExchangeRatesMap();
        if (rates.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rates);
    }
}
