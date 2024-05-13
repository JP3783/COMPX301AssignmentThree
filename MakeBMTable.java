import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MakeBMTable {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java MakeBMTable <input_text> <output_file_path>");
            System.exit(1);
        }

        String inputString = args[0];
        String tableFilePath = args[1];

        ArrayList<ArrayList<String>> skipArray = constructSkipArray(inputString);

        writeSkipArrayToFile(skipArray, tableFilePath);
    }

    private static ArrayList<ArrayList<String>> constructSkipArray(String inputString) {
        ArrayList<ArrayList<String>> skipArray = new ArrayList<>();

        //Code to find the unique characters for the -COLUMN-
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : inputString.toCharArray()) {
            uniqueChars.add(c);
        }

        //Code to populate the top -ROW- with the string to search for
        ArrayList<String> patternRow = new ArrayList<>();
        patternRow.add("*");
        for (char c : inputString.toCharArray()) {
            patternRow.add(String.valueOf(c));
        }
        skipArray.add(patternRow);

        //Main code for finding skip amount and populate array 
        for (char c : uniqueChars) {
            ArrayList<String> row = new ArrayList<>();
            //Character to compare to 
            row.add(String.valueOf(c));
            for (int i = 0; i < inputString.length(); i++) {
                if (inputString.charAt(i) == c) {
                    row.add("0"); //Match
                } else {
                    row.add("-1"); //Not a match 
                    //NEED TO IMPLEMENT LOGIC HERE FOR CALCULATING SKIP DISTANCE
                }
            }
            skipArray.add(row);
        }

        // Add the row for characters not found in the pattern
        ArrayList<String> notFoundRow = new ArrayList<>();
        notFoundRow.add("*");
        for (int i = 0; i < inputString.length(); i++) {
            notFoundRow.add("-1"); //Not a match 
            //NEED TO IMPLEMENT LOGIC HERE FOR CALCULATING SKIP DISTANCE
        }
        skipArray.add(notFoundRow);

        return skipArray;
    }

    private static void writeSkipArrayToFile(ArrayList<ArrayList<String>> skipArray, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
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
