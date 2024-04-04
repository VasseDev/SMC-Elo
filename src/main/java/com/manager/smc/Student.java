package com.manager.smc;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String surname;
    private ArrayList<Test> testsList;
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

    public String getSurname() {
        return surname;
    }

    public List<Test> getTestsList() {
        return testsList;
    }

    public Integer getElo() {return elo;}
    public void updateElo(Integer value) {
        elo += value;
    }

    public void addTest(Test test) {
        if (test != null) {
            testsList.add(test);
        } else {
            System.out.println("Test is null");
        }
    }
}
