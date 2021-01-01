package ru.vsu.telecom.factory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;

public class TypeObjectInjector implements ObjectInjector {
    private AllImplConfig allImplConfig;

    public TypeObjectInjector() {
        this.allImplConfig = new AllImplScannerConfig(new String[] {"ru.vsu.telecom"});
    }
    @Override
    public void inject(Object t) throws IllegalAccessException {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);
                Object object;
                // read packages paths
                if(t.getClass().isAnnotationPresent(Configuration.class)) {
                    String[] packagesPath = t.getClass().getAnnotation(Configuration.class).packagePaths();
                    object = ObjectFactory.getInstance().createObject(field.getType(), packagesPath);
                } else {
                    object = ObjectFactory.getInstance().createObject(field.getType());
                }
                // For collection field
                if (object instanceof Collection) {
                    // get parametrized type
                    Type suitableType = field.getGenericType();
                    ParameterizedType aType = (ParameterizedType) suitableType;
                    Type[] fieldArgTypes = aType.getActualTypeArguments();
                    suitableType = fieldArgTypes[0];
                    // get all implementations
                    Collection<Class<?>> implClasses;
                    if(t.getClass().isAnnotationPresent(Configuration.class)) {
                        String[] packagesPath = t.getClass().getAnnotation(Configuration.class).packagePaths();
                        implClasses = allImplConfig.getImplClasses((Class<Object>) suitableType, packagesPath);
                    } else {
                        implClasses = allImplConfig.getImplClasses((Class<Object>) suitableType);
                    }
                    ((Collection<Object>) object).addAll(
                            implClasses.stream().map(impl -> ObjectFactory.getInstance().createObject(impl)).collect(Collectors.toList())
                    );
                }
                field.set(t, object);
            }
        }
    }
}
