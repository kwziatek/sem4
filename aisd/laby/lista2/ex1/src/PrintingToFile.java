import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PrintingToFile {
    public static void main(String[] args) {

        InsertionSort insertionSort = new InsertionSort();
        QuickSort quickSort = new QuickSort();
        HybridSort hybridSort = new HybridSort();
        for(int i = 10; i <= 50; i += 10) {
            insertionSort.generateSolution(1, i);
            quickSort.generateSolution(1, i);
            hybridSort.generateSolution(1, i);
        }

        for(int i = 1000; i <= 50000; i += 1000) {
            quickSort.generateSolution(1, i);
            hybridSort.generateSolution(1, i);
        }
        try{
            PrintWriter pw = new PrintWriter("dane.txt");
            pw.println(insertionSort.getArray() + "\n" + quickSort.getArray() + "\n" + hybridSort.getArray());
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
