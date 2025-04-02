import java.util.ArrayList;
import java.util.List;

public class fifo {
    private List<Integer> queue;

    public fifo() {
        queue = new ArrayList<Integer>();
    }

    public void push(int value) {
        queue.add(value);
    }

    public int pop() {
        return queue.removeFirst();
    }   
}