import java.util.Scanner;

public class HybridSort {
    public static void main(String[] args) {
        System.out.println("HYBRID SORT");
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        if(size <= 10) {
            InsertionSort insertionSort = new InsertionSort();
            for(int i = 1; i <= 3; i++) {
                insertionSort.generateSolution(i, size);
            }
        } else {
            QuickSort qs = new QuickSort();
            for(int i = 1; i <= 3; i++) {
                qs.generateSolution(i, size);
            }
        }
    }
}
