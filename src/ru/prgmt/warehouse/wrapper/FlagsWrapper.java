package ru.prgmt.warehouse.wrapper;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

/**
 * Обработчик для закрытого набора значений в виде перечисления Java (Enumeration) для множества значений
 * @param <T>
 */
public class FlagsWrapper<T extends Enum<T>> implements ValueWrapper<Set<T>> {
    private Set<String> flags;
    private Class<T> enumClass;

    public FlagsWrapper(Class<T> enumClass) {
        this.enumClass = enumClass;
        T[] values = enumClass.getEnumConstants();
        this.flags = new HashSet<>(values.length);

        for (T value : values) {
            this.flags.add(value.name());
        }
    }

    @Override
    public Set<T> unwrap(String value) throws ParseException {
        Set<T> result = new HashSet<>(flags.size());
        String[] items = value.toUpperCase().split("\\s*,\\s*");

        for (String item : items) {
            if (!item.isEmpty() && flags.contains(item)) {
                result.add(Enum.valueOf(enumClass, item));
            }
        }

        return result;
    }

    @Override
    public String wrap(Set<T> value) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();

        for (T item : value) {
            sb.append(item.name());
            sb.append(',');
        }

        if (!value.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}
