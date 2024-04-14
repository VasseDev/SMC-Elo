package model;

import java.io.*;
import java.util.ArrayList;

public class ConvertDataToCSV {
    public static void write(ArrayList<Test> testArrayList) throws Exception {
        FileWriter fileWriter = new FileWriter("test.csv");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        // Write the header line
        printWriter.println("Subject,Date,Initial Hour,Final Hour");

        // Write data lines
        for (Test test : testArrayList) {
            printWriter.println(test.getSubject().getName() + "," + test.getDate() + "," + test.getInitialHour() + "," + test.getFinalHour());
        }

        // Close the PrintWriter
        printWriter.close();
    }

    public static ArrayList<Test> read(String filePath, ArrayList<Subject> subjectArrayList) throws IOException {
        ArrayList<Test> testArrayList = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

        // Read the header line
        String row = csvReader.readLine();

        // Read data lines
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            // Assuming that the Test class has a constructor that accepts subject, date, initial hour, and final hour
            for (Subject subject : subjectArrayList) {
                if (subject.getName().equalsIgnoreCase(data[0])) {
                    Test test = new Test(subject, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                    testArrayList.add(test);
                    break;
                }
            }
        }

        csvReader.close();

        return testArrayList;
    }
}
