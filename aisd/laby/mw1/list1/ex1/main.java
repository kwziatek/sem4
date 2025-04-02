public class main{
    public static void main(String[] args) {
        lifo stack = new lifo();
        System.out.println("Input:");
        for(int i = 1; i <= 50; i++) {
            stack.push(i);
            System.out.println(i);
        }
        System.out.println("Stack:");
        for(int i = 1; i <= 50; i++) {
            System.out.println(stack.pop());
        }

        fifo queue = new fifo();
        for(int i = 1; i <= 50; i++) {
            queue.push(i);
        }
        System.out.println("Queue:");
        for(int i = 1; i <= 50; i++) {
            System.out.println(queue.pop());
        }
    }
}