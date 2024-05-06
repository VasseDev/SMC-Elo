package student;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Student is a class that represents a student in the system.
 * It provides methods to get and update the student's name, Elo points, and tests list.
 * It also provides methods to add a test to the tests list and get a test from the tests list.
 */
public class Student {
    private String name;
    private ArrayList<StudentTest> testsList;
    private Integer elo;

    /**
     * Constructs a new Student with the specified name.
     *
     * @param name the name of the student
     */
    public Student(String name) {
        this.name = name;
        this.elo = 0;
        this.testsList = new ArrayList<>();
    }

    /**
     * Constructs a new Student with the specified name and tests list.
     *
     * @param name the name of the student
     * @param testsList the list of tests of the student
     */
    public Student(String name, ArrayList<StudentTest> testsList) {
        this.name = name;
        this.testsList = testsList;
        this.elo = 0;
    }

    /**
     * Returns the name of the student.
     *
     * @return the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the name of the student as a StringProperty.
     *
     * @return the name of the student as a StringProperty
     */
    public StringProperty getNameProperty() {
        return new SimpleStringProperty(name);
    }

    /**
     * Returns the tests list of the student.
     *
     * @return the tests list of the student
     */
    public List<StudentTest> getTestsList() {
        return testsList;
    }

    /**
     * Returns the Elo points of the student.
     *
     * @return the Elo points of the student
     */
    public Integer getElo() {return elo;}

    /**
     * Returns the Elo points of the student as a StringProperty.
     *
     * @return the Elo points of the student as a StringProperty
     */
    public StringProperty getEloProperty() {
        return new SimpleStringProperty(String.valueOf(elo));
    }

    /**
     * Updates the Elo points of the student by adding the specified value.
     *
     * @param value the value to add to the Elo points of the student
     */
    public void updateElo(Integer value) {
        elo += value;
    }

    /**
     * Adds a test to the tests list of the student.
     *
     * @param studentTest the test to add to the tests list of the student
     */
    public void addTest(StudentTest studentTest) {
        if (studentTest != null) {
            // check if the test is already in the list
            for (StudentTest test : testsList) {
                if (test.getSubject().getName().equalsIgnoreCase(studentTest.getSubject().getName()) && test.getDate().equals(studentTest.getDate())) {
                    testsList.remove(test);
                    testsList.add(studentTest);
                    return;
                }
            }
            testsList.add(studentTest);
        } else {
            System.out.println("Test is null");
        }
    }

    /**
     * Returns the test with the specified name and date from the tests list of the student.
     *
     * @param name the name of the test
     * @param date the date of the test
     * @return the test with the specified name and date
     */
    public StudentTest getTest(String name, String date) {
        for (StudentTest studentTest : testsList) {
            if (studentTest.getSubject().getName().equalsIgnoreCase(name) && studentTest.getDate().equals(date)) {
                return studentTest;
            }
        }
        return null;
    }
}
