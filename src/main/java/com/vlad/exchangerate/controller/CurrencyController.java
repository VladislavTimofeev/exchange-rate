package com.vlad.exchangerate.controller;

import com.vlad.exchangerate.dto.CurrencyDto;
import com.vlad.exchangerate.dto.ExchangeRateResponse;
import com.vlad.exchangerate.service.CurrencyService;
import com.vlad.exchangerate.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
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
public class CurrencyController {
    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/fetch")
    public ResponseEntity<ExchangeRateResponse> fetchRates() {
        return ResponseEntity.ok(exchangeRateService.fetchExchangeRate());
    }

    @GetMapping("/current")
    public ResponseEntity<Map<String, BigDecimal>> getCurrentRates() {
        return ResponseEntity.ok(exchangeRateService.getExchangeRatesMap());
    }

    @GetMapping
    public ResponseEntity<List<CurrencyDto>> getCurrencies() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    @GetMapping("/rate")
    public ResponseEntity<CurrencyDto> getRate(@RequestParam("code") String code) {
        return currencyService.getRate(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CurrencyDto createCurrency(@RequestBody CurrencyDto currencyDto) {
        return currencyService.addCurrency(currencyDto);
    }
}
