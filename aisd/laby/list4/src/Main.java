public class Main {
    public static void main(String[] args) {
        BST bst = new BST(new Node(4));
        bst.insert(bst.root, 8);
        bst.insert(bst.root, 9);
        bst.insert(bst.root, 2);
        bst.print2DArray(bst.treeToMatrix(bst.root));
        bst.insert(bst.root, 1);
        bst.print2DArray(bst.treeToMatrix(bst.root));
        bst.insert(bst.root, 3);
        bst.print2DArray(bst.treeToMatrix(bst.root));
        bst.insert(bst.root, 7);
        bst.print2DArray(bst.treeToMatrix(bst.root));

        System.out.println(bst.findSuccessor(bst.searchNode(bst.root, 7)).getValue());
        System.out.println(bst.findSuccessor(bst.searchNode(bst.root, 8)).getValue());
        System.out.println(bst.findSuccessor(bst.searchNode(bst.root, 9)));
        System.out.println(bst.findSuccessor(bst.searchNode(bst.root, 1)).getValue());
        System.out.println(bst.findSuccessor(bst.searchNode(bst.root, 4)).getValue());


    }
}