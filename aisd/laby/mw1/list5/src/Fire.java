import java.util.*;

public class Fire {
    Node root;
    public Fire() {
        root = new Node(null, new ArrayList<>(), 0);
    }

     class Node {
        Node parent;
        int value;
        List<Node> children;
        public Node(Node parent, List<Node> children, int value) {
            this.children = children;
            this.parent = parent;
            this.value = value;
        }
        public Node(int value) {
            this.value = value;
            this.children = new ArrayList<>();
        }
    }

    public void createTree(List<List<Integer>> edges) {
        int counter = 0;
        List<Integer> list = edges.getFirst();
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        pq.add(list.getFirst());
        root = new Node(null, new ArrayList<>(), list.getFirst());
        addNode(root, null);

//        pq.add(list.get(1));
//        Node son = new Node(list.get(1));
//        addNode(son, root);
//        counter++;
        while(counter < edges.size()) {
            if(pq.isEmpty()) {
                System.out.println("queue empty");
                break;
            }
            int currentNode = pq.poll();
            for(List<Integer> edge: edges) {
                int nextNode = edge.get(1);
                if(edge.get(0) != currentNode && edge.get(1) != currentNode) {
                    continue;
                } else if(edge.get(0) != currentNode) {
                    nextNode = edge.getFirst();
                }
                Node node = find(root, currentNode);
                Node node1 = find(root, nextNode);
//                System.out.println("after find: " + node + " " + node1);
                if(node1 == null || (!node.children.contains(node1) && !node1.children.contains(node))) {
                    if(node1 == null) {
                        node1 = new Node(nextNode);
                        pq.add(nextNode);
                    }
                    addNode(node1, node);
                    counter++;
                }
            }
        }
    }

    private void addNode(Node node, Node parent) {
        if(parent != null) {
            parent.children.add(node);
        }
        node.parent = parent;
    }

    private Node find(Node node, int value) {
        if(node.value == value) {
            return node;
        }
        for(Node newNode: node.children) {
//            System.out.println(node.value + " " + newNode.value);
            Node nextNode = find(newNode, value);
            if(nextNode != null) {
                return nextNode;
            }
        }
        return null;
    }

    public void printTree() {
        helperPrint(root);
    }

    private void helperPrint(Node node) {
        if(node.parent == null) {
            System.out.println("node: " + node.value + " parent: null");
        } else{
            System.out.println("node: " + node.value + " parent: " + node.parent.value);
        }
        for(Node son: node.children) {
            helperPrint(son);
        }
    }

    public List<List<Integer>> solveFire(int n) {
        List<List<Integer>> result = new ArrayList<>(n);
        increaseSize(result, n);
        int[] dp = new int[n];
        solve(root, result, dp);
        return result;
    }

    private void solve(Node node, List<List<Integer>> result, int[] dp) {
        List<Integer> singleList = new ArrayList<>();
//        System.out.println(result.size());
        if(node.children.isEmpty()) {
            result.set(node.value, singleList);
            dp[node.value] = 0;
            return;
        }
        for(Node child: node.children) {
            solve(child, result, dp);
        }
        List<Pair> map = new ArrayList<>();
        for(Node child: node.children) {
            map.add(new Pair(dp[child.value], child.value));
        }
        Collections.sort(map);


        for(int i = 0; i < map.size(); i++) {
            singleList.add(map.get(i).second);
            dp[node.value] += i + dp[map.get(i).second] + 1;
        }
//        System.out.println(map);
//        System.out.println("node " + node.value + " " + dp[node.value]);
        result.set(node.value, singleList);
    }

    private void increaseSize(List<List<Integer>> result, int n) {
        for(int i = 0; i < n; i++) {
            result.add(null);
        }
    }

    class Pair implements Comparable<Pair>{
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public String toString() {
            return first + " " + second;
        }

        @Override
        public int compareTo(Pair o) {
            return o.first - this.first;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        CompleteGraph completeGraph = new CompleteGraph(n);
        completeGraph.generateCompleteGraph();
        completeGraph.printMatrixOfEdges();
        List<List<Integer>> edges = completeGraph.kruskalMST();
        System.out.println(edges);
        Fire fire = new Fire();
        fire.createTree(edges);
        fire.printTree();


        System.out.println(fire.solveFire(edges.size() + 1));
    }
}
