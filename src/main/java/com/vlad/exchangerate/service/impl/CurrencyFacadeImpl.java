package com.vlad.exchangerate.service.impl;

import com.vlad.exchangerate.dto.CurrencyRequest;
import com.vlad.exchangerate.dto.CurrencyResponse;
import com.vlad.exchangerate.entity.Currency;
import com.vlad.exchangerate.exception.ErrorCode;
import com.vlad.exchangerate.exception.ResourceNotFoundException;
import com.vlad.exchangerate.mapper.CurrencyMapper;
import com.vlad.exchangerate.service.CurrencyFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyFacadeImpl implements CurrencyFacade {

    private final CurrencyReadServiceImpl currencyReadServiceImpl;
    private final CurrencyWriteServiceImpl currencyWriteServiceImpl;
    private final CurrencyMapper currencyMapper;

    @Override
    public List<CurrencyResponse> getAllCurrencies() {
        List<CurrencyResponse> currencies = currencyReadServiceImpl.getAllCurrencies().stream()
                .map(currencyMapper::toResponse)
                .toList();
        log.info("Fetched {} currencies", currencies.size());
        return currencies;
    }

    @Override
    public CurrencyResponse getCurrencyByCode(String code) {
        log.info("Fetching currency by code: {}", code);
        Currency currency = currencyReadServiceImpl.getCurrencyByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        log.info("Currency with code {} found", code);
        return currencyMapper.toResponse(currency);
    }

    @Override
    public CurrencyResponse saveCurrency(CurrencyRequest currencyRequest) {
        log.info("Saving currency with code: {}", currencyRequest.getCode());
        try {
            Currency savedCurrency = currencyWriteServiceImpl.save(currencyRequest);
            log.info("Currency with code {} saved successfully", currencyRequest.getCode());
            return currencyMapper.toResponse(savedCurrency);
        } catch (Exception e) {
            log.error("Failed to save currency with code {}: {}", currencyRequest.getCode(), e.getMessage());
            throw e;
        }
    }
}
