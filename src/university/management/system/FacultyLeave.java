package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FacultyLeave extends JFrame implements ActionListener {

    JTextField tfrom, tto;
    Choice ctime;
    JButton submit, cancel;

    // The logged-in faculty ID (for reference)  
    private String facultyId;

    // NEW: dropdown to choose any faculty ID from admin list  
    JComboBox<String> comboFacultyIds;

    // Constructor that takes logged-in faculty ID
    public FacultyLeave(String facultyId) {
        this.facultyId = facultyId;

        setSize(500, 450);
        setLocation(500, 200);
        setTitle("Faculty Leave Application");
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel heading = new JLabel("Apply for Leave");
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        heading.setBounds(150, 20, 300, 30);
        add(heading);

        JLabel lblSelectId = new JLabel("Select Faculty ID:");
        lblSelectId.setBounds(50, 80, 150, 30);
        add(lblSelectId);

        comboFacultyIds = new JComboBox<>();
        comboFacultyIds.setBounds(200, 80, 150, 30);
        add(comboFacultyIds);

        // Populate combo box from database with all faculty IDs
        try {
            Conn c = new Conn();
           String query = "SELECT faculty_id FROM facultyId";
// change table/column as per your DB
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                comboFacultyIds.addItem(rs.getString("faculty_id"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the logged-in faculty ID as default selected
        comboFacultyIds.setSelectedItem(facultyId);

        JLabel lblFrom = new JLabel("From Date (YYYY-MM-DD):");
        lblFrom.setBounds(50, 130, 150, 30);
        add(lblFrom);

        tfrom = new JTextField();
        tfrom.setBounds(220, 130, 150, 25);
        add(tfrom);

        JLabel lblTo = new JLabel("To Date (YYYY-MM-DD):");
        lblTo.setBounds(50, 180, 150, 30);
        add(lblTo);

        tto = new JTextField();
        tto.setBounds(220, 180, 150, 25);
        add(tto);

        JLabel lblTime = new JLabel("Time Duration:");
        lblTime.setBounds(50, 230, 150, 30);
        add(lblTime);

        ctime = new Choice();
        ctime.add("Full Day");
        ctime.add("Half Day");
        ctime.setBounds(220, 230, 150, 25);
        add(ctime);

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setBounds(100, 300, 100, 30);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBackground(Color.GRAY);
        cancel.setForeground(Color.WHITE);
        cancel.setBounds(230, 300, 100, 30);
        cancel.addActionListener(this);
        add(cancel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String selectedId = (String) comboFacultyIds.getSelectedItem();
            String from = tfrom.getText();
            String to = tto.getText();
            String duration = ctime.getSelectedItem();
            String status = "Pending";

            if (from.isEmpty() || to.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "INSERT INTO teacher_leave (empId, from_date, to_date, duration, status) VALUES ('"
                        + selectedId + "', '" + from + "', '" + to + "', '" + duration + "', '" + status + "')";
                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Leave Request Submitted Successfully for ID: " + selectedId);
                setVisible(false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error while submitting leave!");
                e.printStackTrace();
            }

        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        // test with a dummy faculty ID
        new FacultyLeave("F101");
    }
}
