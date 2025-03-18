package com.vlad.exchangerate.controller;

import com.vlad.exchangerate.dto.CurrencyDto;
import com.vlad.exchangerate.dto.ExchangeRateResponse;
import com.vlad.exchangerate.exception.ExchangeRateFetchException;
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
    public ResponseEntity<List<CurrencyDto>> getAllCurrencies() {
        log.info("Fetching all currencies...");
        List<CurrencyDto> currencies = currencyFacade.getAllCurrencies();
        if (currencies.isEmpty()) {
            log.warn("No currencies found");
            return ResponseEntity.noContent().build();
        }
        log.info("Fetched {} currencies", currencies.size());
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/rate")
    public ResponseEntity<CurrencyDto> getCurrencyByCode(@RequestParam("code") String code) {
        log.info("Fetching currency with code: {}", code);
        return currencyFacade.getCurrencyByCode(code)
                .map(currencyDto -> {
                    log.info("Currency with code {} found", code);
                    return ResponseEntity.ok(currencyDto);
                })
                .orElseGet(() -> {
                    log.warn("Currency with code {} not found", code);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CurrencyDto createCurrency(@Valid @RequestBody CurrencyDto currencyDto) {
        log.info("Creating currency with code: {}", currencyDto.getCode());
        CurrencyDto createdCurrency = currencyFacade.saveCurrency(currencyDto);
        log.info("Currency with code {} created successfully", currencyDto.getCode());
        return createdCurrency;
    }

    @GetMapping("/fetch")
    public ResponseEntity<ExchangeRateResponse> fetchRates() {
        try {
            log.info("Fetching exchange rates...");
            ExchangeRateResponse response = exchangeRateService.fetchExchangeRate();
            log.info("Successfully fetched exchange rates");
            return ResponseEntity.ok(response);
        } catch (ExchangeRateFetchException e) {
            log.error("Failed to fetch exchange rates: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<Map<String, BigDecimal>> getCurrentRates() {
        log.info("Fetching current exchange rates...");
        Map<String, BigDecimal> rates = exchangeRateService.getExchangeRatesMap();
        if (rates.isEmpty()) {
            log.warn("No exchange rates available");
            return ResponseEntity.noContent().build();
        }
        log.info("Fetched {} exchange rates", rates.size());
        return ResponseEntity.ok(rates);
    }
}
