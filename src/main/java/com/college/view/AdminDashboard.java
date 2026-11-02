
package com.college.view;

import com.college.service.StudentService;
import com.college.service.StudentService.Student;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.geometry.Insets;
import java.time.LocalDate;
import java.sql.Date;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;

import com.college.service.DashboardService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AdminDashboard extends BorderPane {
        private final DashboardService dashboardService = new DashboardService();
        private final StudentService studentService = new StudentService();

        public AdminDashboard() {

                // Main background
                setStyle("-fx-background-color: #F1F5F9;");

                // LEFT SIDEBAR
                VBox sidebar = new VBox(18);
                sidebar.setPadding(new Insets(30, 20, 25, 20));
                sidebar.setPrefWidth(245);
                sidebar.setStyle("-fx-background-color: #172554;");

                Label logo = new Label("🎓 COLLEGE PORTAL");
                logo.setTextFill(Color.WHITE);
                logo.setFont(Font.font("Arial", FontWeight.BOLD, 18));

                Label adminLabel = new Label("ADMINISTRATION");
                adminLabel.setTextFill(Color.web("#93C5FD"));
                adminLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));

                Button dashboardBtn = createNavButton("▦   Dashboard", true);

                Button studentsBtn = createNavButton("♙   Students", false);
                studentsBtn.setOnAction(e -> openStudentManagement());
                Button facultyBtn = createNavButton("♧   Faculty", false);
                Button departmentsBtn = createNavButton("▤   Departments", false);
                Button coursesBtn = createNavButton("▣   Courses", false);
                Button attendanceBtn = createNavButton("◷   Attendance", false);
                Button examsBtn = createNavButton("▤   Examinations", false);
                Button feesBtn = createNavButton("₹   Fee Management", false);
                Button timetableBtn = createNavButton("▦   Timetable", false);
                Button reportsBtn = createNavButton("▥   Reports", false);

                Region spacer = new Region();
                VBox.setVgrow(spacer, Priority.ALWAYS);

                Button logoutBtn = createNavButton("↪   Logout", false);

                sidebar.getChildren().addAll(
                                logo,
                                new Label(""),
                                adminLabel,
                                dashboardBtn,
                                studentsBtn,
                                facultyBtn,
                                departmentsBtn,
                                coursesBtn,
                                attendanceBtn,
                                examsBtn,
                                feesBtn,
                                timetableBtn,
                                reportsBtn,
                                spacer,
                                logoutBtn);

                // TOP HEADER
                HBox header = new HBox();
                header.setAlignment(Pos.CENTER_LEFT);
                header.setPadding(new Insets(20, 30, 20, 30));
                header.setStyle("-fx-background-color: white;");

                VBox headerText = new VBox(4);

                Label pageTitle = new Label("Dashboard");
                pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 25));
                pageTitle.setTextFill(Color.web("#172554"));

                Label welcome = new Label("Welcome back, Administrator!");
                welcome.setTextFill(Color.web("#64748B"));

                headerText.getChildren().addAll(pageTitle, welcome);

                Region headerSpacer = new Region();
                HBox.setHgrow(headerSpacer, Priority.ALWAYS);

                Label profile = new Label("ADMIN  ●");
                profile.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                profile.setTextFill(Color.web("#2563EB"));

                header.getChildren().addAll(headerText, headerSpacer, profile);

                // DASHBOARD CONTENT
                VBox content = new VBox(25);
                content.setPadding(new Insets(30));

                Label overviewTitle = new Label("Overview");
                overviewTitle.setFont(Font.font("Arial", FontWeight.BOLD, 21));
                overviewTitle.setTextFill(Color.web("#172554"));

                Label overviewSubtitle = new Label(
                                "Here's what's happening in your college.");
                overviewSubtitle.setTextFill(Color.web("#64748B"));

                // STATISTIC CARDS
                HBox cards = new HBox(18);

                VBox studentCard = createStatCard(
                                "TOTAL STUDENTS",
                                String.valueOf(dashboardService.getTotalStudents()),
                                "Registered students");

                VBox facultyCard = createStatCard(
                                "TOTAL FACULTY",
                                String.valueOf(dashboardService.getTotalFaculty()),
                                "Teaching staff");

                VBox departmentCard = createStatCard(
                                "DEPARTMENTS",
                                String.valueOf(dashboardService.getTotalDepartments()),
                                "Academic departments");

                VBox courseCard = createStatCard(
                                "TOTAL COURSES",
                                String.valueOf(dashboardService.getTotalCourses()),
                                "Available programs");

                cards.getChildren().addAll(
                                studentCard,
                                facultyCard,
                                departmentCard,
                                courseCard);

                for (javafx.scene.Node card : cards.getChildren()) {
                        HBox.setHgrow(card, Priority.ALWAYS);
                }

                // RECENT ACTIVITY PANEL
                VBox activity = new VBox(15);
                activity.setPadding(new Insets(25));
                activity.setStyle(
                                "-fx-background-color: white;" +
                                                "-fx-background-radius: 12;");

                Label activityTitle = new Label("System Overview");
                activityTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
                activityTitle.setTextFill(Color.web("#172554"));

                Label activityDescription = new Label(
                                "Your college management system is ready.");
                activityDescription.setTextFill(Color.web("#64748B"));

                Label activityInfo = new Label(
                                "Use the sidebar to manage students, faculty, courses, " +
                                                "attendance, examinations and fees.");
                activityInfo.setWrapText(true);
                activityInfo.setTextFill(Color.web("#475569"));

                activity.getChildren().addAll(
                                activityTitle,
                                activityDescription,
                                activityInfo);

                content.getChildren().addAll(
                                overviewTitle,
                                overviewSubtitle,
                                cards,
                                activity);

                // Assemble dashboard
                setLeft(sidebar);
                setTop(header);
                setCenter(content);
        }

        private void openStudentManagement() {

                // Page heading
                Label title = new Label("Student Management");
                title.setStyle("""
                                    -fx-font-size: 26px;
                                    -fx-font-weight: bold;
                                    -fx-text-fill: #172554;
                                """);

                Label subtitle = new Label(
                                "View and manage all registered students.");
                subtitle.setStyle("-fx-text-fill: #64748B;");

                // Create student table
                TableView<Student> table = new TableView<>();

                TableColumn<Student, String> idCol = new TableColumn<>("ID");
                idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                                String.valueOf(data.getValue().getStudentId())));

                TableColumn<Student, String> rollCol = new TableColumn<>("Roll Number");
                rollCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getRollNumber()));

                TableColumn<Student, String> nameCol = new TableColumn<>("Student Name");
                nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getFullName()));

                TableColumn<Student, String> emailCol = new TableColumn<>("Email");
                emailCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getEmail() == null
                                                ? ""
                                                : data.getValue().getEmail()));

                TableColumn<Student, String> dobCol = new TableColumn<>("Date of Birth");
                dobCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getDateOfBirth() == null
                                                ? ""
                                                : data.getValue().getDateOfBirth()));

                TableColumn<Student, String> admissionCol = new TableColumn<>("Admission Date");
                admissionCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getAdmissionDate()));

                TableColumn<Student, String> programCol = new TableColumn<>("Program");
                programCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getProgramName()));

                table.getColumns().add(idCol);
                table.getColumns().add(rollCol);
                table.getColumns().add(nameCol);
                table.getColumns().add(emailCol);
                table.getColumns().add(dobCol);
                table.getColumns().add(admissionCol);
                table.getColumns().add(programCol);

                // Load database records
                table.setItems(studentService.getAllStudents());
                TextField searchField = new TextField();
                searchField.setPromptText("Search by name or roll number...");
                searchField.setMaxWidth(350);

                searchField.textProperty().addListener(
                                (observable, oldValue, newValue) -> {

                                        String search = newValue.toLowerCase().trim();

                                        table.setItems(
                                                        studentService.getAllStudents().filtered(student ->

                                                        student.getFullName().toLowerCase().contains(search)
                                                                        ||
                                                                        student.getRollNumber().toLowerCase()
                                                                                        .contains(search)));
                                });

                table.setColumnResizePolicy(
                                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

                // Page layout
                VBox content = new VBox(15);
                content.setPadding(new javafx.geometry.Insets(30));

                Button addStudentBtn = new Button("+ Add Student");

                addStudentBtn.setStyle("""
                                    -fx-background-color: #2563EB;
                                    -fx-text-fill: white;
                                    -fx-font-size: 14px;
                                    -fx-font-weight: bold;
                                    -fx-padding: 10 20;
                                    -fx-background-radius: 8;
                                    -fx-cursor: hand;
                                """);

                addStudentBtn.setOnAction(e -> showAddStudentForm());

                content.getChildren().addAll(
                                title,
                                subtitle,
                                addStudentBtn,
                                searchField,
                                table);

                VBox.setVgrow(table, Priority.ALWAYS);

                setCenter(content);
        }

        private Button createNavButton(String text, boolean active) {

                Button button = new Button(text);

                button.setMaxWidth(Double.MAX_VALUE);
                button.setAlignment(Pos.CENTER_LEFT);
                button.setPadding(new Insets(12, 15, 12, 15));

                String background = active ? "#2563EB" : "transparent";
                String textColor = active ? "white" : "#CBD5E1";

                button.setStyle(
                                "-fx-background-color: " + background + ";" +
                                                "-fx-text-fill: " + textColor + ";" +
                                                "-fx-font-size: 13px;" +
                                                "-fx-background-radius: 7;" +
                                                "-fx-cursor: hand;");

                return button;
        }

        private void showAddStudentForm() {

                Dialog<ButtonType> dialog = new Dialog<>();

                dialog.setTitle("Register New Student");
                dialog.setHeaderText("Enter Student Information");

                GridPane grid = new GridPane();
                grid.setHgap(12);
                grid.setVgap(12);
                grid.setPadding(new Insets(20));

                TextField usernameField = new TextField();
                PasswordField passwordField = new PasswordField();
                TextField rollField = new TextField();
                TextField nameField = new TextField();
                TextField emailField = new TextField();

                DatePicker dobPicker = new DatePicker();
                DatePicker admissionPicker = new DatePicker();

                ComboBox<StudentService.Program> programBox = new ComboBox<>();

                programBox.setItems(studentService.getAllPrograms());
                programBox.setPromptText("Select Program");

                usernameField.setPromptText("Login username");
                passwordField.setPromptText("Login password");
                rollField.setPromptText("Student roll number");
                nameField.setPromptText("Full name");
                emailField.setPromptText("Email address");

                grid.addRow(0, new Label("Username:"), usernameField);
                grid.addRow(1, new Label("Password:"), passwordField);
                grid.addRow(2, new Label("Roll Number:"), rollField);
                grid.addRow(3, new Label("Full Name:"), nameField);
                grid.addRow(4, new Label("Email:"), emailField);
                grid.addRow(5, new Label("Date of Birth:"), dobPicker);
                grid.addRow(6, new Label("Admission Date:"), admissionPicker);
                grid.addRow(7, new Label("Program:"), programBox);

                dialog.getDialogPane().setContent(grid);

                ButtonType saveButton = new ButtonType(
                                "Register",
                                ButtonBar.ButtonData.OK_DONE);

                dialog.getDialogPane().getButtonTypes().addAll(
                                saveButton,
                                ButtonType.CANCEL);

                dialog.setResultConverter(button -> button);

                dialog.showAndWait().ifPresent(result -> {

                        if (result == saveButton) {

                                if (usernameField.getText().isBlank()
                                                || passwordField.getText().isBlank()
                                                || rollField.getText().isBlank()
                                                || nameField.getText().isBlank()
                                                || admissionPicker.getValue() == null
                                                || programBox.getValue() == null) {

                                        showMessage(
                                                        Alert.AlertType.WARNING,
                                                        "Missing Information",
                                                        "Please fill in all required fields.");
                                        return;
                                }

                                LocalDate dob = dobPicker.getValue();
                                LocalDate admission = admissionPicker.getValue();

                                Date sqlDob = dob == null
                                                ? null
                                                : Date.valueOf(dob);

                                Date sqlAdmission = Date.valueOf(admission);

                                int roleId = studentService.getStudentRoleId();

                                if (roleId == -1) {
                                        showMessage(
                                                        Alert.AlertType.ERROR,
                                                        "Error",
                                                        "STUDENT role was not found.");
                                        return;
                                }

                                boolean success = studentService.addStudent(
                                                usernameField.getText().trim(),
                                                passwordField.getText(),
                                                roleId,
                                                programBox.getValue().getProgramId(),
                                                rollField.getText().trim(),
                                                nameField.getText().trim(),
                                                emailField.getText().trim().isEmpty()
                                                                ? null
                                                                : emailField.getText().trim(),
                                                sqlDob,
                                                sqlAdmission);

                                if (success) {
                                        showMessage(
                                                        Alert.AlertType.INFORMATION,
                                                        "Success",
                                                        "Student registered successfully!");

                                        openStudentManagement();

                                } else {
                                        showMessage(
                                                        Alert.AlertType.ERROR,
                                                        "Registration Failed",
                                                        "Could not register student. Check the username, " +
                                                                        "roll number and email for duplicates.");
                                }
                        }
                });
        }

        private void showMessage(
                        Alert.AlertType type,
                        String title,
                        String message) {

                Alert alert = new Alert(type);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
        }

        private VBox createStatCard(
                        String title,
                        String value,
                        String description) {

                VBox card = new VBox(12);
                card.setPadding(new Insets(22));
                card.setMinWidth(150);
                card.setStyle(
                                "-fx-background-color: white;" +
                                                "-fx-background-radius: 12;");

                Label cardTitle = new Label(title);
                cardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 11));
                cardTitle.setTextFill(Color.web("#64748B"));

                Label cardValue = new Label(value);
                cardValue.setFont(Font.font("Arial", FontWeight.BOLD, 30));
                cardValue.setTextFill(Color.web("#172554"));

                Label cardDescription = new Label(description);
                cardDescription.setFont(Font.font(11));
                cardDescription.setTextFill(Color.web("#94A3B8"));

                card.getChildren().addAll(
                                cardTitle,
                                cardValue,
                                cardDescription);

                return card;
        }
}
