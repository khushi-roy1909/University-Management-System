package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDashboard extends JFrame implements ActionListener {

    String username, name, rollno, course, branch;

    // ✅ Constructor
    public StudentDashboard(String username) {
        this.username = username;

        // Static info for now (you can later fetch from DB)
        name = "Khushi Kumari";
        rollno = "1323469";
        course = "BCA";
        branch = "Computer Science";

        setSize(1540, 850);
        setLocation(0, 0);
        setTitle("Student Dashboard - " + username);
        setLayout(null);

        // --- Menu Bar ---
        JMenuBar mb = new JMenuBar();

        JMenu studentMenu = new JMenu("Student Menu");
        studentMenu.setForeground(Color.BLUE);
        mb.add(studentMenu);

        JMenuItem applyLeave = new JMenuItem("Apply for Leave");
        applyLeave.setBackground(Color.WHITE);
        applyLeave.addActionListener(this);
        studentMenu.add(applyLeave);

        JMenuItem leaveStatus = new JMenuItem("Leave Status");
        leaveStatus.setBackground(Color.WHITE);
        leaveStatus.addActionListener(this);
        studentMenu.add(leaveStatus);

        JMenuItem feeForm = new JMenuItem("Fee Form");
        feeForm.setBackground(Color.WHITE);
        feeForm.addActionListener(this);
        studentMenu.add(feeForm);

        JMenuItem result = new JMenuItem("View Result");
        result.setBackground(Color.WHITE);
        result.addActionListener(this);
        studentMenu.add(result);

        JMenu utility = new JMenu("Utility");
        utility.setForeground(Color.RED);
        mb.add(utility);

        JMenuItem notepad = new JMenuItem("Notepad");
        notepad.setBackground(Color.WHITE);
        notepad.addActionListener(this);
        utility.add(notepad);

        JMenuItem calc = new JMenuItem("Calculator");
        calc.setBackground(Color.WHITE);
        calc.addActionListener(this);
        utility.add(calc);

        JMenu exit = new JMenu("Exit");
        exit.setForeground(Color.RED);
        mb.add(exit);

        JMenuItem ex = new JMenuItem("Exit");
        ex.setBackground(Color.WHITE);
        ex.addActionListener(this);
        exit.add(ex);

        setJMenuBar(mb);

        // --- Background Image ---
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/third.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1500, 800, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1500, 800);
        add(image);

        // --- Welcome Text ---
        JLabel welcome = new JLabel("Welcome " + name + " (Roll No: " + rollno + ", " + course + ")");
        welcome.setBounds(50, 40, 1000, 30);
        welcome.setFont(new Font("Tahoma", Font.BOLD, 22));
        welcome.setForeground(Color.BLACK);
        image.add(welcome);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String msg = ae.getActionCommand();

        if (msg.equals("Exit")) {
            setVisible(false);

        } else if (msg.equals("Apply for Leave")) {
            new StudentLeave(rollno);   // ✅ pass roll number

        } else if (msg.equals("Leave Status")) {
    new StudentLeaveDetails(rollno);
        } else if (msg.equals("Fee Form")) {
            new StudentFeeForm();

        } else if (msg.equals("View Result")) {
            new ExaminationDetails();

        } else if (msg.equals("Notepad")) {
            try { Runtime.getRuntime().exec("notepad.exe"); } catch (Exception e) {}
        } else if (msg.equals("Calculator")) {
            try { Runtime.getRuntime().exec("calc.exe"); } catch (Exception e) {}
        }
    }

    public static void main(String[] args) {
        new StudentDashboard("student01");
    }
}
