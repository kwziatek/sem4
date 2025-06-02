import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BST {
    Node root;

    public BST(Node root) {
        this.root = root;
    }

    //inserts recursively into BST, should be called with root and value that new node will take as parameter, creates node with that value
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

    //calls deleteNode if there is a node with value given as parameter, returns otherwise, helper function to make code clearer
    public void delete(int value) {
        Node node = searchNode(root, value);
        if(node == null) {
            return;
        }
        deleteNode(node);
    }

    //recursively deletes Node: finds successor of a Node and takes it's value, then calls this function on successor
    //work easier when either/both children are null
    public void deleteNode(Node node) {
        if(node.getLeft() == null && node.getRight() == null) {
            if(node.getParent() == null) {
                root = null;
                return;
            }
            Node parent = node.getParent();
            if(parent.getLeft() == node) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        } else if(node.getLeft() != null && node.getRight() != null) {
            Node successor = findSuccessor(node);
            node.setValue(successor.getValue());
            deleteNode(successor);
        } else {
            if(node == root) {
                if(root.getLeft() != null) {
                    root = root.getLeft();
                    root.setParent(null);
                } else {
                    root = root.getRight();
                    root.setParent(null);
                }
            } else if(node.getLeft() != null) {
                if(node.getParent().getLeft() == node) {
                    node.getParent().setLeft(node.getLeft());
                } else {
                    node.getParent().setRight(node.getLeft());
                }
                node.getLeft().setParent(node.getParent());
            } else {
                if(node.getParent().getLeft() == node) {
                    node.getParent().setLeft(node.getRight());
                } else {
                    node.getParent().setRight(node.getRight());
                }
                node.getRight().setParent(node.getParent());
            }
        }
    }

    //recursively counts height
    public int height(Node node) {
        if(node == null) {
            return 0;
        }

        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());

        return Math.max(leftHeight, rightHeight) + 1;
    }

    //searches recursively if there is a node with given value, should be started from root
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

    //prints tree nodes in order (asc)
    public void inorderTreeWalk(Node node) {
        if(node.getLeft() != null) {
            inorderTreeWalk(node.getLeft());
        }
        System.out.println(node.getValue());
        if(node.getRight() != null) {
            inorderTreeWalk(node.getRight());
        }
    }

    //finds successor, returns null if there is none
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

    //calls printHelper to print tree
    public void printTree(Node node) {
        printHelper(node, "", true);
    }

    private void printHelper(Node node, String indent, boolean isRight) {
        if (node == null) {
            return;
        }
        // Process right child first
        printHelper(node.getRight(), indent + "    ", true);
        // Print current node
        System.out.print(indent);
        if (indent.isEmpty()) {
            // Root node
            System.out.println(node.getValue());
        } else {
            System.out.println(isRight ? " /-- " + node.getValue() : " \\-- " + node.getValue());
        }
        // Process left child
        printHelper(node.getLeft(), indent + "    ", false);
    }
}
