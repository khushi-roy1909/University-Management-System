package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentLeave extends JFrame implements ActionListener {

    JTextField tfFromDate, tfToDate, tfReason;
    JButton submit, cancel;
    String studentId;

    // Constructor receives student roll number
    StudentLeave(String studentId) {
        this.studentId = studentId;

        setLayout(null);
        setTitle("Student Leave Application");

        JLabel lblHeading = new JLabel("Apply for Leave");
        lblHeading.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblHeading.setBounds(150, 20, 300, 30);
        add(lblHeading);

        JLabel lblFrom = new JLabel("From Date (DD-MM-YYYY)");
        lblFrom.setBounds(50, 80, 200, 30);
        add(lblFrom);

        tfFromDate = new JTextField();
        tfFromDate.setBounds(250, 80, 150, 30);
        add(tfFromDate);

        JLabel lblTo = new JLabel("To Date (DD-MM-YYYY)");
        lblTo.setBounds(50, 130, 200, 30);
        add(lblTo);

        tfToDate = new JTextField();
        tfToDate.setBounds(250, 130, 150, 30);
        add(tfToDate);

        JLabel lblReason = new JLabel("Reason");
        lblReason.setBounds(50, 180, 200, 30);
        add(lblReason);

        tfReason = new JTextField();
        tfReason.setBounds(250, 180, 150, 30);
        add(tfReason);

        submit = new JButton("Submit");
        submit.setBounds(100, 250, 100, 30);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(250, 250, 100, 30);
        cancel.addActionListener(this);
        add(cancel);

        setSize(500, 400);
        setLocation(400, 200);
        setVisible(true);
    }

    // ✅ Helper method to convert date format
    private String convertDateFormat(String date) {
        try {
            java.text.SimpleDateFormat fromUser = new java.text.SimpleDateFormat("dd-MM-yyyy");
            java.text.SimpleDateFormat mySQLFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return mySQLFormat.format(fromUser.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String from = convertDateFormat(tfFromDate.getText());
            String to = convertDateFormat(tfToDate.getText());
            String reason = tfReason.getText();

            try {
                Conn c = new Conn();
                // ✅ Insert with correct column names and converted date format
                String query = "insert into student_leave(rollno, from_date, to_date, reason, status) "
                             + "values('" + studentId + "','" + from + "','" + to + "','" + reason + "','Pending')";

                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Leave Applied Successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentLeave("1323469"); // for testing
    }
}
