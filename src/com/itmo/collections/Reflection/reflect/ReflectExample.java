package com.itmo.collections.Reflection.reflect;

import java.lang.reflect.*;

public class ReflectExample {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String className = args[0];

        Class<?> aClass = Class.forName(className);

        System.out.println("Loaded class " + aClass.getName());

        System.out.println("Declared fields: ========");

        for (Field field : aClass.getDeclaredFields()) {
            System.out.println(field.getName());
        }

        System.out.println("Declared methods: =======");

        Method m = null;

        for (Method method : aClass.getDeclaredMethods()) {
            System.out.println(method.getName());

            if (Modifier.isPrivate(method.getModifiers())
                    && method.getParameters().length == 0 && m == null)
                m = method;
        }

        try {
            Constructor<?> cons = aClass.getDeclaredConstructor(int.class, String.class);

            Object obj = cons.newInstance(10, "Hello, reflect!");

            if (m != null) {
                m.setAccessible(true);

                m.invoke(obj);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
