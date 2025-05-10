import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BST {
    Node root;

    public BST(Node root) {
        this.root = root;
    }

    public void insert(Node node, int k) {
        if(k <= node.getValue()) {
            if(node.getLeft() == null) {
                node.setLeft(new Node(k));
                node.getLeft().setParent(node);
            } else {
                insert(node.getLeft(), k);
            }
        } else {
            if(node.getRight() == null) {
                node.setRight(new Node(k));
                node.getRight().setParent(node);
            } else {
                insert(node.getRight(), k);
            }
        }
    }

    public void delete(int value) {
        Node node = searchNode(root, value);
        if(node == null) {
            return;
        }
        if(node.getLeft() == null && node.getRight() == null) {
            Node parent = node.getParent();
            if(parent.getLeft() == node) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        } else if(node.getLeft() != null && node.getRight() != null) {
            Node successor = findSuccessor(node);
            node.setValue(successor.getValue());
            //to be implemented
        }
    }

    public int height(Node node) {
        if(node == null) {
            return -1;
        }

        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public Node searchNode(Node node, int value) {
        if(node == null) {
            return null;
        }
        if(node.getValue() == value) {
            return node;
        } else if(node.getValue() < value) {
            return searchNode(node.getRight(), value);
        } else {
            return searchNode(node.getLeft(), value);
        }
    }

    public void inorderTreeWalk(Node node) {
        if(node.getLeft() != null) {
            inorderTreeWalk(node.getLeft());
        }
        System.out.println(node.getValue());
        if(node.getRight() != null) {
            inorderTreeWalk(node.getRight());
        }
    }

    public Node findSuccessor(Node node) {
        if(node.getRight() != null) {
            node = node.getRight();
            while(node.getLeft() != null) {
                node = node.getLeft();
            }
            return node;
        } else {
            Node y = node.getParent();
            while(y != null && y.getRight() == node) {
                node = y;
                y = y.getParent();
            }
            return y;
        }
    }

    // Helper function to perform inorder traversal and
    // populate the 2D matrix
    private void inorder(Node node, int row, int col,
                        int height,
                        List<List<String>> ans) {
        if (node == null) {
            return;
        }

        // Calculate offset for child positions
        int offset = (int) Math.pow(2, height - row - 1);

        // Traverse the left subtree
        if (node.getLeft() != null) {
            inorder(node.getLeft(), row + 1, col - offset,
                    height, ans);
        }

        // Place the current node's value in the matrix
        ans.get(row).set(col, String.valueOf(node.getValue()));

        // Traverse the right subtree
        if (node.getRight() != null) {
            inorder(node.getRight(), row + 1, col + offset,
                    height, ans);
        }
    }

    // Function to convert the binary tree to a 2D matrix
    public List<List<String>> treeToMatrix(Node node) {

        // Find the height of the tree
        int height = this.height(node);

        // Rows are height + 1; columns are 2^(height+1) - 1
        int rows = height + 1;
        int cols = (int) Math.pow(2, height + 1) - 1;

        // Initialize 2D matrix with empty strings
        List<List<String>> ans = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<String> row = new ArrayList<>(Collections
                    .nCopies(cols, ""));
            ans.add(row);
        }

        // Populate the matrix using inorder traversal
        inorder(node, 0, (cols - 1) / 2, height, ans);

        return ans;
    }

    // Function to print a 2D matrix
    public void print2DArray(List<List<String>> arr) {
        for (List<String> row : arr) {
            for (String cell : row) {
                if (cell.isEmpty()) {
                    System.out.print(" ");
                } else {
                    System.out.print(cell);
                }
            }
            System.out.println();
        }
    }
}
