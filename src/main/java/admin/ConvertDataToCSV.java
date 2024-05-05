package admin;

import java.io.*;
import java.util.ArrayList;

public class ConvertDataToCSV {
    public static void write(ArrayList<AdminTest> adminTestArrayList) throws Exception {
        FileWriter fileWriter = new FileWriter("test.csv");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        // Write the header line
        printWriter.println("Subject,Date,Initial Hour,Final Hour");

        // Write data lines
        for (AdminTest adminTest : adminTestArrayList) {
            printWriter.println(adminTest.getSubject().getName() + "," + adminTest.getDate() + "," + adminTest.getInitialHour() + "," + adminTest.getFinalHour());
        }

        // Close the PrintWriter
        printWriter.close();
    }

    public static ArrayList<AdminTest> read(String filePath, ArrayList<Subject> subjectArrayList) throws IOException {
        File file = new File(filePath);
        file.createNewFile();
        ArrayList<AdminTest> adminTestArrayList = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

        // Read the header line
        String row = csvReader.readLine();

        // Read data lines
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            // Assuming that the Test class has a constructor that accepts subject, date, initial hour, and final hour
            for (Subject subject : subjectArrayList) {
                if (subject.getName().equalsIgnoreCase(data[0])) {
                    AdminTest adminTest = new AdminTest(subject, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                    adminTestArrayList.add(adminTest);
                    break;
                }
            }
        }

        csvReader.close();

        return adminTestArrayList;
    }
}
