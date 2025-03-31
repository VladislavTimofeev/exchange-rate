package com.vlad.exchangerate.service.impl;

import com.vlad.exchangerate.entity.Currency;
import com.vlad.exchangerate.repository.CurrencyRepository;
import com.vlad.exchangerate.service.CurrencyReadService;
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
public class CurrencyReadServiceImpl implements CurrencyReadService {

    private final CurrencyRepository currencyRepository;

    @Override
    public List<Currency> getAllCurrencies() {
        List<Currency> currencies = currencyRepository.findAll();
        log.info("Fetched {} currencies", currencies.size());
        return currencies;
    }

    @Override
    public Optional<Currency> getCurrencyByCode(String code) {
        log.info("Fetching currency with code: {}", code);
        return currencyRepository.findByCode(code)
                .map(currency -> {
                    log.info("Currency with code {} found", code);
                    return currency;
                });
    }
}
