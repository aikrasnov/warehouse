package ru.prgmt.warehouse.command;

import ru.prgmt.warehouse.context.Context;
import ru.prgmt.warehouse.context.ContextAware;
import ru.prgmt.warehouse.util.Joiner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Реестр команд
 */
public class Commands implements Iterable<Command>, ContextAware {
    private Map<String, Command> mapping = new HashMap<>();

    @Override
    public Iterator<Command> iterator() {
        return Collections.unmodifiableCollection(mapping.values()).iterator();
    }

    @Override
    public void setContext(Context context) {
        for (Command command : mapping.values()) {
            if (command instanceof ContextAware) {
                ((ContextAware) command).setContext(context);
            }
        }
    }

    /**
     * Получить команду с указанным названием
     * @param name Название команды
     * @return Обработчик команды
     * @throws CommandException Указанная команда не найдена
     */
    public Command get(String name) throws CommandException {
        if (name == null || !mapping.containsKey(name)) {
            throw new CommandException(
                    "Команда не указана или указана некорректно. Доступные команды: " +
                            Joiner.on(", ").join(mapping.keySet()));
        }

        return mapping.get(name);
    }

    /**
     * Зарегистрировать команду
     * @param command Обработчик команды
     */
    public void register(Command command) {
        mapping.put(command.name(), command);
    }
}
