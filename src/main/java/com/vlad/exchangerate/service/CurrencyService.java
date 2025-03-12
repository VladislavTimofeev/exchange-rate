package com.vlad.exchangerate.service;

import com.vlad.exchangerate.dto.CurrencyDto;
import com.vlad.exchangerate.mapper.CurrencyCreateMapper;
import com.vlad.exchangerate.mapper.CurrencyReadMapper;
import com.vlad.exchangerate.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyReadMapper currencyReadMapper;
    private final CurrencyCreateMapper currencyCreateMapper;

    public List<CurrencyDto> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .map(currencyReadMapper::map)
                .toList();
    }

    public Optional<CurrencyDto> getRate(String code) {
        return currencyRepository.findByCode(code)
                .map(currencyReadMapper::map);
    }

    @Transactional
    public CurrencyDto addCurrency(CurrencyDto currencyDto) {
        return Optional.of(currencyDto)
                .map(currencyCreateMapper::map)
                .map(currencyRepository::save)
                .map(currencyReadMapper::map)
                .orElseThrow();
    }
}
