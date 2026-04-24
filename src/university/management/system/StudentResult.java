package university.management.system;

import javax.swing.*;
import java.awt.*;

public class StudentResult extends JFrame {
    StudentResult(String username) {
        setTitle("Student Result");
        setSize(600, 400);
        setLocation(400, 200);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Result for: " + username, SwingConstants.CENTER);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setForeground(new Color(0, 102, 204));
        add(heading, BorderLayout.NORTH);

        JLabel marksLabel = new JLabel("Your result will be displayed here.", SwingConstants.CENTER);
        marksLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(marksLabel, BorderLayout.CENTER);

        setVisible(true);
    }
}
