class Node{
    String data;
    Node left;
    Node right;

    public Node(String data){
        this.data = data;
        left = null;
        right = null;
    }
}

class BST{
    Node root;

    public BST(){
        root = null;
    }

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
}
public class GenericsKbBSTApp {
    
}
