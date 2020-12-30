package ru.vsu.telecom.factory;

import org.reflections.Reflections;

/**
 * Responsible for implementing for interfaces
 * @author Pavel_Burdyug
 */
public interface Config {
    /**
     * Return class of interface implementation
     * @param ifc interface class
     * @param <T> interface subtype
     * @return class of interface implementation
     */
    <T> Class<? extends T> getImplClass(Class<T> ifc);

    Reflections getScanner();
}
