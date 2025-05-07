import java.util.*;

public class SelectTesting {
    Comparison comparison;
    Swap swap;
    int k;
    int[] arr;
    int smallArrSize;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NumbersGenerator numbersGenerator = new NumbersGenerator();
//        int n = scanner.nextInt();
//        int k = scanner.nextInt();
//
//        int[] arr = numbersGenerator.generateRandomArray(n);
//        int[] initialArray = Arrays.copyOf(arr, arr.length);
//
//        SelectTesting select = new SelectTesting(k, arr);
//        int res = select.select(arr, 1, n, k);
//        select.printResults();
//
//        select.printArray(arr);
//        select.printArray(initialArray);
//        System.out.println(res);
//        Arrays.sort(arr);
//        select.printArray(arr);


        //        generating Data for charts
        int m = 10;
        for(int i = 100; i <= 50000; i += 100) {
            for(int j = 1; j <= m; j++) {
                int n = i;
                int k = numbersGenerator.randomNumber(n);
                int[] array = numbersGenerator.generateRandomArray(n);
                SelectTesting randomSelect = new SelectTesting(k, array, 9);
                randomSelect.select(array, 1, array.length - 1, k);
                randomSelect.printDataForCharts(n);
            }
        }

    }

    public SelectTesting(int k, int[] arr, int size) {
        swap = new Swap();
        comparison = new Comparison();
        this.k = k;
        this.arr = arr;
        smallArrSize = size;
    }

    public int select(int[] arr, int low, int high, int k) {
//        System.out.println("low " + low + " high " + high + " statystyka " + k);
//        printArray(arr);
        comparison.compares++;
        if(low == high) {
            return arr[low];
        }
        int[] m = findingMedians(arr, low, high);
//        System.out.println("mediany :");
//        printArray(m);
        int x = select(m, 1, ceilDiv(high - low + 1,smallArrSize), ceilDiv(high - low + 1, smallArrSize)/2);
        int r = partition(arr, low, high, x);
//        System.out.println("pivot " + x +  " pozycja " + r);
        int count = r - low + 1;
        comparison.compares++;
        if(count == k) {
            return arr[r];
        } else if(k < count) {
            return select(arr, low, r - 1, k);
        } else {
            return select(arr, r + 1, high, k - count);
        }
    }

    public int partition(int[] arr, int low, int high, int pivot) {
        int i = low - 1;
        int pivot_idx = -1;
        for (int j = low; j <= high; j++) {
            if(comparison.compare(arr[j], pivot)) {
                i++;
                swap.swap(arr, j, i);
            }
            if(arr[j] == pivot) {
                pivot_idx = j;
            }
        }
        swap.swap(arr, pivot_idx, i + 1);
        return i + 1;
    }

    public int[] findingMedians(int[] arr, int low, int high) {
        List<Integer> listOfMedians = new ArrayList<Integer>();
        for(int i = low; i <= high; i += smallArrSize) {
            List<Integer> smallList = new ArrayList<Integer>();
            for(int j = i; j < i + smallArrSize && j <= high; j++) {
                smallList.add(arr[j]);
            }
            smallList.sort(null);
            listOfMedians.add(smallList.get((smallList.size() - 1)/2));
        }
        int[] newArr = new int[listOfMedians.size() + 1];
        for(int i = 0; i < listOfMedians.size(); i++) {
            newArr[i + 1] = listOfMedians.get(i);
        }
        return newArr;
    }

    public int ceilDiv(int a, int b) {
        if(a % b == 0) {
            return a / b;
        }
        return a / b + 1;
    }

    public void printResults() {
        System.out.println("liczba porównań: " + comparison.compares);
        System.out.println("liczba przestawień: " + swap.swaps);
    }

    public void printArray(int[] array) {
        for(int i = 1; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public void printDataForCharts(int n) {
        System.out.println(n + ": " + comparison.compares + " " + swap.swaps);
    }
}
