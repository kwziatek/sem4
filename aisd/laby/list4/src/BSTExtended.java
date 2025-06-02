import java.util.LinkedList;
import java.util.Queue;

public class BSTExtended {
    Node root;
    private long comparisonNumber;
    private long readingNumber;

    public BSTExtended(Node root) {
        this.root = root;
    }

    //inserts recursively into BST, should be called with root and value that new node will take, creates node with that value
    public void insert(Node node, int k) {
        Node current = node;
        Node parent = null;

        // Traverse to find the correct position
        while (current != null) {
            parent = current;
            readingNumber++;
            if (k <= current.getValue()) {
                comparisonNumber++;
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        // Create new node and attach it to the parent
        Node newNode = new Node(k);
        newNode.setParent(parent);
        readingNumber++;
        comparisonNumber++;
        if (k <= parent.getValue()) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
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
        readingNumber += 2;
        if(node.getLeft() == null && node.getRight() == null) {
            if(node.getParent() == null) {
                root = null;
                return;
            }

            Node parent = node.getParent();
            readingNumber++;
            comparisonNumber++;
            readingNumber += 2;
            if(parent.getLeft() == node) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        } else if(node.getLeft() != null && node.getRight() != null) {
            readingNumber += 2;
            Node successor = findSuccessor(node);
            node.setValue(successor.getValue());
            deleteNode(successor);
        } else {
            if(node == root) {
                readingNumber += 3;
                if(root.getLeft() != null) {
                    root = root.getLeft();
                    root.setParent(null);
                } else {
                    root = root.getRight();
                    root.setParent(null);
                }
            } else if(node.getLeft() != null) {
                readingNumber += 3;
                if(node.getParent().getLeft() == node) {
                    node.getParent().setLeft(node.getLeft());
                } else {
                    node.getParent().setRight(node.getLeft());
                }
                readingNumber++;
                node.getLeft().setParent(node.getParent());
            } else {
                readingNumber += 3;
                if(node.getParent().getLeft() == node) {
                    node.getParent().setLeft(node.getRight());
                } else {
                    node.getParent().setRight(node.getRight());
                }
                readingNumber++;
                node.getRight().setParent(node.getParent());
            }
        }
    }

    public int height(Node node) {
        if (node == null) {
            return -1;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        int height = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Number of nodes at current level
            height++; // Increment height for each level

            // Process all nodes at the current level
            for (int i = 0; i < levelSize; i++) {
                Node current = queue.poll();

                // Add left child to queue if it exists
                readingNumber++;
                if (current.getLeft() != null) {
                    queue.offer(current.getLeft());
                }
                // Add right child to queue if it exists\
                readingNumber++;
                if (current.getRight() != null) {
                    queue.offer(current.getRight());
                }
            }
        }

        return height;
    }

    //searches recursively if there is a node with given value, should be started from root
    public Node searchNode(Node node, int value) {
        Node current = node;

        while (current != null) {
            comparisonNumber++;
            if (current.getValue() == value) {
                return current;
            }
            comparisonNumber++;
            readingNumber++;
            if (value < current.getValue()) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
            comparisonNumber++; // For the null check in the while condition
        }

        return null;
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
        readingNumber++;
        if(node.getRight() != null) {
            readingNumber++;
            node = node.getRight();
            while(node.getLeft() != null) {
                readingNumber += 2;
                node = node.getLeft();
            }
            return node;
        } else {
            Node y = node.getParent();
            readingNumber++;
            while(y != null && y.getRight() == node) {
                readingNumber += 3;
                comparisonNumber++;
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

    public void setComparisonNumber(int value) {
        comparisonNumber = value;
    }

    public long getComparisonNumber() {
        return comparisonNumber;
    }

    public void setReadingNumber(long value) {
        readingNumber = value;
    }

    public long getReadingNumber() {
        return readingNumber;
    }
}
