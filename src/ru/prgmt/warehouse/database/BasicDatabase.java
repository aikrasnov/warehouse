package ru.prgmt.warehouse.database;

import ru.prgmt.warehouse.device.Device;
import ru.prgmt.warehouse.device.DeviceNotFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Базовый класс для БД, хранящей список устройств в памяти.
 */
public abstract class BasicDatabase implements Database {
    /**
     * Устройства, индексированные по инвентарному номеру
     */
    protected Map<Integer, Device> devices = new HashMap<>();

    @Override
    public Device find(int sku) throws DeviceNotFoundException {
        if (devices.containsKey(sku)) {
            return devices.get(sku);
        } else {
            throw new DeviceNotFoundException(sku);
        }
    }

    @Override
    public void delete(int sku) throws DeviceNotFoundException {
        if (null == devices.remove(sku)) {
            throw new DeviceNotFoundException(sku);
        }
    }

    @Override
    public void clear() {
        devices.clear();
    }

    @Override
    public void save(Device device) {
        devices.put(device.getSku(), device);
    }

    @Override
    public Iterator<Device> iterator() {
        return Collections.unmodifiableCollection(devices.values()).iterator();
    }
}
