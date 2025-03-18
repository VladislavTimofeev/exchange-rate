package com.vlad.exchangerate.service;

import com.vlad.exchangerate.entity.Currency;
import com.vlad.exchangerate.exception.CurrencyAlreadyExistsException;
import com.vlad.exchangerate.exception.InvalidCurrencyRateException;
import com.vlad.exchangerate.repository.CurrencyRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CurrencyWriteService {

    private final CurrencyRepository currencyRepository;

    public Currency save(@Valid Currency currency) {
        log.info("Saving currency with code: {}", currency.getCode());

        currencyRepository.findByCode(currency.getCode()).ifPresent(existingCurrency -> {
            log.warn("Currency with code {} already exists", currency.getCode());
            throw new CurrencyAlreadyExistsException("Currency with code " + currency.getCode() + " already exists");
        });

        if (currency.getRate() == null || currency.getRate().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Invalid currency rate for {}: {}", currency.getCode(), currency.getRate());
            throw new InvalidCurrencyRateException("Currency rate must be greater than zero");
        }

        try {
            Currency savedCurrency = currencyRepository.save(currency);
            log.info("Currency with code {} saved successfully", currency.getCode());
            return savedCurrency;
        } catch (Exception e) {
            log.error("Failed to save currency with code {}: {}", currency.getCode(), e.getMessage());
            throw e;
        }
    }
}
