package ru.prgmt.warehouse.context;

import ru.prgmt.warehouse.command.Command;
import ru.prgmt.warehouse.command.CommandException;
import ru.prgmt.warehouse.command.Commands;
import ru.prgmt.warehouse.database.Database;
import ru.prgmt.warehouse.database.dummy.NullDatabase;
import ru.prgmt.warehouse.device.Factories;
import ru.prgmt.warehouse.io.ArgumentInput;
import ru.prgmt.warehouse.io.CommandInput;
import ru.prgmt.warehouse.io.NullInput;
import ru.prgmt.warehouse.io.PromptInput;

import java.io.PrintStream;
import java.text.ParseException;
import java.util.Map;

/**
 * Контекст исполнения команд
 */
public class Context {
    /**
     * Источник ввода команд
     */
    private CommandInput commandInput = NullInput.INSTANCE;
    /**
     * Источник ввода аргументов
     */
    private ArgumentInput argumentInput = NullInput.INSTANCE;
    /**
     * Источник ввода запросов
     */
    private PromptInput promptInput = NullInput.INSTANCE;
    /**
     * Поток для вывода сообщений
     */
    private PrintStream output = System.out;
    /**
     * База данных
     */
    private Database database = new NullDatabase();
    /**
     * Реестр фабрик устройств
     */
    private Factories factories = new Factories();
    /**
     * Реестр команд
     */
    private Commands commands = new Commands();

    /**
     * Выполнить команду, используя текущее состояние контекста
     * @throws CommandException Произошла ошибка при выполнении команды
     */
    public void execute() throws CommandException {
        String commandName = commandInput.command();
        Command command = commands.get(commandName);

        Parameters parameters = new Parameters();
        command.register(parameters, this);

        try {
            Map<String, Object> parsedArgs = parameters.parse(argumentInput);
            command.execute(parsedArgs, this);

        } catch (ParseException e) {
            throw new CommandException(e);

        } finally {
            try {
                database.close();
            } catch (Exception e) {
                throw new CommandException(e);
            }
        }
    }

    /**
     * Получить источник ввода аргументов
     * @return источник ввода
     */
    public ArgumentInput argumentInput() {
        return argumentInput;
    }

    /**
     * Получить источник ввода запросов
     * @return Источник ввода
     */
    public PromptInput promptInput() {
        return promptInput;
    }

    /**
     * Получить поток вывода сообщений
     * @return Поток вывода
     */
    public PrintStream output() {
        return output;
    }

    /**
     * Получить БД
     * @return БД
     */
    public Database database() {
        return database;
    }

    /**
     * Получить реестр фабрик устройств
     * @return Реестр
     */
    public Factories factories() {
        return factories;
    }

    /**
     * Получить реестр команд
     * @return Реестр
     */
    public Commands commands() {
        return commands;
    }

    /**
     * Класс-сборщик контекста
     */
    public static class Builder {
        /**
         * Собираемый контекст
         */
        private Context context = new Context();

        /**
         * Собрать контекст
         * @return Контекст
         */
        public Context build() {
            Context result = context;
            context = new Context();

            if (result.commandInput instanceof ContextAware) {
                ((ContextAware) result.commandInput).setContext(result);
            }

            if (result.argumentInput instanceof ContextAware) {
                ((ContextAware) result.argumentInput).setContext(result);
            }

            if (result.promptInput instanceof ContextAware) {
                ((ContextAware) result.promptInput).setContext(result);
            }

            if (result.output instanceof ContextAware) {
                ((ContextAware) result.output).setContext(result);
            }

            if (result.database instanceof ContextAware) {
                ((ContextAware) result.database).setContext(result);
            }

            result.factories.setContext(result);
            result.commands.setContext(result);

            return result;
        }

        /**
         * Изменить источник ввода команд
         * @param input Новый источник ввода
         * @return Этот класс (для цепных методов)
         */
        public Builder with(CommandInput input) {
            context.commandInput = input;
            return this;
        }

        /**
         * Изменить источник ввода аргументов
         * @param input Новый источник ввода
         * @return Этот класс (для цепных методов)
         */
        public Builder with(ArgumentInput input) {
            context.argumentInput = input;
            return this;
        }

        /**
         * Изменить источник ввода запросов
         * @param input Новый источник ввода
         * @return Этот класс (для цепных методов)
         */
        public Builder with(PromptInput input) {
            context.promptInput = input;
            return this;
        }

        /**
         * Изменить поток вывода сообщений
         * @param stream новый поток вывода
         * @return Этот класс (для цепных методов)
         */
        public Builder with(PrintStream stream) {
            context.output = stream;
            return this;
        }

        /**
         * Изменить реализацию БД
         * @param database Новая реализация БД
         * @return Этот класс (для цепных методов)
         */
        public Builder with(Database database) {
            context.database = database;
            return this;
        }
    }
}
