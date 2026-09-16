package com.fakenews.ml;

import java.util.*;
import java.util.regex.Pattern;

public final class TextPreprocessor {
    private static final Pattern NON_LETTER = Pattern.compile("[^a-z\\s]");
    private static final Set<String> STOP_WORDS = Set.of(
            "a","an","the","and","or","but","if","then","than","this","that",
            "these","those","is","are","was","were","be","been","being","to",
            "of","in","on","at","for","from","with","by","as","it","its","into",
            "about","after","before","over","under","during","through","their",
            "there","here","he","she","they","them","his","her","we","you","your",
            "i","me","my","our","ours","who","what","when","where","why","how",
            "will","would","could","should","can","may","might","do","does","did",
            "has","have","had","not","no","news"
    );

    private TextPreprocessor() {}

    public static List<String> tokenize(String text) {
        if (text == null) return List.of();

        String normalized = text.toLowerCase(Locale.ROOT)
                .replaceAll("https?://\\S+|www\\.\\S+", " ")
                .replaceAll("[\\r\\n\\t]+", " ");
        normalized = NON_LETTER.matcher(normalized).replaceAll(" ");

        List<String> tokens = new ArrayList<>();
        for (String word : normalized.split("\\s+")) {
            if (word.length() >= 2 && !STOP_WORDS.contains(word)) {
                tokens.add(simpleStem(word));
            }
        }
        return tokens;
    }

    private static String simpleStem(String word) {
        if (word.length() > 5 && word.endsWith("ing")) return word.substring(0, word.length() - 3);
        if (word.length() > 4 && word.endsWith("ed")) return word.substring(0, word.length() - 2);
        if (word.length() > 4 && word.endsWith("ly")) return word.substring(0, word.length() - 2);
        if (word.length() > 4 && word.endsWith("s")) return word.substring(0, word.length() - 1);
        return word;
    }
}
