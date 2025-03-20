package com.vlad.exchangerate.service.impl;

import com.vlad.exchangerate.dto.CurrencyDto;
import com.vlad.exchangerate.entity.Currency;
import com.vlad.exchangerate.exception.ErrorCode;
import com.vlad.exchangerate.exception.ResourceNotFoundException;
import com.vlad.exchangerate.mapper.CurrencyCreateMapper;
import com.vlad.exchangerate.mapper.CurrencyReadMapper;
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
    private final CurrencyReadMapper currencyReadMapper;
    private final CurrencyCreateMapper currencyCreateMapper;

    @Override
    public List<CurrencyDto> getAllCurrencies() {
        List<CurrencyDto> currencies = currencyReadServiceImpl.getAllCurrencies().stream()
                .map(currencyReadMapper::map)
                .toList();
        log.info("Fetched {} currencies", currencies.size());
        return currencies;
    }

    @Override
    public CurrencyDto getCurrencyByCode(String code) {
        log.info("Fetching currency by code: {}", code);
        Currency currency = currencyReadServiceImpl.getCurrencyByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        log.info("Currency with code {} found", code);
        return currencyReadMapper.map(currency);
    }

    @Override
    public CurrencyDto saveCurrency(CurrencyDto currencyDto) {
        log.info("Saving currency with code: {}", currencyDto.getCode());
        Currency currency = currencyCreateMapper.map(currencyDto);
        try {
            Currency savedCurrency = currencyWriteServiceImpl.save(currency);
            log.info("Currency with code {} saved successfully", currencyDto.getCode());
            return currencyReadMapper.map(savedCurrency);
        } catch (Exception e) {
            log.error("Failed to save currency with code {}: {}", currencyDto.getCode(), e.getMessage());
            throw e;
        }
    }
}
