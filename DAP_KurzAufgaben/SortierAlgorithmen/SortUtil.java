import java.util.Random;
import java.util.function.Consumer;

/**
 *
 */
public class SortUtil {
    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++)
            if (array[i] > array[i + 1])
                return false;
        return true;
    }

    public static boolean isPartialSorted(int[] arr, int start, int end) {
        for (int i = start; i < end - 1; i++)
            if (arr[i] > arr[i + 1])
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
    public static void showArray(int[] array) {
        //Array ausgeben
        System.out.println();
        for (int i = 0; i < array.length; i++)
            System.out.print(array[i] + " ");
        System.out.println();
    }
    // Nutz Sortierungsfunktion auf dem array und gibt die Zeit in ms aus
    public static long messureMS(int[] arr, Consumer<int[]> Sortierung){
        long start=System.currentTimeMillis();
        Sortierung.accept(arr);
        return System.currentTimeMillis()-start;
    }
}
