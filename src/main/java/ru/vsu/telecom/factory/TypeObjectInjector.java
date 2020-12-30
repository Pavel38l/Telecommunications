package ru.vsu.telecom.factory;

import java.lang.reflect.Field;

public class TypeObjectInjector implements ObjectInjector {
    @Override
    public void inject(Object t) throws IllegalAccessException {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);
                Object object = ObjectFactory.getInstance().createObject(field.getType());
                field.set(t, object);
            }
        }
    }
}
