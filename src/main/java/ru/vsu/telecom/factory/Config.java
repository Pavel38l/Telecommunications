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

    /**
     * Return class of interface implementation, search for implementation in specified package
     * @param ifc interface class
     * @param packagePath path to package for scan
     * @param <T> interface subtype
     * @return class of interface implementation, search for implementation in specified package
     */
    <T> Class<? extends T> getImplClass(Class<T> ifc, String[] packagePath);



    Reflections getScanner();
}
