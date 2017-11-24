package com.itmo.collections.Reflection.DIContext;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DIContext {

    private final Map<Class<?>, Object> instances = new HashMap<>();


    public <T> T get(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class<T> aClass = Class.forName(className);

        T obj = getObject(aClass);

        return obj;
    }

    public <T> T getObject(Class<T> aClass) throws IllegalAccessException, InstantiationException {

        T object = aClass.newInstance();

        for (Field field : aClass.getDeclaredFields()) {

            field.setAccessible(true);
            Resource res = field.getAnnotation(Resource.class);
            if (res != null){
                boolean singleton = res.singleton();
                Class<?> type = res.type();

                Class<?> innerClass = type == Object.class ? res.getClass() : type;

                Object innerObject = getInstance(singleton, innerClass);
                field.set(object, innerObject);
            }

        }

        return (T) object;
    }

    private Object getInstance(boolean singleton, Class<?> type) throws InstantiationException, IllegalAccessException {
        Object dependency = singleton && instances.containsKey(type)
                ? instances.get(type)
                : getObject(type);

        if (singleton)
            instances.putIfAbsent(type, dependency);

        return dependency;
    }

}
