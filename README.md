# 📰 TruthLens — AI-Powered News Analysis

---

## 🌐 Live Application

👉 **Try TruthLens here:**
https://truthlens-fake-news-detection-x344.onrender.com

TruthLens is an AI-powered web application that analyzes news text and classifies it as **likely REAL or FAKE** using Machine Learning.

---

## 📌 Overview

TruthLens is an AI-powered Fake News Detection system designed to help users analyze news content and identify potentially misleading information.

The system processes submitted news text using Natural Language Processing (NLP), converts the text into numerical features using **TF-IDF**, and applies a **Multinomial Naive Bayes** classifier to generate a prediction.

The application also provides a confidence score, explanation, word count, and model information.

---

## 🎯 Objectives

* Develop an intelligent system for news classification
* Identify potentially fake or misleading news text
* Apply Machine Learning to a real-world problem
* Use Natural Language Processing for text analysis
* Provide a simple and user-friendly interface
* Display prediction confidence and explanation
* Encourage users to verify important information using trusted sources

---

## ❗ Problem Statement

The rapid spread of misleading and false information through online platforms makes it difficult for users to distinguish reliable information from potentially false content.

Manual verification of every news claim can be time-consuming. TruthLens addresses this problem by providing an AI-assisted system that analyzes the linguistic patterns of submitted news text and classifies it as **REAL or FAKE**.

---

## 💡 Proposed Solution

TruthLens uses a Machine Learning pipeline to analyze submitted news text.

### 🔄 Processing Pipeline

**News Text → Text Preprocessing → TF-IDF → Multinomial Naive Bayes → Prediction**

The system provides:

* 🟢 REAL / 🔴 FAKE classification
* 📊 Confidence percentage
* 💡 Prediction explanation
* 🔢 Word count
* 🧠 Model information

📌 **Note:** The prediction is an AI-assisted classification based on patterns learned from the training dataset. It does not independently verify whether a claim is factually true.

---

## ⚙️ Features

* 📰 Text-based news analysis
* 🧹 Automatic text preprocessing
* 🔤 Tokenization
* 🚫 Stop-word removal
* 🌱 Simple stemming
* 📊 TF-IDF feature extraction
* 🧠 Multinomial Naive Bayes classification
* 📈 Confidence score
* 💡 Prediction explanation
* 🔢 Word count
* 📋 Model information dashboard
* 🔌 REST API
* ❤️ Health check endpoint
* 📱 Responsive web interface
* ☁️ Cloud deployment using Render

---

## 🛠️ Tech Stack

### Backend

* Java 17+
* Spring Boot
* Maven

### Machine Learning

* Natural Language Processing (NLP)
* TF-IDF Vectorization
* Multinomial Naive Bayes

### Frontend

* HTML
* CSS
* JavaScript

### Deployment

* Docker
* Render

### Testing

* JUnit

---

## 🧠 Model Details

* **Model:** Multinomial Naive Bayes
* **Feature Extraction:** TF-IDF
* **Task:** Binary Text Classification
* **Classes:** REAL, FAKE
* **Training Examples:** 10,269
* **Vocabulary Size:** 9,066
* **Validation Accuracy:** 59.7%
* **Dataset:** LIAR benchmark-derived dataset
* **Train/Validation Split:** 80/20
* **Random Seed:** 42

📌 The reported accuracy is measured on the project's held-out validation split. It should not be interpreted as the percentage of factual claims correctly verified.

---

## 📂 Dataset Information

* **Source:** LIAR benchmark dataset
* The original dataset contains labeled statements.
* The labels were converted into two categories for this project.

### Label Mapping

* `true` → REAL
* `mostly-true` → REAL
* `half-true` → REAL
* `barely-true` → FAKE
* `false` → FAKE
* `pants-fire` → FAKE

The final dataset contains **12,836 data records**.

---

## 📁 Project Structure

```text
TruthLens-Fake-News-Detection/
│
├── README.md
├── pom.xml
├── Dockerfile
│
├── docs/
│   └── REPORT_OUTLINE.md
│
└── src/
    ├── main/
    │   ├── java/com/fakenews/
    │   │   ├── FakeNewsApplication.java
    │   │   │
    │   │   ├── controller/
    │   │   │   ├── NewsController.java
    │   │   │   └── GlobalExceptionHandler.java
    │   │   │
    │   │   ├── ml/
    │   │   │   ├── ModelService.java
    │   │   │   ├── TextPreprocessor.java
    │   │   │   ├── TfidfVectorizer.java
    │   │   │   └── NaiveBayesClassifier.java
    │   │   │
    │   │   └── model/
    │   │       ├── PredictionRequest.java
    │   │       └── PredictionResponse.java
    │   │
    │   └── resources/
    │       ├── application.properties
    │       ├── data/
    │       │   └── news_dataset.csv
    │       └── static/
    │           ├── index.html
    │           ├── style.css
    │           └── script.js
    │
    └── test/
        └── java/com/fakenews/ml/
            └── TextPreprocessorTest.java
```

---

## 🚀 How to Run the Project

### 1. Clone the Repository

```bash
git clone https://github.com/Vanyaa14/TruthLens-Fake-News-Detection.git
```

```bash
cd TruthLens-Fake-News-Detection
```

### 2. Build the Project

```bash
mvn clean package
```

### 3. Run the Application

```bash
java -jar target/fake-news-detector-1.0.0.jar
```

### 4. Open the Application

Open your browser and visit:

```text
http://localhost:8080
```

---

## 🖥️ Application Workflow

1. User enters news text
2. Text is sent to the Spring Boot backend
3. Text preprocessing is performed
4. Unnecessary characters are removed
5. Stop words are removed
6. Text is tokenized and normalized
7. TF-IDF converts the text into numerical features
8. Multinomial Naive Bayes analyzes the features
9. The system predicts REAL or FAKE
10. Confidence score is calculated
11. Explanation and result are displayed to the user

---

## 🔌 API Endpoints

### Predict News

```text
POST /api/predict
```

Example request:

```json
{
  "text": "The government announced a new public transport initiative."
}
```

### Model Information

```text
GET /api/model-info
```

### Health Check

```text
GET /actuator/health
```

---

## 📊 Results

The system successfully:

* Classifies news text into REAL or FAKE
* Generates a confidence score
* Provides an explanation for the prediction
* Processes text through an NLP pipeline
* Provides real-time results through the web interface
* Runs as a REST-based Spring Boot application
* Is deployed as a Docker container on Render

### Model Evaluation

**Validation Accuracy: 59.7%**

The accuracy was calculated using a held-out validation portion of the dataset.

---

## ⚠️ Limitations

* The model learns linguistic patterns from the training data rather than independently checking facts.
* Similar-looking or similarly worded news may lead to incorrect predictions.
* Performance can vary on topics that differ significantly from the training dataset.
* Confidence represents the model's classification confidence, not factual certainty.
* The current model has a validation accuracy of 59.7%.
* Important news claims should always be verified using reliable and trusted sources.

---

## 🔮 Future Scope

* 🌐 Integration with trusted fact-checking sources
* 📰 Real-time news verification
* 🔗 URL-based news analysis
* 📱 Mobile application integration
* 🌍 Multilingual fake-news detection
* 🤖 Transformer-based models such as BERT
* 📊 Improved evaluation using Precision, Recall and F1-Score
* 🔍 Explainable AI for detailed prediction reasoning
* 🧠 Larger and more diverse training datasets
* ☁️ Scalable cloud deployment

---

## 🎓 Learning Outcomes

* Understanding Natural Language Processing concepts
* Implementation of TF-IDF
* Understanding Multinomial Naive Bayes
* Working with real-world datasets
* Building REST APIs using Spring Boot
* Integrating Machine Learning with Java
* Creating responsive web interfaces
* Testing Java applications using JUnit
* Dockerizing a Spring Boot application
* Deploying applications to the cloud
* Understanding memory-efficient Machine Learning implementation

---

## 🌐 Deployment

TruthLens is deployed using **Docker and Render**.

### Live Application

👉 https://truthlens-fake-news-detection-x344.onrender.com

The application can be accessed directly through a web browser without requiring local installation.

---

## 👩‍💻 Developed By

**Vanya Singh**

---

## 🌍 Conclusion

TruthLens demonstrates how Artificial Intelligence, Natural Language Processing, and Machine Learning can be applied to the problem of misleading information.

The system provides an easy-to-use platform where users can submit news text and receive an AI-assisted **REAL or FAKE classification**, along with confidence and explanation.

TruthLens is designed as an **assistive analysis tool** and encourages users to verify important information through trusted and reliable sources.

---

## 💬 Quote

> **"Analyze Responsibly. Verify What Matters."** 📰🔍

---
