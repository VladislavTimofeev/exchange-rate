package com.vlad.exchangerate.mapper;

import com.vlad.exchangerate.dto.CurrencyDto;
import com.vlad.exchangerate.entity.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyCreateMapper implements Mapper<CurrencyDto, Currency> {

    @Override
    public Currency map(CurrencyDto object) {
        return new Currency(
                null,
                object.getCode(),
                object.getName(),
                object.getRate()
        );
    }
}
