package ru.prgmt.warehouse.device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Базовый класс для экземпляров устройств
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Device implements Serializable {
    /**
     * Инвентарный номер
     */
    int sku;
    /**
     * Название
     */
    String name;
    /**
     * Дата ввода в эксплуатацию
     */
    Date date;
    /**
     * Получить понятное для типа устройства
     * @return Тип устройства
     */
    public abstract String getTypeName();

    /**
     * Получить понятное описание устройства
     * @return Описание устройства
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date) + ' ' + sku + ' ' + getTypeName() + ' ' + name;
    }

    /**
     * Получить инвентарный номер
     * @return Инвентарный номер
     */
    public Integer getSku() {
        return sku;
    }
}
