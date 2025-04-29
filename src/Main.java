import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args)
    {

//        CSVFileProcessor fileProcess = new CSVFileProcessor("application_data.csv");
//        fileProcess.buildCSVList();
//        Evaluate evaluation = new Evaluate();
//        evaluation.printData();
        Prediction prediction = new Prediction("application_data.csv");
        prediction.printFreqTable();
        Evaluate evaluate = new Evaluate("application_data.csv");
        evaluate.evaluateAccuracy();
        System.out.println("testing freq table:");
        evaluate.printTestFreqTable();

        System.out.println("prediction frequency table:");
        prediction.printFreqTable();

        System.out.println("testing rule percentage:");
        evaluate.printTestingRulePercentage();






        //GUI myScreen = new GUI("My gui", "application_data.csv");





    }
}