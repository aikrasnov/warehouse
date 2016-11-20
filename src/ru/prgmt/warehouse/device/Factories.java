package ru.prgmt.warehouse.device;

import ru.prgmt.warehouse.context.Context;
import ru.prgmt.warehouse.context.ContextAware;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Реестр фабрик устройств
 */
public class Factories implements Iterable<DeviceFactory<?>>, ContextAware {
    /**
     * Словарь по идентификаторам типов фабрик
     */
    private Map<String, DeviceFactory<?>> idMapping = new HashMap<>();
    /**
     * Словать по Java типам фабрик
     */
    private Map<Class<? extends Device>, DeviceFactory<?>> typeMapping = new HashMap<>();

    @Override
    public void setContext(Context context) {
        for (DeviceFactory<?> factory : idMapping.values()) {
            if (factory instanceof ContextAware) {
                ((ContextAware) factory).setContext(context);
            }
        }
    }

    @Override
    public Iterator<DeviceFactory<?>> iterator() {
        return Collections.unmodifiableCollection(idMapping.values()).iterator();
    }

    /**
     * Получить фабрику устройств по идентификатору типа
     * @param typeId Идентификатор типа
     * @return Фабрика устройств
     * @throws FactoryNotFoundException Фабрика для указанного идентификатора типа не зарегистрирована
     */
    public DeviceFactory get(String typeId) throws FactoryNotFoundException {
        DeviceFactory<?> factory = idMapping.get(typeId);

        if (factory != null) {
            return factory;
        } else {
            throw new FactoryNotFoundException(typeId);
        }
    }

    /**
     * Получить фабрику устройств по Java типу устройства
     * @param deviceClass Java тип устройства
     * @param <T> Java тип устройства
     * @return Фабрика устройств
     * @throws FactoryNotFoundException Фабрика для указанного Java типа не зарегистрирована
     */
    public <T extends Device> DeviceFactory<T> get(Class<T> deviceClass) throws FactoryNotFoundException {
        DeviceFactory<?> factory = typeMapping.get(deviceClass);

        if (factory != null) {
            return (DeviceFactory<T>) factory;
        } else {
            throw new FactoryNotFoundException(deviceClass);
        }
    }

    /**
     * Зарегистрировать фабрику устройств
     * @param factory Фабрика устройств
     */
    public void register(DeviceFactory<?> factory) {
        idMapping.put(factory.typeId(), factory);
        typeMapping.put(factory.deviceClass(), factory);
    }
}
