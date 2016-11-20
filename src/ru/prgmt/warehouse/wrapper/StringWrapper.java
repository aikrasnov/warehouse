package ru.prgmt.warehouse.wrapper;

import java.text.ParseException;

/**
 * Обработчик для строки
 */
public class StringWrapper implements ValueWrapper<String> {
    @Override
    public String unwrap(String value) throws ParseException {
        int newLinePos = value.indexOf('\n');
        if (newLinePos != -1) {
            throw new ParseException(value, newLinePos);
        }

        int lineFeedPos = value.indexOf('\r');
        if (lineFeedPos != -1) {
            throw new ParseException(value, lineFeedPos);
        }

        return value;
    }

    @Override
    public String wrap(String value) throws IllegalArgumentException {
        return value;
    }
}
