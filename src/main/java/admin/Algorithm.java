package admin;

import student.Student;
import student.StudentTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Algorithm class is responsible for calculating and updating the Elo points of students.
 * It uses the Elo rating system, which is a method for calculating the relative skill levels of players in zero-sum games.
 */
public class Algorithm {
    private ArrayList<Student> studentsList; // List of students
    private final double negativeMultiplier = 0.5; // Multiplier used when a student's mark is below the average

    /**
     * Constructs a new Algorithm with an empty list of students.
     */
    public Algorithm() {
        this.studentsList = new ArrayList<Student>();
    }

    /**
     * Returns the list of students.
     *
     * @return the list of students
     */
    public List<Student> getStudentsList() {
        return studentsList;
    }

    /**
     * Calculates the average mark of a test for a given subject and date.
     *
     * @param subject the subject of the test
     * @param date the date of the test
     * @return the average mark of the test
     */
    private double averageTest(Subject subject, String date){
        double sum=0;
        int counter=0;
        for (Student student: studentsList) {
            for (StudentTest studentTest : student.getTestsList()) {
                if (studentTest.getSubject().equals(subject) && studentTest.getDate().equals(date)) {
                    sum += studentTest.getMark().getValue();
                    counter++;
                }
            }
        }
        return sum/counter;
    }

    /**
     * Adds a student to the list of students.
     *
     * @param student the student to add
     */
    public void addStudent(Student student) {
        studentsList.add(student);
    }

    /**
     * Updates the Elo points of all students for a given subject and date.
     * The Elo points are increased if a student's mark is above the average, and decreased otherwise.
     *
     * @param subject the subject of the test
     * @param date the date of the test
     */
    public void updateEloPoints(Subject subject, String date) {
        double averageGrade = averageTest(subject, date);
        System.out.println(averageGrade);
        for (Student student: studentsList) {
            for (StudentTest studentTest : student.getTestsList()) {
                if (studentTest.getSubject().equals(subject) && studentTest.getDate().equals(date)) {
                    if (studentTest.getMark().getValue() >= averageGrade) {
                        student.updateElo((int) ((studentTest.getMark().getValue() - averageGrade)
                                * studentTest.getSubject().getMultiplier()));
                    } else {
                        student.updateElo((int) ((studentTest.getMark().getValue() - averageGrade)
                                * studentTest.getSubject().getMultiplier() * negativeMultiplier));
                    }
                }
            }
        }
    }
}