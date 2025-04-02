import java.util.*;

public class cyclicListsManager {
    private List<cyclicList> lists;
    private int size;

    public cyclicListsManager() {
        lists = new ArrayList<cyclicList>();
    }
    
    public cyclicList get(int index) {
        return lists.get(index);
    }

    public int getIndex(cyclicList list) {
        return lists.indexOf(list);
    }

    public void add(cyclicList list) {
        size++;
        lists.add(list);
    }

    public void remove(int index) {
        size--;
        lists.remove(index);
    }

    public void insert(cyclicList list, int value) {
        list.add(value);
    }

    public void merge(cyclicList list1, cyclicList list2) {
        for (int i = 0; i < list2.getSize(); i++) {
            list1.add(list2.get());
            list2.move();
        }
        remove(getIndex(list2));
    }

    public int getSize() {
        return size;
    }
}