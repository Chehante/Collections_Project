package com.itmo.collections.Pattern.Observer;

public class GreenAlarm implements Alarm{
    boolean needToNotify = true;

    @Override
    public void tempChanged(int temp) {
        if (temp >= 100) {
            if (needToNotify) {
                needToNotify = false;
                System.out.println("GREEN LEVEL!");
            }
        }
        else
            needToNotify = true;
    }
}
