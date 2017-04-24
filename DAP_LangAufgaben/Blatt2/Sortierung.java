import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * Aufgabe 1&2
 */
public class Sortierung {
    //Algorithmus abbildung also von String -> Algoritmus(int[] arr)
    private static Map<String, Consumer<int[]>> algMap = new HashMap<>();
    //Füll abbildung also von String -> Füllfunktion(int n)
    private static Map<String, Function<Integer, int[]>> fillMap = new HashMap<>();

    static {
        //Initialisierung der Abbildungen
        algMap.put("merge", (arr) -> mergeSort(arr));
        algMap.put("insert", (arr) -> insertionSort(arr));
        fillMap.put("rand", (n) -> fillRandom(n));
        fillMap.put("auf", (n) -> fillIncreasing(n));
        fillMap.put("ab", (n) -> fillDecreasing(n));
    }
    public static void main(String... args) {
        int n;
        String sortType = "merge", fillType = "rand";

        if (args == null || !(args.length >= 1 && args.length <= 3)) {
            System.out.println("Parameter anzahl inkorrekt!");
            System.out.println("Nutze 'Sortierung n' mit n Natürliche Zahl um ein Zufälliges Array mit Mergesort zu sortieren.");
            System.out.println("Nutze 'Sorterung n [merge|insert [rand|auf|ab]]' für bestimmtes Sortieren.");
            return; //ERROR Aufruf hat falsche Form
        }
        try {
            n = Integer.parseInt(args[0]);
            if (n <= 0)
                throw new IllegalStateException();
            // n ist nun valid
        } catch (Exception e) {
            System.out.println("Erster Parameter ist keine Natürliche Zahl!");
            return;
        }
        if (args.length > 1) {  //fülltyp und sortierverfahren aus Parameter entnehmen
            sortType = args[1];
            if (args.length > 2)
                fillType = args[2];
        }
        // sortType,fillType nun valid
        if (!algMap.containsKey(sortType)) {    //Prüfen ob das sortierverfahren vorhanden ist
            System.out.println("Sortierung : " + sortType + " wurde nicht gefunden! Nutze  merge|insert ");
            return;
        }
        if (!fillMap.containsKey(fillType)) {   //analog
            System.out.println("Füllart : " + fillType + " wurde nicht gefunden! Nutze rand|auf|ab ");
            return;
        }
        // füllmethode und algorithmus existieren

        int[] array = fillMap.get(fillType).apply(n);   //füllverfahren auswählen und mit n aufrufen
        long start = System.currentTimeMillis();
        algMap.get(sortType).accept(array); //Algorithmus aufrufen
        long end = System.currentTimeMillis();

        if (!isSorted(array))
            System.out.println("Feld ist NICHT sortiert!");
        else
            System.out.println("Feld ist Sortiert! Sortierung in : " + (end - start) + "ms abgeschlossen!");
        if (n <= 100)
            showArray(array);
    }

    public static void showArray(int[] array) {
        //Array ausgeben
        System.out.println();
        for (int i = 0; i < array.length; i++)
            System.out.print(array[i] + " ");
        System.out.println();
    }

    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            assert isPartialSorted(array, 0, j);
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];    // move array[j] to array[j+1]
                j--;
            }
            // array[j]<= key
            array[j + 1] = key;
        }
        assert isSorted(array);
    }


    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++)
            if (array[i] > array[i + 1])
                return false;
        return true;
    }

    private static boolean isPartialSorted(int[] arr, int start, int end) {
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
            assert isPartialSorted(array, left, currentI);
        }
    }

    public static void mergeSort(int[] array, int[] tempArray, int left, int right) {
        if (left >= right)
            return;
        int q = (right + left) / 2;
        mergeSort(array, tempArray, left, q);
        mergeSort(array, tempArray, q + 1, right);
        assert isPartialSorted(array, left, q);   // linke hälfte ist sortiert
        assert isPartialSorted(array, q + 1, right);    // rechte hälfte ist sortiert
        merge(array, tempArray, left, q, right);    //assert passiert im aufrufendem call! auf left bis right
    }

    public static void mergeSort(int[] array) {
        int[] tmpArray = new int[array.length];
        // right ist inklusive nach Zusatzblatt2
        mergeSort(array, tmpArray, 0, array.length - 1);
        assert isSorted(array);
    }
}
