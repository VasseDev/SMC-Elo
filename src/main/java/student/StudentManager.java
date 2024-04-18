package student;

import admin.AdminTest;
import admin.Subject;
import admin.TestsManager;

import java.util.ArrayList;

public class StudentManager {
    private ArrayList<Student> studentsList;

    public StudentManager() {
        Subject subject = new Subject("Matematica", 1);
        this.studentsList = new ArrayList<>();
        Student student = new Student("John", "Doe");
        student.addTest(new StudentTest(subject, "2020-01-01", new Mark(5.0), 12, 13));
        studentsList.add(student);
        Student student2 = new Student("Jane", "Doe");
        student2.addTest(new StudentTest(subject, "2020-01-01", new Mark(6.0), 12, 13));
        studentsList.add(student2);
        Student student3 = new Student("Alice", "Smith");
        student3.addTest(new StudentTest(subject, "2020-01-01", new Mark(7.0), 12, 13));
        studentsList.add(student3);
        Student student4 = new Student("Bob", "Smith");
        student4.addTest(new StudentTest(subject, "2020-01-01", new Mark(8.0), 12, 13));
        studentsList.add(student4);
        Student student5 = new Student("Charlie", "Brown");
        student5.addTest(new StudentTest(subject, "2020-01-01", new Mark(9.0), 12, 13));
        studentsList.add(student5);
        Student student6 = new Student("David", "Brown");
        student6.addTest(new StudentTest(subject, "2020-01-01", new Mark(10.0), 12, 13));
        studentsList.add(student6);
    }

    public void addStudent(Student student) {
        if (student != null) {
            studentsList.add(student);
        } else {
            System.out.println("Student is null");
        }
    }

    public void removeStudent(Student student) {
        if (student != null) {
            studentsList.remove(student);
        } else {
            System.out.println("Student is null");
        }
    }

    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }
}
