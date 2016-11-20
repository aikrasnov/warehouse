package ru.prgmt.warehouse.command;

import ru.prgmt.warehouse.context.Context;
import ru.prgmt.warehouse.context.Parameters;
import ru.prgmt.warehouse.device.Device;
import ru.prgmt.warehouse.device.DeviceFactory;
import ru.prgmt.warehouse.wrapper.ChoiceWrapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Реализация команды "Добавить устройство"
 */
public class AddCommand implements Command {
    @Override
    public String name() {
        return "add";
    }

    @Override
    public void register(Parameters parameters, Context context) {
        List<String> typeIds = new ArrayList<>();

        for (DeviceFactory<?> factory : context.factories()) {
            typeIds.add(factory.typeId());
        }

        parameters.required("type", new ChoiceWrapper(typeIds));
    }

    @Override
    public void execute(Map<String, Object> parameters, Context context) throws CommandException {
        String typeId = (String) parameters.get("type");
        DeviceFactory<Device> factory = context.factories().get(typeId);

        try {
            Parameters deviceParams = factory.newParameters();
            Map<String, Object> deviceAttrs = deviceParams.parse(context.argumentInput());
            Device device = factory.newDevice(deviceAttrs);

            context.database().save(device);

        } catch (ParseException e) {
            throw new CommandException(e);
        }
    }
}
