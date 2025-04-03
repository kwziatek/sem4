import java.util.Arrays;
import java.util.Scanner;

public class InsertionSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        System.out.println("INSERTION SORT");

        for(int i = 1; i <= 3; i++) {
            generateSolution(i, num);
        }
    }

    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && arr[j] < arr[j - 1]) {
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
            if(arr.length < 40 && arr[0] >= 0) {
                printArray(arr);
            }
        }
    }

    public static void printArray(int[] arr) {
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static void generateSolution(int num, int size) {
        NumbersGenerator ng = new NumbersGenerator();
        int [] t;
        if(num == 1) {
            System.out.println("random order:");
            t = ng.generateRandomArray(size);
        } else if(num == 2) {
            System.out.println("ascending order:");
            t = ng.generateAscendingArray(size);
        } else if(num == 3) {
            System.out.println("descending order:");
            t = ng.generateDescendingArray(size);
        } else {
            return;
        }

        int [] copy = Arrays.copyOf(t, size);
        printArray(t);
        System.out.println();
        insertionSort(t);
        System.out.println();
        System.out.print("Wyjściowa tablica:   ");
        printArray(copy);
        System.out.print("Posortowana tablica: ");
        printArray(t);
        System.out.println();
    }

}