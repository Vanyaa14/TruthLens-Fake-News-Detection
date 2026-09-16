package com.fakenews.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PredictionRequest(
        @NotBlank(message = "News text is required")
        @Size(min = 10, max = 10000, message = "News must contain 10 to 10000 characters")
        String text
) {}
