
package com.college;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordGenerator {

    public static void main(String[] args) {

        String[] passwords = {
            "Admin@123",
            "Faculty@123",
            "Student@123",
            "Student@456"
        };

        for (String password : passwords) {

            String hashedPassword = BCrypt.hashpw(
                password,
                BCrypt.gensalt(12)
            );

            System.out.println("Password: " + password);
            System.out.println("Hash: " + hashedPassword);
            System.out.println("--------------------------");
        }
    }
}

