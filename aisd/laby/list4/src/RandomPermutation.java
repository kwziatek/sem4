import java.util.Random;

public class RandomPermutation {
    public static int[] generateRandomPermutation(int n) {
        int[] permutation = new int[n];

        // Initialize array with values 1 to n
        for (int i = 0; i < n; i++) {
            permutation[i] = i + 1;
        }

        Random rnd = new Random();

        // Fisher-Yates shuffle algorithm
        for (int i = n - 1; i > 0; i--) {
            // Generate random index between 0 and i (inclusive)
            int j = rnd.nextInt(i + 1);

            // Swap elements at positions i and j
            int temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }

        return permutation;
    }

}