package ru.prgmt.warehouse.device;

/**
 * Исключение, выбрасываемое при операциях над несуществующим устройством
 */
public class DeviceNotFoundException extends Exception {
    /**
     * Конструктор
     * @param sku Инвентарный номер
     */
    public DeviceNotFoundException(int sku) {
        super("Указнный SKU (" + sku + ") не найден в БД");
    }
}
