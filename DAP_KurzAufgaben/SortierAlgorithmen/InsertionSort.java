/**
 *
 */
public class InsertionSort {
    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            assert SortUtil.isPartialSorted(array, 0, j);
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];    // move array[j] to array[j+1]
                j--;
            }
            // array[j]<= key und j+1 ist frei
            array[j + 1] = key;
        }
        assert SortUtil.isSorted(array);
    }
}
