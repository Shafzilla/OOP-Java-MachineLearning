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

* [Description](#description)
* [Installation](#installation)
* [Usage](#usage)
* [Prediction](#Prediction)

## Description

Provide a more detailed explanation of your project. You can include:

* Reduces the need for an individual to speak with a human to find out wether their 
loan would be approved
* features : predictor, train classifier, save to csv file
## Installation

Detailed instructions on how to install and set up the project.
To install this project :
pull all the files



* **Prerequisites:**
    * For all features to be functional, you need all the classes

* **Steps:**
    1.  pull all the files from github

![Project Logo](images/img_1.png)


![Project Logo](images/img.png)
    2.  Run main class
    3.  (Optional) replace "application#_data.csv" with another file
## Usage

* Feature values can either be 'Yes' or 'No'
* the feature Values in the GUI automatically appear as Yes,Yes,Yes,Yes.
* The 'Predict' button outputs the chances of acceptance. Only uses the 4 feature values.
* The 'Train' button trains the model and defines the classifier
* The 'Save' button saves the selected feature and label values to the CSV file

![Project Logo](images/img_2.png)

* Include code snippets, screenshots, or GIFs if helpful.
* Explain any important concepts or workflows.


## Prediction

The CSV file is dynamically read. The values of the CSV file are put into an arraylist of maps.
Each element in a map is a column, and each element in the arraylisst is a row. This arraylist 
is passed to the Prediction class. In this class, a frequency table is generated from the values
in the arraylist. This frequency table is used to generate the percentages of the likelyhood of 
an application being accepted for each permutation. These rules are then passed to the GUI class 
where the feature values are matched up to the permutation and the rule is retrieved and displayed.


## Accuracy Evaluation

The arrayList is passed to the Evaluate class where a frequency table is generated based off of the 
first 150 rows and a classifer is defined to get the likelyhood of an application being accepted (Yes/No).
From the arraylist, the next 50 rows are compared with the classifier. The number of times the classifier
was correct / incorrect were counted, and used to calculate the accuracy of the classifier.

## Saving Entry

When the save button is pressed, the selected feature values and the label value are saved directly to the 
csv file.

## GUI (Graphical User Interface)

The `GUI` class provides the main interactive window for the application, built using Java Swing.

*   **Window Setup:** It extends `JFrame` to create the application window and uses a `FlowLayout` to arrange components simply from left to right.
*   **User Input:** Five `JComboBox` elements allow the user to select "Yes" or "No" for the four features (HasGoodCredit, HasStableJob, HasDebt, HasCollateral) and the outcome (ApplicationIsAccepted). These are used both for making predictions and for saving new data entries.
*   **Action Buttons:**
    *   **Predict:** Takes the current selections from the first four combo boxes, looks up the corresponding pre-calculated prediction rule (if the model has been trained), and displays the percentage chance of acceptance in a `JOptionPane` pop-up. Requires the model to be trained first.
    *   **Train:** Initiates the training process by creating `Prediction` and `Evaluate` objects using the data from the specified CSV file. It calculates and displays the model's accuracy on the GUI itself and shows a confirmation message via `JOptionPane`.
    *   **Save:** Takes the current selections from all five combo boxes and appends them as a new comma-separated row to the `application_data.csv` file. This allows users to add new training examples directly through the interface. Buttons feature custom icons loaded via the `makeIcon` helper method.
*   **Event Handling:** The class implements `ActionListener` to detect button clicks and trigger the corresponding `predict()`, `train()`, or `save()` methods.
*   **Feedback:** `JOptionPane` dialog boxes are used extensively to provide feedback to the user, such as prediction results, training confirmation, save confirmation, and error messages   **Event Handling:** The class implements `ActionListener` to detect button clicks a

## CSVFileProcessor

The `CSVFileProcessor` class handles the reading and initial processing of the input CSV data file (`application_data.csv` by default).

*   **File Reading:** It takes the CSV file path in its constructor and reads the file line by line.
*   **Data Parsing:**
    *   It extracts the header row to identify the feature names (e.g., "HasGoodCredit", "HasStableJob", etc.).
    *   It parses each subsequent data row, splitting the comma-separated values.
*   **Data Structure:** It stores the processed data in an `ArrayList<Map<String, String>>`. Each `Map` in the list represents a single row from the CSV, mapping feature names (keys) to their corresponding values ("Yes" or "No").
*   **Permutation Generation:** It calculates and stores all 16 possible permutations (2^4) of the four input features ("Yes"/"No"). This permutation table is used by other classes for matching input conditions.
*   **Data Access:** It provides getter methods (`getListCSV()`, `getFeatureValues()`, `getPermutationTable()`) for other classes (`Prediction`, `Evaluate`, `GUI`) to access the processed data, feature names, and the permutation table.

## Prediction

The `Prediction` class is responsible for analyzing the entire dataset loaded from the CSV file to calculate the probability of a "Yes" outcome (ApplicationIsAccepted) for each possible combination of input features.

*   **Initialization:** Takes the CSV file path in its constructor and uses `CSVFileProcessor` to load the complete dataset, feature names, and the pre-generated permutation table.
*   **Frequency Calculation:** The `generateFrequencyTable()` method iterates through every row in the loaded dataset. For each row, it matches the four feature values to one of the 16 permutations and increments the count for either "Yes" or "No" outcomes in the `frequencyOutcomes` table based on the row's "ApplicationIsAccepted" value.
*   **Rule Generation:** The `generateRule()` method calculates the percentage chance of a "Yes" outcome for each of the 16 permutations based on the counts stored in the `frequencyOutcomes` table. These percentages are stored in the `permRulesPercentage` array.
*   **Data Provision:** It provides getter methods (e.g., `getPermRulesPercentage()`, `getFrequencyOutcomes()`) to make the calculated frequencies and percentage rules available to other classes, primarily the `GUI` for displaying predictions.
*   **Base Class:** It serves as the parent class for `Evaluate`, which inherits its data loading capabilities and basic structure but performs calculations on a specific subset of the data for accuracy testing.

## Evaluate

The `Evaluate` class extends `Prediction` and is specifically designed to assess the predictive accuracy of the model based on the provided dataset.

*   **Purpose:** To determine how well the rules derived from one part of the data predict outcomes in another part.
*   **Inheritance:** It inherits data loading and basic structure from the `Prediction` class.
*   **Train/Test Split:** Unlike `Prediction` which uses the whole dataset, `Evaluate` performs a specific train/test split:
    *   **Training Subset:** It calculates a separate frequency table (`testingFreqTable`) and percentage rules (`testingRulePercentage`) using only the *first 150 rows* of the dataset.
    *   **Classifier Generation:** Based on these training rules, it creates a simple binary classifier (`testLikelyHood`) - if the percentage rule for a permutation is >= 50%, the likely outcome is "Yes", otherwise "No".
    *   **Testing Subset:** It then tests this classifier against the *next 50 rows* (rows 150 to 199) of the dataset.
*   **Accuracy Calculation:** The `testPredictorAccuracy()` method compares the classifier's prediction ("Yes" or "No") for each row in the testing subset against the actual "ApplicationIsAccepted" value in that row. It counts the number of correct and incorrect predictions.
*   **Result:** It calculates the final accuracy as the percentage of correct predictions within the testing subset and stores it in `testAccuracy`.
*   **Usage:** The `evaluateAccuracy()` method orchestrates the entire process (training on 150, testing on 50). The `getTestAccuracy()` method allows other classes, like the `GUI`, to retrieve and display the calculated accuracy score.