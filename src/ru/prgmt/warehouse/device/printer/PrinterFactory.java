package ru.prgmt.warehouse.device.printer;

import ru.prgmt.warehouse.context.Parameters;
import ru.prgmt.warehouse.device.DeviceFactory;
import ru.prgmt.warehouse.wrapper.BooleanWrapper;

import java.util.Map;

/**
 * Реализация фабрики устройств для принтера
 */
public class PrinterFactory extends DeviceFactory<Printer> {
    public PrinterFactory() {
        super("PRINTER", Printer.class);
    }

    @Override
    protected void register(Parameters parameters) {
        super.register(parameters);
        parameters.required("color", new BooleanWrapper());
        parameters.required("network", new BooleanWrapper());
    }

    @Override
    protected Printer create() {
        return new Printer();
    }

    @Override
    protected void initialize(Printer device, Map<String, Object> parameters) {
        super.initialize(device, parameters);
        device.color = (boolean) parameters.get("color");
        device.network = (boolean) parameters.get("network");
    }
}
