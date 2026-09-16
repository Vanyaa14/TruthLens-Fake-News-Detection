package com.fakenews.ml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fakenews.model.PredictionResponse;

@Service
public class ModelService {

    private final TfidfVectorizer vectorizer =
            new TfidfVectorizer();

    private final NaiveBayesClassifier classifier =
            new NaiveBayesClassifier();

    private int trainingSize;
    private double validationAccuracy;

    public ModelService() {
        train();
    }

    private void train() {

        List<String> texts = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        /*
         * Load dataset.
         */
        try (
                InputStream input =
                        new ClassPathResource(
                                "data/news_dataset.csv"
                        ).getInputStream();

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        input,
                                        StandardCharsets.UTF_8
                                )
                        )
        ) {

            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {

                if (header) {
                    header = false;
                    continue;
                }

                int comma = line.lastIndexOf(',');

                if (comma <= 0) {
                    continue;
                }

                String text =
                        line.substring(0, comma).trim();

                String label =
                        line.substring(comma + 1)
                                .trim()
                                .toUpperCase(Locale.ROOT);

                if (
                        !text.isBlank()
                                &&
                        (
                                label.equals("REAL")
                                        ||
                                label.equals("FAKE")
                        )
                ) {

                    texts.add(text);
                    labels.add(label);
                }
            }

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Could not load training dataset.",
                    e
            );
        }

        if (texts.size() < 10) {

            throw new IllegalStateException(
                    "Training dataset is too small."
            );
        }

        /*
         * Tokenize documents.
         */
        List<List<String>> tokenDocs =
                texts.stream()
                        .map(TextPreprocessor::tokenize)
                        .toList();

        /*
         * Deterministic 80/20 split.
         */
        List<Integer> indexes =
                new ArrayList<>();

        for (int i = 0; i < texts.size(); i++) {
            indexes.add(i);
        }

        Collections.shuffle(
                indexes,
                new Random(42)
        );

        int trainCount =
                Math.max(
                        8,
                        (int) Math.round(
                                texts.size() * 0.8
                        )
                );

        /*
         * Create training documents list.
         */
        List<List<String>> trainDocs =
                new ArrayList<>();

        for (int i = 0; i < trainCount; i++) {

            trainDocs.add(
                    tokenDocs.get(
                            indexes.get(i)
                    )
            );
        }

        /*
         * Build TF-IDF vocabulary.
         */
        vectorizer.fit(trainDocs);

        /*
         * IMPORTANT:
         *
         * We no longer create:
         *
         * double[][] xTrain
         *
         * Instead, we train one document at a time.
         */
        classifier.startTraining(
                vectorizer.size()
        );

        for (int i = 0; i < trainCount; i++) {

            int original =
                    indexes.get(i);

            double[] vector =
                    vectorizer.transform(
                            tokenDocs.get(original)
                    );

            classifier.addTrainingExample(
                    vector,
                    labels.get(original)
            );
        }

        /*
         * Finish Naive Bayes training.
         */
        classifier.finishTraining();

        trainingSize = trainCount;

        /*
         * Validation.
         *
         * Only one vector is created at a time.
         */
        int correct = 0;
        int validationCount = 0;

        for (
                int i = trainCount;
                i < indexes.size();
                i++
        ) {

            int original =
                    indexes.get(i);

            double[] vector =
                    vectorizer.transform(
                            tokenDocs.get(original)
                    );

            NaiveBayesClassifier.Prediction prediction =
                    classifier.predict(vector);

            if (
                    prediction.label()
                            .equals(labels.get(original))
            ) {

                correct++;
            }

            validationCount++;
        }

        validationAccuracy =
                validationCount == 0
                        ? 0.0
                        : (double) correct
                        / validationCount;
    }

    /*
     * Predict news entered by the user.
     */
    public PredictionResponse predict(
            String text
    ) {

        List<String> tokens =
                TextPreprocessor.tokenize(text);

        double[] vector =
                vectorizer.transform(tokens);

        NaiveBayesClassifier.Prediction prediction =
                classifier.predict(vector);

        String explanation;

        if (tokens.isEmpty()) {

            explanation =
                    "Very little usable text was found, "
                            + "so this prediction may be unreliable.";

        } else if (prediction.confidence() < 0.65) {

            explanation =
                    "The model found mixed signals. "
                            + "Treat this result as uncertain "
                            + "and verify the claim using trusted sources.";

        } else if (
                prediction.label().equals("FAKE")
        ) {

            explanation =
                    "The wording contains patterns that are "
                            + "more similar to the FAKE examples "
                            + "in the training data.";

        } else {

            explanation =
                    "The wording contains patterns that are "
                            + "more similar to the REAL examples "
                            + "in the training data.";
        }

        return new PredictionResponse(

                prediction.label(),

                Math.round(
                        prediction.confidence()
                                * 1000.0
                ) / 10.0,

                explanation,

                tokens.size(),

                "TF-IDF + Multinomial Naive Bayes"
        );
    }

    /*
     * Model information for the dashboard.
     */
    public Map<String, Object> modelInfo() {

        Map<String, Object> info =
                new LinkedHashMap<>();

        info.put(
                "model",
                "TF-IDF + Multinomial Naive Bayes"
        );

        info.put(
                "trainingExamples",
                trainingSize
        );

        info.put(
                "vocabularySize",
                vectorizer.size()
        );

        info.put(
                "validationAccuracy",
                Math.round(
                        validationAccuracy
                                * 1000.0
                ) / 10.0
        );

        info.put(
                "datasetNote",
                "Dataset derived from the LIAR benchmark "
                        + "and converted into binary REAL/FAKE "
                        + "labels for this project."
        );

        return info;
    }
}