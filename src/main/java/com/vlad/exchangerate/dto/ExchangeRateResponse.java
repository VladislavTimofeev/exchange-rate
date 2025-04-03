package com.vlad.exchangerate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangeRateResponse {

    private boolean success;

    @NotNull(message = "Timestamp cannot be null")
    private Long timestamp;

    @NotBlank(message = "Base currency cannot be empty")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Base currency must contain only uppercase letters")
    private String base;

    @NotBlank(message = "Date cannot be empty")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in YYYY-MM-DD format")
    private String date;

    @NotEmpty(message = "Rates cannot be empty")
    private Map<String, BigDecimal> rates;
}
