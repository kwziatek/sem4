import java.util.List;
import java.util.ArrayList;

public class lifo{
    private List<Integer> stack;

    public lifo() {
        stack = new ArrayList<Integer>();
    }

    public void push(int value) {
        stack.add(value);
    }

    public int pop() {
        return stack.removeLast();
    }
}