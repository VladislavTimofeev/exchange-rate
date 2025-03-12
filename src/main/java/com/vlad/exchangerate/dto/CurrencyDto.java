package com.vlad.exchangerate.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CurrencyDto {
    String code;
    String name;
    BigDecimal rate;
}
