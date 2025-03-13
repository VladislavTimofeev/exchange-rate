package com.vlad.exchangerate.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangeRateResponse {
    private boolean success;
    private Long timestamp;
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;
}
