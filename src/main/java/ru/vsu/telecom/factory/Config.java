package ru.vsu.telecom.factory;

import ru.vsu.telecom.data.util.InsertSorter;
import ru.vsu.telecom.data.util.QuickSorter;
import ru.vsu.telecom.data.util.Sorter;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for implementing for interfaces
 * @author Pavel_Burdyug
 */
public class Config {
    /**
     * key - interface
     * value - implementation
     */
    Map<Class, Class> ifc2ImplClass = new HashMap<>();
    public Config() {
        ifc2ImplClass.put(Sorter.class, QuickSorter.class);
    }

    /**
     * Return class of interface implementation
     * @param ifc interface class
     * @param <T> interface subtype
     * @return class of interface implementation
     */
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.get(ifc);
    }
}
