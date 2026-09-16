package com.fakenews.controller;

import com.fakenews.ml.ModelService;
import com.fakenews.model.PredictionRequest;
import com.fakenews.model.PredictionResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class NewsController {
    private final ModelService modelService;

    public NewsController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping("/predict")
    public ResponseEntity<PredictionResponse> predict(@Valid @RequestBody PredictionRequest request) {
        return ResponseEntity.ok(modelService.predict(request.text()));
    }

    @GetMapping("/model-info")
    public ResponseEntity<Map<String, Object>> modelInfo() {
        return ResponseEntity.ok(modelService.modelInfo());
    }
}
