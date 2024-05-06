package student;

import admin.Subject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * StudentTest is a class that represents a test taken by a student.
 * It provides methods to get the subject, date, initial hour, final hour, and mark of the test.
 * It also provides a method to get the mark of the test as a StringProperty.
 */
public class StudentTest {
    private final Subject subject; // The subject of the test
    private final String date; // The date of the test
    private final int initialHour; // The initial hour of the test
    private final int finalHour; // The final hour of the test
    private final Mark mark; // The mark of the test

    /**
     * Constructs a new StudentTest with the specified subject, date, mark, initial hour, and final hour.
     *
     * @param subject the subject of the test
     * @param date the date of the test
     * @param mark the mark of the test
     * @param initialHour the initial hour of the test
     * @param finalHour the final hour of the test
     */
    public StudentTest(Subject subject, String date, Mark mark, int initialHour, int finalHour){
        this.subject=subject;
        this.date=date;
        this.initialHour = initialHour;
        this.finalHour = finalHour;
        this.mark = mark;
    }

    /**
     * Returns the subject of the test.
     *
     * @return the subject of the test
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Returns the date of the test.
     *
     * @return the date of the test
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the initial hour of the test.
     *
     * @return the initial hour of the test
     */
    public int getInitialHour() {
        return initialHour;
    }

    /**
     * Returns the final hour of the test.
     *
     * @return the final hour of the test
     */
    public int getFinalHour() {
        return finalHour;
    }

    /**
     * Returns the mark of the test.
     *
     * @return the mark of the test
     */
    public Mark getMark() {
        return mark;
    }

    /**
     * Returns the mark of the test as a StringProperty.
     *
     * @return the mark of the test as a StringProperty
     */
    public StringProperty getMarkProperty() {
        return new SimpleStringProperty(String.valueOf(mark.getValue()));
    }
}