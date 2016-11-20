package ru.prgmt.warehouse.database.xml;

import ru.prgmt.warehouse.device.Device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Коренвой элемент для XML БД
 */
@XmlRootElement(name = "Database")
@XmlAccessorType(XmlAccessType.FIELD)
public class DatabaseRoot {
    @XmlElementWrapper(name = "Devices")
    List<Device> devices = new ArrayList<>();
}
