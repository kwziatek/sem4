import java.util.Arrays;
import java.util.Scanner;

public class RandomSelect {
    Comparison comparison;
    Swap swap;
    int k;
    int[] arr;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NumbersGenerator numbersGenerator = new NumbersGenerator();
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        int[] arr = numbersGenerator.generateRandomArray(n);
        int[] sortedArray = Arrays.copyOf(arr, arr.length);

        RandomSelect randomSelect = new RandomSelect(k, arr);
        int res = randomSelect.randomSelect(arr, 1, n, k);
        randomSelect.printResults();

        randomSelect.printArray(arr);
        randomSelect.printArray(sortedArray);
        System.out.println(res);
        Arrays.sort(arr);
        randomSelect.printArray(arr);

//        generating Data for charts
//        int m = 10;
//        for(int i = 100; i <= 50000; i += 100) {
//            for(int j = 1; j <= m; j++) {
//                int n = i;
//                int k = numbersGenerator.randomNumber(n);
//                int[] array = numbersGenerator.generateRandomArray(n);
//                RandomSelect randomSelect = new RandomSelect(k, array);
//                randomSelect.randomSelect(array, 1, array.length - 1, k);
//                randomSelect.printDataForCharts(n);
//            }
//        }

    }

    public RandomSelect(int k, int[] arr) {
        swap = new Swap();
        comparison = new Comparison();
        this.k = k;
        this.arr = arr;
    }

    public int randomSelect(int[] arr, int low, int high, int k) {
        comparison.compares++;
        if(low == high) {
            return arr[low];
        }
        int r = partition(arr, low, high);
        int count = r - low + 1;
        comparison.compares++;
        if(count == k) {
            return arr[r];
        } else if(k < count) {
            return randomSelect(arr, low, r - 1, k);
        } else {
            return randomSelect(arr, r + 1, high, k - count);
        }
    }

    public int partition(int[] arr, int low, int high) {
        NumbersGenerator numbersGenerator = new NumbersGenerator();
        int h = numbersGenerator.randomNumberBounded(low, high);
        swap.swap(arr, h, high);
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if(comparison.compare(arr[j], pivot)) {
                i++;
                swap.swap(arr, j, i);
            }
        }
        swap.swap(arr, i + 1, high);
        return i + 1;
    }

    public void printResults() {
        System.out.println("liczba porównań: " + comparison.compares);
        System.out.println("liczba przestawień: " + swap.swaps);
    }

    public void printArray(int[] array) {
        for(int i = 1; i < arr.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public void printDataForCharts(int n) {
        System.out.println(n + ": " + comparison.compares + " " + swap.swaps);
    }
}
