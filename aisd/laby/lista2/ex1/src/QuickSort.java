import java.util.Arrays;
import java.util.Scanner;

public class QuickSort {
    int comparisonCounter = 0;
    int switchesCounter = 0;
    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        System.out.println("QUICK SORT");

        for(int i = 1; i <= 3; i++) {
            quickSort.generateSolution(i, num);
        }
    }

    public void quicksort(int[] arr, int low, int high) {
        if(low < high) {
            if(arr.length < 40) {
                printArray(arr);
            }
            int pivot_idx = partition(arr, low, high);
            quicksort(arr, low, pivot_idx - 1);
            quicksort(arr, pivot_idx + 1, high);
        }

    }

    public int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            increaseComparisonCounter();
            if(compare(arr[j], pivot)) {
                i++;
                swap(arr, j, i);
                increaseSwitchesCounter();
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    public static void printArray(int[] arr) {
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public void generateSolution(int num, int size) {
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
        if(size < 40) {
            int [] copy = Arrays.copyOf(t, size);
            printArray(t);
            System.out.println();
            quicksort(t, 0, size - 1);
            System.out.println();
            System.out.print("Wyjściowa tablica:   ");
            printArray(copy);
            System.out.print("Posortowana tablica: ");
            printArray(t);
            System.out.println();
        } else {
            quicksort(t, 0,size - 1);
            System.out.println("Liczba porównań między kluczami: " + comparisonCounter);
            System.out.println("Liczba zamian kluczy: " + switchesCounter);
        }
        System.out.println("Czy jest posortowana? " + checkIfSorted(t));
        setComparisonCounterToZero();
        setSwitchesCounterToZero();

    }

    public void increaseComparisonCounter() {
        comparisonCounter++;
    }

    public void increaseSwitchesCounter() {
        switchesCounter++;
    }

    public void setComparisonCounterToZero() {
        comparisonCounter = 0;
    }

    public void setSwitchesCounterToZero() {
        switchesCounter = 0;
    }

    public static void swap(int [] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr [y] = temp;
    }

    public static boolean compare(int a, int b) {
        return a < b;
    }

    public static boolean checkIfSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }

    public int getComparisonCounter() {
        return comparisonCounter;
    }

    public int getSwitchesCounter() {
        return switchesCounter;
    }
}
