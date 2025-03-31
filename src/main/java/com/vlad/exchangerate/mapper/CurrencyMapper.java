package com.vlad.exchangerate.mapper;

import com.vlad.exchangerate.dto.CurrencyRequest;
import com.vlad.exchangerate.dto.CurrencyResponse;
import com.vlad.exchangerate.entity.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    Currency toEntity(CurrencyRequest currencyRequest);
    CurrencyResponse toResponse(Currency currency);
}
