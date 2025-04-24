import java.util.ArrayList;
import java.util.Scanner;

public class HybridSort {
    ArrayList<Integer> array = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("HYBRID SORT");
        HybridSort hybridSort = new HybridSort();
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        for(int i = 1; i <= 3; i++) {
            hybridSort.generateSolution(i, size);
        }

    }
    public void generateSolution(int num, int size) {
        if(size <= 10) {
            InsertionSort insertionSort = new InsertionSort();
            insertionSort.generateSolution(num, size);
            array.add(insertionSort.getArray().get(insertionSort.getArray().size() - 2));
            array.add(insertionSort.getArray().getLast());
        } else {
            QuickSort qs = new QuickSort();
            qs.generateSolution(num, size);
            array.add(qs.getArray().get(qs.getArray().size() - 2));
            array.add(qs.getArray().getLast());
        }
    }

    public ArrayList<Integer> getArray() {
        return array;
    }

}
