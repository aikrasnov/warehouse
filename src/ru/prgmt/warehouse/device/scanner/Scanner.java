package ru.prgmt.warehouse.device.scanner;

import ru.prgmt.warehouse.device.Device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Set;

/**
 * Экземпляр устройства "Сканер"
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Scanner extends Device implements Serializable {
    /**
     * Признак цвета
     */
    boolean color;
    /**
     * Набор признаков наличия сетевых подключений
     */
    Set<NetworkType> network;

    @Override
    public String getTypeName() {
        return "Сканер";
    }

    @Override
    public String toString() {
        return super.toString() + " - " + (color ? "цветной" : "ч/б") + ", " + (network.isEmpty() ? "локальный" : "сетевой") + formatNetworkFlags(network);
    }

    private static String formatNetworkFlags(Set<NetworkType> networkTypes) {
        StringBuilder sb = new StringBuilder();

        for (NetworkType networkType : networkTypes) {
            sb.append(", с ");
            sb.append(networkType.name);
        }

        return sb.toString();
    }

}
