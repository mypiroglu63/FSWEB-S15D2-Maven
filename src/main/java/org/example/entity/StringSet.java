package org.example.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.stream.Collectors;

public class StringSet {

    public static List<String> findUniqueWords(String text) {
        // 1. İstenmeyen karakterleri temizle
        String cleanedText = text.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();

        // 2. Kelimeleri ayır ve bir sete ekle
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(cleanedText.split("\\s+")));

        // 3. Unique kelimeleri alfabetik olarak sırala
        List<String> sortedUniqueWords = uniqueWords.stream()
                .sorted()
                .collect(Collectors.toList());

        // 4. Sonuçları return et
        return sortedUniqueWords;
    }
}
