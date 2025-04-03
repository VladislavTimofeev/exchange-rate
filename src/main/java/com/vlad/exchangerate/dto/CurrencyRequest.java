package com.vlad.exchangerate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class CurrencyRequest {

    @NotBlank(message = "Currency code cannot be empty")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 letters")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must contain only uppercase letters")
    String code;

    @NotBlank(message = "Currency name cannot be empty")
    @Size(max = 14, message = "Currency name cannot exceed 14 characters")
    String name;
}
