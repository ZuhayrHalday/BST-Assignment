import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GenericsKbArrayApp {
    private static String[] arrFile;
    
    public static int LineCount(String fileName){
        int numLines = 0;
        String file = fileName;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while (bufferedReader.readLine() != null){
                numLines++;
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.err.println("Ran into an error while trying to read file: " + e.getMessage());
        }
        return numLines;
    }

}
