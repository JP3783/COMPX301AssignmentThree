import java.util.ArrayList;

/**
 * 
 */
public class MakeBMTable {
    public static void main(String[] args){
        //Command-line Argument Validation
        if(args.length != 2){
            System.err.println("Usage: java MakeBMTable <input_text> <output_file_path>");
            System.exit(1);
        }

        //Reading Command-line Arguments
        String inputString = args[0];
        String tableFilePath = args[1];

        int stringLength = inputString.length();
        System.out.println("Length of input word: " + stringLength);
        
        //Construction of Skip Array
        ArrayList<String> textArray = new ArrayList<String>();
        textArray.add(inputString);

        //Given a pattern string and its length, construct two arrays
        //Bad character heuristic array (BC)
        

    }
}