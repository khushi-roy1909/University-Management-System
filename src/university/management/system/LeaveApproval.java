package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LeaveApproval extends JFrame implements ActionListener {
    JTable table;
    JButton approveBtn, rejectBtn, refreshBtn;
    Choice leaveType; // To choose Student / Faculty

    LeaveApproval() {
        setTitle("Manage Leave Requests");
        setSize(900, 600);
        setLocation(300, 100);
        setLayout(null);

        JLabel heading = new JLabel("Leave Requests (Student / Faculty)");
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        heading.setBounds(250, 20, 400, 30);
        add(heading);

        leaveType = new Choice();
        leaveType.add("Student");
        leaveType.add("Faculty");
        leaveType.setBounds(50, 70, 150, 25);
        add(leaveType);

        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 120, 780, 300);
        add(jsp);

        approveBtn = new JButton("Approve");
        approveBtn.setBounds(200, 450, 120, 30);
        approveBtn.addActionListener(this);
        add(approveBtn);

        rejectBtn = new JButton("Reject");
        rejectBtn.setBounds(350, 450, 120, 30);
        rejectBtn.addActionListener(this);
        add(rejectBtn);

        refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(500, 450, 120, 30);
        refreshBtn.addActionListener(this);
        add(refreshBtn);

        showTableData("Student");

        leaveType.addItemListener(e -> showTableData(leaveType.getSelectedItem()));

        setVisible(true);
    }

    public void showTableData(String type) {
        try {
            Conn c = new Conn();
            String query = "";
            if (type.equals("Student")) {
                query = "SELECT rollno AS ID, from_date, to_date, duration, reason, status FROM student_leave";
            } else {
                query = "SELECT empId AS ID, from_date, to_date, duration, reason, status FROM teacher_leave";
            }

            ResultSet rs = c.s.executeQuery(query);
            table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a leave record first!");
                return;
            }

            String id = table.getValueAt(row, 0).toString();
            String type = leaveType.getSelectedItem();
            String status = "";

            if (ae.getSource() == approveBtn) status = "Approved";
            else if (ae.getSource() == rejectBtn) status = "Rejected";
            else if (ae.getSource() == refreshBtn) {
                showTableData(type);
                return;
            }

            Conn c = new Conn();
            String query = "";
            if (type.equals("Student")) {
                query = "UPDATE student_leave SET status='" + status + "' WHERE rollno='" + id + "'";
            } else {
                query = "UPDATE teacher_leave SET status='" + status + "' WHERE empId='" + id + "'";
            }

            c.s.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Leave " + status + " successfully!");

            showTableData(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LeaveApproval();
    }
}
