package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class StudentLeaveStatus extends JFrame {
    
    JTable table;

    StudentLeaveStatus() {
        setSize(900, 600);
        setLocation(300, 100);
        setLayout(null);

        JLabel heading = new JLabel("Your Leave Status");
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        heading.setBounds(350, 20, 300, 30);
        add(heading);

        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(20, 80, 850, 400);
        add(jsp);

        loadTableData();

        setVisible(true);
    }

    void loadTableData() {
        try {
            Conn c = new Conn();
            String query = "select * from studentleave"; 
            ResultSet rs = c.s.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new StudentLeaveStatus();
    }
}
