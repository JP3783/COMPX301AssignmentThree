import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 */
public class MakeBMTable {
    public static void main(String[] args) {
        // Command-line Argument Validation
        if (args.length != 2) {
            System.err.println("Usage: java MakeBMTable <input_text> <output_file_path>");
            System.exit(1);
        }

        // Reading Command-line Arguments
        String inputString = args[0];
        String tableFilePath = args[1];

        // Compute BM skip array
        ArrayList<ArrayList<String>> skipArray = constructSkipArray(inputString);

        // Write skip array to file
        writeSkipArrayToFile(skipArray, inputString, tableFilePath);
    }

    // Function to construct skip array and visualize it in the console
    private static ArrayList<ArrayList<String>> constructSkipArray(String inputString) {
        ArrayList<ArrayList<String>> skipArray = new ArrayList<>();

        // Initialize skip array with *
        ArrayList<String> headerRow = new ArrayList<>();
        headerRow.add("*");
        skipArray.add(headerRow);

        // Add characters found in the input string to skip array
        for (int i = 0; i < inputString.length(); i++) {
            ArrayList<String> row = new ArrayList<>();
            char c = inputString.charAt(i);
            row.add(String.valueOf(c));
            skipArray.add(row);
        }

        return skipArray;
    }

    // Function to write skip array to file
    private static void writeSkipArrayToFile(ArrayList<ArrayList<String>> skipArray, String inputString, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write skip array to file
            for (ArrayList<String> row : skipArray) {
                for (int i = 0; i < row.size(); i++) {
                    writer.write(row.get(i));
                    if (i < row.size() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
