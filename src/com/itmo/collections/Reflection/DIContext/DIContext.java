package com.itmo.collections.Reflection.DIContext;

import java.lang.reflect.Field;

public class DIContext {

    public Object get(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class<?> aClass = Class.forName(className);

        Object obj = getObject(aClass);

        return aClass.newInstance();
    }

    public Object getObject(Class<?> aClass) throws IllegalAccessException, InstantiationException {

        Object object = aClass.newInstance();

        for (Field field : aClass.getDeclaredFields()) {

            field.setAccessible(true);
            Resource res = field.getAnnotation(Resource.class);
            if (res != null){
                Class<?> innerClass = res.getClass();
                Object innerObject = getObject(innerClass);
                field.set(object, innerObject);
            }

        }

        return object;
    }

}
