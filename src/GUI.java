import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private CSVFileProcessor trainer;

    private double[] percentageClassifier;

    private String[][] frequencyTable;

    public GUI(String title)
    {
        super(title);

        trainer = new CSVFileProcessor("application_data.csv");
        trainer.buildCSVList();
        trainer.generateRule();
        percentageClassifier = trainer.getPermRulesPercentage();
        frequencyTable = trainer.getPermutationTable();
        trainer.printFrequencyTable();

        setSize(400, 300);
        setLayout(new FlowLayout());

        String[] BinaryOptions = {"Yes", "No"};
        creditBox = new JComboBox<>(BinaryOptions);
        jobBox = new JComboBox<>(BinaryOptions);
        debtBox = new JComboBox<>(BinaryOptions);
        collateralBox = new JComboBox<>(BinaryOptions);


        JButton predictionButton = new JButton("Predict");
        predictionButton.addActionListener(this);

        add(new JLabel("Has Good Credit:"));
        add(creditBox);
        add(new JLabel("Has Stable Job"));
        add(jobBox);
        add(new JLabel("Has Debt"));
        add(debtBox);
        add(new JLabel("Has Collateral"));
        add(collateralBox);
        add(predictionButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e)
    {

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




}
