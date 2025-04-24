public class DualPivotQuickSort {
    int[] arr;
    public DualPivotQuickSort(int[] arr) {
        this.arr = arr;
    }
    public record Pivot(int left, int right) { }

    public static void main(String[] Args) {
        int[] t = {5, 7, 1, 4, 10, 41, 60};
        DualPivotQuickSort dualPivotQuickSort = new DualPivotQuickSort(t);
        dualPivotQuickSort.dualPivotQuickSort(0, t.length - 1);
        QuickSort.printArray(t);
    }

    public void dualPivotQuickSort(int low, int high) {
        if(low >= high || high >= arr.length || low < 0) {
            return;
        }
        Pivot pivot = partition(low, high);
        dualPivotQuickSort(low, pivot.left() - 1);
        dualPivotQuickSort(pivot.left() + 1, pivot.right() - 1);
        dualPivotQuickSort(pivot.right() + 1, high);
    }

    public Pivot partition(int low, int high) {
        if(arr[low] > arr[high]) {
            QuickSort.swap(arr, high, low);
        }

        int leftPivotIndex = low + 1;
        int rightPivotIndex = high - 1;
        int iterator = low + 1;
        while(iterator <= rightPivotIndex) {
            if(arr[iterator] < arr[low]) {
                QuickSort.swap(arr, iterator++, leftPivotIndex++);
            } else if(arr[iterator] > arr[high]) {
                QuickSort.swap(arr, iterator, rightPivotIndex--);
            } else {
                iterator++;
            }
        }
        QuickSort.swap(arr, low, --leftPivotIndex);
        QuickSort.swap(arr, high, ++rightPivotIndex);
        return new Pivot(leftPivotIndex, rightPivotIndex);
    }

}
