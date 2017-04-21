import java.util.Random;

/**
 *
 */
public class Sortierung {
    public static void main(String... args) {

    }

    public static void insertionSort(int[] array) {

    }


    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++)
            if (array[i] > array[i + 1])
                return false;
        return true;
    }

    private static Random random = new Random();

    public static int[] fillRandom(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n < 0");
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = random.nextInt();
        return arr;
    }

    public static int[] fillIncreasing(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n < 0");
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = i;
        return arr;
    }

    public static int[] fillDecreasing(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n < 0");
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = n - i;
        return arr;
    }

    public static void mergeSort(int[] array,int[] tempArray,int left,int right){

    }

    public static void mergeSort(int[] array) {
        int[] tmpArray = new int[array.length];
        mergeSort(array, tmpArray, 0, array.length - 1);
        assert isSorted(array);
    }
}
