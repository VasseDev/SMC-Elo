package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import student.Student;
import student.StudentTest;

/**
 * StudentTable is a utility class that represents a student's test data in a tabular format.
 * It is used for displaying student data in a TableView.
 */
public class StudentTable {
    private final Student student; // The student
    private final StudentTest test; // The student's test
    private int elo; // The student's Elo rating

    /**
     * Constructs a new StudentTable with the specified student and test.
     *
     * @param student the student
     * @param test the student's test
     */
    public StudentTable(Student student, StudentTest test) {
        this.student = student;
        this.test = test;
    }

    /**
     * Constructs a new StudentTable with the specified student, test, and Elo rating.
     *
     * @param student the student
     * @param test the student's test
     * @param elo the student's Elo rating
     */
    public StudentTable(Student student, StudentTest test, int elo) {
        this.student = student;
        this.test = test;
        this.elo = elo;
    }

    // Getters and setters

    /**
     * Returns the student's name property.
     *
     * @return the student's name property
     */
    public StringProperty getNameProperty() {
        return student.getNameProperty();
    }

    /**
     * Returns the student's mark property.
     * If the student's test is null, it returns an empty string property.
     *
     * @return the student's mark property
     */
    public StringProperty getMarkProperty() {
        if (this.test != null) {
            return this.test.getMarkProperty();
        } else {
            return new SimpleStringProperty("");
        }
    }
}