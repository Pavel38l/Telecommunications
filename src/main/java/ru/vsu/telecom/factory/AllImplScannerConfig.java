package ru.vsu.telecom.factory;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Collection;
import java.util.Set;

public class AllImplScannerConfig implements AllImplConfig {

    @Getter
    private Reflections scanner;

    public AllImplScannerConfig(String packagePath) {
        this.scanner = new Reflections(packagePath);
    }

    @Override
    public <T> Collection<Class<? extends T>> getImplClasses(Class<T> ifc) {
        Set<Class<? extends T>> impls = scanner.getSubTypesOf(ifc);
        if (impls.size() < 1) {
            throw new RuntimeException(ifc + " don't have implementations!");
        }
        return impls;
    }
}
