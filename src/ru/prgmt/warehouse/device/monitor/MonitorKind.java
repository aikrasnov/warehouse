package ru.prgmt.warehouse.device.monitor;

/**
 * Перечисление типов монитора
 */
public enum MonitorKind {
    TUBE ("ЭЛТ"),
    LCD ("ЖК"),
    PROJECTOR ("Проектор");

    String name;

    MonitorKind(String name) {
        this.name = name;
    }
}
