package ru.prgmt.warehouse.command;

import ru.prgmt.warehouse.context.Context;
import ru.prgmt.warehouse.context.Parameters;
import ru.prgmt.warehouse.device.DeviceNotFoundException;
import ru.prgmt.warehouse.wrapper.ChoiceWrapper;
import ru.prgmt.warehouse.wrapper.IntegerWrapper;

import java.util.Map;

/**
 * Реализация команды "Удалить устройство"
 */
public class DeleteCommand implements Command {
    @Override
    public String name() {
        return "delete";
    }

    @Override
    public void register(Parameters parameters, Context context) {
        parameters.optional("sku", new IntegerWrapper());
    }

    @Override
    public void execute(Map<String, Object> parameters, Context context) throws CommandException {
        Integer sku = (Integer) parameters.get("sku");

        if (sku != null) {
            try {
                context.database().delete(sku);
            } catch (DeviceNotFoundException e) {
                throw new CommandException(e);
            }

        } else {
            String choice = context.promptInput().optional(
                    "Не указан инвентарный номер, - вы действительно хотите очистить всю БД? (y или yes)",
                    new ChoiceWrapper("y", "yes")
            );

            if (choice != null) {
                context.database().clear();
            }
        }
    }
}
