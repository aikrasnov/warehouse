package ru.prgmt.warehouse.io;

import ru.prgmt.warehouse.wrapper.ValueWrapper;

import java.text.ParseException;

/**
 * Реализация ввода команд, аргументов, и запросов, не возвращающая ничего ("заглушка")
 */
public class NullInput implements CommandInput, ArgumentInput, PromptInput {
    public static final NullInput INSTANCE = new NullInput();

    @Override
    public String command() {
        return null;
    }

    @Override
    public String argument(String key) {
        return null;
    }

    @Override
    public <T> T required(String message, ValueWrapper<T> parser) throws ParseException {
        throw new ParseException("Не поддерживается", 0);
    }

    @Override
    public <T> T optional(String message, ValueWrapper<T> parser) {
        return null;
    }
}
