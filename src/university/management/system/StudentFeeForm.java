package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentFeeForm extends JFrame implements ActionListener {

    JTextField rollField, courseField, amountField, usernameField;
    JButton pay, back;

    public StudentFeeForm() {
        setTitle("Student Fee Payment");
        setSize(550, 450);
        setLocation(400, 200);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        JLabel heading = new JLabel("Student Fee Payment");
        heading.setBounds(140, 30, 300, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading.setForeground(new Color(0, 102, 204));
        add(heading);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(100, 100, 120, 25);
        add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(220, 100, 180, 25);
        add(usernameField);

        JLabel lblRoll = new JLabel("Roll Number:");
        lblRoll.setBounds(100, 140, 120, 25);
        add(lblRoll);

        rollField = new JTextField();
        rollField.setBounds(220, 140, 180, 25);
        add(rollField);

        JLabel lblCourse = new JLabel("Course:");
        lblCourse.setBounds(100, 180, 120, 25);
        add(lblCourse);

        courseField = new JTextField();
        courseField.setBounds(220, 180, 180, 25);
        add(courseField);

        JLabel lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(100, 220, 120, 25);
        add(lblAmount);

        amountField = new JTextField();
        amountField.setBounds(220, 220, 180, 25);
        add(amountField);

        pay = new JButton("Pay");
        pay.setBounds(150, 300, 90, 35);
        pay.setBackground(new Color(0, 153, 0));
        pay.setForeground(Color.WHITE);
        pay.addActionListener(this);
        add(pay);

        back = new JButton("Back");
        back.setBounds(270, 300, 90, 35);
        back.setBackground(new Color(204, 0, 0));
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == pay) {
            String username = usernameField.getText().trim();
            String rollno = rollField.getText().trim();
            String course = courseField.getText().trim();
            String amount = amountField.getText().trim();

            // --- Validation ---
            if (username.isEmpty() || rollno.isEmpty() || course.isEmpty() || amount.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
                return;
            }

            try {
                Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Amount must be a valid number!");
                return;
            }

            // --- Database Insertion ---
            try {
                Conn c = new Conn();
                String query = "INSERT INTO studentfees (username, rollno, course, amount) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, username);
                pst.setString(2, rollno);
                pst.setString(3, course);
                pst.setString(4, amount);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Fee Submitted Successfully!");
                usernameField.setText("");
                rollField.setText("");
                courseField.setText("");
                amountField.setText("");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error while submitting fee!\n" + e.getMessage());
                e.printStackTrace();
            }

        } else if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentFeeForm();
    }
}
