/**
 * 
 */
public class MakeBMTable {
    public static void main(String[] args){
        if(args.length != 2){
            System.err.println("Usage: java MakeBMTable <input_text> <table_file_path>");
            System.exit(1);
        }

        String inputString = args[0];
        String tableFilePath = args[1];
    }
}