package com.vlad.exchangerate.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CurrencyResponse {
    String code;
    String name;
    BigDecimal rate;
}
