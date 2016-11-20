package ru.prgmt.warehouse.command;

/**
 * Исколючение, выбрасываемое при ошибке выполнения команды
 */
public class CommandException extends Exception {
    /**
     * Конструктор
     * @param message Сообщение ошибки
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Конструктор
     * @param cause Исключение-источник ошибки
     */
    public CommandException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
