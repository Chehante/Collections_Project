package com.itmo.collections.Reflection.DIContext;

public @interface Resource {
    Class<?> type() default Object.class;
}
