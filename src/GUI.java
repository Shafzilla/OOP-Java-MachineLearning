import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class GUI extends JFrame implements ActionListener {

    private JComboBox<String> creditBox;
    private JComboBox<String> jobBox;
    private JComboBox<String> debtBox;
    private JComboBox<String> collateralBox;

    public GUI(String title){

        super(title);
        setSize(400, 300);
        setLayout(new FlowLayout());

        String[] BinaryOptions = {"yes", "No"};
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

        int creditIndex = credit.equals("Yes") ? 1 : 0;
        int jobIndex = job.equals("Yes") ? 1 : 0;
        int debtIndex = debt.equals("Yes") ? 1 : 0;
        int collateralIndex = collateral.equals("Yes") ? 1 : 0;


    }
}
