package ru.vsu.telecom.factory;

/**
 * @author Pavel_Burdyug
 * Configures objects injecting annotated fields
 */
public interface ObjectInjector {
    /**
     * Injects annotated object fields
     * @param t object to configure
     */
    void inject(Object t) throws IllegalAccessException;
}
