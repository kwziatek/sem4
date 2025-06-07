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

    private class Edge implements Comparable<Edge> {
        int from, to;
        double weight;
        private Edge(int from, int to, double value) {
            this.from = from;
            this.to = to;
            this.weight = value;
        }

        @Override
        public int compareTo(Edge o) {
            double helper = this.weight - o.weight;
            if(helper > 0) {
                return 1;
            } else if(helper == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }

//    public List<Integer> primMST() {
//        List<Integer> result = new ArrayList<>();
//        for(int i = 0; i < size; i++) {
//            result.add(-1);
//        }
//        PriorityQueue<Edge> pq = new PriorityQueue<>();
//        int root = 0;
//        int currentNode = root;
//        int counter = 1;
//        while(counter < size) {
//            for(int i = 0; i < size; i ++) {
//                if(edges[currentNode][i] != 0 && result.get(i) == -1 && i != root) {
//                    pq.add(new Edge(currentNode, i, edges[currentNode][i]));
//                }
//            }
//
//            while(true) {
//                Edge edge = pq.poll();
//                if(edge == null) {
//                    System.out.println("queue is empty");
//                    return result;
//                }
//                int idx = edge.to;
//                if(result.get(idx) == -1 && idx != root) {
//                    result.set(idx, edge.from);
//                    currentNode = idx;
//                    counter ++;
//                    break;
//                }
//            }
//        }
//        return result;
//    }

    public List<Integer> primMST() {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            result.add(-1);
        }
        IndexedPQ<Edge> pq = new IndexedPQ<>(size + 1);
        int root = 0;
        int currentNode = root;
        int counter = 1;
        while(counter < size) {
            for(int i = 0; i < size; i ++) {
                double currentWeight = edges[currentNode][i];
                if(currentWeight != 0 && result.get(i) == -1 && i != root) {
                    Edge newEdge = new Edge(currentNode, i, currentWeight);
                    if(!pq.contains(i)) {
                        pq.insert(i, newEdge);
                    } else {
                        Edge edge = pq.getKey(i);
                        if(currentWeight < edge.weight) {
                            pq.update(i, newEdge);
                        }
                    }
                }
            }

            while(true) {
                int node = pq.peak();
                Edge edge = pq.getKey(node);
                pq.remove(node);
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

    public List<List<Integer>> kruskalMST() {
        List<Edge> listOfEdges = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        List<List<Integer>> parts = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if (i < j) {
                    listOfEdges.add(new Edge(i, j, edges[i][j]));
                }
            }
        }
//        for(Edge edge: listOfEdges) {
//            System.out.println(edge.from + " " + edge.to);
//        }
        Collections.sort(listOfEdges);
        int ptr = 0;
        for(int i = 1; i < size; i++) {
            while(true) {
                Edge edge = listOfEdges.get(ptr);
                ptr++;
                if(!doesEdgeCreateCycle(parts, edge)) {
//                    for(List<Integer> array : parts) {
//                        for(Integer node: array) {
//                            System.out.println(node);
//                        }
//                        System.out.println();
//                    }
                    List<Integer> toAdd = new ArrayList<>();
                    toAdd.add(edge.from);
                    toAdd.add(edge.to);
                    result.add(toAdd);
                    break;
                }
            }
        }

        return result;
    }

    private boolean doesEdgeCreateCycle(List<List<Integer>> parts, Edge edge) {
        int from = -1;
        int to = -1;
        for (int i = 0; i < parts.size(); i++) {
            for (int node : parts.get(i)) {
                if (node == edge.from) {
                    from = i;
                } else if (node == edge.to) {
                    to = i;
                }
            }
        }
        if (from == -1 && to == -1) {
            List<Integer> toAdd = new ArrayList<>();
            toAdd.add(edge.from);
            toAdd.add(edge.to);
            parts.add(toAdd);
            return false;
        } else if (from == -1) {
            parts.get(to).add(edge.from);
            return false;
        }
        else if (to == -1) {
            parts.get(from).add(edge.to);
            return false;
        } else {
            if(to != from) {
                parts.get(to).addAll(parts.get(from));
                return false;
            }
            else {
                return true;
            }
        }


    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        CompleteGraph completeGraph = new CompleteGraph(n);
        completeGraph.generateCompleteGraph();
        completeGraph.printMatrixOfEdges();
        System.out.println(completeGraph.primMST());
        System.out.println(completeGraph.kruskalMST());
    }

}
