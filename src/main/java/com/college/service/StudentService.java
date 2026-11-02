
package com.college.service;

import com.college.config.DBConnection;

import java.sql.Statement;
import java.sql.Date;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.SimpleStringProperty;

public class StudentService {

    public static class Student {

        private final int studentId;
        private final String rollNumber;
        private final String fullName;
        private final String email;
        private final String dateOfBirth;
        private final String admissionDate;
        private final String programName;

        public Student(int studentId, String rollNumber,
                String fullName, String email,
                String dateOfBirth, String admissionDate,
                String programName) {

            this.studentId = studentId;
            this.rollNumber = rollNumber;
            this.fullName = fullName;
            this.email = email;
            this.dateOfBirth = dateOfBirth;
            this.admissionDate = admissionDate;
            this.programName = programName;
        }

        public int getStudentId() {
            return studentId;
        }

        public String getRollNumber() {
            return rollNumber;
        }

        public String getFullName() {
            return fullName;
        }

        public String getEmail() {
            return email;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public String getAdmissionDate() {
            return admissionDate;
        }

        public String getProgramName() {
            return programName;
        }
    }

    public static class Program {

        private final int programId;
        private final String programName;

        public Program(int programId, String programName) {
            this.programId = programId;
            this.programName = programName;
        }

        public int getProgramId() {
            return programId;
        }

        public String getProgramName() {
            return programName;
        }

        @Override
        public String toString() {
            return programName;
        }
    }

    public boolean updateStudent(
            int studentId,
            int programId,
            String rollNumber,
            String fullName,
            String email,
            Date dateOfBirth,
            Date admissionDate) {

        String sql = """
                    UPDATE students
                    SET program_id = ?,
                        roll_number = ?,
                        full_name = ?,
                        email = ?,
                        date_of_birth = ?,
                        admission_date = ?
                    WHERE student_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, programId);
            ps.setString(2, rollNumber);
            ps.setString(3, fullName);
            ps.setString(4, email);
            ps.setDate(5, dateOfBirth);
            ps.setDate(6, admissionDate);
            ps.setInt(7, studentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Program> getAllPrograms() {

        ObservableList<Program> programs = FXCollections.observableArrayList();

        String sql = """
                    SELECT program_id, program_name
                    FROM programs
                    ORDER BY program_name
                """;

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                programs.add(new Program(
                        rs.getInt("program_id"),
                        rs.getString("program_name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return programs;
    }

    public int getStudentRoleId() {

        String sql = """
                    SELECT role_id
                    FROM roles
                    WHERE role_name = 'STUDENT'
                """;

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("role_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public boolean addStudent(
            String username,
            String password,
            int roleId,
            int programId,
            String rollNumber,
            String fullName,
            String email,
            Date dateOfBirth,
            Date admissionDate) {

        String userSql = """
                    INSERT INTO users (role_id, username, password_hash)
                    VALUES (?, ?, ?)
                """;

        String studentSql = """
                    INSERT INTO students
                    (user_id, program_id, roll_number, full_name,
                     email, date_of_birth, admission_date)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                // Hash the password
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

                // Create user account
                int userId;

                try (PreparedStatement ps = con.prepareStatement(
                        userSql, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, roleId);
                    ps.setString(2, username);
                    ps.setString(3, hashedPassword);

                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "User ID generation failed.");
                        }

                        userId = rs.getInt(1);
                    }
                }

                // Create student record
                try (PreparedStatement ps = con.prepareStatement(studentSql)) {

                    ps.setInt(1, userId);
                    ps.setInt(2, programId);
                    ps.setString(3, rollNumber);
                    ps.setString(4, fullName);
                    ps.setString(5, email);
                    ps.setDate(6, dateOfBirth);
                    ps.setDate(7, admissionDate);

                    ps.executeUpdate();
                }

                // Save both records
                con.commit();
                return true;

            } catch (SQLException | RuntimeException e) {

                con.rollback();
                e.printStackTrace();
                return false;

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Student> getAllStudents() {

        ObservableList<Student> students = FXCollections.observableArrayList();

        String sql = """
                    SELECT s.student_id, s.roll_number, s.full_name,
                           s.email, s.date_of_birth, s.admission_date,
                           p.program_name
                    FROM students s
                    JOIN programs p ON s.program_id = p.program_id
                    ORDER BY s.student_id
                """;

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Student student = new Student(
                        rs.getInt("student_id"),
                        rs.getString("roll_number"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("date_of_birth"),
                        rs.getString("admission_date"),
                        rs.getString("program_name"));

                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}
