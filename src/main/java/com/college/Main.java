
package com.college;

import com.college.view.AdminDashboard;
import com.college.service.AuthService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Left branding panel
        VBox leftPanel = new VBox(20);
        leftPanel.setAlignment(Pos.CENTER_LEFT);
        leftPanel.setPadding(new Insets(50));
        leftPanel.setPrefWidth(400);
        leftPanel.setStyle("-fx-background-color: #172554;");

        Label logo = new Label("🎓 COLLEGE PORTAL");
        logo.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        logo.setTextFill(Color.WHITE);

        Label heading = new Label("Manage.\nLearn.\nGrow.");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 42));
        heading.setTextFill(Color.WHITE);

        Label description = new Label(
                "An integrated academic and administrative management system.");
        description.setWrapText(true);
        description.setFont(Font.font(15));
        description.setTextFill(Color.LIGHTGRAY);

        leftPanel.getChildren().addAll(logo, heading, description);

        // Right login panel
        VBox loginPanel = new VBox(15);
        loginPanel.setAlignment(Pos.CENTER);
        loginPanel.setPadding(new Insets(50));
        loginPanel.setPrefWidth(500);
        loginPanel.setStyle("-fx-background-color: #F8FAFC;");

        Label title = new Label("Welcome Back!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        title.setTextFill(Color.web("#172554"));

        Label subtitle = new Label("Sign in to access your account");
        subtitle.setTextFill(Color.GRAY);

        TextField username = new TextField();
        username.setPromptText("Enter your username");
        username.setPrefHeight(45);
        username.setMaxWidth(320);

        PasswordField password = new PasswordField();
        password.setPromptText("Enter your password");
        password.setPrefHeight(45);
        password.setMaxWidth(320);

        Button loginButton = new Button("LOGIN");
        loginButton.setPrefWidth(320);
        loginButton.setPrefHeight(45);
        loginButton.setStyle(
                "-fx-background-color: #2563EB;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;");

        Label message = new Label();
        message.setTextFill(Color.web("#DC2626"));

        AuthService authService = new AuthService();

        loginButton.setOnAction(event -> {

            String enteredUsername = username.getText().trim();
            String enteredPassword = password.getText();

            if (enteredUsername.isBlank() || enteredPassword.isBlank()) {

                message.setText("Please enter username and password.");
                message.setTextFill(Color.RED);

                return;
            }

            String result = authService.login(
                    enteredUsername,
                    enteredPassword);

            switch (result) {

                case "ADMIN":

                    AdminDashboard dashboard = new AdminDashboard();

                    Scene dashboardScene = new Scene(dashboard, 1200, 750);

                    stage.setScene(dashboardScene);
                    stage.setTitle("College Management System - Admin");
                    stage.centerOnScreen();

                    break;

                case "FACULTY":
                    message.setText("Welcome Faculty!");
                    message.setTextFill(Color.GREEN);
                    break;

                case "STUDENT":
                    message.setText("Welcome Student!");
                    message.setTextFill(Color.GREEN);
                    break;

                case "INVALID":
                    message.setText("Invalid username or password.");
                    message.setTextFill(Color.RED);
                    break;

                case "INACTIVE":
                    message.setText("Your account is inactive.");
                    message.setTextFill(Color.RED);
                    break;

                default:
                    message.setText("Something went wrong. Try again.");
                    message.setTextFill(Color.RED);
                    break;
            }
        });

        loginPanel.getChildren().addAll(
                title,
                subtitle,
                new Label(""),
                username,
                password,
                loginButton,
                message);

        // Main layout
        HBox root = new HBox(leftPanel, loginPanel);

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("College Management System");
        stage.setScene(scene);
        stage.setMinWidth(750);
        stage.setMinHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
