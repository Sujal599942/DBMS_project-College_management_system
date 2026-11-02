
package com.college.service;

import com.college.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardService {

    private int getCount(String sql) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getTotalStudents() {
        return getCount("SELECT COUNT(*) FROM students");
    }

    public int getTotalFaculty() {
        return getCount("SELECT COUNT(*) FROM faculty");
    }

    public int getTotalDepartments() {
        return getCount("SELECT COUNT(*) FROM departments");
    }

    public int getTotalCourses() {
        return getCount("SELECT COUNT(*) FROM subjects");
    }
}

