package ru.prgmt.warehouse.io;

import ru.prgmt.warehouse.wrapper.ValueWrapper;

import java.text.ParseException;

public interface PromptInput {
    <T> T required(String message, ValueWrapper<T> parser) throws ParseException;
    <T> T optional(String message, ValueWrapper<T> parser);
}
