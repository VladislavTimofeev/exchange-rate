package com.vlad.exchangerate.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class CurrencyDto {

    @NotBlank(message = "Currency code cannot be empty")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 letters")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must contain only uppercase letters")
    String code;

    @NotBlank(message = "Currency name cannot be empty")
    @Size(max = 14, message = "Currency name cannot exceed 14 characters")
    String name;

    @NotNull(message = "Currency rate cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Currency rate must be greater than 0")
    BigDecimal rate;
}
