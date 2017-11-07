package com.itmo.collections;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by xmitya on 07.04.17.
 */
public class ReadWords {
    public static void main(String[] args) throws IOException {
        // Создаем файл, указывая путь к текстовому файлу на диске
        File text = new File("D:\\ТРТИЛЕК\\Java\\WarAndPiece.txt");

        // Вычитываем все строки из файла
        List<String> lines = Files.readAllLines(text.toPath());

        // Создаем пустую коллекцию для слов.
        List<String> words = new ArrayList<>();

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

        //count words
        HashMap <String, Integer> hashMap = countWords(words);

        //grouping of words length
        HashMap <Integer, List> hashMapOfEqualLength = getEqualLength(hashMap);

        //top 10
        List list = topTen(hashMap);

        HashMap <Character, Double> litters = countLitters(hashMap);

    }

    public static HashMap<String, Integer> countWords(List<String> lst){

        HashMap<String, Integer> hashMap = new HashMap<>();

        for (String s : lst){
            if (hashMap.containsKey(s))
                hashMap.put(s, hashMap.get(s).intValue() + 1);
            else
                hashMap.put(s, 1);
        }

        for (Map.Entry<String, Integer> entry: hashMap.entrySet()){
            System.out.println("Word \"" + entry.getKey() + "\" occurs " + entry.getValue() + " times");
        }

        return hashMap;
    }

    public static HashMap<Integer, List> getEqualLength(HashMap<String, Integer> hm) {

        HashMap<Integer, List> lengthOfWords = new HashMap<Integer, List>();

        for (Map.Entry<String, Integer> entry : hm.entrySet()) {

            String currentWord = entry.getKey();
            int currentLength = currentWord.length();

            if (lengthOfWords.containsKey(currentLength))
                lengthOfWords.get(currentLength).add(currentWord);
            else {
                List<String> newList = new ArrayList<String>() {
                };
                newList.add(currentWord);
                lengthOfWords.put(currentLength, newList);
            }

        }

        return lengthOfWords;
    }

    public static HashMap<Integer, List> getEqualLengthWithoutArticle(HashMap<String, Integer> hm) {

        HashMap<Integer, List> lengthOfWords = new HashMap<>();
        List<String> lstArticle = new ArrayList<>();
        lstArticle.add("the");
        lstArticle.add("a");
        lstArticle.add("on");
        lstArticle.add("to");
        lstArticle.add("under");
        lstArticle.add("at");

        for (Map.Entry<String, Integer> entry : hm.entrySet()) {

            String currentWord = entry.getKey();
            int currentLength = currentWord.length();

            if (lstArticle.contains(currentWord))
                continue;

            if (lengthOfWords.containsKey(currentLength))
                lengthOfWords.get(currentLength).add(currentWord);
            else {
                List<String> newList = new ArrayList<String>() {
                };
                newList.add(currentWord);
                lengthOfWords.put(currentLength, newList);
            }

        }

        return lengthOfWords;
    }

    public static List topTen(HashMap hm){
        List list = new ArrayList(hm.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        List topTen =  list.subList(0, 9);

        return topTen;
    }

    public static HashMap<Character, Double> countLitters(HashMap<String, Integer> hm){
        HashMap<Character, Integer> hmLitters = new HashMap<>();
        Double litQuantity = 0.0;

        for (Map.Entry<String, Integer> entry: hm.entrySet()){
            litQuantity += entry.getKey().length() * entry.getValue();
            for (int i = 0; i < entry.getKey().length(); i++){
                Character a = entry.getKey().charAt(i);

                if (hmLitters.containsKey(a))
                    hmLitters.put(a, hmLitters.get(a) + entry.getValue());
                else
                    hmLitters.put(a, entry.getValue());
            }
        }

        HashMap<Character, Double> percentOfLitters = new HashMap<>();
        for (Map.Entry<Character, Integer> entry: hmLitters.entrySet()) {
            percentOfLitters.put(entry.getKey(), entry.getValue()/litQuantity);
        }
        return percentOfLitters;
    }
}
