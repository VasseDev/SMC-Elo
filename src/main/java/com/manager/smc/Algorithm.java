package com.manager.smc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.round;

public class Algorithm {
    private ArrayList<Student> studentsList;
    private final double negativeMultiplier = 0.5;

    public Algorithm() {
        this.studentsList = new ArrayList<Student>();
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }

    private double averageTest(Subject subject, String date){
        double sum=0;
        int counter=0;
        for (Student student: studentsList) {
            for (Test test : student.getTestsList()) {
                if (test.getSubject().equals(subject) && test.getDate().equals(date)) {
                    sum += test.getMark().getValue();
                    counter++;
                }
            }
        }
        return sum/counter;
    }

    public void addStudent(Student student) {
        studentsList.add(student);
    }

    public void updateEloPoints(Subject subject, String date) {
        double averageGrade = averageTest(subject, date);
        System.out.println(averageGrade);
        for (Student student: studentsList) {
            for (Test test : student.getTestsList()) {
                if (test.getSubject().equals(subject) && test.getDate().equals(date)) {
                    if (test.getMark().getValue() >= averageGrade) {
                        student.updateElo((int) ((test.getMark().getValue() - averageGrade)
                                * test.getSubject().getMultiplier()));
                    } else {
                        student.updateElo((int) ((test.getMark().getValue() - averageGrade)
                                * test.getSubject().getMultiplier() * negativeMultiplier));
                    }
                }
            }
        }
    }
}
