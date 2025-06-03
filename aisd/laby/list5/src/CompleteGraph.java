import java.util.*;

public class CompleteGraph {
    class Node {
        int value;
        Node(int v) {
            value = v;
        }
    }

//    class Edge {
//        Node u,v;
//        double weight;
//        Edge(Node u, Node v, double weight) {
//            this.u = u;
//            this.v = v;
//            this.weight = weight;
//        }
//    }

    Node[] nodes;
    double[][] edges;
    int size;

    public CompleteGraph(int n) {
        size = n;
        nodes = new Node[n];
        edges = new double[n][];
    }

    public void generateCompleteGraph() {
        for(int i = 0; i < size; i++) {
            nodes[i] = new Node(i);
        }

        for(int i = 0; i < size; i++) {
            edges[i] = new double[size];
            for(int j = 0; j < size; j++) {
                if(i != j) {
                    if(i < j) {
                        Random rand = new Random();
                        double weight = rand.nextDouble();
                        edges[i][j] = weight;
                    } else {
                        edges[i][j] = edges[j][i];
                    }
                }
            }
        }
    }

    public void printMatrixOfEdges() {
        for(double[] array: edges) {
            for(double edge: array) {
                System.out.println(edge);
            }
            System.out.println();
        }

    }

    private class Edge implements Comparable<Edge>{
        int from, to;
        double value;
        private Edge(int from, int to, double value) {
            this.from = from;
            this.to = to;
            this.value = value;
        }

        @Override
        public int compareTo(Edge o) {
            double helper = this.value - o.value;
            if(helper > 0) {
                return 1;
            } else if(helper == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    public List<Integer> primsMST() {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            result.add(-1);
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        int root = 0;
        int currentNode = root;
        int counter = 1;
        while(counter <= size) {
            for(int i = 0; i < size; i ++) {
                if(edges[currentNode][i] != 0 && result.get(i) == -1 && i != root) {
                    pq.add(new Edge(currentNode, i, edges[currentNode][i]));
                }
            }

            while(true) {
                Edge edge = pq.poll();
                if(edge == null) {
                    System.out.println("queue is empty");
                    return result;
                }
                int idx = edge.to;
                if(result.get(idx) == -1 && idx != root) {
                    result.set(idx, edge.from);
                    currentNode = idx;
                    counter ++;
                    break;
                }
            }
        }
        return result;
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        CompleteGraph completeGraph = new CompleteGraph(n);
        completeGraph.generateCompleteGraph();
        completeGraph.printMatrixOfEdges();
        System.out.println(completeGraph.primsMST());
    }

}
