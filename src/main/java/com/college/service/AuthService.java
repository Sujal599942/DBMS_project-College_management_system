
package com.college.service;

import com.college.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    public String login(String username, String password) {

        String sql = """
            SELECT u.password_hash, u.is_active, r.role_name
            FROM users u
            JOIN roles r ON u.role_id = r.role_id
            WHERE u.username = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return "INVALID";
                }

                if (rs.getBoolean("is_active") == false) {
                    return "INACTIVE";
                }

                String storedHash = rs.getString("password_hash");

                if (BCrypt.checkpw(password, storedHash)) {
                    return rs.getString("role_name");
                }

                return "INVALID";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}

