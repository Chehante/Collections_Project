package com.itmo.multithreading.CallableFuture;

import sun.plugin2.jvm.CircularByteBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;

public class TopWordsPullLambda {

    private static Map<String, Integer> mainMap = new HashMap<>();

    public static void main(String[] args) {

        int cpuQuant = Runtime.getRuntime().availableProcessors();

        List<String> lst = getListOfWords("src/com/itmo/multithreading/Sinchronized/WarAndPiece.txt");

        int size = lst.size()/cpuQuant;
        int lastsize = lst.size()/cpuQuant + lst.size()%cpuQuant;

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future> futureList = new ArrayList<>();

        for (int i = 0; i < cpuQuant; i++) {
            List<String> sublist = lst.subList(i * size, i != cpuQuant - 1 ? i * size + size : lst.size());
            futureList.add(executor.submit(() -> countWords(sublist)));
        }

        for (Future future : futureList) {
            // ждем, пока future task не закончит выполнение
            HashMap<String, Integer> localHashMap;
            try {
                localHashMap = (HashMap<String, Integer>) (future.get());
                for (Map.Entry<String, Integer> entry : localHashMap.entrySet()) {
                    mainMap.merge(entry.getKey(), entry.getValue(), (integer, integer2) -> integer + integer2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdownNow();

        List<Map.Entry<String, Integer>> ls = topTen((HashMap)getMainMap());

        for (Map.Entry<String, Integer> l : ls) {
            System.out.println(l.getKey() + " : " + l.getValue());
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

    private static HashMap<String, Integer> countWords(List<String> lst){

        HashMap<String, Integer> hashMap = new HashMap<>();

        for (String s : lst){
            if (hashMap.containsKey(s))
                hashMap.put(s, hashMap.get(s).intValue() + 1);
            else
                hashMap.put(s, 1);
        }

        return hashMap;
    }

    private static List<String> getListOfWords(String path) {

        File text = new File(path);

        List<String> lines;
        List<String> words = new ArrayList<>();

        try {
            lines = Files.readAllLines(text.toPath());

            for (String line : lines) {
                // Для каждой строки
                String[] wordSplit =
                        line.toLowerCase() // Переводим в нижний регистр
                                .replaceAll("\\p{Punct}", " ") // Заменяем все знаки на пробел
                                .trim() // Убираем пробелы в начале и конце строки.
                                .split("\\s"); // Разбиваем строки на слова

                for (String s : wordSplit) {
                    // Выбираем только непустые слова.
                    if (s.length() > 0)
                        words.add(s.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("File does not exist.");
            e.printStackTrace();
        }

        return words;
    }

    public static Map<String, Integer> getMainMap() {
        return mainMap;
    }
}
