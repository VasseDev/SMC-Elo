package login.ui;

import main.TestsManager;
import admin.ui.AdminUiController;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.db.MongoClientConnection;
import student.Student;
import student.StudentManager;
import student.ui.StudentUIController;

import java.io.IOException;

/**
 * LoginUIController is the controller class for the login UI.
 * It handles user interactions with the login form and manages the login process.
 */
public class LoginUIController {
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    ChoiceBox<String> loginTypeChoiceBox;

    private Stage stage;
    private StudentManager studentManager;

    /**
     * Initializes the login UI controller.
     * It sets the default login type and initializes the student manager.
     */
    @FXML
    private void initialize() {
        loginTypeChoiceBox.getItems().addAll("Admin", "Student");
        loginTypeChoiceBox.setValue("Student");
        studentManager = new StudentManager();
    }

    /**
     * Handles the click event of the submit button.
     * It disables the root pane, retrieves the entered username, password, and login type,
     * and starts a new task to check the login credentials.
     */
    public void onSubmitButtonClick() {
        // Disable the root pane
        AnchorPane root = (AnchorPane) usernameTextField.getScene().getRoot();
        root.setDisable(true);

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String loginType = loginTypeChoiceBox.getValue();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                MongoClientConnection mongoClientConnection = new MongoClientConnection();
                mongoClientConnection.checkLoginCredentials(username, password, loginType,
                        LoginUIController.this, root, studentManager);
                return null;
            }
        };
        // Run the task in a separate thread
        new Thread(task).start();
    }

    /**
     * Opens the admin dashboard.
     * It loads the admin UI, creates a new scene with the loaded root node, sets the new scene on the current stage,
     * and sets the stage in the admin controller.
     */
    public void openAdminDashboard() {
        try {
            // Load the new FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin/ui/adminUI.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new scene with the root node
            Scene scene = new Scene(root);

            // Get the current stage and set the new scene
            stage = (Stage) usernameTextField.getScene().getWindow();
            stage.setScene(scene);

            // Get the controller of the admin page
            AdminUiController adminController = fxmlLoader.getController();
            adminController.setStage(stage); // Set the stage in adminController
            stage.setOnCloseRequest(adminController::closeWindowEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the student dashboard.
     * It imports the student list, loads the student UI, creates a new scene with the loaded root node,
     * sets the new scene on the current stage, finds the logged-in student, and sets the current student in the student controller.
     */
    public void openStudentDashboard() {
        try {
            main.db.MongoClientConnection mongoClientConnection = new main.db.MongoClientConnection();
            mongoClientConnection.importStudentList(studentManager, new TestsManager());
            // Load the new FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/student/ui/studentUI.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new scene with the root node
            Scene scene = new Scene(root);

            // Get the current stage and set the new scene
            stage = (Stage) usernameTextField.getScene().getWindow();
            stage.setScene(scene);

            Student student = studentManager.findStudent(usernameTextField.getText());
            // set the main student
            StudentUIController controller = fxmlLoader.getController();
            controller.setMainModel(studentManager); // Ensure this is called before setCurrentStudent
            controller.setCurrentStudent(student);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
