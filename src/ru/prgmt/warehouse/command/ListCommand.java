package ru.prgmt.warehouse.command;

import ru.prgmt.warehouse.context.Context;
import ru.prgmt.warehouse.context.Parameters;
import ru.prgmt.warehouse.device.Device;

import java.util.Map;

/**
 * Реализация команды "Удалить устройство"
 */
public class ListCommand implements Command {
    @Override
    public String name() {
        return "list";
    }

    @Override
    public void register(Parameters parameters, Context context) {
    }

    @Override
    public void execute(Map<String, Object> parameters, Context context) throws CommandException {
        for (Device device : context.database()) {
            context.output().println(device.toString());
        }
    }
}
