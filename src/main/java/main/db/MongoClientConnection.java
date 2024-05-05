package main.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;
import student.Student;
import student.StudentManager;
import student.StudentTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MongoClientConnection {
    private String connectionString;
    private MongoClient mongoClient;
    private MongoDatabase database;

    public void loadCSVToDatabase() {
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

        MongoCollection<Document> collection = database.getCollection("Tests");

        try (BufferedReader br = new BufferedReader(new FileReader("test.csv"))) {
            String line;
            // Read the first line of the CSV file (header)
            String[] headers = br.readLine().split(",");
            List<Document> testsInCSV = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Document doc = new Document();
                for (int i = 0; i < values.length; i++) {
                    doc.append(headers[i], values[i]);
                }
                testsInCSV.add(doc);
            }

            // Delete tests from the admin.database that have been deleted locally
            for (Document doc : collection.find()) {
                boolean found = false;
                for (Document testInCSV : testsInCSV) {
                    if (doc.get("Subject").equals(testInCSV.get("Subject")) &&
                            doc.get("Date").equals(testInCSV.get("Date")) &&
                            doc.get("Initial Hour").equals(testInCSV.get("Initial Hour")) &&
                            doc.get("Final Hour").equals(testInCSV.get("Final Hour"))) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    collection.deleteOne(doc);
                }
            }

            // Insert new tests into the admin.database
            for (Document testInCSV : testsInCSV) {
                Document query = new Document();
                query.append("Subject", testInCSV.get("Subject"));
                query.append("Date", testInCSV.get("Date"));
                query.append("Initial Hour", testInCSV.get("Initial Hour"));
                query.append("Final Hour", testInCSV.get("Final Hour"));
                Document existingTest = collection.find(query).first();

                // If the test is not in the admin.database, insert it
                if (existingTest == null) {
                    collection.insertOne(testInCSV);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
    }

    public void generateCSVFromDatabase() {
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

        MongoCollection<Document> collection = database.getCollection("Tests");

        try {
            // Overwrite the file with a new FileWriter
            FileWriter fileWriter = new FileWriter("test.csv", false);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Write the header line
            printWriter.println("Subject,Date,Initial Hour,Final Hour");

            // Write data lines
            for (Document doc : collection.find()) {
                String dataLine = doc.get("Subject") + "," + doc.get("Date") + "," + doc.get("Initial Hour") + "," + doc.get("Final Hour");
                System.out.println(dataLine);
                printWriter.println(dataLine);
            }

            // Close the PrintWriter
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
    }

    public void importStudentList(StudentManager studentManager) {
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

        MongoCollection<Document> collection = database.getCollection("Students");

        // Write data lines
        studentManager.getStudentsList().clear();
        for (Document doc : collection.find()) {
            studentManager.addStudent(new Student(doc.get("name").toString()));
        }

        // Close the client
        mongoClient.close();
    }

    public void exportStudentTests(Student student) {
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

        MongoCollection<Document> collection = database.getCollection("Students");

        // Write data lines
        FindIterable<Document> copyDocuments = collection.find();
        for (Document doc : copyDocuments) {
            if (doc.get("name").equals(student.getName())) {
                collection.deleteOne(doc);
                Document newDoc = new Document();
                newDoc.append("name", student.getName());
                List<Document> tests = new ArrayList<>();
                for (StudentTest studentTest : student.getTestsList()) {
                    Document test = new Document();
                    test.append("Subject", studentTest.getSubject().getName());
                    test.append("Date", studentTest.getDate());
                    test.append("Grade", studentTest.getMark());
                    tests.add(test);
                }
                newDoc.append("Tests", tests);
                collection.insertOne(newDoc);
                break;
            }
        }

        // Close the client
        mongoClient.close();
    }
}