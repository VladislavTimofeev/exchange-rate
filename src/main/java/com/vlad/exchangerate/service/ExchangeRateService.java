package com.vlad.exchangerate.service;

import com.vlad.exchangerate.dto.ExchangeRateResponse;

import java.math.BigDecimal;
import java.util.Map;

public interface ExchangeRateService {
    ExchangeRateResponse fetchExchangeRate();

    Map<String, BigDecimal> getExchangeRatesMap();

    void fetchExchangeRateAndSave();
}
