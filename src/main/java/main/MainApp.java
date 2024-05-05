package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        loadFXML("/login/ui/loginUI.fxml");
    }

    private void loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxml)); // Create a new FXMLLoader
        Scene scene = new Scene(fxmlLoader.load()); // Load the scene
        primaryStage.setTitle("SMC"); // Set the title of the stage
        primaryStage.setScene(scene); // Set the scene of the stage
        primaryStage.setResizable(false); // Make the stage non-resizable (pd)
        primaryStage.show(); // Show the stage
    }

    public static void main(String[] args) {
        launch(args);
    }

}

