import java.lang.reflect.Array;
import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        MergeSort mergeSort = new MergeSort();
        int [] t = {6, 5, 4, 5, 1, 2, 10, 4, 6};
        t = mergeSort.mergeSort(t);
        QuickSort.printArray(t);
    }
    public int[] mergeSort(int[] arr) {
        if(arr.length <= 1) {
            return arr;
        }
        int mid = arr.length / 2;
        int [] leftHalf = mergeSort(Arrays.copyOfRange(arr, 0, mid));
        int [] rightHalf = mergeSort(Arrays.copyOfRange(arr, mid, arr.length));

        return merge(leftHalf, rightHalf);
    }

    public static int[] merge(int[] leftHalf, int[] rightHalf) {
        int[] merged = new int[leftHalf.length + rightHalf.length];
        int i = 0, j = 0, k = 0;
        while(i < leftHalf.length && j < rightHalf.length) {
            if(leftHalf[i] <= rightHalf[j]) {
                merged[k] = leftHalf[i];
                i++;
            } else {
                merged[k] = rightHalf[j];
                j++;
            }
            k++;
        }

        while(i < leftHalf.length) {
            merged[k++] = leftHalf[i++];
        }

        while(j < rightHalf.length) {
            merged[k++] = rightHalf[j++];
        }
        return merged;
    }
}
