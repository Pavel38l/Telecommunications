package ru.vsu.telecom.data.dbloader.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * Class for marshalling and unmarshalling {@link LocalDate}
 * @author Burdyug Pavel
 */

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    /**
     * Returns LocalDate from xml
     * @param v xml string
     * @return LocalDate
     */
    public LocalDate unmarshal(String v) {
        return LocalDate.parse(v);
    }

    /**
     * Returns xml string of LocalDate
     * @param v LocalDate to marshall
     * @return xml string of LocalDate
     */
    public String marshal(LocalDate v)  {
        return v.toString();
    }
}