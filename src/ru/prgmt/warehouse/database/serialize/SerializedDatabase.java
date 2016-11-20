package ru.prgmt.warehouse.database.serialize;

import ru.prgmt.warehouse.database.BasicDatabase;
import ru.prgmt.warehouse.device.Device;

import java.io.*;
import java.util.Map;

/**
 * Реализация БД, использующая для чтения и записи файла встроенные мехнизмы сериализации Java.
 */
public class SerializedDatabase extends BasicDatabase {
    /**
     * Путь к файлу БД
     */
    private File file;

    /**
     * Конструктор
     * @param file Путь к файлу БД
     */
    public SerializedDatabase(File file) {
        this.file = file;

        if (file.isFile()) {
            try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
                devices = (Map<Integer, Device>) stream.readObject();

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Не удалось прочитать файл БД", e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(devices);

        } catch (IOException e) {
            throw new RuntimeException("Не удалось записать файл БД", e);
        }
    }
}
