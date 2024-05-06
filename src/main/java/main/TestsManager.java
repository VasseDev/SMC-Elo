package main;

import admin.AdminTest;
import admin.ConvertDataToCSV;
import admin.Subject;
import main.db.MongoClientConnection;

import java.util.ArrayList;

/**
 * TestsManager is a class that manages the tests and subjects in the system.
 * It provides methods to add and remove tests, save and load tests to and from a CSV file, and load tests to and from a MongoDB database.
 * It also provides a method to convert a grade from a string to a double.
 */
public class TestsManager {
    private ArrayList<AdminTest> testsList;
    private ArrayList<Subject> subjectsList;

    /**
     * Constructs a new TestsManager and initializes the subjects list with predefined subjects.
     */
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

    /**
     * Adds a test to the tests list.
     *
     * @param subject the subject of the test
     * @param date the date of the test
     * @param initialHour the initial hour of the test
     * @param finalHour the final hour of the test
     */
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
                return;
            }
        }
    }

    /**
     * Removes a test from the tests list.
     *
     * @param subject the subject of the test
     * @param date the date of the test
     * @param initialHour the initial hour of the test
     * @param finalHour the final hour of the test
     */
    public void removeTest(String subject, String date, int initialHour, int finalHour) {
        AdminTest toRemove = null;
        for (AdminTest test : testsList) {
            if (test.getSubject().getName().equals(subject) && test.getDate().equals(date)
                    && test.getInitialHour() == initialHour && test.getFinalHour() == finalHour) {
                toRemove = test;
                break;
            }
        }
        if (toRemove != null) {
            testsList.remove(toRemove);
            System.out.println("Test removed from list");
        }
    }

    /**
     * Saves the tests list to a CSV file.
     */
    public void saveToCSV() {
        try {
            ConvertDataToCSV.write(testsList);
        } catch (Exception e) {
            // create file

        }
    }

    /**
     * Reads the tests list from a CSV file.
     *
     * @return the tests list
     */
    public ArrayList<AdminTest> readFromCSV() {
        try {
            testsList = ConvertDataToCSV.read("test.csv", subjectsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return testsList;
    }

    /**
     * Loads the tests list to a MongoDB database.
     */
    public void loadToDB() {
        MongoClientConnection mongoClientConnection = new MongoClientConnection();
        mongoClientConnection.loadCSVToDatabase();
    }

    /**
     * Loads the tests list from a MongoDB database.
     */
    public void loadFromDB() {
        MongoClientConnection mongoClientConnection = new MongoClientConnection();
        mongoClientConnection.generateCSVFromDatabase();
    }

    /**
     * Returns the tests list.
     *
     * @return the tests list
     */
    public ArrayList<AdminTest> getTestsList() {
        return testsList;
    }

    /**
     * Returns the subject with the specified name.
     *
     * @param name the name of the subject
     * @return the subject with the specified name
     */
    public Subject getSubject(String name) {
        for (Subject s : subjectsList) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Converts a grade from a string to a double.
     *
     * @param grade the grade as a string
     * @return the grade as a double
     */
    public double convertGrade(String grade) {
        // split string in two parts
        String[] parts = grade.split("");
        // check if the grade is a number
        if (parts.length == 1) {
            return Double.parseDouble(grade);
        } else {
            // check if the grade is a number with a comma
            if (parts[1].equals("½")) {
                return Double.parseDouble(parts[0]) + 0.5;
            } else if (parts[1].equals("+")) {
                return Double.parseDouble(parts[0]) + 0.25;
            } else if (parts[1].equals("-")) {
                return Double.parseDouble(parts[0]) - 0.25;
            } else if (parts[1].equals("0")) {
                if (parts.length > 2) {
                    return Double.parseDouble(parts[0] + parts[1]) - 0.25;
                }
                return Double.parseDouble(parts[0] + parts[1]);
            }
        }
        return 0;
    }
}
