# TruthLens — Fake News Detection System

A Java AI/ML academic project that classifies news text as **REAL** or **FAKE** using a text-processing pipeline based on **TF-IDF** and **Multinomial Naive Bayes**.

> **Important:** This project is an educational classifier. It does not verify facts on the internet and a prediction is not proof that a story is true or false.

## Features

- Java 17 + Spring Boot
- TF-IDF text feature extraction implemented in Java
- Multinomial Naive Bayes classifier implemented in Java
- Training from a CSV dataset
- Automatic 80/20 validation split
- Accuracy shown on the web dashboard
- REST API for predictions
- Responsive web interface
- Health endpoint for deployment
- Docker support
- Fully runnable from the command line

## Requirements

- Java 17 or newer
- Maven 3.9+ (or use an IDE with Maven support)
- Internet connection the first time Maven downloads dependencies

Check:

```bash
java -version
mvn -version
```

## Run locally

Clone the repository:

```bash
git clone https://github.com/YOUR_USERNAME/fake-news-detector.git
cd fake-news-detector
```

Build:

```bash
mvn clean package
```

Run:

```bash
java -jar target/fake-news-detector-1.0.0.jar
```

Open in your browser:

```text
http://localhost:8080
```

## Run directly with Maven

```bash
mvn spring-boot:run
```

Then open `http://localhost:8080`.

## API

### Prediction

`POST /api/predict`

Example:

```bash
curl -X POST http://localhost:8080/api/predict \
  -H "Content-Type: application/json" \
  -d "{\"text\":\"The national weather agency issued a forecast for heavy rainfall this week.\"}"
```

Example response:

```json
{
  "prediction": "REAL",
  "confidence": 78.4,
  "explanation": "The wording contains patterns that are more similar to the REAL examples in the training data.",
  "wordCount": 12,
  "model": "TF-IDF + Multinomial Naive Bayes"
}
```

### Model information

```text
GET /api/model-info
```

### Health check

```text
GET /actuator/health
```

## Dataset

The repository contains a **small synthetic educational dataset** in:

```text
src/main/resources/data/news_dataset.csv
```

The dataset is intentionally small so the project remains easy to understand and run. For a serious ML experiment, replace it with a larger, properly sourced and verified dataset while keeping the same `text,label` structure.

Do not claim that the included dataset provides real-world fact-checking accuracy.

## Project architecture

```text
Browser
   |
   | HTTP
   v
Spring Boot REST API
   |
   v
Text Preprocessor
   |
   v
TF-IDF Vectorizer
   |
   v
Multinomial Naive Bayes
   |
   v
REAL / FAKE + confidence
```

## Important files

```text
src/main/java/com/fakenews/
├── FakeNewsApplication.java
├── controller/
│   ├── NewsController.java
│   └── GlobalExceptionHandler.java
├── ml/
│   ├── ModelService.java
│   ├── TextPreprocessor.java
│   ├── TfidfVectorizer.java
│   └── NaiveBayesClassifier.java
└── model/
    ├── PredictionRequest.java
    └── PredictionResponse.java

src/main/resources/
├── application.properties
├── data/news_dataset.csv
└── static/
    ├── index.html
    ├── style.css
    └── script.js
```

## Deploy with Docker

Build:

```bash
docker build -t fake-news-detector .
```

Run:

```bash
docker run -p 8080:8080 fake-news-detector
```

Open:

```text
http://localhost:8080
```

The Docker image also respects the `PORT` environment variable, which is useful on cloud platforms.

## Deploying to a cloud service

This project includes a `Dockerfile`, so use a cloud service that supports Docker containers.

Generic deployment process:

1. Create a public GitHub repository.
2. Push this project to the repository.
3. Create a new web service on your chosen cloud platform.
4. Connect the GitHub repository.
5. Select Docker/container deployment if the platform asks for a runtime.
6. Use the platform-provided port environment variable if required.
7. Deploy.
8. Open the generated public HTTPS URL.
9. Test `/actuator/health` and the web interface.

Do not commit passwords, API keys, tokens, or other secrets to GitHub.

## GitHub submission

The repository must be public.

Submit only the repository root URL, for example:

```text
https://github.com/YOUR_USERNAME/fake-news-detector
```

Do not submit:

```text
https://github.com/YOUR_USERNAME/fake-news-detector/tree/main
```

## Suggested project report sections

1. Title
2. Abstract
3. Problem Statement
4. Objectives
5. Existing System
6. Proposed System
7. Technologies Used
8. System Architecture
9. Dataset
10. Data Preprocessing
11. TF-IDF
12. Naive Bayes Algorithm
13. Implementation
14. Testing
15. Evaluation Metrics
16. Screenshots
17. Limitations
18. Future Scope
19. Conclusion
20. References

## Future scope

- Train on a substantially larger verified dataset.
- Add source credibility signals.
- Add multilingual support.
- Add explainable feature-level analysis.
- Add fact-checking source integration.
- Add database-backed prediction history.
- Add user authentication if required.

## License

For academic/educational use. Replace the dataset with properly licensed data before public commercial use.
