package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminDashboard extends JFrame implements ActionListener {

    JTable table;
    JButton approveBtn, rejectBtn, refreshBtn;
    DefaultListModel<String> listModel;
    JScrollPane scrollPane;

    AdminDashboard() {
        setTitle("Admin Dashboard - Leave Approvals");
        setSize(900, 600);
        setLocation(250, 100);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 247, 250));

        JLabel heading = new JLabel("Admin Dashboard - Leave Management");
        heading.setBounds(180, 20, 600, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setForeground(new Color(0, 102, 204));
        add(heading);

        // ---- Table to show leave requests ----
        String[] columnNames = {"ID", "Username", "Status"};
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(150, 100, 600, 300);
        add(scrollPane);

        // ---- Buttons ----
        approveBtn = new JButton("Approve");
        approveBtn.setBounds(200, 430, 150, 40);
        approveBtn.setBackground(new Color(0, 153, 0));
        approveBtn.setForeground(Color.WHITE);
        approveBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        approveBtn.addActionListener(this);
        add(approveBtn);

        rejectBtn = new JButton("Reject");
        rejectBtn.setBounds(380, 430, 150, 40);
        rejectBtn.setBackground(new Color(204, 0, 0));
        rejectBtn.setForeground(Color.WHITE);
        rejectBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        rejectBtn.addActionListener(this);
        add(rejectBtn);

        refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(560, 430, 150, 40);
        refreshBtn.setBackground(new Color(0, 102, 204));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        refreshBtn.addActionListener(this);
        add(refreshBtn);

        loadLeaveRequests();

        setVisible(true);
    }

    public void loadLeaveRequests() {
        try {
            Conn c = new Conn();
            String query = "SELECT id, username, status FROM studentleave";
            ResultSet rs = c.s.executeQuery(query);

            // Convert ResultSet to JTable
            table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "⚠️ Please select a student leave request first!");
            return;
        }

        String id = table.getValueAt(row, 0).toString();
        String username = table.getValueAt(row, 1).toString();

        if (ae.getSource() == approveBtn) {
            updateLeaveStatus(id, "Approved", username);
        } else if (ae.getSource() == rejectBtn) {
            updateLeaveStatus(id, "Rejected", username);
        } else if (ae.getSource() == refreshBtn) {
            loadLeaveRequests();
        }
    }

    private void updateLeaveStatus(String id, String status, String username) {
        try {
            Conn c = new Conn();
            String query = "UPDATE studentleave SET status='" + status + "' WHERE id='" + id + "'";
            c.s.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "✅ Leave request for '" + username + "' marked as " + status);
            loadLeaveRequests();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
