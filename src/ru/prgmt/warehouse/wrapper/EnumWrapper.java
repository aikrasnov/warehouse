package ru.prgmt.warehouse.wrapper;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

/**
 * Обработчик для закрытого набора значений в виде перечисления Java (Enumeration) для одного значения
 * @param <T> Тип перечисления Java
 */
public class EnumWrapper<T extends Enum<T>> implements ValueWrapper<T> {
    private Class<T> enumClass;
    private Set<String> choices;

    public EnumWrapper(Class<T> enumClass) {
        this.enumClass = enumClass;

        T[] values = enumClass.getEnumConstants();
        choices = new HashSet<>(values.length);

        for (T value : values) {
            choices.add(value.name());
        }
    }

    @Override
    public T unwrap(String value) throws ParseException {
        value = value.toUpperCase();

        if (!choices.contains(value)) {
            throw new ParseException(value, 0);
        }

        return Enum.valueOf(enumClass, value);
    }

    @Override
    public String wrap(T value) throws IllegalArgumentException {
        return value.name();
    }
}
