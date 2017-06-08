import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

public class EditDistance {
    public static class IntPair {
        int a, b;

        IntPair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
    public static class Pair {
        public Pair(String a, String b) {
            this.a = a;
            this.b = b;
        }

        public String toString() {
            return a + "::" + b;
        }

        String a, b;
    }
    public static int distance(String a, String b) {
        int[][] arr = new int[a.length() + 1][b.length() + 1];
        //arr[i][j] <=> Anzahl der änderungen beim vergleichen von a[1...i] und b[1...j]
        for (int i = 0; i < arr.length; i++)
            arr[i][0] = i;
        for (int j = 0; j < arr[0].length; j++)
            arr[0][j] = j;
        for (int i = 1; i < arr.length; i++)
            for (int j = 1; j < arr[i].length; j++) {
                arr[i][j] = Math.min(arr[i - 1][j] + 1, arr[i][j - 1] + 1);
                //Erstes bedeutet Delta wenn wir von A[i] das ite löschen
                //Zweites bedeutet Delta wenn wir von B[j] das jte Löschen bzw relative zu A addieren

                //falls a==b so muss dieses Zeichen nicht ersetzt werden
                arr[i][j] = Math.min(arr[i][j], arr[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1));
                // nte Zeichen von a bzw b ist bei b.charAt(n-1)
            }
        printArray(arr);
        LinkedList<Pair> l = new LinkedList();
        System.out.println(rc(a, b, arr, a.length(), b.length(), l));
        return arr[a.length()][b.length()];
    }

    public static List<Pair> rc(String a, String b, int[][] arr, int i, int j, LinkedList<Pair> list) {
        System.out.println("I : " + i + " J " + j);
        if (i == 0 && j == 0) return list;

        if (i >= 1 && j >= 1)//Zeichenvergleich möglich
        if (a.charAt(i - 1) == (b.charAt(j - 1)))
            if (arr[i][j] == arr[i - 1][j - 1])
                return rc(a, b, arr, i - 1, j - 1, list);//nichts ersetzen

        if (i >= 1 && j >= 0)//Es kann etwas gelöscht werden...
            if (arr[i][j] == arr[i - 1][j] + 1) {//Löschen
                list.addFirst(new Pair("DEL@ : " + i, a.charAt(i - 1) + ""));
                return rc(a, b, arr, i - 1, j, list);
            }

        if (i >= 0 && j >= 1)//Ein Zeichen kann hinzugefügt werden...
            if (arr[i][j] == arr[i][j - 1] + 1) {//Relative Addition
                list.addFirst(new Pair("ADD@ : " + i, "" + b.charAt(j - 1)));//Add nachdem iten zeichen
                return rc(a, b, arr, i, j - 1, list);
            }

        //Ersetzen... muss passieren da arr wohldef.
        list.addFirst(new Pair("REPLACE@ : " + i, a.charAt(i - 1) + " -> " + b.charAt(j - 1)));
        return rc(a, b, arr, i - 1, j - 1, list);

    }

    public static void cc(String a, String b, List<Pair> list) {

    }
    public static void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length - 1; j++)
                System.out.print(arr[i][j] + " ,");
            if (arr[i].length >= 1)
                System.out.println(arr[i][arr[i].length - 1]);
        }
        System.out.println();
    }
    public static int printEditOperations(String a, String b) {
        return 0;
    }

    private static RandomAccessFile raf = null;

    public static void main(String[] args) throws IOException {
        List<Pair> pairList = new LinkedList<>();
        List<String> baseStrings = new LinkedList<>();
        boolean OFlag = false;
        if (args.length == 0) return;
        else OFlag = args[args.length - 1].equals("-o");
        if (args.length == 1 + (OFlag ? 1 : 0))
            raf = new RandomAccessFile(args[0], "");
        if (args.length == 2 + (OFlag ? 1 : 0))
            pairList.add(new Pair(args[0], args[1]));
        if (raf == null && pairList.size() == 0) return;//Invalide Eingabe




        //Formatierungs bzw lese fehler werden nicht gefangen
        if (raf != null)
            for (String s = raf.readLine(); s != null; s = raf.readLine())
                baseStrings.add(s);

        if (baseStrings.size() >= 1)
            pairList = combineStrings(baseStrings);
        for (Pair p : pairList)
            if (OFlag)
                printEditOperations(p.a, p.b);
            else
                System.out.println("Distance : " + distance(p.a, p.b) + " von " + p.a + " :: " + p.b);

    }

    //Jede kombination... also auch Sym
    private static List<Pair> combineStrings(List<String> base) {
        List<Pair> pairList = new LinkedList<>();
        for (String a : base)
            for (String b : base)
                if (a.equals(b)) continue;
                else pairList.add(new Pair(a, b));
        return pairList;
    }
}
