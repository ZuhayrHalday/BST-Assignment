import java.io.File;
import java.util.Scanner;

/**
 * Serves as a node of a BST.
 */
class Node{
    String data;
    Node left;
    Node right;

    /**
     * Construction of node.
     * 
     * @param data The data/value stored in the node of the BST.
     */
    public Node(String data){
        this.data = data;
        left = null;
        right = null;
    }
}

/**
 * Class representing a BST data type.
 */
class BST{
    Node root;

    /**
     * Construction of an empty BST as seen in lecture notes.
     */
    public BST(){
        root = null;
    }

    /**
     * Inserts a new node into the BST using the standard BST insertion algorithm.
     * 
     * @param data Data of the new node being added to the BST.
     */
    public void insert(String data){
        root = insertBST(root, data);
    }

    private Node insertBST(Node root, String data){
        if (root == null) {
            root = new Node(data);
            return root;
        }

        int cmp = data.compareTo(root.data);
        if (cmp < 0) {
            root.left = insertBST(root.left, data);
        } else if (cmp > 0) {
            root.right = insertBST(root.right, data);
        }

        return root;
    }

    /**
     * Searches for a node in the BST.
     * 
     * @param term The term which the node to be found stores.
     * @return true if the term is found, false otherwise.
     */
    public boolean search(String searchTerm){
        return searchBST(root, searchTerm);
    }

    private boolean searchBST(Node root, String searchTerm){
        if (root == null){
            return false;
        }

        String[] partOfLine =root.data.split("\t");
        String term = partOfLine[0];
        if (term.equals(searchTerm) || term.startsWith(term + " ")){
            System.out.println(root.data);
            return true;
        }

        int cmp = searchTerm.compareTo(term);
        if (cmp < 0){
            return searchBST(root.left, searchTerm);
        }

        return searchBST(root.right, searchTerm);

    }

    /**
     * Searches for an node in the BST using both its term and statement data.
     * 
     * @param term     The term to be found.
     * @param sentence The sentence to be found.
     * @return true if the item is found, false otherwise.
     */
    public boolean TermAndStatementSearch(String searchTerm, String searchStatement){
        return TermAndStatementSearchBST(root, searchTerm, searchStatement);
    }

    private boolean TermAndStatementSearchBST(Node root, String searchTerm, String searchStatement){
        if (root == null){
            System.out.println("Term and statement: '" +searchTerm +"' and '" + searchStatement+ "' was not found in the knowledge base.");
            return false;
        }

        String[] partOfLine =root.data.split("\t");
        String term = partOfLine[0];
        String statement = partOfLine[1];
        if (term.equals(searchTerm) && statement.equals(searchStatement)) {
            System.out.println("\nStatement found: " + statement + " (Confidence score: " + partOfLine[2] + ")\n");
            return true;
        }

        int cmp = searchTerm.compareTo(term);
        if (cmp < 0){
            return TermAndStatementSearchBST(root.left, searchTerm, searchStatement);
        }

        return TermAndStatementSearchBST(root.right, searchTerm, searchStatement);
    }

    /**
     * Prints the terms in the BST in order of the preOrder traversal algorithm.
     */
    public void preOrderTraversal(String searchTerm){
        boolean foundTerm = preOrderTraversalBST(root, searchTerm);
        if (foundTerm == false){
            System.out.println("'" + searchTerm + "' was not found in the current knowledge base.");
        }
    }

    private boolean preOrderTraversalBST(Node root, String searchTerm){
        boolean foundTerm = false;
        if (root != null){
            String[] partOfLine =root.data.split("\t");
            String term = partOfLine[0];
            if (term.equals(searchTerm) || term.startsWith(searchTerm + " ")){
                String statement = partOfLine[1];
                String score = partOfLine[2];
                System.out.println("\nStatement found: " + statement + " (Confidence score: " + score + ")\n");
                foundTerm =true;
            }
            foundTerm |= preOrderTraversalBST(root.left, searchTerm);
            foundTerm |= preOrderTraversalBST(root.right, searchTerm);
        }
        return foundTerm;
    }
}

/**
 * Main class of the BST program.
 */
public class GenericsKbBSTApp {
    private static BST bst;
    private static boolean fileError = true;

    /**
     * Loads the file contents into the BST data structure.
     * 
     * @param fileName The name of the file containing the data to be read.
     */
    private static void loadFile(String fileName){
        try {
            Scanner file = new Scanner(new File(fileName));
            while (file.hasNextLine()) {
                String line = file.nextLine();
                bst.insert(line); // Assuming each line represents a statement in the knowledge base
            }
            System.out.println("\nKnowledge base loaded successfully.\n");
            fileError = false;
            file.close();
        } catch (Exception e) {
            System.err.println("Ran into an error while trying to read file: " + e.getMessage());
            fileError = true;
        }
    }
    
    /**
     * Main method of the BST program.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args){
        bst = new BST();
        Scanner keyboard = new Scanner(System.in);
        String menuInput = "";

        while (!menuInput.equals("5")) {
            String menu = "Choose an action from the menu:\r\n" + //
                    "1. Load a knowledge base from a file\r\n" + //
                    "2. Add a new statement to the knowledge base\r\n" + //
                    "3. Search for an item in the knowledge base by term\r\n" + //
                    "4. Search for a item in the knowledge base by term and sentence\r\n" + //
                    "5. Quit\r\n\n" + //
                    "Enter your choice: ";
            System.out.print(menu);
            menuInput = keyboard.nextLine().trim();

            if (menuInput.equals("1")) {
                System.out.print("Enter file name: ");
                String dataInput = keyboard.nextLine();
                loadFile(dataInput);

            } else if (menuInput.equals("2")) {
                if (fileError) {
                    System.out.println("\nKnowledge base has not been loaded yet.\n");
                    continue;
                }
                System.out.print("Enter the term: ");
                String term = keyboard.nextLine();

                System.out.print("Enter the statement: ");
                String statement = keyboard.nextLine();

                System.out.print("Enter the confidence score: ");
                String confidence = keyboard.nextLine();

                String newData = term + "\t" + statement + "\t" + confidence;

                bst.insert(newData);
                System.out.println("\nStatement for term " + term + " has been updated.\n");

            } else if (menuInput.equals("3")) {
                if (fileError) {
                    System.out.println("\nKnowledge base has not been loaded yet.\n");
                    continue;
                }
                System.out.print("Enter the term to search: ");
                String searchTerm = keyboard.nextLine();
                System.out.println("");

                bst.preOrderTraversal(searchTerm);
                System.out.println("");

            } else if (menuInput.equals("4")) {
                if (fileError) {
                    System.out.println("\nKnowledge base has not been loaded yet.\n");
                    continue;
                }
                System.out.print("Enter the term: ");
                String searchTerm = keyboard.nextLine();

                System.out.print("Enter the statement to search for: ");
                String searchStatement = keyboard.nextLine();

                bst.TermAndStatementSearch(searchTerm, searchStatement); 
            }
        }

        keyboard.close();
    }
}
