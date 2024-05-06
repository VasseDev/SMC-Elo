package login.db;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import login.ui.LoginUIController;
import main.TestsManager;
import org.bson.Document;
import student.Student;
import student.StudentManager;

import java.util.Optional;

/**
 * MongoClientConnection is a utility class that provides methods to connect to a MongoDB database and perform login operations.
 */
public class MongoClientConnection {
    private String connectionString;
    private MongoClient mongoClient;
    private MongoDatabase database;

    /**
     * Checks the login credentials of a user.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param loginType the type of login (Student or Admin)
     * @param loginUIController the controller of the login UI
     * @param root the root node of the login UI
     * @param studentManager the manager of the students
     */
    public void checkLoginCredentials(String username, String password, String loginType, LoginUIController loginUIController, AnchorPane root, StudentManager studentManager) {
        connectionString = DBCredentials.connectionString;
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        mongoClient = MongoClients.create(settings);
        try {
            // Send a ping to confirm a successful connection
            database = mongoClient.getDatabase("StudentsDB");
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
        } catch (MongoException e) {
            e.printStackTrace();
        }

        MongoCollection<Document> collection = database.getCollection(loginType + "LoginCredentials");
        if (loginType.equals("Student")) {
            boolean usernameFound = checkUsername(username, collection);
            if (!usernameFound) {
                javafx.application.Platform.runLater(() -> {
                    System.out.println("User doesn't exist!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.getButtonTypes().remove(ButtonType.OK);
                    alert.getButtonTypes().add(ButtonType.CANCEL);
                    alert.getButtonTypes().add(ButtonType.YES);
                    alert.setTitle("Crea Account");
                    alert.setContentText("Account non esistente. Vuoi creare un nuovo profilo con le credenziali inserite?");
                    Optional<ButtonType> res = alert.showAndWait();

                    if (res.isPresent()) {
                        if (res.get().equals(ButtonType.YES)) {
                            Document newDoc = new Document("username", username)
                                    .append("password", password);
                            collection.insertOne(newDoc);
                            // Add the new student to the student manager
                            studentManager.addStudent(new Student(username));
                            System.out.println("Student added!");
                            System.out.println("Account created successfully!");
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Crea Account");
                            alert2.setContentText("Account creato con successo!");
                            alert2.showAndWait();
                            javafx.application.Platform.runLater(loginUIController::openStudentDashboard);
                        } else if (res.get().equals(ButtonType.CANCEL)) {
                            javafx.application.Platform.runLater(() -> root.setDisable(false));
                        }
                    }
                });
                MongoCollection<Document> collection2 = database.getCollection("Students");
                collection2.insertOne(new Document("name", username)
                        .append("elo", 0));

            } else if (checkPassword(password, collection)) {
                javafx.application.Platform.runLater(loginUIController::openStudentDashboard);
            } else {
                javafx.application.Platform.runLater(() -> {
                    System.out.println("Wrong password!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login");
                    alert.setContentText("Password errata!");
                    alert.showAndWait();
                    javafx.application.Platform.runLater(() -> root.setDisable(false));
                });
            }
        } else {
            boolean usernameFound = checkUsername(username, collection);
            if (!usernameFound) {
                javafx.application.Platform.runLater(() -> {
                    System.out.println("User doesn't exist!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login");
                    alert.setContentText("Utente non esistente!");
                    alert.showAndWait();
                    javafx.application.Platform.runLater(() -> root.setDisable(false));
                });
            } else if (checkPassword(password, collection)) {
                javafx.application.Platform.runLater(loginUIController::openAdminDashboard);
            } else {
                javafx.application.Platform.runLater(() -> {
                    System.out.println("Wrong password!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login");
                    alert.setContentText("Password errata!");
                    alert.showAndWait();
                    javafx.application.Platform.runLater(() -> root.setDisable(false));
                });
            }
        }
    }

    /**
     * Checks if a username exists in a collection.
     *
     * @param username the username to check
     * @param collection the collection to check in
     * @return true if the username exists, false otherwise
     */
    private boolean checkUsername(String username, MongoCollection<Document> collection) {
        boolean usernameFound = false;
        for (Document doc : collection.find()) {
            if (doc.get("username").equals(username)) {
                usernameFound = true;
            }
        }
        return usernameFound;
    }

    /**
     * Checks if a password exists in a collection.
     *
     * @param password the password to check
     * @param collection the collection to check in
     * @return true if the password exists, false otherwise
     */
    private boolean checkPassword(String password, MongoCollection<Document> collection) {
        boolean passwordFound = false;
        for (Document doc : collection.find()) {
            if (doc.get("password").equals(password)) {
                passwordFound = true;
            }
        }
        return passwordFound;
    }
}
