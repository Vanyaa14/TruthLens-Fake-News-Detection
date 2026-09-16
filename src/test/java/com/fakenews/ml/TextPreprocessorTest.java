package com.fakenews.ml;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TextPreprocessorTest {
    @Test
    void removesStopWordsAndNormalizesText() {
        List<String> tokens = TextPreprocessor.tokenize("The QUICK foxes are running!");

        assertFalse(tokens.contains("the"));
        assertTrue(tokens.contains("quick"));
        assertTrue(tokens.contains("foxe"));
        assertTrue(tokens.contains("runn"));
    }
}