
public class SplayTree {
    private class Node {
        int value; // holds the key
        Node parent; // pointer to the parent
        Node left; // pointer to left child
        Node right; // pointer to right child

        public Node(int data) {
            this.value = data;
            this.parent = null;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public SplayTree() {
        root = null;
    }

    private void deleteNodeHelper(Node node, int key) {
        Node x = null;
        Node t = null;
        Node s = null;
        while (node != null){
            if (node.value == key) {
                x = node;
            }

            if (node.value <= key) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if (x == null) {
            System.out.println("Couldn't find key in the tree");
            return;
        }
        // split operation
        splay(x);
        if (x.right != null) {
            t = x.right;
            t.parent = null;
        } else {
            t = null;
        }
        s = x;
        s.right = null;
        x = null;

        // join operation
        if (s.left != null){ // remove x
            s.left.parent = null;
        }
        root = join(s.left, t);
        s = null;
    }

    // rotate left at node x
    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    // rotate right at node x
    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // Splaying operation. It moves x to the root of the tree
    private void splay(Node x) {
        while (x.parent != null) {
            if (x.parent.parent == null) {
                if (x == x.parent.left) {
                    // zig rotation
                    rightRotate(x.parent);
                } else {
                    // zag rotation
                    leftRotate(x.parent);
                }
            } else if (x == x.parent.left && x.parent == x.parent.parent.left) {
                // zig-zig rotation
                rightRotate(x.parent.parent);
                rightRotate(x.parent);
            } else if (x == x.parent.right && x.parent == x.parent.parent.right) {
                // zag-zag rotation
                leftRotate(x.parent.parent);
                leftRotate(x.parent);
            } else if (x == x.parent.right && x.parent == x.parent.parent.left) {
                // zig-zag rotation
                leftRotate(x.parent);
                rightRotate(x.parent);
            } else {
                // zag-zig rotation
                rightRotate(x.parent);
                leftRotate(x.parent);
            }
        }
    }

    // joins two trees s and t
    private Node join(Node s, Node t){
        if (s == null) {
            return t;
        }

        if (t == null) {
            return s;
        }
        Node x = maximum(s);
        splay(x);
        x.right = t;
        t.parent = x;
        return x;
    }

    // find the node with the minimum key
    public Node minimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // find the node with the maximum key
    public Node maximum(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // insert the key to the tree in its appropriate position
    public void insert(int key) {
        Node node = new Node(key);
        Node y = null;
        Node x = this.root;

        while (x != null) {
            y = x;
            if (node.value < x.value) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        // y is parent of x
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.value < y.value) {
            y.left = node;
        } else {
            y.right = node;
        }

        // splay node
        splay(node);
    }

    // delete the node from the tree
    void deleteNode(int data) {
        deleteNodeHelper(this.root, data);
    }

    // finds the height of the tree
    public int findHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(Node node) {
        if(node == null) {
            return 0;
        }
        int left = calculateHeight(node.left);
        int right = calculateHeight(node.right);
        return Math.max(left, right) + 1;
    }

    public void printTree() {
        printHelper(root, " ",true);
    }

    private void printHelper(Node node, String indent, boolean isRight) {
        if (node == null) {
            return;
        }
        // Process right child first
        printHelper(node.right, indent + "    ", true);
        // Print current node
        System.out.print(indent);
        if (indent.isEmpty()) {
            // Root node
            System.out.println(node.value);
        } else {
            System.out.println(isRight ? " /-- " + node.value : " \\-- " + node.value);
        }
        // Process left child
        printHelper(node.left, indent + "    ", false);
    }


    public static void main(String [] args) {
        SplayTree splayTree = new SplayTree();
        int n = 25;
        insertSortedPermutation(n, splayTree);

        System.out.println(splayTree.findHeight());

        deletePermutation(n, splayTree);

        insertPermutation(n, splayTree);

        deletePermutation(n, splayTree);
    }

    public static void deletePermutation(int n, SplayTree splayTree) {
        int[] arr = RandomPermutation.generateRandomPermutation(n);
        for(int i = 1; i <= n; i++) {
            System.out.println("delete " + arr[i - 1]);
            splayTree.deleteNode(arr[i - 1]);
            splayTree.printTree();
            System.out.println();
        }
    }

    public static void insertPermutation(int n, SplayTree splayTree) {
        int[] arr = RandomPermutation.generateRandomPermutation(n);
        for(int i = 1; i <= n; i++) {
            System.out.println("insert " + arr[i - 1]);
            splayTree.insert(arr[i - 1]);
            splayTree.printTree();
            System.out.println();
        }
    }

    public static void insertSortedPermutation(int n, SplayTree splayTree) {
        for(int i = 1; i <= n; i++) {
            System.out.println("insert " + i);
            splayTree.insert(i);
            splayTree.printTree();
            System.out.println();
        }
    }

}