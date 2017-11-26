package com.itmo.collections.Pattern.Observer;

public class RedAlarm implements Alarm {
    boolean needToNotify = true;

    @Override
    public void tempChanged(int temp) {
        if (temp >= 600) {
            if (needToNotify) {
                needToNotify = false;
                System.out.println("<<<<<<<DANGER>>>>>>>");
            }
        }
        else
            needToNotify = true;
    }
}
