import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This program reads generics from a text file and stores them in an array in order to manipulate the data.
 */
public class GenericsKbArrayApp {
    private static String[] arrFile = null;
    private static boolean fileError = false;
    
    /**
     * Finds the number of lines in a file in order for an adequately sized array to be made.
     * 
     * @param fileName The name of the file which is being read.
     * @return The total amount of lines in the file.
     */
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

    /**
     * Reads all the lines from a file into an array.
     * 
     * @param fileName The name of the file which is being read.
     * @return An array containing all lines from a given file.
     */
    public static String[] ReadIntoArray(String fileName){
        int numLines = LineCount(fileName);
        String[] arrFile = new String[numLines];
        fileError = false;

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
            fileError = true;
        }
        return arrFile;    
    }

    /**
     * Searches for an entry in the array using the term.
     * 
     * @param term The term which needs to be found.
     */
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
                System.out.println("\nStatement found: " + statement + " (Confidence score: " + score + ")\n");
                termFound = true;
            }
        }

        if (termFound == false){
            System.out.println("'" + term + "' was not found in the current knowledge base.");
        }
    }

    /**
     * Searches for an entry in the array using the term and its matching statement.
     * 
     * @param term      The term to be found.
     * @param statement The statement to be found.
     */
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
                System.out.println("\nThe statement was found and has a confidence score of  " + score + ".\n");
                termFound = true;
                break;
            }
        }

        if (termFound == false) {
            System.out.println("Term and statement: '" +term +"' and '" + statement+ "' was not found in the knowledge base.");
        }
    }

    /**
     * The main method which contains most of the code dealing with user interaction.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {

        String menuInput = "";
        String dataInput = "";
        Scanner keyboard = new Scanner(System.in);

        String menu = "Choose an action from the menu:\r\n" + //
                "1. Load a knowledge base from a file\r\n" + //
                "2. Add a new statement to the knowledge base\r\n" + //
                "3. Search for an item in the knowledge base by term\r\n" + //
                "4. Search for a item in the knowledge base by term and sentence\r\n" + //
                "5. Quit\r\n\n" + //
                "Enter your choice: ";

        while (!menuInput.equals("5")) {
            System.out.print(menu);
            menuInput = keyboard.nextLine().trim();

            if (menuInput.equals("1")) {
                System.out.print("Enter file name: ");
                dataInput = keyboard.nextLine();
                arrFile = ReadIntoArray(dataInput);
                
                if (fileError == false){
                    System.out.println("\n Knowledge base loaded successfully.\n");
                }

            } else if (menuInput.equals("2")) {
                if (arrFile == null) {
                    System.out.println("\nMissing data, knowledge base not loaded yet.\n");
                    continue;
                }
                System.out.print("Enter the term: ");
                dataInput = keyboard.nextLine();
                String term = dataInput;

                System.out.print("Enter the statement: ");
                dataInput = keyboard.nextLine();
                String statement = dataInput;

                System.out.print("Enter the confidence score: ");
                dataInput = keyboard.nextLine();
                String score = dataInput;

                String newElement = term + "\t" + statement + "\t" + score;

                arrFile = Arrays.copyOf(arrFile, arrFile.length + 1);
                arrFile[arrFile.length - 1] = newElement;

                System.out.println("\nStatement for term " + term + " has been updated.\n");

            } else if (menuInput.equals("3")) {
                if (arrFile == null) {
                    System.out.println("\nMissing data, knowledge base not loaded yet.\n");
                    continue;
                }
                System.out.print("Enter the term to search: ");
                dataInput = keyboard.nextLine();
                TermSearch(dataInput);

            } else if (menuInput.equals("4")) {
                if (arrFile == null) {
                    System.out.println("\nMissing data, knowledge base not loaded yet.\n");
                    continue;
                }
                System.out.print("Enter the term: ");
                dataInput = keyboard.nextLine();
                String term = dataInput;

                System.out.print("Enter the statement to search for: ");
                dataInput = keyboard.nextLine();
                String statement = dataInput;

                TermAndStatementSearch(term, statement);
            }
        }
        keyboard.close();
    }
}
