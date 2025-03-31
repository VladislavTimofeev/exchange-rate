package com.vlad.exchangerate.service.impl;

import com.vlad.exchangerate.dto.CurrencyRequest;
import com.vlad.exchangerate.entity.Currency;
import com.vlad.exchangerate.exception.CurrencyException;
import com.vlad.exchangerate.exception.ErrorCode;
import com.vlad.exchangerate.mapper.CurrencyMapper;
import com.vlad.exchangerate.repository.CurrencyRepository;
import com.vlad.exchangerate.service.CurrencyWriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CurrencyWriteServiceImpl implements CurrencyWriteService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    @Override
    public Currency save(@Valid CurrencyRequest currencyRequest) {
        log.info("Saving currency with code: {}", currencyRequest.getCode());

        currencyRepository.findByCode(currencyRequest.getCode())
                .ifPresent(existingCurrency -> {
                    log.warn("Currency with code {} already exists", currencyRequest.getCode());
                    throw new CurrencyException(ErrorCode.CURRENCY_ALREADY_EXISTS);
                });

        Currency currency = currencyMapper.toEntity(currencyRequest);

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
