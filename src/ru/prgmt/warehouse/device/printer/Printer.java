package ru.prgmt.warehouse.device.printer;

import ru.prgmt.warehouse.device.Device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * Экземпляр устройства "Принтер"
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Printer extends Device implements Serializable {
    /**
     * Признак цвета
     */
    boolean color;
    /**
     * Признак сетевого подключения
     */
    boolean network;

    @Override
    public String getTypeName() {
        return "Принтер";
    }

    @Override
    public String toString() {
        return super.toString() + " - " + (color ? "цветной" : "ч/б") + ", " + (network ? "сетевой" : "локальный");
    }

}
