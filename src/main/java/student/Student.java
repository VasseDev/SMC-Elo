package student;

import admin.AdminTest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String surname;
    private ArrayList<StudentTest> testsList;
    private Integer elo;

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.elo = 0;
        this.testsList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public StringProperty getNameProperty() {
        return new SimpleStringProperty(name);
    }

    public String getSurname() {
        return surname;
    }

    public List<StudentTest> getTestsList() {
        return testsList;
    }

    public Integer getElo() {return elo;}
    public void updateElo(Integer value) {
        elo += value;
    }

    public void addTest(StudentTest studentTest) {
        if (studentTest != null) {
            testsList.add(studentTest);
        } else {
            System.out.println("Test is null");
        }
    }

    public StudentTest getTest(String name, String date) {
        for (StudentTest studentTest : testsList) {
            if (studentTest.getSubject().getName().equalsIgnoreCase(name) && studentTest.getDate().equals(date)) {
                System.out.println("Test found");
                return studentTest;
            }
        }
        return null;
    }
}
