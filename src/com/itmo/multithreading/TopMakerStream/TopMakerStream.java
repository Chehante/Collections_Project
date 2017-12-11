package com.itmo.multithreading.TopMakerStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopMakerStream {

    public static void main(String[] args) throws IOException {

        String wapPath = "src/com/itmo/multithreading/Sinchronized/WarAndPiece.txt";
        File file = new File(wapPath);

        Map<Integer, List<String>> result =
                Files.lines(file.toPath())
                        .parallel()
                        .map(line -> line.toLowerCase().replaceAll("\\pP", " "))
                        .flatMap(line -> Arrays.stream(line.split(" ")))
                        .map(String::trim)
                        .distinct()
                        .collect(Collectors.groupingBy(s -> s.length()));

        for (Map.Entry<Integer, List<String>> integerListEntry : result.entrySet()) {
            System.out.println(integerListEntry.getKey() + ":" + integerListEntry.getValue());
        }


    }


}
