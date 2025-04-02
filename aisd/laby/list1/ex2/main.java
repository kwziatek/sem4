import java.util.Random;

public class main {
        public static void main(String[] args) {
        int [] t = new int[10000];
        cyclicListsManager manager = new cyclicListsManager();
        manager.add(new cyclicList());
        manager.add(new cyclicList());
        for(int i = 15; i <= 25; i++) {
            manager.insert(manager.get(0), i);
            manager.insert(manager.get(1), i + 37);
        }
 
        System.out.println(manager.getSize());
        for(int i = 0; i < manager.getSize(); i++) {
            System.out.print("Printing list " + i + ":");
            manager.get(i).printList();
        }
        manager.merge(manager.get(0), manager.get(1));
        System.out.println(manager.getSize());
        for(int i = 0; i < manager.getSize(); i++) {
            System.out.print("Printing list " + i + ":");
            manager.get(i).printList();
        }

        manager.add(new cyclicList());
        fillArray(t, 10000);
        fillCyclicList(t, manager.get(1));
        double value = 0;
        for(int i = 1; i <= 1000; i++) {
            int k = drawNumber(10000);
            value += findNumber(manager.get(1), t[k]);
        }
        value /= 1000;
        System.out.println("for numbers on the list: " + value);

        value = 0;
        for(int i = 1; i <= 1000; i++) {
            int k = drawNumber(100000);
            value += findNumber(manager.get(1), k); 
        }
        value /= 1000;
        System.out.println("for numbers from 0 to 100000: " + value);
    }
   
    private static void fillArray(int [] t, int n) {
        int k = 100000;
        for(int i = 0; i < n; i++) {
            Random rand = new Random();
            t[i] = rand.nextInt(k);
        }
    }

    private static int drawNumber(int k) {
        Random rand = new Random();
        int number = rand.nextInt(k);
        return number;
    }

    private static int findNumber(cyclicList list, int number) {
        int counter = 1;
        while(list.get() != number) {
            list.move();
            counter++;
            if(counter > list.getSize()) {
                return list.getSize();
            }
        }
        return counter;
    }

    private static void fillCyclicList(int [] t, cyclicList list) {
        for(int i = 0; i < t.length; i++) {
            list.add(t[i]);
        }
    }
}
