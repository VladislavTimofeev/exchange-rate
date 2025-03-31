package com.vlad.exchangerate.service;

import com.vlad.exchangerate.dto.CurrencyRequest;
import com.vlad.exchangerate.dto.CurrencyResponse;

import java.util.List;

public interface CurrencyFacade {
    List<CurrencyResponse> getAllCurrencies();
    CurrencyResponse getCurrencyByCode(String code);
    CurrencyResponse saveCurrency(CurrencyRequest currencyRequest);
}
