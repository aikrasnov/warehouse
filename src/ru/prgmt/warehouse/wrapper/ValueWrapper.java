package ru.prgmt.warehouse.wrapper;

import java.text.ParseException;

/**
 * Интерфейс для обработчиков значений параметров и аргументов
 * @param <T> Тип обрабатываемого значения
 */
public interface ValueWrapper<T> {
    /**
     * Преобразовать строку в значение
     * @param value Строка
     * @return Значение
     * @throws ParseException Невозможно преобразовать указанную строку в значение
     */
    T unwrap(String value) throws ParseException;

    /**
     * Преобразовать указанное значение в строку
     * @param value Значение
     * @return Строка
     * @throws IllegalArgumentException Неовзможно преобразовать указанное значение в строку
     */
    String wrap(T value) throws IllegalArgumentException;
}
