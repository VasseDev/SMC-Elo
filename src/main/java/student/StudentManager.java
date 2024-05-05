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
            for (StudentTest studentTest : student.getTestsList()) {
                student.updateElo((int) Math.round(studentTest.getMark().getValue() * studentTest.getSubject().getMultiplier()));
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
