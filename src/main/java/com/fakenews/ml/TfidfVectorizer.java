package com.fakenews.ml;

import java.util.*;

public class TfidfVectorizer {
    private final Map<String, Integer> vocabulary = new LinkedHashMap<>();
    private double[] idf;

    public void fit(List<List<String>> documents) {
        vocabulary.clear();
        Map<String, Integer> documentFrequency = new HashMap<>();

        for (List<String> doc : documents) {
            Set<String> unique = new HashSet<>(doc);
            for (String token : unique) {
                documentFrequency.merge(token, 1, Integer::sum);
            }
        }

        List<String> terms = new ArrayList<>(documentFrequency.keySet());
        Collections.sort(terms);

        for (String term : terms) {
            vocabulary.put(term, vocabulary.size());
        }

        int n = documents.size();
        idf = new double[vocabulary.size()];

        for (Map.Entry<String, Integer> entry : vocabulary.entrySet()) {
            int df = documentFrequency.getOrDefault(entry.getKey(), 0);
            idf[entry.getValue()] = Math.log((n + 1.0) / (df + 1.0)) + 1.0;
        }
    }

    public double[] transform(List<String> tokens) {
        double[] vector = new double[vocabulary.size()];
        if (tokens.isEmpty()) return vector;

        Map<String, Integer> counts = new HashMap<>();
        for (String token : tokens) {
            if (vocabulary.containsKey(token)) {
                counts.merge(token, 1, Integer::sum);
            }
        }

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            int index = vocabulary.get(entry.getKey());
            double tf = (double) entry.getValue() / tokens.size();
            vector[index] = tf * idf[index];
        }
        return vector;
    }

    public int size() {
        return vocabulary.size();
    }
}
