package ru.prgmt.warehouse.wrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Обработчик для даты
 */
public class DateWrapper implements ValueWrapper<Date> {
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public Date unwrap(String value) throws ParseException {
        return format.parse(value);
    }

    @Override
    public String wrap(Date value) throws IllegalArgumentException {
        return format.format(value);
    }
}
