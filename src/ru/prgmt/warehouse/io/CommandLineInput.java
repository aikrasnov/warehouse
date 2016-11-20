package ru.prgmt.warehouse.io;

import ru.prgmt.warehouse.command.CommandException;
import ru.prgmt.warehouse.wrapper.ValueWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация ввода команд, аргументов, и запросов с помощью системной командной строки
 */
public class CommandLineInput implements CommandInput, ArgumentInput, PromptInput {
    private String command;
    private Map<String, String> values;

    /**
     * Конструктор
     * @param arguments Аргументы командной строки
     */
    public CommandLineInput(String[] arguments) throws CommandException {
        this.values = new HashMap<>(arguments.length);

        int index = 0;
        while (index < arguments.length) {
            String arg = arguments[index];

            if (arg.startsWith("--")) {
                if (index < (arguments.length - 1)) {
                    values.put(arg.substring(2), arguments[++index]);
                } else {
                    throw new CommandException("Не указано значение для аргумента " + arg);
                }

            } else if (command == null) {
                command = arg;

            } else {
                throw new CommandException("Некорректный аргумент: " + arg);
            }

            index++;
        }
    }

    @Override
    public String command() {
        return command;
    }

    @Override
    public String argument(String key) {
        return values.get(key);
    }

    @Override
    public <T> T required(String message, ValueWrapper<T> parser) throws ParseException {
        return prompt(message, parser, true);
    }

    @Override
    public <T> T optional(String message, ValueWrapper<T> parser) {
        try {
            return prompt(message, parser, false);
        } catch (ParseException e) {
            throw new IllegalStateException("Should not happen", e);
        }
    }

    private <T> T prompt(String message, ValueWrapper<T> parser, boolean required) throws ParseException {
        System.out.print(message + ": ");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            return parser.unwrap(reader.readLine());

        } catch (ParseException e) {
            if (required) {
                throw e;
            } else {
                return null;
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("Не удалось прочитать ответ", e);
        }
    }
}
