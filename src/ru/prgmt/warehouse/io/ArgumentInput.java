package ru.prgmt.warehouse.io;

/**
 * Интерфейс для получения именованных аргументов
 */
public interface ArgumentInput {
    /**
     * Получить указанный аргумент
     * @param key Название
     * @return Значение аргумента, или {@code null} если не указан
     */
    String argument(String key);
}
