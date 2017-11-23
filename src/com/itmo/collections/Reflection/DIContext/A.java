package com.itmo.collections.Reflection.DIContext;

public class A {
    private String str;

    @Resource (type = Impl.class)
    private I i;

    public I getI(){
        return i;
    }
}
