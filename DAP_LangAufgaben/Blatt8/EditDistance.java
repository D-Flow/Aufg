import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EditDistance {
    public static int[][] createTable(String a, String b) {
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
        return arr;
    }

    public static int distance(String a, String b) {
        int[][] arr = createTable(a, b);
        return arr[a.length()][b.length()];
    }

    public static LinkedList<CharChange> linearisedList(LinkedList<CharChange> l) {
        int offset = 0;
        LinkedList<CharChange> retl = new LinkedList<>();
        while (l.size() > 0) {
            CharChange change = l.removeFirst();
            for (int i = 0; i < offset; i++)
                change.increment();
            for (int i = offset; i < 0; i++)
                change.decrement();
            if (change.delete) offset--;
            if (change.add) offset++;
            retl.add(change);
        }
        return retl;
    }

    public static List<CharChange> changeList(String a, String b, int[][] arr, int i, int j, LinkedList<CharChange> list) {
        if (i == 0 && j == 0) return list;

        if (i >= 1 && j >= 1)//Zeichenvergleich möglich
        if (a.charAt(i - 1) == (b.charAt(j - 1)))
            if (arr[i][j] == arr[i - 1][j - 1])
                return changeList(a, b, arr, i - 1, j - 1, list);//nichts ersetzen

        if (i >= 1 && j >= 0)//Es kann etwas gelöscht werden...
            if (arr[i][j] == arr[i - 1][j] + 1) {//Löschen
                list.addFirst(new CharChange(i, a.charAt(i - 1), true));//new Pair("DEL@ : " + i, a.charAt(i - 1) + ""));
                return changeList(a, b, arr, i - 1, j, list);
            }

        if (i >= 0 && j >= 1)//Ein Zeichen kann hinzugefügt werden...
            if (arr[i][j] == arr[i][j - 1] + 1) {//Relative Addition nach dem iten Zeichen also in i+1
                list.addFirst(new CharChange(i + 1, b.charAt(j - 1), false));//"ADD@ : " + i, "" + b.charAt(j - 1)));//Add nachdem iten zeichen
                return changeList(a, b, arr, i, j - 1, list);
            }

        //Ersetzen... muss passieren da arr wohldef.
        list.addFirst(new CharChange(i, a.charAt(i - 1), b.charAt(j - 1)));
        return changeList(a, b, arr, i - 1, j - 1, list);

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

    public static void printEditOperations(String a, String b) {
        int[][] arr = createTable(a, b);
        System.out.println("Vergleiche " + a + " mit " + b);
        LinkedList<CharChange> linkedList = new LinkedList<>();
        changeList(a, b, arr, a.length(), b.length(), linkedList);
        linkedList = linearisedList(linkedList);
        ArrayList<Character> characters = new ArrayList<>(Math.max(a.length(), b.length()));
        for (int i = 0; i < a.length(); i++)
            characters.add(a.charAt(i));

        System.out.println("0) Endkosten : " + arr[a.length()][b.length()] + " start mit : " + characters + "");
        int nr = 1;
        for (int i = 1; characters.size() > i - 1 || linkedList.size() > 0; ) {
            System.out.print(nr + ") ");
            nr++;
            CharChange c = null;
            if (linkedList.size() > 0)
                c = linkedList.getFirst();
            if (c != null && c.index == i) {
                System.out.print("Kosten : " + 1 + " " + c);
                linkedList.removeFirst();
                i += c.apply(characters);
            } else {
                System.out.print("Kosten : 0 Übernehme Zeichen@" + i + "  '" + characters.get(i - 1) + "'");
                i++;
            }
            System.out.println(" : " + characters);
        }
        System.out.println();
    }

    private static RandomAccessFile raf = null;

    public static void main(String[] args) throws IOException {
        List<Pair> pairList = new LinkedList<>();
        List<String> baseStrings = new LinkedList<>();
        boolean OFlag = false;
        if (args.length == 0) return;
        else OFlag = args[args.length - 1].equals("-o");
        if (args.length == 1 + (OFlag ? 1 : 0))
            raf = new RandomAccessFile(args[0], "rw");
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
