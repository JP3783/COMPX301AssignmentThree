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
        int[] skipArray = constructSkipArray(inputString);

        // Write skip array to file
        writeSkipArrayToFile(skipArray, inputString, tableFilePath);
    }

    // Function to construct skip array and visualize it in the console
    private static int[] constructSkipArray(String inputString) {
        int[] skipArray = new int[256];
        Arrays.fill(skipArray, inputString.length());

        // Populate skip array
        for (int i = 0; i < inputString.length(); i++) {
            skipArray[inputString.charAt(i)] = inputString.length() - i - 1;
        }

        // Print skip array
        System.out.println("Skip Array:");
        for (int i = 0; i < 256; i++) {
            if (skipArray[i] != inputString.length()) {
                char c = (char) i;
                System.out.println("Character: " + c + ", Skip Distance: " + skipArray[i]);
            }
        }

        return skipArray;
    }
    // Function to write skip array to file
    private static void writeSkipArrayToFile(int[] skipArray, String inputString, String filePath) {
    }
}