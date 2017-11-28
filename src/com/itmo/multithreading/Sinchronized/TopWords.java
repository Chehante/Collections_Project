package com.itmo.multithreading.Sinchronized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class TopWords {

    private Map<String, Integer> mainMap = new HashMap<>();

    public static void main(String[] args) {

        TopWords tw = new TopWords();

        int cpuQuant = Runtime.getRuntime().availableProcessors();

        List<String> lst = tw.getListOfWords("src/com/itmo/multithreading/Sinchronized/WarAndPiece.txt");

        int size = lst.size()/cpuQuant;
        int lastsize = lst.size()/cpuQuant + lst.size()%cpuQuant;

        Queue<Thread> threads = new LinkedList<>();

        for (int i = 0; i < cpuQuant; i++) {
            List<String> sublist = lst.subList(i * size, i != cpuQuant - 1 ? i * size + size : lst.size());
            Thread t = tw.new TopMaker(sublist);
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Map.Entry<String, Integer>> ls = tw.topTen((HashMap)tw.getMainMap());

        for (Map.Entry<String, Integer> l : ls) {
            System.out.println(l.getKey() + " : " + l.getValue());
        }


    }

    public class TopMaker extends Thread{

        private Map<String, Integer> threadMap;

        private List<String> lst;

        public TopMaker(List<String> lst){
            this.lst = lst;
        }

        @Override
        public void run() {

            threadMap = countWords(lst);
            synchronized (TopWords.this) {
                for (Map.Entry<String, Integer> entrySet : threadMap.entrySet()) {
                    mainMap.merge(entrySet.getKey(), entrySet.getValue(), (integer, integer2) -> integer + integer2);
                }
            }

        }
    }

    private List topTen(HashMap hm){
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

    private HashMap<String, Integer> countWords(List<String> lst){

        HashMap<String, Integer> hashMap = new HashMap<>();

        for (String s : lst){
            if (hashMap.containsKey(s))
                hashMap.put(s, hashMap.get(s).intValue() + 1);
            else
                hashMap.put(s, 1);
        }

        return hashMap;
    }

    private List<String> getListOfWords(String path) {

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

    public Map<String, Integer> getMainMap() {
        return mainMap;
    }
}
