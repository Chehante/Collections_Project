package com.itmo.collections.Reflection.DIContext;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DIContext {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    private static final DIContext INSTANCE = new DIContext();

    private DIContext(){}

    static DIContext getInstance() {
        return INSTANCE;
    }

    public <T> T get(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class<T> aClass = (Class<T>) Class.forName(className);

        return getObject(aClass, false);
    }

    private <T> T getObject(Class<T> clazz, boolean singleton) {
        Object obj;

        try {
            obj = getInstance(singleton, clazz);

            inject(obj);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(clazz.getName(), e);
        }

        return (T) obj;
    }

    private void inject(Object obj) {
        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            Resource res = field.getAnnotation(Resource.class);

            if (res != null) {
                boolean singleton = res.singleton();

                Class<?> type = res.type() == Object.class ? field.getType() : res.type();

                inject(obj, singleton, type, field);
            }
        }
    }

    private void inject(Object obj, boolean singleton, Class<?> type, Field fld) {
        try {
            fld.setAccessible(true);

            Object dependency = getObject(type, singleton);

            fld.set(obj, dependency);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    private Object getInstance(boolean singleton, Class<?> type) throws InstantiationException, IllegalAccessException {
        Object dependency = singleton && instances.containsKey(type)
                ? instances.get(type)
                : type.newInstance();

        if (singleton)
            instances.putIfAbsent(type, dependency);

        return dependency;
    }

}
