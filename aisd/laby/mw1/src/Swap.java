public class Swap {
    long swaps;
    public Swap() {
        swaps = 0;
    }
    public void swap(int [] arr, int x, int y) {
        swaps++;
        int temp = arr[x];
        arr[x] = arr[y];
        arr [y] = temp;
    }
}
