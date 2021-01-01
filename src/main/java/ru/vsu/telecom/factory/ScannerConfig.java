package ru.vsu.telecom.factory;

import lombok.Getter;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import ru.vsu.telecom.data.util.InsertSorter;
import ru.vsu.telecom.data.util.QuickSorter;
import ru.vsu.telecom.data.util.Sorter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Responsible for implementing for interfaces
 * @author Pavel_Burdyug
 */
public class ScannerConfig implements Config {
    /**
     * key - interface
     * value - implementation
     */
    private String[] packagePath;
    @Getter
    private Reflections scanner;
    private Map<Class, Class> ifc2ImplClass;

    public ScannerConfig(String[] packagePath, Map<Class, Class> ifc2ImplClass) {
        this.packagePath = packagePath;
        this.scanner = new Reflections(packagePath);
        this.ifc2ImplClass = ifc2ImplClass;
    }

    /**
     * Return class of interface implementation.
     * If don't have implementation scan package to find it and save in map
     * @param ifc interface class
     * @param <T> interface subtype
     * @return class of interface implementation
     */
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return getImplClass(ifc, packagePath);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc, String[] packagePath) {
        Reflections sc = new Reflections(packagePath);
        return ifc2ImplClass.computeIfAbsent(ifc, impl -> {
            Set<Class<? extends T>> impls = sc.getSubTypesOf(ifc);
            if (impls.size() != 1) {
                throw new RuntimeException(ifc + " don't have implementations or have more than 1!");
            }

            return impls.iterator().next();
        });
    }
}
