import java.util.*;
public class cyclicList {
    private List<Integer> list;
    private int size;
    private int index;

    public cyclicList() {
        list = new ArrayList<Integer>();
        index = 0;
    }

    public int get(){
        return list.get(index);
    }

    public void add(int value) {
        size++;
        list.add(value);
    }

    public void move() {
        index++;
        if (index == list.size()) {
            index = 0;
        }
    }

    public int getSize() {
        return size;
    }

    public void printList() {
        for (Integer i: list) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
