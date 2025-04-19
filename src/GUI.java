import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GUI extends JFrame implements ActionListener
{

    private JComboBox<String> creditBox;
    private JComboBox<String> jobBox;
    private JComboBox<String> debtBox;
    private JComboBox<String> collateralBox;
    private JComboBox<String> acceptedBox;
    private JButton predictionButton;
    private JButton displayDataButton;
    private JButton saveButton;
    private JButton trainButton;
    private boolean trained = false;

    private CSVFileProcessor trainer;

    private double[] percentageClassifier;

    private String[][] frequencyTable;

    public GUI(String title)
    {
        super(title);

        trainer = new CSVFileProcessor("application_data.csv");


        setSize(400, 300);
        setLayout(new FlowLayout());

        String[] BinaryOptions = {"Yes", "No"};
        creditBox = new JComboBox<>(BinaryOptions);
        jobBox = new JComboBox<>(BinaryOptions);
        debtBox = new JComboBox<>(BinaryOptions);
        collateralBox = new JComboBox<>(BinaryOptions);
        acceptedBox = new JComboBox<>(BinaryOptions);

        predictionButton = new JButton("Predict");
        predictionButton.addActionListener(this);
        trainButton = new JButton("Train");
        trainButton.addActionListener(this);
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        add(new JLabel("Has Good Credit:"));
        add(creditBox);
        add(new JLabel("Has Stable Job"));
        add(jobBox);
        add(new JLabel("Has Debt"));
        add(debtBox);
        add(new JLabel("Has Collateral"));
        add(collateralBox);
        add(new JLabel("Is Accepted"));
        add(acceptedBox);
        add(predictionButton);
        add(trainButton);
        add(saveButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == predictionButton)
        {
            predict();
        }
        if (e.getSource() == trainButton)
        {
            train();
        }
        if (e.getSource() == saveButton)
        {
            save((String) creditBox.getSelectedItem(), (String) jobBox.getSelectedItem(), (String) debtBox.getSelectedItem(), (String) collateralBox.getSelectedItem(), (String) acceptedBox.getSelectedItem());
        }



    }

    public void predict()
    {
        if (!trained)
        {
            JOptionPane.showMessageDialog(this, "You have not trained on the data yet", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String credit = (String) creditBox.getSelectedItem();
        String job = (String) jobBox.getSelectedItem();
        String debt = (String) debtBox.getSelectedItem();
        String collateral = (String) collateralBox.getSelectedItem();

        boolean found = false;

        int permIndex = 0;

        for (String[] row : frequencyTable)
        { // start for loop

            if (Objects.equals(row[0], credit) && Objects.equals(row[1], job) && Objects.equals(row[2], debt)
                    && Objects.equals(row[3], collateral))
            { // start if
                double acceptedPercentage = percentageClassifier[permIndex];

                JOptionPane.showMessageDialog(this,
                        "chances of loan approval: " + String.format("%.2f", acceptedPercentage) + "%",
                        "Prediction Result",
                        JOptionPane.INFORMATION_MESSAGE
                );

                found = true;
                break;

            } // end if
            permIndex = permIndex + 1;

        } // end for loop

        if(!found)
        { // start if
            JOptionPane.showMessageDialog(this,
                    "not found",
                    "no match",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } // end if

        for(double element : percentageClassifier)
        {
            System.out.println(element);
        }

    }

    public void train()
    {
        trainer.buildCSVList();
        trainer.generateRule();
        percentageClassifier = trainer.getPermRulesPercentage();
        frequencyTable = trainer.getPermutationTable();
        trained = true;

    }

    public void save(String credit, String job, String debt, String collateral, String accepted)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("application_data.csv", true)))
        {
            String enteredData = String.format("%s,%s,%s,%s,%s\n", credit, job, debt, collateral, accepted);
            bw.write(enteredData);

        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(this,
                    "Error",
                    "Error",
                    JOptionPane.INFORMATION_MESSAGE
            );
            e.printStackTrace();
        }

    }





}
