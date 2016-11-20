package ru.prgmt.warehouse.database;

import ru.prgmt.warehouse.device.Device;
import ru.prgmt.warehouse.device.DeviceNotFoundException;

/**
 * Интерфейс БД для хранения и операций над устройствами
 */
public interface Database extends Iterable<Device>, AutoCloseable {
    /**
     * Найти устройство по инвентарному номеру
     * @param sku Инвентарный номер
     * @return Экземпляр устройства
     * @throws DeviceNotFoundException Устройство с указанным инвентарным номером не найдено
     */
    Device find(int sku) throws DeviceNotFoundException;

    /**
     * Удалить устройство по инвентарному номеру
     * @param sku Инвентарный номер
     * @throws DeviceNotFoundException Устройство с указанным инвентарным номером не найдено
     */
    void delete(int sku) throws DeviceNotFoundException;

    /**
     * Удалить все записи
     */
    void clear();

    /**
     * Сохранить устройство в БД
     * @param device Экземпляр устройства
     */
    void save(Device device);
}
