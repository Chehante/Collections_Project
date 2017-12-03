package com.itmo.multithreading.Sinchronized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TopWordsWithQueue {

    public static void main(String[] args) {

        String stop = "STOP";
        BlockingQueue blockingQueue = new ArrayBlockingQueue(10);
        BlockingQueue<HashMap<String, Integer>> finalQueue = new ArrayBlockingQueue<>(10);
        HashMap<String, Integer> mainMap = new HashMap<>();

        int cpuQuant = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < cpuQuant; i++) {
            Thread t = new TopMaker(blockingQueue, stop, finalQueue, cpuQuant);
            t.start();
        }

        List<String> lst = putToBlockingQue(blockingQueue, "src/com/itmo/multithreading/Sinchronized/WarAndPiece.txt", stop, cpuQuant);

        synchronized (finalQueue){
            try {
                finalQueue.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (HashMap<String, Integer> stringIntegerHashMap : finalQueue) {
            for (Map.Entry<String, Integer> entrySet : stringIntegerHashMap.entrySet()) {
                mainMap.merge(entrySet.getKey(), entrySet.getValue(), (integer, integer2) -> integer + integer2);
            }
        }


        List<Map.Entry<String, Integer>> ls = topTen(mainMap);

        for (Map.Entry<String, Integer> l : ls) {
            System.out.println(l.getKey() + " : " + l.getValue());
        }
    }

    public static class TopMaker extends Thread{

        BlockingQueue<String> blockingQueue;
        BlockingQueue<HashMap<String, Integer>> finalQueue;
        String stop;
        HashMap<String, Integer> threadMap = new HashMap<>();
        int cpuQuantity;

        private TopMaker(BlockingQueue blockingQueue, String stop, BlockingQueue finalQueue, int cpuQuant){
            this.blockingQueue = blockingQueue;
            this.stop = stop;
            this.finalQueue = finalQueue;
            this.cpuQuantity = cpuQuant;
        }

        @Override
        public void run() {

            String stringOfBook = "";

            while (true) {
                try {
                    stringOfBook = blockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (stringOfBook == stop)
                    break;

                // Для каждой строки
                String[] wordSplit =
                        stringOfBook.toLowerCase() // Переводим в нижний регистр
                                .replaceAll("\\p{Punct}", " ") // Заменяем все знаки на пробел
                                .trim() // Убираем пробелы в начале и конце строки.
                                .split("\\s"); // Разбиваем строки на слова

                for (String s : wordSplit) {
                    // Выбираем только непустые слова.
                    if (s.length() > 0) {
                        //мерджим с локальной мапой
                        if (threadMap.containsKey(s))
                            threadMap.put(s, threadMap.get(s).intValue() + 1);
                        else
                            threadMap.put(s, 1);
                    }
                }

            }

            finalQueue.add(threadMap);

            if (finalQueue.size() == cpuQuantity) {
                synchronized (finalQueue) {
                    finalQueue.notify();
                }
            }
        }
    }

    private static List topTen(HashMap hm){
        List list = new ArrayList(hm.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        List topTen =  list.subList(0, 10);

        return topTen;
    }

    private static List<String> putToBlockingQue(BlockingQueue<String> blockingQueue, String path, String stop, int cpuQuant) {

        File text = new File(path);

        List<String> lines;
        List<String> words = new ArrayList<>();

        try {
            lines = Files.readAllLines(text.toPath());

            for (String line : lines) {
                blockingQueue.put(line);
            }

            for (int i = 0; i < cpuQuant; i++) {
                blockingQueue.put(stop);
            }

        } catch (IOException e) {
            System.out.println("File does not exist.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return words;
    }

}
