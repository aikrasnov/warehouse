package ru.prgmt.warehouse.database.xml;

import ru.prgmt.warehouse.context.Context;
import ru.prgmt.warehouse.context.ContextAware;
import ru.prgmt.warehouse.database.BasicDatabase;
import ru.prgmt.warehouse.device.Device;
import ru.prgmt.warehouse.device.DeviceFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Реализация БД, использующая в качестве хранилища файл XML.</p>
 * <p>Не тестировалась на работоспособность.</p>
 */
public class XmlDatabase extends BasicDatabase implements ContextAware {
    private File file;
    private JAXBContext context;

    /**
     * Конструктор
     * @param file Путь к файлу БД
     */
    public XmlDatabase(File file) {
        this.file = file;
    }

    @Override
    public void setContext(Context context) {
        List<Class<?>> deviceClasses = new ArrayList<>();
        for (DeviceFactory<?> factory : context.factories()) {
            deviceClasses.add(factory.deviceClass());
        }

        try {
            this.context = JAXBContext.newInstance(deviceClasses.toArray(new Class[deviceClasses.size()]));
        } catch (JAXBException e) {
            throw new RuntimeException("Не удалось обработать типы устройств", e);
        }

        if (file.isFile()) {
            try {
                Unmarshaller unmarshaller = this.context.createUnmarshaller();
                DatabaseRoot root = (DatabaseRoot) unmarshaller.unmarshal(file);
                devices = new HashMap<>(root.devices.size());

                for (Device device : root.devices) {
                    devices.put(device.getSku(), device);
                }

            } catch (JAXBException e) {
                throw new RuntimeException("Не удалось прочитать файл БД", e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        DatabaseRoot root = new DatabaseRoot();
        root.devices.addAll(devices.values());

        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(root, file);
    }

}
