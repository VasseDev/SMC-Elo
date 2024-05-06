package main;

import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * MainApp is the entry point of the application.
 * It extends the JavaFX Application class and overrides the start method.
 */
public class MainApp extends Application {
    private Stage primaryStage; // The primary stage of the application

    /**
     * The start method is the main entry point for all JavaFX applications.
     * It is called after the init method has returned, and after the system is ready for the application to begin running.
     *
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet()); // Set the user agent stylesheet
        this.primaryStage = primaryStage; // Set the primary stage
        loadFXML("/login/ui/loginUI.fxml"); // Load the FXML file
    }

    /**
     * This method loads the FXML file and sets the scene for the primary stage.
     *
     * @param fxml the path to the FXML file
     * @throws IOException if the FXML file can't be loaded
     */
    private void loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxml)); // Create a new FXMLLoader
        Scene scene = new Scene(fxmlLoader.load()); // Load the scene
        primaryStage.setTitle("SMC"); // Set the title of the stage
        primaryStage.setScene(scene); // Set the scene of the stage
        primaryStage.setResizable(false); // Make the stage non-resizable
        primaryStage.show(); // Show the stage
    }

    /**
     * The main method is used to launch the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}