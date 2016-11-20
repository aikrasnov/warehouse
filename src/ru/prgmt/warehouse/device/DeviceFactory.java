package ru.prgmt.warehouse.device;

import ru.prgmt.warehouse.context.Parameters;
import ru.prgmt.warehouse.wrapper.DateWrapper;
import ru.prgmt.warehouse.wrapper.IntegerWrapper;
import ru.prgmt.warehouse.wrapper.StringWrapper;

import java.util.Date;
import java.util.Map;

/**
 * Базовый класс для фабрики устройств
 * @param <T> Тип устройства
 */
public abstract class DeviceFactory<T extends Device> {
    /**
     * Идентификатор типа устройства
     */
    private String typeId;

    /**
     * Java тип устройства
     */
    private Class<T> deviceClass;

    /**
     * Конуструктор
     * @param typeId Идентификатор типа устройства
     * @param deviceClass Java тип устройства
     */
    public DeviceFactory(String typeId, Class<T> deviceClass) {
        this.typeId = typeId;
        this.deviceClass = deviceClass;
    }

    /**
     * Получить идентификатор типа устройства
     * @return Идентификатор
     */
    public String typeId() {
        return typeId;
    }

    /**
     * Получить Java класс устройства
     * @return Java класс
     */
    public Class<T> deviceClass() {
        return deviceClass;
    }

    /**
     * Создать новый нобор параметров для данного устройства
     * @return Набор параметров
     */
    public Parameters newParameters() {
        Parameters parameters = new Parameters();
        register(parameters);

        return parameters;
    }

    /**
     * Создать новый экземпляр устройства на основе указанного набора параметров
     * @param parameters Набор параметров
     * @return Экземпляр устройства
     */
    public T newDevice(Map<String, Object> parameters) {
        T device = create();
        initialize(device, parameters);

        return device;
    }

    /**
     * Зеригстрировать необзодимые параметры в указанном наборе
     * @param parameters Набор параметров
     */
    protected void register(Parameters parameters) {
        parameters.required("sku", new IntegerWrapper());
        parameters.required("name", new StringWrapper());
        parameters.required("date", new DateWrapper());
    }

    /**
     * Создать новый экземпляр устройства
     * @return Экземпляр устройства
     */
    protected abstract T create();

    /**
     * Установить свойства экземпляра устройства из указанных значений параметров
     * @param device Экземпляр устройства
     * @param parameters Значения параметров
     */
    protected void initialize(T device, Map<String, Object> parameters) {
        device.sku = (int) parameters.get("sku");
        device.name = (String) parameters.get("name");
        device.date = (Date) parameters.get("date");
    }
}
