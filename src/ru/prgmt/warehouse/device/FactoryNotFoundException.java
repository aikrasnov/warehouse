package ru.prgmt.warehouse.device;

/**
 * Исключение, выбрасываемое при попытке получения незарегистрированной фабрики
 */
public class FactoryNotFoundException extends RuntimeException {
    /**
     * Конструктор
     * @param typeId Идентификатор типа устройства
     */
    public FactoryNotFoundException(String typeId) {
        super("Фабрика для устройства с типом " + typeId + " не найдена");
    }

    /**
     * Конструктор
     * @param deviceClass Java тип устройства
     */
    public FactoryNotFoundException(Class<? extends Device> deviceClass) {
        super("Фабрика для устройства с типом " + deviceClass.getSimpleName() + " не найдена");
    }
}
