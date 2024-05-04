package admin;

import database.MongoClientConnection;

import java.util.ArrayList;

public class TestsManager {
    ArrayList<AdminTest> testsList;
    ArrayList<Subject> subjectsList;
    
    public TestsManager() {
        this.testsList = new ArrayList<AdminTest>();
        this.subjectsList = new ArrayList<Subject>();
        subjectsList.add(new Subject("Matematica", 100));
        subjectsList.add(new Subject("Italiano", 100));
        subjectsList.add(new Subject("Inglese", 80));
        subjectsList.add(new Subject("Storia", 100));
        subjectsList.add(new Subject("Scienze", 80));
        subjectsList.add(new Subject("Arte", 60));
        subjectsList.add(new Subject("Educazione Fisica", 40));
        subjectsList.add(new Subject("Religione", 10));
        subjectsList.add(new Subject("Musica", 60));
        subjectsList.add(new Subject("Tecnologia", 60));
        subjectsList.add(new Subject("Informatica", 80));
        subjectsList.add(new Subject("Geografia", 80));
        subjectsList.add(new Subject("Filosofia", 100));
        subjectsList.add(new Subject("Latino", 100));
        subjectsList.add(new Subject("Greco", 100));
        subjectsList.add(new Subject("TPS", 80));
        subjectsList.add(new Subject("Sistemi e reti", 80));
        subjectsList.add(new Subject("Telecomunicazioni", 80));
    }


    public void addTest(String subject, String date, int initialHour, int finalHour) {
        for (Subject s : subjectsList) {
            if (s.getName().equalsIgnoreCase(subject)) {
                AdminTest adminTest = new AdminTest(s, date, initialHour, finalHour);

                for (AdminTest t : testsList) {
                    if (t.getDate().equals(adminTest.getDate())
                            && t.getSubject().getName().equalsIgnoreCase(adminTest.getSubject().getName())) {
                        testsList.remove(t);
                        testsList.add(adminTest);
                        return;
                    }
                }

                testsList.add(adminTest);
                System.out.println("Test added");
                System.out.println("Subject: " + s.getName());
                System.out.println("Date: " + date);
                System.out.println("Initial Hour: " + initialHour);
                System.out.println("Final Hour: " + finalHour);
                return;
            }
        }
    }

    public void saveToCSV() {
        try {
            ConvertDataToCSV.write(testsList);
        } catch (Exception e) {
            // create file

        }
        printTestsList();
    }

    public ArrayList<AdminTest> readFromCSV() {
        try {
            testsList = ConvertDataToCSV.read("test.csv", subjectsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        printTestsList();
        return testsList;
    }

    public void loadToDB() {
        MongoClientConnection mongoClientConnection = new MongoClientConnection();
        mongoClientConnection.loadCSVToDatabase();
    }

    public void loadFromDB() {
        MongoClientConnection mongoClientConnection = new MongoClientConnection();
        mongoClientConnection.generateCSVFromDatabase();
    }

    private void printTestsList() {
        for (AdminTest t : testsList) {
            System.out.println("Subject: " + t.getSubject().getName());
            System.out.println("Date: " + t.getDate());
            System.out.println("Initial Hour: " + t.getInitialHour());
            System.out.println("Final Hour: " + t.getFinalHour());
        }
    }
}
