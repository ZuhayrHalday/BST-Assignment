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

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while (bufferedReader.readLine() != null){
            numLines++;
        }
        
        return numLines;
    }

}
