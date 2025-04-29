# OOP Machine Learning 

A Java program that utilises a GUI where the user can enter a set of 
permutations and get a prediction of whether an application would be approved.
The model uses a csv file with 4 columns of categorical data and a 5th column with a label value
to train and define the classifier of whether a loan is likely to be approved or not.
The program allows the user to predict by entering 4 feature values, train the data dynamically from the CSV file,
or enter the label value to save an entry straight to the CSV file.

This project is useful for real world application where you have to train a model to analyse previous 
data and predict an outcome.

## Table of Contents

*   [Features](#features)
*   [Classes](#classes)
*   [Installation](#installation)
*   [Usage](#usage)
*   [Future Enhancements](#future-enhancements)

## Features

This application implements the following functionalities:

1.  **Loan Approval Prediction:** Users can input values for the four features (Has Good Credit, Has Stable Job, Has Debt, Has Collateral) via dropdown menus and receive a percentage probability of their loan application being accepted.
2.  **Dynamic Model Training:** The predictive model can be trained (or retrained) directly from the `application_data.csv` file by clicking the "Train" button. This reads the dataset and calculates the probability rules based on the frequency of outcomes for each input permutation.
3.  **Data Expansion:** Users can add new examples to the training dataset. By selecting values for all four features *and* the known outcome ("Application Accepted"), users can click "Save" to append this new entry to the `application_data.csv` file. The model is automatically retrained after saving.
4.  **Accuracy Evaluation:** The application assesses the predictor's accuracy. When "Train" is clicked, it internally trains a model on the first 150 rows of data and then tests its predictions against the next 50 rows, displaying the calculated accuracy percentage on the GUI.

## Classes

The project is structured into the following main classes:

*   **`GUI`**:
    *   Provides the main interactive window using Java Swing.
    *   Contains `JComboBox` elements for user input (features and label).
    *   Includes buttons ("Predict", "Train", "Save") to trigger core actions.
    *   Displays model accuracy after training.
    *   Uses `JOptionPane` for feedback messages (predictions, confirmations, errors).
    *   Handles button click events to orchestrate the application's workflow.

*   **`CSVFileProcessor`**:
    *   Handles reading and parsing the input `application_data.csv` file.
    *   Extracts the header row for feature names.
    *   Parses data rows into an `ArrayList<Map<String, String>>` structure.
    *   Generates and stores all 16 possible permutations of the four input features.
    *   Provides methods for other classes to access the loaded data, features, and permutations.

*   **`Prediction`**:
    *   Analyzes the *entire* dataset loaded via `CSVFileProcessor`.
    *   Calculates a frequency table (`frequencyOutcomes`) counting "Yes"/"No" outcomes for each input permutation across all data.
    *   Generates probability rules (`permRulesPercentage`) based on the full dataset frequencies.
    *   Provides these rules to the `GUI` for making predictions.
    *   Serves as the base class for `Evaluate`.

*   **`Evaluate`**:
    *   Extends `Prediction` to specifically assess model accuracy.
    *   Performs a train/test split: trains a temporary model using only the *first 150 rows*.
    *   Generates a simple binary classifier ("Yes"/"No") based on the 150-row training rules (>=50% probability = "Yes").
    *   Tests this classifier against the *next 50 rows* (150-199).
    *   Compares predictions to actual labels in the test set to calculate accuracy.
    *   Provides the calculated accuracy score to the `GUI`.

*   **`Main`**: (Assuming you have one)
    *   Contains the `main` method, the entry point of the application.
    *   Instantiates and displays the `GUI` window.

## Installation

1.  **Prerequisites:**
    *   Java Development Kit (JDK) installed (e.g., version 8 or higher).
    *   An IDE like IntelliJ IDEA, Eclipse, or VS Code (optional, but recommended).
    *   The `application_data.csv` file present in the project's root directory (or specified path).
    *   An `images` folder containing the icon files (`predictIcon.jpg`, `trainingIcon.png`, `saveIcon.png`) in the project's root directory.
2.  **Steps:**
    *   Clone or download the project files from the repository.
    *   Open the project in your IDE or compile the `.java` files using a terminal (`javac *.java`).
    *   Run the `Main` class (or execute the compiled code: `java Main`).

## Usage

1.  Launch the application by running the `Main` class.
2.  The GUI window will appear.
    ![](images/img_2.png)
3.  **Train the Model:** Click the "Train" button first. This reads the `application_data.csv`, calculates prediction rules, evaluates accuracy on a subset, and displays the accuracy.
4.  **Make a Prediction:**
    *   Select "Yes" or "No" for the first four features ("Has Good Credit", "Has Stable Job", etc.).
    *   Click the "Predict" button. A pop-up will show the calculated percentage chance of the loan being accepted based on the trained model.
5.  **Save a New Entry:**
    *   Select "Yes" or "No" for all four features.
    *   Select the *actual known outcome* ("Yes" or "No") in the fifth dropdown ("Application Accepted").
    *   Click the "Save" button. The entry will be appended to the `application_data.csv` file, and the model will be automatically retrained.

## Future Enhancements

If more time were available, I would add the folowing improvements:


*   **Data Validation:** Add validation to make sure the loaded CSV data is as expected ("Yes"/"No" values only, correct number of columns). The application would prevent saving if input is incomplete.
*   **UI Enhancements:**
    *   Use more sophisticated layout managers (like `GridBagLayout` or `BorderLayout`) for better component arrangement and resizing. this would allow me to have a clearly defined prediction section and saving section
    *   Display prediction results directly on the main GUI instead of only in a pop-up.
    *   Add a status bar or log area for more detailed feedback.
*   **Model Persistence:** Save the calculated `frequencyOutcomes` or `permRulesPercentage` to a separate file after training, so the application doesn't always have to need to retrain on startup. Add a "Load Model" button.
*   **Input Flexibility:** Allow specifying the input CSV file path via the GUI or command-line arguments instead of hardcoding it in Main.java.
*   **Unit Testing:** Add unit tests for the `CSVFileProcessor`, `Prediction`, and `Evaluate` classes to verify their logic independently.