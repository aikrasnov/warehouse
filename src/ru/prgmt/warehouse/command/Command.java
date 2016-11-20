package ru.prgmt.warehouse.command;

import ru.prgmt.warehouse.context.Context;
import ru.prgmt.warehouse.context.Parameters;

import java.util.Map;

/**
 * Базовый интерфейс команды
 */
public interface Command {
    /**
     * Название команды
     * @return Название
     */
    String name();

    /**
     * Зарегистрировать параметры команды в указанном наборе параметров
     * @param parameters Набор параметров
     * @param context Конекст исполнения
     */
    void register(Parameters parameters, Context context);

    /**
     * Выполнить команду с указанными параметрами
     * @param parameters Набор параметров
     * @param context Конекст исполнения
     * @throws CommandException Произошла ошибка при выполнении команды
     */
    void execute(Map<String, Object> parameters, Context context) throws CommandException;
}
