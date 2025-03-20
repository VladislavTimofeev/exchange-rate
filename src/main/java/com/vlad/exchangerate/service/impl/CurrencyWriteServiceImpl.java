package com.vlad.exchangerate.service.impl;

import com.vlad.exchangerate.entity.Currency;
import com.vlad.exchangerate.exception.CurrencyException;
import com.vlad.exchangerate.exception.ErrorCode;
import com.vlad.exchangerate.exception.CurrencyRateException;
import com.vlad.exchangerate.repository.CurrencyRepository;
import com.vlad.exchangerate.service.CurrencyWriteService;
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
public class CurrencyWriteServiceImpl implements CurrencyWriteService {

    private final CurrencyRepository currencyRepository;

    @Override
    public Currency save(@Valid Currency currency) {
        log.info("Saving currency with code: {}", currency.getCode());

        currencyRepository.findByCode(currency.getCode())
                .ifPresent(existingCurrency -> {
            log.warn("Currency with code {} already exists", currency.getCode());
            throw new CurrencyException(ErrorCode.CURRENCY_ALREADY_EXISTS);
        });

        if (currency.getRate() == null || currency.getRate().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Invalid currency rate for {}: {}", currency.getCode(), currency.getRate());
            throw new CurrencyRateException(ErrorCode.INVALID_CURRENCY_RATE);
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
