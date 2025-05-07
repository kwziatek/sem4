public class Comparison {
    long compares;
    public Comparison() {
        compares = 0;
    }

    public boolean compare(int a, int b) {
        compares++;
        return a < b;
    }
}
