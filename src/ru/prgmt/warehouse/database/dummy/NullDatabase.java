package ru.prgmt.warehouse.database.dummy;

import ru.prgmt.warehouse.database.Database;
import ru.prgmt.warehouse.device.Device;
import ru.prgmt.warehouse.device.DeviceNotFoundException;

import java.util.Collections;
import java.util.Iterator;

/**
 * <p>Реализация базы данных - "заглушка".</p>
 * <p>Она всегда пустая, операции изменения не делают ничего.</p>
 */
public class NullDatabase implements Database {
    @Override
    public Device find(int sku) throws DeviceNotFoundException {
        throw new DeviceNotFoundException(sku);
    }

    @Override
    public void delete(int sku) throws DeviceNotFoundException {
        throw new DeviceNotFoundException(sku);
    }

    @Override
    public void clear() {
    }

    @Override
    public void save(Device device) {
    }

    @Override
    public Iterator<Device> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public void close() throws Exception {
    }
}
