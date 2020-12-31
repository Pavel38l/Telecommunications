package ru.vsu.telecom.factory;

import lombok.SneakyThrows;
import ru.vsu.telecom.data.util.QuickSorter;
import ru.vsu.telecom.data.util.Sorter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * Factory for creating objects
 * @author Pavel_Burdyug
 */
public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    private ScannerConfig config;
    private List<ObjectInjector> injectors = new ArrayList<>();

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    private ObjectFactory() {
        config = new ScannerConfig("ru.vsu.telecom", new HashMap<>(
                Map.of(Sorter.class, QuickSorter.class, List.class, ArrayList.class))
        );
        for (Class<? extends ObjectInjector> injector : config.getScanner().getSubTypesOf(ObjectInjector.class)) {
            try {
                injectors.add(injector.getDeclaredConstructor().newInstance());
            } catch (ReflectiveOperationException ex) {
                System.out.println(ex.getMessage());
            }

        }
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
        for (ObjectInjector objectInjector : injectors) {
            objectInjector.inject(t);
        }
        return t;
    }
}
