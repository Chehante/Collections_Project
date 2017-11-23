package com.itmo.collections.Reflection.reflect.injector;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Injector {
    private final Map<Class<?>, Object> instances = new HashMap<>();

    private static final Injector INSTANCE = new Injector();

    public static Injector instance() {
        return INSTANCE;
    }

    private Injector() {
    }

    public <T> T getObject(Class<?> clazz, boolean singleton) {
        Object obj;

        try {
            obj = getInstance(singleton, clazz);

            inject(obj);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(clazz.getName(), e);
        }

        return (T) obj;
    }

    public void inject(Object obj) {
        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            Dependency depAn = field.getAnnotation(Dependency.class);

            if (depAn != null) {
                boolean singleton = depAn.singleton();

                Class<?> type = depAn.type() == Object.class ? field.getType() : depAn.type();

                inject(obj, singleton, type, field);
            }
        }
    }

    private void inject(Object obj, boolean singleton, Class<?> type, Field fld) {
        try {
            fld.setAccessible(true);

            Object dependency = getInstance(singleton, type);

            fld.set(obj, dependency);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    private Object getInstance(boolean singleton, Class<?> type) throws InstantiationException, IllegalAccessException {
        Object dependency = singleton && instances.containsKey(type)
                ? instances.get(type)
                : type.newInstance();

        instances.putIfAbsent(type, dependency);

        return dependency;
    }
}
