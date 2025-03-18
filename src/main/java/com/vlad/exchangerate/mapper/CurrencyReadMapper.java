package com.vlad.exchangerate.mapper;

import com.vlad.exchangerate.dto.CurrencyDto;
import com.vlad.exchangerate.entity.Currency;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyReadMapper implements Mapper<Currency, CurrencyDto> {
    @Override
    public CurrencyDto map(Currency object) {
        if (object == null) {
            return new CurrencyDto("", "", BigDecimal.ZERO);
        }
        return new CurrencyDto(
                object.getCode(),
                object.getName(),
                object.getRate()
        );
    }
}
