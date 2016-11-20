package ru.prgmt.warehouse;

import ru.prgmt.warehouse.command.Command;
import ru.prgmt.warehouse.command.CommandException;
import ru.prgmt.warehouse.context.Context;
import ru.prgmt.warehouse.database.serialize.SerializedDatabase;
import ru.prgmt.warehouse.device.DeviceFactory;
import ru.prgmt.warehouse.io.ArgumentInput;
import ru.prgmt.warehouse.io.CommandInput;
import ru.prgmt.warehouse.io.CommandLineInput;
import ru.prgmt.warehouse.io.PromptInput;

import java.io.File;
import java.util.ServiceLoader;

/**
 * Класс приложения для запуска команд
 */
public class Application {
    public static void main(String[] args) throws CommandException {
        try {
            Context.Builder builder = new Context.Builder();

            builder.with(new SerializedDatabase(new File("warehouse.db")));

            CommandLineInput input = new CommandLineInput(args);
            builder.with((CommandInput) input);
            builder.with((ArgumentInput) input);
            builder.with((PromptInput) input);

            Context context = builder.build();

            ServiceLoader<DeviceFactory> factoryLoader = ServiceLoader.load(DeviceFactory.class);
            for (DeviceFactory factory : factoryLoader) {
                context.factories().register(factory);
            }

            ServiceLoader<Command> commandLoader = ServiceLoader.load(Command.class);
            for (Command command : commandLoader) {
                context.commands().register(command);
            }

            context.execute();

        } catch (CommandException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
