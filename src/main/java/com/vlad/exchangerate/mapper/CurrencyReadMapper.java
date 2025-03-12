package com.vlad.exchangerate.mapper;

import com.vlad.exchangerate.dto.CurrencyDto;
import com.vlad.exchangerate.entity.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyReadMapper implements Mapper<Currency, CurrencyDto> {

    @Override
    public CurrencyDto map(Currency object) {
        return new CurrencyDto(
                object.getCode(),
                object.getName(),
                object.getRate()
        );
    }
}
