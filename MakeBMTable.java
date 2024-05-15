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
                String tmp = inputString.substring(i, inputString.length());
                StringBuilder sb = new StringBuilder(tmp);
                sb.setCharAt(0, c);
                String text = sb.toString();
                int skipDistance = calculateSkipDistance(text, inputString);
                row.add(String.valueOf(skipDistance));
            }
            skipArray.add(row);
        }

        // Add the row for characters not found in the pattern
        ArrayList<String> notFoundRow = new ArrayList<>();
        notFoundRow.add("*");
        for (int i = 0; i < inputString.length(); i++) {
            String tmp = inputString.substring(i, inputString.length());
            StringBuilder sb = new StringBuilder(tmp);
            sb.setCharAt(0, '*');
            String text = sb.toString();
            int skipDistance = calculateSkipDistance(text, inputString);
            notFoundRow.add(String.valueOf(skipDistance));
        }
        skipArray.add(notFoundRow);

        return skipArray;
    }


        public static int calculateSkipDistance(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        for (int i = 0; i < patternLength; i++) {
            
            String subPattern = pattern.substring(0, patternLength - i);
            String subText = text;

            if(subPattern.length() > textLength){
                subPattern = pattern.substring(subPattern.length() - textLength, patternLength - i);
            }
            else if(subPattern.length() < textLength){
                subText = text.substring(textLength - subPattern.length(), textLength);
            }
            if (subPattern.equals(subText)){
                //Return the distance which is the amount of times the pattern has been moved across
                return i;
            }
        }
        //If it is not found return 6
        return 6;
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
}
