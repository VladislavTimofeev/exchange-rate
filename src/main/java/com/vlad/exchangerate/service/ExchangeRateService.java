package com.vlad.exchangerate.service;

import com.vlad.exchangerate.client.ExternalExchangeRateApi;
import com.vlad.exchangerate.dto.ExchangeRateResponse;
import com.vlad.exchangerate.entity.Currency;
import com.vlad.exchangerate.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExternalExchangeRateApi externalExchangeRateApi;
    private final CurrencyRepository currencyRepository;
    private final Map<String, BigDecimal> exchangeRatesMap = new HashMap<>();

    @Value("${exchange.api.key}")
    private String apiKey;

    public ExchangeRateResponse fetchExchangeRate() {
        return externalExchangeRateApi.getExchangeRate(apiKey);
    }

    public Map<String, BigDecimal> getExchangeRatesMap() {
        return new HashMap<>(exchangeRatesMap);
    }

    @Scheduled(fixedRate = 600000)
    public void fetchExchangeRateAndSave() {
        try {
            ExchangeRateResponse exchangeRateResponse = externalExchangeRateApi.getExchangeRate(apiKey);
            checkExchangeRateResponse(exchangeRateResponse);
        } catch (Exception e) {
            System.err.println("Exchange rates fetching failed" + e.getMessage());
        }
    }

    private void checkExchangeRateResponse(ExchangeRateResponse exchangeRateResponse) {
        if (exchangeRateResponse.isSuccess()) {
            exchangeRatesMap.clear();
            exchangeRatesMap.putAll(exchangeRateResponse.getRates());
            exchangeRateResponse.getRates().forEach((code, rate) -> {
                Currency currency = currencyRepository.findByCode(code)
                        .orElse(new Currency(null, code, code, rate, null));
                currency.setRate(rate);
                currencyRepository.save(currency);
            });
            System.out.println("Exchange rates fetched successfully");
        } else {
            System.out.println("Exchange rates fetching failed" + exchangeRateResponse);
        }
    }
}
