package com.vlad.exchangerate.service;

import com.vlad.exchangerate.entity.Currency;
import com.vlad.exchangerate.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CurrencyReadService {

    private final CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrencies() {
        log.info("Fetching all currencies from the database...");
        List<Currency> currencies = currencyRepository.findAll();
        log.info("Fetched {} currencies", currencies.size());
        return currencies;
    }

    public Optional<Currency> getCurrencyByCode(String code) {
        log.info("Fetching currency with code: {}", code);
        Optional<Currency> currency = currencyRepository.findByCode(code);
        currency.ifPresentOrElse(
                c -> log.info("Currency with code {} found", code),
                () -> log.warn("Currency with code {} not found", code)
        );
        return currency;
    }
}
