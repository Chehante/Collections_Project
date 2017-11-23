package com.itmo.collections.Reflection.reflect.strings;

import java.lang.reflect.Field;

public class Utils {
    public static String toString(Object o) throws IllegalAccessException {
        Class<?> clazz = o.getClass();

        StringBuilder sb = new StringBuilder();

        toString(clazz, sb, o);

        return sb.toString();
    }

    private static void toString(Class<?> clazz, StringBuilder sb, Object o) throws IllegalAccessException {
        sb.append("{").append(clazz.getSimpleName()).append("=");

        for (Field field : clazz.getDeclaredFields()) {
            sb.append(field.getName()).append("=");

            field.setAccessible(true);

            if (!field.getType().isPrimitive()) {
                Object objFld = field.get(o);

                if (objFld == null)
                    sb.append("null");
                else
                    sb.append(objFld.toString());
            }
            else
                sb.append(field.get(o));

            sb.append(";");
        }

        sb.append("}");
    }


}
