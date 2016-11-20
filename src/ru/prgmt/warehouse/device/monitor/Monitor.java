package ru.prgmt.warehouse.device.monitor;

import ru.prgmt.warehouse.device.Device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * Экземпляр устройства "Монитор"
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Monitor extends Device implements Serializable {
    /**
     * Размер
     */
    int size;
    /**
     * Признак цвета
     */
    boolean color;
    /**
     * Тип монитора
     */
    MonitorKind kind;

    @Override
    public String getTypeName() {
        return "Монитор";
    }

    @Override
    public String toString() {
        return super.toString() + " - " + (color ? "цветной" : "ч/б") + ' ' + kind.name + ", " + size + ' ' + formatUnit(size);
    }

    /**
     * Отформатировать единицу измерения
     * @param value Количество единиц
     * @return Отформатированная строка
     */
    private static String formatUnit(int value) {
        if (value > 10 && value < 20) {
            return "дюймов";
        }

        switch (value % 10) {
            case 0:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return "дюймов";

            case 1:
                return "дюйм";

            case 2:
            case 3:
            case 4:
                return "дюйма";

            default:
                throw new IllegalStateException("Should not happen");
        }
    }

}
