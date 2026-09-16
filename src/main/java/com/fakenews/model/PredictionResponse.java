package com.fakenews.model;

public record PredictionResponse(
        String prediction,
        double confidence,
        String explanation,
        int wordCount,
        String model
) {}
