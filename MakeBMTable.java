import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        // System.out.println("Skip Array:");
        // for (int i = 0; i < 256; i++) {
        //     if (skipArray[i] != inputString.length()) {
        //         char c = (char) i;
        //         System.out.println("Character: " + c + ", Skip Distance: " + skipArray[i]);
        //     }
        // }

        return skipArray;
    }
    // Function to write skip array to file
    private static void writeSkipArrayToFile(int[] skipArray, String inputString, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header row
            StringBuilder headerRow = new StringBuilder("*");
            for (int i = 0; i < inputString.length(); i++) {
                headerRow.append(",").append(inputString.charAt(i));
            }
            writer.write(headerRow.toString());
            writer.newLine();
    
            // Write skip array rows
            //NEED TO IMPLEMENT THAT LOGIC
            
    
            // Write the last row for characters not found in the pattern
            //StringBuilder lastRow = new StringBuilder("*");
            //Write out last row
            //NEED TO IMPLEMENT THAT LOGIC
            //writer.write(lastRow.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}