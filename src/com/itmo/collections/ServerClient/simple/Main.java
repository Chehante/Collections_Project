package com.itmo.collections.ServerClient.simple;

import java.io.*;

/**
 * Created by xmitya on 27.11.16.
 */
public class Main {
    public static void main(String[] args) throws IOException,
            ClassNotFoundException {
        File statFile = new File("stat.bin");

        LaunchStat stat;

        // Если это первый запуск, то необходимо создать stat.
        if (!statFile.exists())
            stat = new LaunchStat();
        else {
            // А если повторный, то читаем с диска.
            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(statFile))) {
                stat = (LaunchStat) objIn.readObject();
            }
        }

        if (stat.isFirstLaunch())
            System.out.println("It's a first launch!");
        else
            System.out.println(stat);

        // Обновляем статистику.
        stat.update();

        // Сохраняем на диск1
        try (ObjectOutputStream objOut = new ObjectOutputStream(
                new FileOutputStream(statFile))) {
            objOut.writeObject(stat);
        }
    }
}
