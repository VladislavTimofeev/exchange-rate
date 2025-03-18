package com.vlad.exchangerate.mapper;

import com.vlad.exchangerate.dto.CurrencyDto;
import com.vlad.exchangerate.entity.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CurrencyCreateMapper implements Mapper<CurrencyDto, Currency> {
    @Override
    public Currency map(CurrencyDto object) {
        if (object == null) {
            return new Currency(null, "", "", BigDecimal.ZERO, null);
        }
        return new Currency(
                null,
                object.getCode(),
                object.getName(),
                object.getRate(),
                null
        );
    }
}
