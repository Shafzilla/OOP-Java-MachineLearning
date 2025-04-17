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


    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
