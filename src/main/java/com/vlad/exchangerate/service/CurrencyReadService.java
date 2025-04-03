package com.vlad.exchangerate.service;

import com.vlad.exchangerate.entity.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyReadService {
    List<Currency> getAllCurrencies();

    Optional<Currency> getCurrencyByCode(String code);
}
