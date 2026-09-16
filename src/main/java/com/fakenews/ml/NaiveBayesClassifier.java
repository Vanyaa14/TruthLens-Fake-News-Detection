package com.fakenews.ml;

import java.util.*;

public class NaiveBayesClassifier {
    private final String[] labels = {"REAL", "FAKE"};
    private final double[] classLogPrior = new double[2];
    private final double[][] logLikelihood = new double[2][];
    private boolean trained = false;

    public void fit(double[][] x, String[] y) {
        int features = x[0].length;
        logLikelihood[0] = new double[features];
        logLikelihood[1] = new double[features];

        double[] classTotals = new double[2];
        double[][] featureTotals = new double[2][features];
        int[] classDocs = new int[2];

        for (int i = 0; i < x.length; i++) {
            int c = y[i].equalsIgnoreCase("REAL") ? 0 : 1;
            classDocs[c]++;
            for (int j = 0; j < features; j++) {
                double value = x[i][j];
                featureTotals[c][j] += value;
                classTotals[c] += value;
            }
        }

        int vocabularySize = features;
        for (int c = 0; c < 2; c++) {
            classLogPrior[c] = Math.log((classDocs[c] + 1.0) / (x.length + 2.0));
            double denominator = classTotals[c] + vocabularySize;
            for (int j = 0; j < features; j++) {
                double probability = (featureTotals[c][j] + 1.0) / denominator;
                logLikelihood[c][j] = Math.log(probability);
            }
        }
        trained = true;
    }

    public Prediction predict(double[] vector) {
        if (!trained) throw new IllegalStateException("Model has not been trained.");

        double[] scores = new double[2];
        for (int c = 0; c < 2; c++) {
            scores[c] = classLogPrior[c];
            for (int j = 0; j < vector.length; j++) {
                if (vector[j] != 0) {
                    scores[c] += vector[j] * logLikelihood[c][j];
                }
            }
        }

        double max = Math.max(scores[0], scores[1]);
        double exp0 = Math.exp(scores[0] - max);
        double exp1 = Math.exp(scores[1] - max);
        double confidenceReal = exp0 / (exp0 + exp1);

        if (confidenceReal >= 0.5) {
            return new Prediction("REAL", confidenceReal);
        }
        return new Prediction("FAKE", 1.0 - confidenceReal);
    }

    public record Prediction(String label, double confidence) {}
}
