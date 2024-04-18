package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import student.Student;
import student.StudentTest;

public class StudentTable {
    private final Student student;
    private final StudentTest test;

    public StudentTable(Student student, StudentTest test) {
        this.student = student;
        this.test = test;
    }

    // getters and setters
    public StringProperty getNameProperty() {
        return student.getNameProperty();
    }

    public StringProperty getMarkProperty() {
        return test.getMarkProperty();
    }
}
