public class Ex1 {
    public static void main(String[] args) {

        BST bst = new BST(new Node(-1));
        int n = 25;
        insertSortedPermutation(n, bst);

        System.out.println(bst.height(bst.root));

        deletePermutation(n, bst);

        insertPermutation(n, bst);

        deletePermutation(n, bst);
    }

    public static void deletePermutation(int n, BST bst) {
        int[] arr = RandomPermutation.generateRandomPermutation(n);
        for(int i = 1; i <= n; i++) {
            System.out.println("delete " + arr[i - 1]);
            bst.delete(arr[i - 1]);
            bst.printTree(bst.root);
            System.out.println();
        }
    }

    public static void insertPermutation(int n, BST bst) {
        int[] arr = RandomPermutation.generateRandomPermutation(n);
        bst.root = new Node(arr[0]);
        System.out.println("insert " + arr[0]);
        bst.printTree(bst.root);
        for(int i = 2; i <= n; i++) {
            System.out.println("insert " + arr[i - 1]);
            bst.insert(bst.root, arr[i - 1]);
            bst.printTree(bst.root);
            System.out.println();
        }
    }

    public static void insertSortedPermutation(int n, BST bst) {
        bst.root = new Node(1);

        for(int i = 2; i <= n; i++) {
            System.out.println("insert " + i);
            bst.insert(bst.root, i);
            bst.printTree(bst.root);
            System.out.println();
        }
    }
}