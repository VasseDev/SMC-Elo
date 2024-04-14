package model;

import java.util.ArrayList;

public class TestsManager {
    ArrayList<Test> testsList;
    ArrayList<Subject> subjectsList;
    
    public TestsManager() {
        this.testsList = new ArrayList<Test>();
        this.subjectsList = new ArrayList<Subject>();
        subjectsList.add(new Subject("Matematica", 1));
        subjectsList.add(new Subject("Italiano", 1));
        subjectsList.add(new Subject("Inglese", 1));
        subjectsList.add(new Subject("Storia", 1));
        subjectsList.add(new Subject("Scienze", 1));
        subjectsList.add(new Subject("Arte", 1));
        subjectsList.add(new Subject("Educazione Fisica", 1));
        subjectsList.add(new Subject("Religione", 1));
        subjectsList.add(new Subject("Musica", 1));
        subjectsList.add(new Subject("Tecnologia", 1));
        subjectsList.add(new Subject("Informatica", 1));
        subjectsList.add(new Subject("Geografia", 1));
        subjectsList.add(new Subject("Filosofia", 1));
        subjectsList.add(new Subject("Latino", 1));
        subjectsList.add(new Subject("Greco", 1));
        subjectsList.add(new Subject("TPS", 1));
        subjectsList.add(new Subject("Sistemi e reti", 1));
        subjectsList.add(new Subject("Telecomunicazioni", 1));
    }
    
    public void addTest(String subject, String date, int initialHour, int finalHour) {
        for (Subject s : subjectsList) {
            if (s.getName().equalsIgnoreCase(subject)) {
                Test test = new Test(s, date, initialHour, finalHour);

                for (Test t : testsList) {
                    if (t.getDate().equals(test.getDate())
                            && t.getSubject().getName().equalsIgnoreCase(test.getSubject().getName())) {
                        testsList.remove(t);
                        testsList.add(test);
                        return;
                    }
                }

                testsList.add(test);
                System.out.println("Test added");
                System.out.println("Subject: " + s.getName());
                System.out.println("Date: " + date);
                System.out.println("Initial Hour: " + initialHour);
                System.out.println("Final Hour: " + finalHour);
                return;
            }
        }
    }

    public void saveToCSV() {
        try {
            ConvertDataToCSV.write(testsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        printTestsList();
    }

    public ArrayList<Test> readFromCSV() {
        try {
            testsList = ConvertDataToCSV.read("test.csv", subjectsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        printTestsList();
        return testsList;
    }

    private void printTestsList() {
        for (Test t : testsList) {
            System.out.println("Subject: " + t.getSubject().getName());
            System.out.println("Date: " + t.getDate());
            System.out.println("Initial Hour: " + t.getInitialHour());
            System.out.println("Final Hour: " + t.getFinalHour());
        }
    }
}
