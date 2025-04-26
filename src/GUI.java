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
    private String file;

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

        file = "application_data.csv";

        trainer = new CSVFileProcessor(file);


        setSize(800, 600);
        setLayout(new FlowLayout());




        String[] BinaryOptions = {"Yes", "No"};
        creditBox = new JComboBox<>(BinaryOptions);
        jobBox = new JComboBox<>(BinaryOptions);
        debtBox = new JComboBox<>(BinaryOptions);
        collateralBox = new JComboBox<>(BinaryOptions);
        acceptedBox = new JComboBox<>(BinaryOptions);

        ImageIcon icon = new ImageIcon("predictIcon.jpg");
        Image scaledImage = icon.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        ImageIcon icon2 = new ImageIcon("trainingIcon.png");
        Image scaledImage2 = icon2.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon resizedIcon2 = new ImageIcon(scaledImage2);

        ImageIcon icon3 = new ImageIcon("saveIcon.png");
        Image scaledImage3 = icon3.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon resizedIcon3 = new ImageIcon(scaledImage3);



        predictionButton = new JButton(resizedIcon);
        predictionButton.addActionListener(this);
        trainButton = new JButton(resizedIcon2);
        trainButton.addActionListener(this);
        saveButton = new JButton(resizedIcon3);
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

//        JPanel panel1 = new JPanel();
//        panel1.add(new JLabel("Has Good Credit:"));
//        panel1.add(creditBox);
//        panel1.add(new JLabel("Has Stable Job"));
//        panel1.add(jobBox);
//        panel1.add(new JLabel("Has Debt"));
//        panel1.add(debtBox);
//        panel1.add(new JLabel("Has Collateral"));
//        panel1.add(collateralBox);
//        panel1.add(new JLabel("Is Accepted"));
//        panel1.add(acceptedBox);
//        panel1.add(saveButton);
//
//
//
//
//
//        JPanel panel2 = new JPanel();
//        panel2.add(new JLabel("Has Good Credit:"));
//        panel2.add(creditBox);
//        panel2.add(new JLabel("Has Stable Job"));
//        panel2.add(jobBox);
//        panel2.add(new JLabel("Has Debt"));
//        panel2.add(debtBox);
//        panel2.add(new JLabel("Has Collateral"));
//        panel2.add(collateralBox);
//        panel2.add(new JLabel("Is Accepted"));
//        panel2.add(acceptedBox);
//        panel2.add(saveButton);
//
//        JPanel panel3 = new JPanel();
//        panel3.add(new JLabel("Has Good Credit:"));
//        panel3.add(creditBox);
//        panel3.add(new JLabel("Has Stable Job"));
//        panel3.add(jobBox);
//        panel3.add(new JLabel("Has Debt"));
//        panel3.add(debtBox);
//        panel3.add(new JLabel("Has Collateral"));
//        panel3.add(collateralBox);
//        panel3.add(new JLabel("Is Accepted"));
//        panel3.add(acceptedBox);
//        panel3.add(saveButton);
//
//
//
//
//
//        JTabbedPane tabbedPane = new JTabbedPane();
//        tabbedPane.add("Predict", panel1);
//        tabbedPane.add("save", panel2);
//        tabbedPane.add("predict", panel3);


//        add(tabbedPane);




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
        try
        {
            Prediction prediction = new Prediction(file);
            Evaluate evaluation = new Evaluate(file);
            evaluation.evaluateAccuracy();
            double accuracy = evaluation.getTestAccuracy();
            percentageClassifier = prediction.getPermRulesPercentage();
            frequencyTable = trainer.getPermutationTable();
            trained = true;

            JLabel accuracyLabel = new JLabel("Accuracy" + accuracy);
            add(accuracyLabel);
            revalidate();
            repaint();



            JOptionPane.showMessageDialog(this,
                    "Model Trained",
                    "Trained",
                    JOptionPane.INFORMATION_MESSAGE
            );

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this,
                    "Error",
                    "Error",
                    JOptionPane.INFORMATION_MESSAGE
            );
            e.printStackTrace();
        }


    }

    public void save(String credit, String job, String debt, String collateral, String accepted)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true)))
        {
            String enteredData = String.format("%s,%s,%s,%s,%s\n", credit, job, debt, collateral, accepted);
            bw.write(enteredData);
            JOptionPane.showMessageDialog(this,
                    "Entry Saved",
                    "Entry Saved",
                    JOptionPane.INFORMATION_MESSAGE
            );

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
