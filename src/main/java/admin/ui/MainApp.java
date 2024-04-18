package admin.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import admin.TestsManager;

import java.io.IOException;
import java.util.Optional;

public class MainApp extends Application {
    private Stage primaryStage;
    private MainUiController mainController;
    private boolean dayPage;
    TestsManager testsManager;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        loadFXML("mainUI.fxml");
    }

    private void loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxml)); // Create a new FXMLLoader
        Scene scene = new Scene(fxmlLoader.load()); // Load the scene
        primaryStage.setTitle("SMC"); // Set the title of the stage
        primaryStage.setScene(scene); // Set the scene of the stage
        primaryStage.setResizable(false); // Make the stage non-resizable (pd)
        primaryStage.show(); // Show the stage

        testsManager = new TestsManager();

        MainUiController mainUiController = fxmlLoader.getController(); // Get the controller of the FXML
        mainUiController.setMainModel(testsManager); // Set the model of the controller

        primaryStage.setOnCloseRequest(this::closeWindowEvent); // Set the close window event
    }

    private void closeWindowEvent(WindowEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle("Chiusura applicazione");
        alert.setContentText("Sei sicuro di voler chiudere il programma?");
        alert.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alert.showAndWait();

        if(res.isPresent()) {
            if(res.get().equals(ButtonType.CANCEL))
                //metodo che interrompe la chiusura della finestra
                event.consume();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}

