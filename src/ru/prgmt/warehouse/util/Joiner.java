package ru.prgmt.warehouse.util;

/**
 * Вспомогательный класс для склеивания строк с помощью разделителя
 */
public class Joiner {
    /**
     * Разделитель
     */
    private String delimiter;

    /**
     * Конструктор
     * @param delimiter Разделитель
     */
    private Joiner(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Создать экземпляр класса с указанным разделителем
     * @param delimiter Разделитель
     * @return Экземпляр класса
     */
    public static Joiner on(String delimiter) {
        return new Joiner(delimiter);
    }

    /**
     * Создать экземпляр класса с указанным разделителем
     * @param delimier Разделитель
     * @return Экземпляр класса
     */
    public static Joiner on(char delimier) {
        return new Joiner("" + delimier);
    }

    /**
     * Склеить набор значаений, используя установленный разделитель и
     * указанный обработчик для извлечения строк.
     * @param iterable Набор значений
     * @param extractor Обработчик извлечения строк
     * @param <T> Тип элемента набора
     * @return Склееная строка
     */
    public <T> String join(Iterable<T> iterable, Extractor<T> extractor) {
        StringBuilder sb = new StringBuilder();

        for (T value : iterable) {
            sb.append(extractor.extract(value));
            sb.append(delimiter);
        }

        if (0 < sb.length()) {
            sb.delete(sb.length() - delimiter.length(), sb.length());
        }

        return sb.toString();
    }

    /**
     * Склеить набор значений, используя установленный разделитель и приводя каждое значение к строке
     * @param iterable Набор значений
     * @return Склееная строка
     */
    @SuppressWarnings("unchecked")
    public String join(Iterable<?> iterable) {
        return join(iterable, new Extractor() {
            @Override
            public String extract(Object value) {
                return String.valueOf(value);
            }
        });
    }

    /**
     * Обработчик извлечения строки
     * @param <T> Тип обрабатываемого значения
     */
    public interface Extractor<T> {
        /**
         * Извлечь строку из значения
         * @param value Значение
         * @return Извлечённая строка
         */
        String extract(T value);
    }
}
