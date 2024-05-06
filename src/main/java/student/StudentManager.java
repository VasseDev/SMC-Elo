package student;

import admin.Subject;

import java.util.ArrayList;

/**
 * StudentManager is a class that manages the students in the system.
 * It provides methods to add a student to the students list, calculate the Elo points of the students, and get the students list.
 * It also provides a method to find a student in the students list by name.
 */
public class StudentManager {
    private ArrayList<Student> studentsList;

    /**
     * Constructs a new StudentManager and initializes the students list.
     */
    public StudentManager() {
        studentsList = new ArrayList<>();
    }

    /**
     * Adds a student to the students list.
     *
     * @param student the student to add to the students list
     */
    public void addStudent(Student student) {
        if (student != null) {
            studentsList.add(student);
        } else {
            System.out.println("Student is null");
        }
    }

    /**
     * Calculates the Elo points of the students.
     * It iterates over the students list and for each student, it iterates over the tests list of the student and updates the Elo points of the student.
     */
    public void calculateEloPoints() {
        for (Student student : studentsList) {
            for (StudentTest studentTest : student.getTestsList()) {
                student.updateElo((int) Math.round(studentTest.getMark().getValue() * studentTest.getSubject().getMultiplier()));
            }
        }
    }

    /**
     * Returns the students list.
     *
     * @return the students list
     */
    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }

    /**
     * Finds a student in the students list by name.
     *
     * @param studentName the name of the student
     * @return the student with the specified name, or null if the student is not found
     */
    public Student findStudent(String studentName) {
        for (Student student : studentsList) {
            if (student.getName().equalsIgnoreCase(studentName)) {
                return student;
            }
        }
        return null;
    }
}
