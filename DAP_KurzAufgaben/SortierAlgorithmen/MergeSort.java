
/**
 *
 */
public class MergeSort {
    private static void merge(int[] array, int[] tempArray, int left, int middle, int right) {
        for (int i = left; i <= right; i++)  // kopieren damit beim zusammenfügen nichts verloren wird   => wähle immer aus tmp
            tempArray[i] = array[i];

        int rightPointer = middle + 1, leftPointer = left;  //Pointer auf die jeweils aktuellen elemente im linken bzw. rechten teil array
        for (int currentI = left; currentI <= right; currentI++) {
            if (leftPointer <= middle && rightPointer <= right)     // links und rechts sind valide möglichkeiten => wähle kleinste
                if (tempArray[leftPointer] <= tempArray[rightPointer]) {
                    array[currentI] = tempArray[leftPointer]; //linke hälfte besitzt kleineres element => wähle es und schiebe pointer 1 weiter
                    leftPointer++;
                } else {
                    array[currentI] = tempArray[rightPointer];  // analog mit wähle rechtes element
                    rightPointer++;
                }
            else {//wenn eintrifft wird nur noch dies ausgeführt da der invalide pointer niemals valid wird!

                //nur ein Pointer ist valid => wähle den validen
                if (leftPointer <= middle) {
                    array[currentI] = tempArray[leftPointer];
                    leftPointer++;
                } else {
                    array[currentI] = tempArray[rightPointer];
                    rightPointer++;
                }
            }
            assert SortUtil.isPartialSorted(array, left, currentI);
        }
    }

    public static void mergeSort(int[] array, int[] tempArray, int left, int right) {
        if (left >= right)
            return;
        int q = (right + left) / 2;
        mergeSort(array, tempArray, left, q);
        mergeSort(array, tempArray, q + 1, right);
        assert SortUtil.isPartialSorted(array, left, q);   // linke hälfte ist sortiert
        assert SortUtil.isPartialSorted(array, q + 1, right);    // rechte hälfte ist sortiert
        merge(array, tempArray, left, q, right);    //assert passiert im aufrufendem call! auf left bis right
    }

    public static void mergeSort(int[] array) {
        int[] tmpArray = new int[array.length];
        // right ist inklusive nach Zusatzblatt2
        mergeSort(array, tmpArray, 0, array.length - 1);
        assert SortUtil.isSorted(array);
    }
}
