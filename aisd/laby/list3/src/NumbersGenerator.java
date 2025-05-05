import java.security.SecureRandom;
import java.util.Arrays;

public class NumbersGenerator {

    SecureRandom secureRandom = new SecureRandom();

    public int [] generateRandomArray(int size) {
        int [] array = new int[size + 1];
        for (int i = 1; i < size; i++) {
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

    public int randomNumber(int bound) {
        return secureRandom.nextInt(bound) + 1;
    }

    public int randomNumberBounded(int lowerBound, int upperBound) {
        try{
            return secureRandom.nextInt(upperBound - lowerBound + 1) + lowerBound;
        } catch (IllegalArgumentException e) {
            System.out.println("illegal argument");
        }
        return 1;
    }
}