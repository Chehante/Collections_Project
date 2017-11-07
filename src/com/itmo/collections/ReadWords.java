package com.itmo.collections;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xmitya on 07.04.17.
 */
public class ReadWords {
    public static void main(String[] args) throws IOException {
        // Создаем файл, указывая путь к текстовому файлу на диске
        File text = new File("E:\\Java\\WarAndPiece.txt");

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

        HashMap <String, Integer> hashMap = countWords(words);

        HashMap <Integer, List> hashMapOfEqualLength = getEqualLength(hashMap);

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
}
