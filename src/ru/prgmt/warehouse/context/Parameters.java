package ru.prgmt.warehouse.context;

import ru.prgmt.warehouse.io.ArgumentInput;
import ru.prgmt.warehouse.util.Joiner;
import ru.prgmt.warehouse.wrapper.ValueWrapper;

import java.text.ParseException;
import java.util.*;

/**
 * <p>Набор параметров.</p>
 * <p>Позволяет решистрировать описания параметров и получать их преобразованные значения.</p>
 */
public class Parameters implements Iterable<Parameters.Parameter> {
    /**
     * Словарь параметров по ключу
     */
    private Map<String, Parameter> mapping = new HashMap<>();

    @Override
    public Iterator<Parameter> iterator() {
        return Collections.unmodifiableCollection(mapping.values()).iterator();
    }

    /**
     * Зарегистрировать обязательный параметр
     * @param key Ключ
     * @param parser Обработчик значения
     */
    public void required(String key, ValueWrapper<?> parser) {
        mapping.put(key, new Parameter(key, parser, true));
    }

    /**
     * Зарегистрировать необязательный параметр
     * @param key Ключ
     * @param parser Обработчик значения
     */
    public void optional(String key, ValueWrapper<?> parser) {
        mapping.put(key, new Parameter(key, parser, false));
    }

    /**
     * Получить преобразованные значения зарегистрированных параметров
     * @param source Источник значений
     * @return Словарь значений по ключу
     * @throws ParseException Некоторые значения не могут быть получены
     */
    public Map<String, Object> parse(ArgumentInput source) throws ParseException {
        Map<String, Object> result = new HashMap<>();
        Set<String> failedKeys = new HashSet<>();
        Set<String> absentKeys = new HashSet<>();

        for (Map.Entry<String, Parameter> entry : mapping.entrySet()) {
            Parameter parameter = entry.getValue();

            try {
                String rawValue = source.argument(entry.getKey());

                if (rawValue != null) {
                    Object parsedValue = parameter.parser.unwrap(rawValue);
                    result.put(entry.getKey(), parsedValue);

                } else if (parameter.required) {
                    absentKeys.add(entry.getKey());
                }

            } catch (ParseException e) {
                if (parameter.required) {
                    failedKeys.add(entry.getKey());
                }
            }
        }

        if (failedKeys.isEmpty() && absentKeys.isEmpty()) {
            return result;

        } else if (!absentKeys.isEmpty()) {
            throw new ParseException("Не указаны обязательные параметры: " + Joiner.on(", ").join(absentKeys), 0);

        } else {
            throw new ParseException("Указаны некорректные параметры: " + Joiner.on(", ").join(failedKeys), 0);
        }
    }

    /**
     * Представление параметра
     */
    public static class Parameter {
        /**
         * Ключ
         */
        private String key;
        /**
         * Обработчик значения
         */
        private ValueWrapper parser;
        /**
         * Признак обязательности
         */
        private boolean required;

        /**
         * Конструктор
         * @param key Ключ
         * @param parser Обработчик значения
         * @param required Признак обязательности
         */
        public Parameter(String key, ValueWrapper parser, boolean required) {
            this.key = key;
            this.parser = parser;
            this.required = required;
        }

        /**
         * Получить ключ
         * @return Ключ
         */
        public String getKey() {
            return key;
        }

        /**
         * Получить обработчик значения
         * @return Обработчик
         */
        public ValueWrapper getWrapper() {
            return parser;
        }

        /**
         * Получить признак обязательности
         * @return Признак
         */
        public boolean isRequired() {
            return required;
        }
    }
}
