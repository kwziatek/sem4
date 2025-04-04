import java.security.SecureRandom;
import java.util.Arrays;

public class NumbersGenerator {

    SecureRandom secureRandom = new SecureRandom();

    public int [] generateRandomArray(int size) {
        int [] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = secureRandom.nextInt(2 * size);
        }
        return array;
    }

    public int [] generateAscendingArray(int size) {
        int [] array = generateRandomArray(size);
        Arrays.sort(array);
        return array;
    }

    public int [] generateDescendingArray(int size) {
        int [] array = generateRandomArray(size);
        for (int i = 0; i < array.length; i++) {
            array[i] = -array[i];
        }
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = -array[i];
        }
        return array;
    }
}
