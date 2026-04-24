package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FacultyLeaveDetails extends JFrame {

    JTable table;

    FacultyLeaveDetails(String empId) {
        setTitle("Faculty Leave Details - " + empId);
        setSize(700, 400);
        setLocation(400, 200);

        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        add(jsp);

        try {
            Conn c = new Conn();
            String query = "SELECT from_date, to_date, duration, reason, status FROM teacher_leave WHERE empId='" + empId + "'";
            ResultSet rs = c.s.executeQuery(query);
            table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
}
