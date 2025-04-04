import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InsertionSort {
    int comparisonCounter = 0;
    int switchesCounter = 0;
    ArrayList<Integer> array = new ArrayList<>();
    public static void main(String[] args) {
        InsertionSort insertionSort = new InsertionSort();
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        System.out.println("INSERTION SORT");

        for(int i = 1; i <= 3; i++) {
            insertionSort.generateSolution(i, num);
        }
    }

    public void insertionSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            int j = i;
            increaseComparisonCounter();
            while (j > 0 && compare(arr[j], arr[j - 1])) {
                swap(arr, j);
                increaseSwitchesCounter();
                j--;
                if(j != 0) {
                    increaseComparisonCounter();
                }
            }
            if(arr.length < 40) {
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
            insertionSort(t);
            System.out.println();
            System.out.print("Wyjściowa tablica:   ");
            printArray(copy);
            System.out.print("Posortowana tablica: ");
            printArray(t);
            System.out.println();
        } else {
            insertionSort(t);
        }


        System.out.println("Liczba porównań między kluczami: " + comparisonCounter);
        System.out.println("Liczba zamian kluczy: " + switchesCounter);

        if(num == 1) {
            array.add(comparisonCounter);
            array.add(switchesCounter);
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

    public static void swap(int [] arr, int x) {
        int temp = arr[x];
        arr[x] = arr[x - 1];
        arr [x - 1] = temp;
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

    public ArrayList<Integer> getArray() {
        return array;
    }


}