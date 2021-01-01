package ru.vsu.telecom.factory;

import org.reflections.Reflections;

import java.util.Collection;

/**
 * Responsible for implementing classes for interfaces
 * @author Pavel_Burdyug
 */
public interface AllImplConfig {
    /**
     * Return classes of interface implementation
     * @param ifc interface class
     * @param <T> interface subtype
     * @return classes of interface implementation
     */
    <T> Collection<Class<? extends T>> getImplClasses(Class<T> ifc);

    /**
     * Return classes of interface implementation, search for implementations in specified package
     * @param ifc interface class
     * @param packagePath paths to package for scan
     * @param <T> interface subtype
     * @return classes of interface implementation, search for implementations in specified package
     */
    <T> Collection<Class<? extends T>> getImplClasses(Class<T> ifc, String[] packagePath);

    Reflections getScanner();
}
