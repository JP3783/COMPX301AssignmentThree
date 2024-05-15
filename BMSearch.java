import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BMSearch {

    private static String searchString;

    public static void main(String[] args) {
        // Check if command-line arguments are provided correctly
        if (args.length != 2) {
            System.err.println("Usage: java BMSearch <skip_array_file> <text_file>");
            System.exit(1);
        }

        // Parse command-line arguments
        String skipArrayFile = args[0];
        String textFile = args[1];

        try{
            loadSkipArray(skipArrayFile);
            searchInText(textFile);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void loadSkipArray(String skipArrayFile) throws IOException{
        try(BufferedReader reader = new BufferedReader(new FileReader(skipArrayFile))){
            //The first line contains the word, so extract it
            String line = reader.readLine();
            String resultString = line.replaceAll("[,*]", "");
            searchString = resultString;

            //System.out.println(searchString);  //Test if it outputs the correct word
        }
    }

    // Function to search for target string in text file
    private static void searchInText(String textFile) throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader(textFile))){
            String line;
            
            //Search for word
            while ((line = br.readLine()) != null){
                //If word is found in the line, display the line
                if(line.contains(searchString)){
                    System.out.println(line);
                }
            }
        }
    }
}
