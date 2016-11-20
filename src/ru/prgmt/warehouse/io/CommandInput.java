package ru.prgmt.warehouse.io;

/**
 * Интерфейс для получения команды
 */
public interface CommandInput {
    /**
     * Получить название команды
     * @return Название команды
     */
    String command();
}
