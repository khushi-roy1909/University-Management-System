package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JButton login, cancel;
    JTextField tfusername;
    JPasswordField tfpassword;
    JComboBox<String> roleBox;

    Login() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(40, 20, 100, 20);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(150, 20, 150, 20);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40, 70, 100, 20);
        add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(150, 70, 150, 20);
        add(tfpassword);

        JLabel lblrole = new JLabel("Login As");
        lblrole.setBounds(40, 110, 100, 20);
        add(lblrole);

        String roles[] = {"Student", "Faculty", "Admin"};
        roleBox = new JComboBox<>(roles);
        roleBox.setBounds(150, 110, 150, 20);
        roleBox.setBackground(Color.WHITE);
        add(roleBox);

        login = new JButton("Login");
        login.setBounds(40, 160, 120, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        login.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(login);

        cancel = new JButton("Cancel");
        cancel.setBounds(180, 160, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 0, 200, 200);
        add(image);

        setSize(600, 300);
        setLocation(500, 250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String username = tfusername.getText();
            String password = new String(tfpassword.getPassword());
            String role = (String) roleBox.getSelectedItem();

            String query = "select * from login where username='" + username + "' and password='" + password + "' and role='" + role + "'";

            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    setVisible(false);

                    // ✅ Open the correct dashboard based on role
                    if (role.equals("Admin")) {
                        new Project();
                    } else if (role.equals("Faculty")) {
                         String facultyId = tfusername.getText();
                        new FacultyDashboard(facultyId);
                    } else if (role.equals("Student")) {
                        new StudentDashboard(username); // ✅ Pass username to StudentDashboard
                     try {
        Conn c2 = new Conn();
        String q2 = "SELECT * FROM student_leave WHERE rollno='" + username + "' ORDER BY id DESC LIMIT 1";
        ResultSet rs2 = c2.s.executeQuery(q2);
        if (rs2.next()) {
            String stat = rs2.getString("status");
            String from = rs2.getString("from_date");
            String to = rs2.getString("to_date");
            JOptionPane.showMessageDialog(null,
                "Last Leave Request (" + from + " to " + to + ") was " + stat + ".");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username, password, or role");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            }

        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
