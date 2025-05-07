public class BinSearch {
    int[] arr;
    int v;
    Comparison comparison;
    public static void main(String[] args) {

        NumbersGenerator numbersGenerator = new NumbersGenerator();
        int n = 20;
        int[] arr = numbersGenerator.generateAscendingArray(n);

        for(int i = 1; i <= 5; i++) {
            int rand = numbersGenerator.randomNumber(2 * n);
            Comparison comparison = new Comparison();
            BinSearch binSearch = new BinSearch(arr, rand, comparison);
            int res = binSearch.binSearch(1, n);
            binSearch.printArray(arr);
            System.out.println(rand);
            System.out.println(res + " " + comparison.compares);
            System.out.println();
        }

//generowanie danych do wykresu
//        for(int i = 1000; i <= 100000; i += 1000) {
//            for(int j = 1; j <= 10; j++) {
//                int[] arr = numbersGenerator.generateAscendingArray(i);
//                int rand = numbersGenerator.randomNumber(i);
//                rand = arr[rand];
//                Comparison comparison = new Comparison();
//                BinSearch binSearch = new BinSearch(arr, rand, comparison);
//                binSearch.binSearch(1, i);
//                long comp1 = comparison.compares;
//
//                comparison.compares = 0;
//                binSearch.v = arr[1];
//                binSearch.binSearch(1, i);
//                long comp2 = comparison.compares;
//
//                comparison.compares = 0;
//                binSearch.v = arr[(i + 1) / 2];
//                binSearch.binSearch(1, i);
//                long comp3 = comparison.compares;
//
//                comparison.compares = 0;
//                binSearch.v = arr[i];
//                binSearch.binSearch(1, i);
//                long comp4 = comparison.compares;
//
//                comparison.compares = 0;
//                binSearch.v = 3 * i;
//                binSearch.binSearch(1, i);
//                long comp5 = comparison.compares;
//
//                System.out.println(i + ": " + comp2 + " " + comp3 + " " + comp4 + " " + comp5 + " " + comp1);
//            }
//        }
    }

    public BinSearch(int[] arr, int v, Comparison comparison) {
        this.arr = arr;
        this.v = v;
        this.comparison = comparison;
    }

    public int binSearch(int low, int high) {
        comparison.compares++;
        if(low + 1 >= high) {
            comparison.compares++;
            if(arr[low] == v || arr[high] == v) {
                return 1;
            }
            return 0;
        }
        int mid = (low + high) / 2;
        comparison.compares++;
        if(arr[mid] == v) {
            return 1;
        } else if(arr[mid] < v) {
            return binSearch(mid, high);
        } else {
            return binSearch(low, mid);
        }
    }

    public void printArray(int[] array) {
        for(int i = 1; i < arr.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

}
