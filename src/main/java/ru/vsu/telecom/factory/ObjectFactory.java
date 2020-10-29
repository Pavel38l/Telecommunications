package ru.vsu.telecom.factory;

import lombok.SneakyThrows;

/**
 * Simple factory for creating objects
 * @author Pavel_Burdyug
 */
public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config;

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    private ObjectFactory() {
        config = new Config();
    }


    /**
     * Return an object of a class or its implementation
     * @param type type of created object
     * @param <T> subtype of created object
     * @return An object of a class or its implementation
     */
    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;
        if (implClass.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = implClass.getDeclaredConstructor().newInstance();
        return t;
    }
}
