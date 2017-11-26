package com.itmo.collections.Pattern.Observer;

import java.util.ArrayList;
import java.util.List;

public class Sensor {

    List<Alarm> listeners = new ArrayList<>();

    public void subscribe(Alarm alarm){
        listeners.add(alarm);
    }

    public void unsubscribe(Alarm alarm){
        listeners.remove(alarm);
    }

    public void notifyListeners(int temp){
        for (Alarm listener : listeners) {
            listener.tempChanged(temp);
        }

    }

    public static void main(String[] args) {
        Sensor snsr = new Sensor();

        Alarm green = new GreenAlarm();
        snsr.subscribe(green);

        Alarm yellow = new YellowAlarm();
        snsr.subscribe(yellow);

        Alarm red = new RedAlarm();
        snsr.subscribe(red);

        for (int i = 0; i < 650; i++) {
            snsr.notifyListeners(i);
        }

        for (int i = 200; i < 650; i++) {
            snsr.notifyListeners(i);
        }
    }

}
