package com.vlad.exchangerate.service;

import com.vlad.exchangerate.dto.CurrencyDto;

import java.util.List;

public interface CurrencyFacade {
    List<CurrencyDto> getAllCurrencies();
    CurrencyDto getCurrencyByCode(String code);
    CurrencyDto saveCurrency(CurrencyDto currencyDto);
}
