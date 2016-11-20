package ru.prgmt.warehouse.wrapper;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Обработчик для открытого набора значений
 */
public class ChoiceWrapper implements ValueWrapper<String> {
    private Set<String> choices;

    public ChoiceWrapper(Collection<String> choices) {
        this.choices = new HashSet<>(choices.size());

        for (String choice : choices) {
            this.choices.add(choice.toUpperCase());
        }
    }

    public ChoiceWrapper(String... choices) {
        this(Arrays.asList(choices));
    }

    @Override
    public String unwrap(String value) throws ParseException {
        value = value.toUpperCase();

        if (!choices.contains(value)) {
            throw new ParseException(value, 0);
        }

        return value;
    }

    @Override
    public String wrap(String value) throws IllegalArgumentException {
        return value;
    }
}
