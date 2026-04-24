package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.net.MalformedURLException;


public class FacultyDashboard extends JFrame implements ActionListener {

    String facultyId; // ✅ Declare faculty ID variable

    // ✅ Constructor now requires a faculty ID (from login)
    public FacultyDashboard(String facultyId) {
        this.facultyId = facultyId; // ✅ store facultyId for future use

        setSize(1540, 850);
        setLocation(0, 0);
        setTitle("Faculty Dashboard - " + facultyId);
        setLayout(null);

        // Background image
     // After setLayout(null) etc
// Load resource
URL bgUrl = getClass().getResource("/icons/third.jpg");
if (bgUrl == null) {
    System.err.println("Background image not found: /icons/third.jpg");
} else {
    ImageIcon i1 = new ImageIcon(bgUrl);
    Image i2 = i1.getImage().getScaledInstance(1500, 800, Image.SCALE_DEFAULT);
    ImageIcon i3 = new ImageIcon(i2);
    JLabel image = new JLabel(i3);
    image.setBounds(0, 0, 1500, 800);
    add(image);
    // Now, if you want to add other components on top of this
    // you can either add them to image (image.setLayout(null);…) or ensure image is added first
}

// then add JMenuBar and so on


        JMenuBar mb = new JMenuBar();

        // Faculty Menu
        JMenu facultyMenu = new JMenu("Faculty Menu");
        facultyMenu.setForeground(Color.BLUE);
        mb.add(facultyMenu);

        JMenuItem applyLeave = new JMenuItem("Apply for Leave");
        applyLeave.setBackground(Color.WHITE);
        applyLeave.addActionListener(this);
        facultyMenu.add(applyLeave);

        JMenuItem leaveStatus = new JMenuItem("Leave Status");
        leaveStatus.setBackground(Color.WHITE);
        leaveStatus.addActionListener(this);
        facultyMenu.add(leaveStatus);

        // Examination Menu
        JMenu exam = new JMenu("Examination");
        exam.setForeground(Color.RED);
        mb.add(exam);

        JMenuItem entermarks = new JMenuItem("Enter Marks");
        entermarks.setBackground(Color.WHITE);
        entermarks.addActionListener(this);
        exam.add(entermarks);

        JMenuItem result = new JMenuItem("View Results");
        result.setBackground(Color.WHITE);
        result.addActionListener(this);
        exam.add(result);

        // Fee Menu
        JMenu fee = new JMenu("Fee Details");
        fee.setForeground(Color.BLUE);
        mb.add(fee);

        JMenuItem feestructure = new JMenuItem("View Fee Structure");
        feestructure.setBackground(Color.WHITE);
        feestructure.addActionListener(this);
        fee.add(feestructure);

        // Utility
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

        // Exit Menu
        JMenu exit = new JMenu("Exit");
        exit.setForeground(Color.RED);
        mb.add(exit);

        JMenuItem ex = new JMenuItem("Exit");
        ex.setBackground(Color.WHITE);
        ex.addActionListener(this);
        exit.add(ex);

        setJMenuBar(mb);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String msg = ae.getActionCommand();

        if (msg.equals("Exit")) {
            setVisible(false);

        } else if (msg.equals("Apply for Leave")) {
            new FacultyLeave(facultyId);  // ✅ pass facultyId properly

        } else if (msg.equals("Leave Status")) {
            new FacultyLeaveDetails(facultyId); // ✅ pass facultyId properly

        } else if (msg.equals("Enter Marks")) {
            new EnterMarks();

        } else if (msg.equals("View Results")) {
            new ExaminationDetails();

        } else if (msg.equals("View Fee Structure")) {
            new FeeStructure();

        } else if (msg.equals("Notepad")) {
            try {
                Runtime.getRuntime().exec("notepad.exe");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (msg.equals("Calculator")) {
            try {
                Runtime.getRuntime().exec("calc.exe");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // ✅ Example: pass a faculty ID for testing
        new FacultyDashboard("F101");
    }
}
