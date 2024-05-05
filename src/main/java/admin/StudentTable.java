package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import student.Student;
import student.StudentTest;

public class StudentTable {
    private final Student student;
    private final StudentTest test;
    private int elo;

    public StudentTable(Student student, StudentTest test) {
        this.student = student;
        this.test = test;
    }

    public StudentTable(Student student, StudentTest test, int elo) {
        this.student = student;
        this.test = test;
        this.elo = elo;
    }

    // getters and setters
    public StringProperty getNameProperty() {
        return student.getNameProperty();
    }

    public StringProperty getMarkProperty() {
        if (this.test != null) {
            return this.test.getMarkProperty();
        } else {
            return new SimpleStringProperty("");
        }
    }
}
