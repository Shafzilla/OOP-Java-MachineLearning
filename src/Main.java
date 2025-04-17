import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args)
    {

        CSVFileProcessor fileProcess = new CSVFileProcessor();
        fileProcess.buildCSVList();
        //fileProcess.printCSVList();
        fileProcess.printFrequencyTable();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI myScreen = new GUI("My gui");

            }
        });




    }
}