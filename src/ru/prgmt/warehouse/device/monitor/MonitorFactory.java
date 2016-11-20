package ru.prgmt.warehouse.device.monitor;

import ru.prgmt.warehouse.context.Parameters;
import ru.prgmt.warehouse.device.DeviceFactory;
import ru.prgmt.warehouse.wrapper.BooleanWrapper;
import ru.prgmt.warehouse.wrapper.EnumWrapper;
import ru.prgmt.warehouse.wrapper.IntegerWrapper;

import java.util.Map;

/**
 * Реализация фабрики устройств для монитора
 */
public class MonitorFactory extends DeviceFactory<Monitor> {
    public MonitorFactory() {
        super("MONITOR", Monitor.class);
    }

    @Override
    protected void register(Parameters parameters) {
        super.register(parameters);
        parameters.required("size", new IntegerWrapper());
        parameters.required("color", new BooleanWrapper());
        parameters.required("kind", new EnumWrapper<>(MonitorKind.class));
    }

    @Override
    protected Monitor create() {
        return new Monitor();
    }

    @Override
    protected void initialize(Monitor device, Map<String, Object> parameters) {
        super.initialize(device, parameters);
        device.size = (int) parameters.get("size");
        device.color = (boolean) parameters.get("color");
        device.kind = (MonitorKind) parameters.get("kind");
    }
}
