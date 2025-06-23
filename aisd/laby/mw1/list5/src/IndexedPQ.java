import java.util.Arrays;

public class IndexedPQ<T extends Comparable<T>> {

    private int maxSize;
    private int size;
//    private int[] heap;
    private int[] indexToHeap;  //maps index to heap
    private int[] heapToIndex;  //maps heap to index
    private T[] keys;   //stores prioryties of keys

    @SuppressWarnings("unchecked")
    public IndexedPQ(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max size must be positive");
        }
        this.maxSize = maxSize;
        this.size = 0;
//        heap = new int[maxSize];
        indexToHeap = new int[maxSize];
        heapToIndex = new int[maxSize];
        keys = (T[]) new Comparable[maxSize];
        Arrays.fill(indexToHeap, -1);
    }

    public void insert(int index, T key) {
        if(index < 0 || index >= maxSize) {
            throw new IllegalArgumentException("Invalid index");
        }
        size++;
        indexToHeap[index] = size - 1;
        heapToIndex[size - 1] = index;
//        System.out.println("insert" + (size - 1) + " " + index);
        keys[index] = key;
        swim(size - 1);
    }

    private void swim(int idx) {
        if(idx == 0) {
            return;
        }
        int p = (idx - 1) / 2;
        if(less(idx, p)) {
            swap(idx, p);
            swim(p);
        }
    }

    private void sink(int idx) {
        while(true) {
            int left = 2 * idx + 1;
            int right = 2 * idx + 2;
            int smallest = left;

            if (right < size && less(right, left)) {
                smallest = right;
            }
            if (left >= size || less(idx, smallest)) {
                break;
            }

            swap(smallest, idx);
            idx = smallest;
        }

    }

    private boolean less(int i, int j) {
        return keys[heapToIndex[i]].compareTo(keys[heapToIndex[j]]) < 0;
    }

    private void swap(int i, int j) {
        indexToHeap[heapToIndex[i]] = j;
        indexToHeap[heapToIndex[j]] = i;
        int tmp = heapToIndex[i];
        heapToIndex[i] = heapToIndex[j];
        heapToIndex[j] = tmp;
    }

    public void update(int index, T key) {
        keys[index] = key;
        int heapPos = indexToHeap[index];
        swim(heapPos);
        sink(heapPos);
    }

    public void remove(int index) {
        if(!contains(index)) {
            return;
        }
        System.out.println("remove: " + index + " " + indexToHeap[index] + " " + indexToHeap[size - 1]);
        int heapPos = indexToHeap[index];
        swap(heapPos, size - 1);
        keys[index] = null;
        indexToHeap[index] = -1;
        heapToIndex[size - 1] = -1;
        size--;
        if(heapPos < size) {
            swim(heapPos);
            sink(heapPos);
        }


    }
    public boolean contains(int index) {
        if(index < 0 || index >= maxSize) {
            return false;
        }
        return indexToHeap[index] != -1;
    }

    //return -1 if IPQ empty
    public int peak() {
        if(isEmpty()) {
            return -1;
        } else {
            return heapToIndex[0];
        }
    }


    public T getKey(int index) {
        if(!contains(index)) {
            return null;
        } else {
            return keys[index];
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printIPQ() {
        for(int i = 0; i < size; i++) {
            System.out.println(i + " " + heapToIndex[i] + " " + keys[heapToIndex[i]]);
        }
    }

    public static void main(String[] args) {
        IndexedPQ<Integer> indexedPQ = new IndexedPQ<>(10);
        for(int i = 0; i < 10; i++) {
            indexedPQ.insert(i, 10 - i);
            indexedPQ.printIPQ();
            System.out.println();
        }

        indexedPQ.update(7, 7);
        indexedPQ.update(8,8);
        indexedPQ.printIPQ();

        indexedPQ.remove(7);
        indexedPQ.printIPQ();
        indexedPQ.remove(8);

        indexedPQ.printIPQ();

    }
}
