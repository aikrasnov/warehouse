package ru.prgmt.warehouse.wrapper;

import java.text.ParseException;

/**
 * Обработчик для целого числа
 */
public class IntegerWrapper implements ValueWrapper<Integer> {
    @Override
    public Integer unwrap(String value) throws ParseException {
        try {
            int result = Integer.parseInt(value);

            if (result < 0) {
                throw new ParseException(value, 0);
            }

            return result;

        } catch (NumberFormatException e) {
            throw new ParseException(value, 0);
        }
    }

    @Override
    public String wrap(Integer value) throws IllegalArgumentException {
        return value.toString();
    }
}
