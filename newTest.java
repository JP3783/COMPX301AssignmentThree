import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class newTest {
    public static void main(String[] args) {
        // Check if the correct number of command-line arguments is provided
        if (args.length != 2) {
            System.err.println("Usage: java MakeBMTable <input_text> <output_file_path>");
            System.exit(1);
        }

        // Extract input text and output file path from command-line arguments
        String inputString = args[0];
        String tableFilePath = args[1];

        // Construct the skip array for the input text
        ArrayList<ArrayList<String>> skipArray = constructSkipArray(inputString);

        // Write the skip array to the output file
        writeSkipArrayToFile(skipArray, tableFilePath);
    }

    // Function to construct the skip array
    private static ArrayList<ArrayList<String>> constructSkipArray(String inputString) {
        // Initialize the skip array
        ArrayList<ArrayList<String>> skipArray = new ArrayList<>();
    
        // Find unique characters in the input string
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : inputString.toCharArray()) {
            uniqueChars.add(c);
        }
    
        // Populate the first row of the skip array with the input string
        ArrayList<String> patternRow = new ArrayList<>();
        patternRow.add("*");
        for (char c : inputString.toCharArray()) {
            patternRow.add(String.valueOf(c));
        }
        skipArray.add(patternRow);
    
        // Main loop to compute skip distances for each unique character
        for (char c : uniqueChars) {
            ArrayList<String> row = new ArrayList<>();
            // Add the current character to the row
            row.add(String.valueOf(c));
    
            // Preprocessing for bad character rule
            int[] badCharacter = new int[256]; // Assuming ASCII characters
            for (int i = 0; i < inputString.length(); i++) {
                badCharacter[inputString.charAt(i)] = i;
            }
    
            // Preprocessing for good suffix rule
            int[] suffix = new int[inputString.length() + 1];
            int[] borders = new int[inputString.length() + 1];
            suffix(inputString, suffix, borders);
    
            // Loop through each position in the input string
            for (int i = 0; i < inputString.length(); i++) {
                // Find the index of the character in the input string
                int matchIndex = findMatch(inputString, i, c);
                if (matchIndex != -1) {
                    // If a match is found, add the skip distance to the row
                    row.add(String.valueOf(i - matchIndex));
                } else {
                    // If no match is found, calculate skip distances using bad character and good suffix rules
                    int badCharSkip = i - badCharacter[c];
                    int goodSuffixSkip = goodSuffix(inputString, i, borders, suffix);
    
                    // If the pattern includes the right side, adjust the skip distance accordingly
                    if (i + 1 < inputString.length() && inputString.substring(i).equals(inputString.substring(0, inputString.length() - 1 - i))) {
                        goodSuffixSkip = inputString.length();
                    }

                    // goodSuffixSkip = inputString.length();
    
                    // Choose the maximum skip distance and add it to the row
                    row.add(String.valueOf(Math.max(badCharSkip, goodSuffixSkip)));
                }
            }
            // Add the row to the skip array
            skipArray.add(row);
        }
    
        // Add the row for characters not found in the pattern
        ArrayList<String> notFoundRow = new ArrayList<>();
        notFoundRow.add("*");
        for (int i = 0; i < inputString.length(); i++) {
            notFoundRow.add(String.valueOf(inputString.length())); // Skip distance is the length of the pattern
        }
        skipArray.add(notFoundRow);
    
        return skipArray;
    }
    
    // Function to find the match index of a character in the input string
    private static int findMatch(String inputString, int index, char c) {
        for (int i = index; i >= 0; i--) {
            if (inputString.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }
    
    // Function to compute the suffix array
    private static void suffix(String inputString, int[] suffix, int[] borders) {
        int length = inputString.length();
        int g = length - 1;
        int f = 0;
        suffix[length - 1] = length;

        for (int i = length - 2; i >= 0; i--) {
            if (i > g && suffix[i + length - 1 - f] < i - g) {
                suffix[i] = suffix[i + length - 1 - f];
            } else {
                if (i < g) {
                    g = i;
                }
                f = i;
                while (g >= 0 && inputString.charAt(g) == inputString.charAt(g + length - 1 - f)) {
                    g--;
                }
                suffix[i] = f - g;
            }
        }

        for (int i = 0; i < length; i++) {
            borders[i] = 0;
        }
        for (int i = length - 1; i >= 0; i--) {
            borders[length - suffix[i]] = i;
        }
    }
    
    // Function to compute the skip distance using the good suffix rule
    private static int goodSuffix(String inputString, int index, int[] borders, int[] suffix) {
        int length = inputString.length();
        int next = borders[length - index];
        if (next != -1) {
            return length - next - 1;
        }

        for (int i = index + 2; i <= length; i++) {
            if (suffix[length - i] == i) {
                return i;
            }
        }
        return length;
    }
    
    // Function to write the skip array to the output file
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
            //Display a little message to show data has been successfully written
            System.out.println("Skip table successfully written to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}