package com.vlad.exchangerate.service.impl;

import com.vlad.exchangerate.client.ExternalExchangeRateApi;
import com.vlad.exchangerate.config.ExternalApiConfig;
import com.vlad.exchangerate.dto.ExchangeRateResponse;
import com.vlad.exchangerate.entity.Currency;
import com.vlad.exchangerate.exception.ErrorCode;
import com.vlad.exchangerate.exception.ExchangeRateFetchException;
import com.vlad.exchangerate.repository.CurrencyRepository;
import com.vlad.exchangerate.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExternalExchangeRateApi externalExchangeRateApi;
    private final CurrencyRepository currencyRepository;
    private final Map<String, BigDecimal> exchangeRatesMap = new HashMap<>();
    private final ExternalApiConfig externalApiConfig;

    @Override
    public ExchangeRateResponse fetchExchangeRate() {
        try {
            return externalExchangeRateApi.getExchangeRate(externalApiConfig.getApiKey());
        } catch (Exception e) {
            log.error("Failed to fetch exchange rate: {}", e.getMessage());
            throw new ExchangeRateFetchException(ErrorCode.EXCHANGE_RATE_FETCH_FAILED);
        }
    }

    @Override
    public Map<String, BigDecimal> getExchangeRatesMap() {
        return new HashMap<>(exchangeRatesMap);
    }

    @Override
    @Scheduled(fixedRateString = "${app.scheduled-tasks.apiUpdateInterval}")
    public void fetchExchangeRateAndSave() {
        try {
            log.info("Fetching exchange rates...");
            ExchangeRateResponse exchangeRateResponse = externalExchangeRateApi.getExchangeRate(externalApiConfig.getApiKey());
            checkExchangeRateResponse(exchangeRateResponse);
        } catch (Exception e) {
            log.error("Error occurred while fetching exchange rates: {}", e.getMessage());
        }
    }

    private void checkExchangeRateResponse(ExchangeRateResponse exchangeRateResponse) {
        if (exchangeRateResponse.isSuccess()) {
            updateExchangeRates(exchangeRateResponse.getRates());
            log.info("Exchange rates fetched and saved successfully.");
        } else {
            log.warn("Failed to fetch exchange rates: {}", exchangeRateResponse);
        }
    }

    private void updateExchangeRates(Map<String, BigDecimal> rates) {
        exchangeRatesMap.clear();
        exchangeRatesMap.putAll(rates);

        rates.forEach((code, rate) -> {
            Currency currency = currencyRepository.findByCode(code)
                    .orElse(Currency.builder()
                            .code(code)
                            .name(code)
                            .rate(rate)
                            .build());

            currency.setRate(rate);
            currencyRepository.save(currency);
        });
    }
}
