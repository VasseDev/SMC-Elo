package student;

import admin.Subject;

import java.util.ArrayList;

public class StudentManager {
    private ArrayList<Student> studentsList;

    public StudentManager() {
        studentsList = new ArrayList<>();
    }

    public void addStudent(Student student) {
        if (student != null) {
            studentsList.add(student);
        } else {
            System.out.println("Student is null");
        }
    }

    public void calculateEloPoints() {
        for (Student student : studentsList) {
            System.out.println("Calculating elo points for student " + student.getName());
            System.out.println("Student tests list: " + student.getTestsList());
            for (StudentTest studentTest : student.getTestsList()) {
                student.updateElo((int) Math.round(studentTest.getMark().getValue() * studentTest.getSubject().getMultiplier()));
                System.out.println("Student " + student.getName() + " has " + student.getElo() + " elo points");
            }
        }
    }

    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }

    public Student findStudent(String studentName) {
        for (Student student : studentsList) {
            if (student.getName().equalsIgnoreCase(studentName)) {
                return student;
            }
        }
        return null;
    }
}
