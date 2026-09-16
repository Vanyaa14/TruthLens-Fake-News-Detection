// =========================
// GET HTML ELEMENTS
// =========================

const text = document.getElementById("newsText");
const counter = document.getElementById("counter");
const result = document.getElementById("result");
const errorBox = document.getElementById("error");
const loading = document.getElementById("loading");


// =========================
// CHARACTER COUNTER
// =========================

text.addEventListener("input", () => {

    counter.textContent =
        `${text.value.length} characters`;

});


// =========================
// SAMPLE BUTTON
// =========================

document.getElementById("sampleBtn").addEventListener("click", () => {

    text.value =
        "The national weather agency issued a forecast saying heavy rainfall is expected in several regions this week.";

    text.dispatchEvent(
        new Event("input")
    );

    result.classList.add("hidden");

    errorBox.classList.add("hidden");

});


// =========================
// ANALYZE BUTTON
// =========================

document.getElementById("checkBtn").addEventListener(
    "click",
    async () => {

        const value = text.value.trim();


        // Hide old result
        result.classList.add("hidden");

        // Hide old error
        errorBox.classList.add("hidden");


        // Validate input
        if (value.length < 10) {

            showError(
                "Please enter at least 10 characters."
            );

            return;
        }


        // Show loading
        loading.classList.remove("hidden");


        try {

            const response = await fetch(
                "/api/predict",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body: JSON.stringify({
                        text: value
                    })
                }
            );


            const data =
                await response.json();


            // Handle backend error
            if (!response.ok) {

                throw new Error(
                    data.error ||
                    "Could not analyze the news."
                );

            }


            // Display result
            renderResult(data);


        } catch (error) {

            showError(
                error.message ||
                "Something went wrong. Please try again."
            );


        } finally {

            loading.classList.add("hidden");

        }

    }
);


// =========================
// DISPLAY RESULT
// =========================

function renderResult(data) {

    const badge =
        document.getElementById("badge");


    // Set badge text
    badge.textContent =
        data.prediction;


    // Set badge style
    if (data.prediction === "FAKE") {

        badge.className =
            "badge fake";

    } else {

        badge.className =
            "badge";

    }


    // Prediction heading
    document.getElementById(
        "prediction"
    ).textContent =
        data.prediction === "FAKE"
            ? "LIKELY FAKE NEWS"
            : "LIKELY REAL NEWS";


    // Confidence
    document.getElementById(
        "confidence"
    ).textContent =
        `${data.confidence}%`;


    // Confidence bar
    document.getElementById(
        "barFill"
    ).style.width =
        `${data.confidence}%`;


    // Explanation
    document.getElementById(
        "explanation"
    ).textContent =
        data.explanation;


    // Word count
    document.getElementById(
        "wordCount"
    ).textContent =
        data.wordCount;


    // Model name
    document.getElementById(
        "modelName"
    ).textContent =
        data.model;


    // Show result
    result.classList.remove("hidden");

}


// =========================
// ERROR MESSAGE
// =========================

function showError(message) {

    errorBox.textContent =
        message;

    errorBox.classList.remove(
        "hidden"
    );

}


// =========================
// LOAD MODEL INFORMATION
// =========================

async function loadModelInfo() {

    try {

        const response =
            await fetch(
                "/api/model-info"
            );


        if (!response.ok) {

            throw new Error(
                "Could not load model information."
            );

        }


        const data =
            await response.json();


        // Training examples
        document.getElementById(
            "trainingExamples"
        ).textContent =
            Number(
                data.trainingExamples
            ).toLocaleString();


        // Vocabulary
        document.getElementById(
            "vocabulary"
        ).textContent =
            Number(
                data.vocabularySize
            ).toLocaleString();


        // Accuracy
        document.getElementById(
            "accuracy"
        ).textContent =
            `${data.validationAccuracy}%`;


    } catch (error) {

        console.error(
            "Model information could not be loaded:",
            error
        );

    }

}


// =========================
// START APPLICATION
// =========================

loadModelInfo();