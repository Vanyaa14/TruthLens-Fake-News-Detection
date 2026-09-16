package com.fakenews.ml;

public class NaiveBayesClassifier {

    private final String[] labels = {"REAL", "FAKE"};

    private double[] classLogPrior = new double[2];
    private double[][] logLikelihood = new double[2][];

    private double[] classTotals = new double[2];
    private double[][] featureTotals = new double[2][];

    private int[] classDocs = new int[2];

    private int vocabularySize;
    private int totalDocuments;

    private boolean trained = false;

    public void startTraining(int features) {

        vocabularySize = features;

        classTotals = new double[2];
        classDocs = new int[2];

        featureTotals = new double[2][features];

        logLikelihood[0] = new double[features];
        logLikelihood[1] = new double[features];

        totalDocuments = 0;
        trained = false;
    }

    public void addTrainingExample(double[] vector, String label) {

        int c = label.equalsIgnoreCase("REAL") ? 0 : 1;

        classDocs[c]++;
        totalDocuments++;

        for (int j = 0; j < vector.length; j++) {

            double value = vector[j];

            if (value != 0) {
                featureTotals[c][j] += value;
                classTotals[c] += value;
            }
        }
    }

    public void finishTraining() {

        if (totalDocuments == 0) {
            throw new IllegalStateException("No training data provided.");
        }

        for (int c = 0; c < 2; c++) {

            classLogPrior[c] =
                    Math.log(
                            (classDocs[c] + 1.0)
                                    / (totalDocuments + 2.0)
                    );

            double denominator =
                    classTotals[c] + vocabularySize;

            for (int j = 0; j < vocabularySize; j++) {

                double probability =
                        (featureTotals[c][j] + 1.0)
                                / denominator;

                logLikelihood[c][j] =
                        Math.log(probability);
            }
        }

        trained = true;
    }

    public Prediction predict(double[] vector) {

        if (!trained) {
            throw new IllegalStateException(
                    "Model has not been trained."
            );
        }

        double[] scores = new double[2];

        for (int c = 0; c < 2; c++) {

            scores[c] = classLogPrior[c];

            for (int j = 0; j < vector.length; j++) {

                if (vector[j] != 0) {

                    scores[c] +=
                            vector[j] * logLikelihood[c][j];
                }
            }
        }

        double max = Math.max(scores[0], scores[1]);

        double exp0 =
                Math.exp(scores[0] - max);

        double exp1 =
                Math.exp(scores[1] - max);

        double confidenceReal =
                exp0 / (exp0 + exp1);

        if (confidenceReal >= 0.5) {

            return new Prediction(
                    "REAL",
                    confidenceReal
            );

        } else {

            return new Prediction(
                    "FAKE",
                    1.0 - confidenceReal
            );
        }
    }

    public record Prediction(
            String label,
            double confidence
    ) {}
}