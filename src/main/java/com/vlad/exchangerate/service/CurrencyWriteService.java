package com.vlad.exchangerate.service;

import com.vlad.exchangerate.dto.CurrencyRequest;
import com.vlad.exchangerate.entity.Currency;
import jakarta.validation.Valid;

public interface CurrencyWriteService {
    Currency save(@Valid CurrencyRequest currencyRequest);
}
