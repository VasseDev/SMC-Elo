package model;

public class AlgorithmTest {
    public void testUpdateEloPoints() {
        // Create some mock data
        Subject subject = new Subject("Math", 100);
        String date = "2023-03-01";
        Mark mark = new Mark(8.5);
        Test test = new Test(subject, date, mark);
        Student student = new Student("Pietro", "Vassena");
        student.addTest(test);

        Mark mark1 = new Mark(3.5);
        Test test1 = new Test(subject, date, mark1);
        Student student1 = new Student("Federico", "Ricciardelli");
        student1.addTest(test1);

        Mark mark2 = new Mark(8);
        Test test2 = new Test(subject, date, mark2);
        Student student2 = new Student("Maurizio", "Testa");
        student2.addTest(test2);

        Mark mark3 = new Mark(7.5);
        Test test3 = new Test(subject, date, mark3);
        Student student3 = new Student("Giovanni", "Bianchi");
        student3.addTest(test3);

        Mark mark4 = new Mark(6.75);
        Test test4 = new Test(subject, date, mark4);
        Student student4 = new Student("Mario", "Rossi");
        student4.addTest(test4);

        Mark mark5 = new Mark(5.25);
        Test test5 = new Test(subject, date, mark5);
        Student student5 = new Student("Luca", "Verdi");
        student5.addTest(test5);

        Mark mark6 = new Mark(10);
        Test test6 = new Test(subject, date, mark6);
        Student student6 = new Student("Paolo", "Neri");
        student6.addTest(test6);

        Mark mark7 = new Mark(3);
        Test test7 = new Test(subject, date, mark7);
        Student student7 = new Student("Giovanni", "Bianchi");
        student7.addTest(test7);

        Mark mark8 = new Mark(2);
        Test test8 = new Test(subject, date, mark8);
        Student student8 = new Student("Mariolino", "Rossi");
        student8.addTest(test8);

        Mark mark9 = new Mark(1);
        Test test9 = new Test(subject, date, mark9);
        Student student9 = new Student("Lucio", "Verdi");
        student9.addTest(test9);

        Mark mark10 = new Mark(9.5);
        Test test10 = new Test(subject, date, mark10);
        Student student10 = new Student("Paolino", "Neri");
        student10.addTest(test10);


        // Create an instance of Algorithm and add the student to it
        Algorithm algorithm = new Algorithm();
        algorithm.addStudent(student);
        algorithm.addStudent(student1);
        algorithm.addStudent(student2);
        algorithm.addStudent(student3);
        algorithm.addStudent(student4);
        algorithm.addStudent(student5);
        algorithm.addStudent(student6);
        algorithm.addStudent(student7);
        algorithm.addStudent(student8);
        algorithm.addStudent(student9);
        algorithm.addStudent(student10);

        // Call the method to be tested
        algorithm.updateEloPoints(subject, date);

        for (Student currentStudent: algorithm.getStudentsList()) {
            System.out.println(currentStudent.getName() + " " + currentStudent.getSurname());
            System.out.println(currentStudent.getElo());
        }
    }
}