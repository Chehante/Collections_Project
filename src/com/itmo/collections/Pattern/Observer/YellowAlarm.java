package com.itmo.collections.Pattern.Observer;

public class YellowAlarm implements Alarm{

    boolean needToNotify = true;

    @Override
    public void tempChanged(int temp) {
        if (temp >= 300) {
            if (needToNotify) {
                needToNotify = false;
                System.out.println("YELLOW LEVEL!!!");
            }
        }
        else
            needToNotify = true;
    }
}
