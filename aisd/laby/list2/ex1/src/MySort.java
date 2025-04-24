
import java.util.Arrays;

public class MySort {
    public static void main(String[] args) {
        int [] t = {1, 8, 5, 4, 10, 9, 1};
        MySort mySort = new MySort();
        t = mySort.mySort(t);
        QuickSort.printArray(t);
    }

    public int[] mySort(int[] arr) {
        if(arr.length <= 1) {
            return arr;
        }
        int i = 1;
        while(arr[i - 1] <= arr[i]) {
            i++;
            if(i == arr.length) {
                return arr;
            }
        }
        int [] leftPart = Arrays.copyOfRange(arr, 0, i);
        int [] rightPart = Arrays.copyOfRange(arr, i, arr.length);
//        QuickSort.printArray(leftPart);
//        QuickSort.printArray(rightPart);
        arr = MergeSort.merge(leftPart, mySort(rightPart));

        return arr;
    }
}
