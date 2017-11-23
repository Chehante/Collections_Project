package com.itmo.collections.Reflection.reflect;

public class Bean {
    private final int val;
    private String mystr;

    public Bean(int val, String mystr) {
        this.val = val;
        this.mystr = mystr;
    }

    public int getVal() {
        return val;
    }

    public String getMystr() {
        return mystr;
    }

    public void setMystr(String mystr) {
        this.mystr = mystr;
    }

    private void callme() {
        System.out.println("Private method called");
    }

    @Override
    public String toString() {
        return "Bean{" +
                "val=" + val +
                ", mystr='" + mystr + '\'' +
                '}';
    }
}
