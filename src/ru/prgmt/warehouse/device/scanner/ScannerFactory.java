package ru.prgmt.warehouse.device.scanner;

import ru.prgmt.warehouse.context.Parameters;
import ru.prgmt.warehouse.device.DeviceFactory;
import ru.prgmt.warehouse.wrapper.BooleanWrapper;
import ru.prgmt.warehouse.wrapper.FlagsWrapper;

import java.util.Map;
import java.util.Set;

/**
 * Реализация фабрики устройств для сканера
 */
public class ScannerFactory extends DeviceFactory<Scanner> {
    public ScannerFactory() {
        super("SCANNER", Scanner.class);
    }

    @Override
    protected void register(Parameters parameters) {
        super.register(parameters);
        parameters.required("color", new BooleanWrapper());
        parameters.required("network", new FlagsWrapper<>(NetworkType.class));
    }

    @Override
    protected Scanner create() {
        return new Scanner();
    }

    @Override
    protected void initialize(Scanner device, Map<String, Object> parameters) {
        super.initialize(device, parameters);
        device.color = (boolean) parameters.get("color");
        device.network = (Set<NetworkType>) parameters.get("network");
    }
}
