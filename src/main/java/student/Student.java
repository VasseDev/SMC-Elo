package student;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private ArrayList<StudentTest> testsList;
    private Integer elo;

    public Student(String name) {
        this.name = name;
        this.elo = 0;
        this.testsList = new ArrayList<>();
    }

    public Student(String name, ArrayList<StudentTest> testsList) {
        this.name = name;
        this.testsList = testsList;
        this.elo = 0;
    }

    public String getName() {
        return name;
    }

    public StringProperty getNameProperty() {
        return new SimpleStringProperty(name);
    }

    public List<StudentTest> getTestsList() {
        return testsList;
    }

    public Integer getElo() {return elo;}

    public StringProperty getEloProperty() {
        return new SimpleStringProperty(String.valueOf(elo));
    }

    public void updateElo(Integer value) {
        elo += value;
    }

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

    public StudentTest getTest(String name, String date) {
        for (StudentTest studentTest : testsList) {
            if (studentTest.getSubject().getName().equalsIgnoreCase(name) && studentTest.getDate().equals(date)) {
                return studentTest;
            }
        }
        return null;
    }
}
