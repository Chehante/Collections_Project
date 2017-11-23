package com.itmo.collections.Reflection;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Utils {

    public static String toString(Object o) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();

        return toString(o, stringBuilder);
    }

    public static String toString(Object o, StringBuilder stringBuilder) throws IllegalAccessException {

        Class<?> aClass = o.getClass();

        stringBuilder.append(aClass.getSimpleName()).append("{");

        for (Field field : aClass.getDeclaredFields()) {
            field.setAccessible(true);
            Exclude excl = field.getAnnotation(Exclude.class);
            if (excl == null){
                if (field.getType().isPrimitive())
                stringBuilder.append(field.getName()).append(" = ").append(field.get(o).toString()).append(";");
                else if (field.getType() == String.class)
                    stringBuilder.append(field.getName()).append(" = ").append(field.get(o)).append(";");
                else if (field.getType() == HashMap.class)
                    continue;
                else {
                    stringBuilder.append("\n").append("  ");
                    toString(field.get(o), stringBuilder);
                }
            }
        }

        stringBuilder.append("}");
        return stringBuilder.toString();

    }

}

