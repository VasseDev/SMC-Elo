package login.db;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.bson.Document;

import java.io.IOException;
import java.util.Optional;

public class MongoClientConnection {
    private String connectionString;
    private MongoClient mongoClient;
    private MongoDatabase database;

    public void checkLoginCredentials(String username, String password, String loginType) {
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

        boolean usernameFound = false;
        for (Document doc : collection.find()) {
            if (doc.get("username").equals(username)) {
                usernameFound = true;
                if (doc.get("password").equals(password)) {
                    System.out.println("Login successful!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login");
                    alert.setContentText("Login effettuato con successo!");
                    alert.showAndWait();
                } else {
                    System.out.println("Password is incorrect!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login");
                    alert.setContentText("Password errata!");
                    alert.showAndWait();
                }
            }
        }

        if (!usernameFound) {
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
                    System.out.println("Account created successfully!");
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Crea Account");
                    alert2.setContentText("Account creato con successo!");
                    alert2.showAndWait();
                }
            }
        }
    }
}
