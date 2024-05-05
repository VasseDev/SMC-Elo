package login.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import login.db.MongoClientConnection;

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
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String loginType = loginTypeChoiceBox.getValue();

        MongoClientConnection mongoClientConnection = new MongoClientConnection();
        mongoClientConnection.checkLoginCredentials(username, password, loginType);
    }
}
