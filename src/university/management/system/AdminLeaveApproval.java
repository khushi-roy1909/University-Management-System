package university.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminLeaveApproval extends JFrame implements ActionListener {

    JTable table;
    DefaultTableModel model;
    JButton approveBtn, rejectBtn, refreshBtn;

    public AdminLeaveApproval() {
        setTitle("Admin - Leave Approval Portal");
        setSize(900, 500);
        setLocation(350, 150);
        setLayout(null);

        JLabel heading = new JLabel("Leave Approval Management");
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading.setBounds(280, 20, 400, 30);
        add(heading);

        // Table setup
        String[] columnNames = {"Type", "ID", "From Date", "To Date", "Duration", "Reason", "Status"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(30, 80, 820, 250);
        add(sp);

        approveBtn = new JButton("Approve");
        approveBtn.setBounds(200, 360, 120, 30);
        approveBtn.addActionListener(this);
        add(approveBtn);

        rejectBtn = new JButton("Reject");
        rejectBtn.setBounds(360, 360, 120, 30);
        rejectBtn.addActionListener(this);
        add(rejectBtn);

        refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(520, 360, 120, 30);
        refreshBtn.addActionListener(this);
        add(refreshBtn);

        loadLeaveData();
        setVisible(true);
    }

    // 🧾 Load all leave requests from both tables
    private void loadLeaveData() {
        model.setRowCount(0); // clear table
        try {
            Conn c = new Conn();

            // student leave
            ResultSet rs1 = c.s.executeQuery("SELECT rollno AS ID, from_date, to_date, duration, reason, status FROM student_leave");
            while (rs1.next()) {
                model.addRow(new Object[]{
                        "Student",
                        rs1.getString("ID"),
                        rs1.getString("from_date"),
                        rs1.getString("to_date"),
                        rs1.getString("duration"),
                        rs1.getString("reason"),
                        rs1.getString("status")
                });
            }

            // faculty leave
            ResultSet rs2 = c.s.executeQuery("SELECT empId AS ID, from_date, to_date, duration, '' AS reason, status FROM teacher_leave");
            while (rs2.next()) {
                model.addRow(new Object[]{
                        "Faculty",
                        rs2.getString("ID"),
                        rs2.getString("from_date"),
                        rs2.getString("to_date"),
                        rs2.getString("duration"),
                        "", // reason column blank
                        rs2.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first!");
            return;
        }

        String type = table.getValueAt(row, 0).toString();
        String id = table.getValueAt(row, 1).toString();

        if (ae.getSource() == approveBtn || ae.getSource() == rejectBtn) {
            String newStatus = (ae.getSource() == approveBtn) ? "Approved" : "Rejected";

            try {
                Conn c = new Conn();
                String query = "";

                if (type.equals("Student")) {
                    query = "UPDATE student_leave SET status = '" + newStatus + "' WHERE rollno = '" + id + "'";
                } else {
                    query = "UPDATE teacher_leave SET status = '" + newStatus + "' WHERE empId = '" + id + "'";
                }

                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Leave " + newStatus + "!");
                loadLeaveData();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == refreshBtn) {
            loadLeaveData();
        }
    }

    public static void main(String[] args) {
        new AdminLeaveApproval();
    }
}
