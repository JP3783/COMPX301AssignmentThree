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
                    System.out.println(i + " dont skip " + c);
                } else {
                    // Calculate the skip distance based on the Good Suffix Heuristic
                    int skipDistance = calculateSkipDistance(inputString, i, c);
                    row.add(String.valueOf(skipDistance));
                }
            }
            skipArray.add(row);
        }

        // Add the row for characters not found in the pattern
        ArrayList<String> notFoundRow = new ArrayList<>();
        notFoundRow.add("*");
        for (int i = 0; i < inputString.length(); i++) {
            //notFoundRow.add(String.valueOf(inputString.length())); //Length of the pattern ????????????
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
            System.out.println("Successfully written to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static int calculateSkipDistance(String inputString, int i, char c){
        //inputString is just kokako

        char character = inputString.charAt(i);
        System.out.println("== " + i + " " + "Expect: " + character + " See: " + c);


        StringBuilder sb = new StringBuilder();
        for(int j = i; j < inputString.length(); j++) {
            if(i == j) {
            sb.append(c);
            } else {
            sb.append(inputString.charAt(j));
            }
        }
        System.out.println(sb);
        
        if (inputString.contains(sb)) {
            System.out.println( "' found in string.");
            //Find distance to the nearest one
        } else {
            System.out.println("' not found in string.");
            //Print the lenfth of the string
            return inputString.length();
        }

        int lastOccurrence = inputString.lastIndexOf(c);
        int value = i - lastOccurrence;
        return value;
    }



}
