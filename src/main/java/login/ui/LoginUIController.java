package login.ui;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.db.MongoClientConnection;

import java.io.IOException;

public class LoginUIController {
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    ChoiceBox<String> loginTypeChoiceBox;

    @FXML
    private void initialize() {
        loginTypeChoiceBox.getItems().addAll("Admin", "Student");
        loginTypeChoiceBox.setValue("Student");
    }

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
                mongoClientConnection.checkLoginCredentials(username, password, loginType, LoginUIController.this);
                return null;
            }
        };
        // Run the task in a separate thread
        new Thread(task).start();
    }

    public void openAdminDashboard() {
        try {
            // Load the new FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin/ui/adminUI.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new scene with the root node
            Scene scene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
