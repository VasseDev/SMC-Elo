package admin;

import java.io.*;
import java.util.ArrayList;

/**
 * ConvertDataToCSV is a utility class that provides methods to write and read AdminTest data to and from a CSV file.
 */
public class ConvertDataToCSV {
    /**
     * Writes a list of AdminTest objects to a CSV file.
     *
     * @param adminTestArrayList the list of AdminTest objects to write
     * @throws Exception if an I/O error occurs
     */
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

    /**
     * Reads a CSV file and returns a list of AdminTest objects.
     *
     * @param filePath the path of the CSV file to read
     * @param subjectArrayList the list of Subject objects to match with the AdminTest objects
     * @return a list of AdminTest objects
     * @throws IOException if an I/O error occurs
     */
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