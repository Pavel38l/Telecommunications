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
     * @return classes collection for interface implementation
     */
    <T> Collection<Class<? extends T>> getImplClasses(Class<T> ifc);

    Reflections getScanner();
}
