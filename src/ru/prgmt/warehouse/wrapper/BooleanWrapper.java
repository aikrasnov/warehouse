package ru.prgmt.warehouse.wrapper;

import java.text.ParseException;

/**
 * Обработчик для логического значения
 */
public class BooleanWrapper implements ValueWrapper<Boolean> {
    @Override
    public Boolean unwrap(String value) throws ParseException {
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.valueOf(value);
        } else {
            throw new ParseException(value, 0);
        }
    }

    @Override
    public String wrap(Boolean value) throws IllegalArgumentException {
        return value.toString();
    }
}
