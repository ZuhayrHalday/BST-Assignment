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

    public static String[] ReadIntoArray(String fileName){
        int numLines = LineCount(fileName);
        String[] arrFile = new String[numLines];

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int arrIndex = 0;
            String newLine;

            while ((newLine = bufferedReader.readLine()) != null){
                arrFile[arrIndex++] = newLine;
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.err.println("Ran into an error while trying to read file: " + e.getMessage());
        }
        return arrFile;    
    }

    public static void TermSearch(String term){
        if (arrFile == null){
            System.out.println("Missing data, knowledge base not loaded yet.");
            return;
        }

        boolean termFound = false;

        for (String result : arrFile){
            String[] partOfLine = result.split("\t");
            if (partOfLine.length >= 3 && (partOfLine[0].equals(term) || partOfLine[0].startsWith(term + " ") || partOfLine[0].endsWith(" " + term) || partOfLine[0].contains(" " + term + " "))){
                String statement = partOfLine[1];
                String score = partOfLine[2];
                System.out.println("\nStatement termFound: " + statement + " (Confidence score: " + score + ")\n");
                termFound = true;
                break;
            }
        }

        if (termFound == false){
            System.out.println("'" + term + "' was not termFound in the current knowledge base.");
        }
    }

    public static void TermAndStatementSearch(String term, String statement) {
        if (arrFile == null) {
            System.out.println("Missing data, knowledge base not loaded yet.");
            return;
        }

        boolean termFound = false;

        for (String result : arrFile) {
            String[] partOfLine = result.split("\t");
            if (partOfLine.length >= 3 && partOfLine[0].equals(term) && partOfLine[1].equals(statement)) {
                String score = partOfLine[2];
                System.out.println("\nThe statement was termFound and has a confidence score of  " + score + ".\n");
                termFound = true;
                break;
            }
        }

        if (termFound == false) {
            System.out.println("Term and statement: '" +term +"' and '" + statement+ "' was not termFound in the knowledge base.");
        }
    }
}
