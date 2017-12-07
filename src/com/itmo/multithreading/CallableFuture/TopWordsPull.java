package com.itmo.multithreading.CallableFuture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;

public class TopWordsPull {

    private static Map<String, Integer> mainMap = new HashMap<>();

    public static void main(String[] args) {

        int cpuQuant = Runtime.getRuntime().availableProcessors();

        List<String> lst = getListOfWords("src/com/itmo/multithreading/Sinchronized/WarAndPiece.txt");

        int size = lst.size()/cpuQuant;
        int lastsize = lst.size()/cpuQuant + lst.size()%cpuQuant;

        Executor executor = Executors.newFixedThreadPool(cpuQuant);
        List<Future> futureList = new ArrayList<>();

        for (int i = 0; i < cpuQuant; i++) {
            List<String> sublist = lst.subList(i * size, i != cpuQuant - 1 ? i * size + size : lst.size());
            FutureTask<List> future = new FutureTask<List>(new TopMaker(sublist));
            executor.execute(future);
        }

                for (Future future : futureList) {
                    if (future.isDone()) {
                        // ждем, пока future task не закончит выполнение
                        HashMap<String, Integer> localHashMap = (HashMap<String, Integer>) (future.get());
                    }
                }


                System.out.println("Ждем, пока FutureTask2 не закончит свое выполнение");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if(s !=null) {
                    System.out.println("Результат выполнения FutureTask2 = " + s);
                }



        //            synchronized (TopWords.this) {
//                for (Map.Entry<String, Integer> entrySet : threadMap.entrySet()) {
//                    mainMap.merge(entrySet.getKey(), entrySet.getValue(), (integer, integer2) -> integer + integer2);
//                }
//            }

        List<Map.Entry<String, Integer>> ls = topTen((HashMap)getMainMap());

        for (Map.Entry<String, Integer> l : ls) {
            System.out.println(l.getKey() + " : " + l.getValue());
        }


    }

    public static class TopMaker implements Callable{

        private Map<String, Integer> threadMap;

        private List<String> lst;

        public TopMaker(List<String> lst){
            this.lst = lst;
        }

        @Override
        public Object call() throws Exception {
            threadMap = countWords(lst);

            return  threadMap;
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
