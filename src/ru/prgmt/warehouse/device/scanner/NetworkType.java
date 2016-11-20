package ru.prgmt.warehouse.device.scanner;

/**
 * перечисление с типами сетевого подключения для сканера
 */
public enum NetworkType {
    ETHERNET ("Ethernet"),
    WIFI ("WiFi");

    String name;

    NetworkType(String name) {
        this.name = name;
    }
}
