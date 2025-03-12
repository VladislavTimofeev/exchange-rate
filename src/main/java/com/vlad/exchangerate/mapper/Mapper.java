package com.vlad.exchangerate.mapper;

public interface Mapper<F, T> {
    T map(F object);
}
