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
                //arr[i-1][j]+1 minimal <=> A[1...i] ohne a_i kann optimal gelöst werden
                //arr[i][j-1]+1 minimal <=> B[1...j] ohne b_j kann optimal gelöst werden
                // => A[1...i] mit b_j ist optimal bzgl B[1...j-1]

                //falls a==b so muss dieses Zeichen nicht ersetzt werden <=> a wird übernommen bzw ite Zeichen
                arr[i][j] = Math.min(arr[i][j], arr[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1));
                // nte Zeichen von a bzw b ist bei b.charAt(n-1)
            }
        return arr;
    }

    public static int distance(String a, String b) {
        int[][] arr = createTable(a, b);
        return arr[a.length()][b.length()];
    }

    //Addition und Lösch OP werden 'gleichzeitig' betrachtet
    //Nach Add muss die Folgenden Ops auf index+1 zugreifen
    //Sub analog
    public static LinkedList<CharTRF> applyOffsetChange(LinkedList<CharTRF> l) {
        int offset = 0;
        LinkedList<CharTRF> retl = new LinkedList<>();
        while (l.size() > 0) {
            CharTRF change = l.removeFirst();
            change.ShiftIndex(offset);
            if (change.delete) offset--;
            if (change.add) offset++;
            retl.add(change);
        }
        return retl;
    }

    // (i,j)->(0,0) und monoton fallend somit immer First addition
    public static List<CharTRF> changeList(String a, String b, int[][] arr, int i, int j, LinkedList<CharTRF> list) {
        if (i == 0 && j == 0) return list;

        if (i >= 1 && j >= 1)//Zeichenvergleich möglich
        if (a.charAt(i - 1) == (b.charAt(j - 1)))
            if (arr[i][j] == arr[i - 1][j - 1]) {
                list.addFirst(new CharTRF(i, a.charAt(i - 1), a.charAt(i - 1)));
                return changeList(a, b, arr, i - 1, j - 1, list);//nichts ersetzen, also keep
            }
        if (i >= 1 && j >= 0)//Es kann etwas gelöscht werden...
            if (arr[i][j] == arr[i - 1][j] + 1) {//Löschen
                list.addFirst(new CharTRF(i, a.charAt(i - 1), true));
                return changeList(a, b, arr, i - 1, j, list);
            }

        if (i >= 0 && j >= 1)//Ein Zeichen kann hinzugefügt werden...
            if (arr[i][j] == arr[i][j - 1] + 1) {//Relative Addition nach dem iten Zeichen also in i+1
                list.addFirst(new CharTRF(i + 1, b.charAt(j - 1), false));
                return changeList(a, b, arr, i, j - 1, list);
            }

        //Ersetzen... muss passieren da arr wohldef.
        list.addFirst(new CharTRF(i, a.charAt(i - 1), b.charAt(j - 1)));
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

        LinkedList<CharTRF> linkedList = new LinkedList<>();
        changeList(a, b, arr, a.length(), b.length(), linkedList);
        linkedList = applyOffsetChange(linkedList);

        ArrayList<Character> characters = new ArrayList<>(Math.max(a.length(), b.length()));
        for (int i = 0; i < a.length(); i++)
            characters.add(a.charAt(i));

        System.out.println("0) Endkosten : " + arr[a.length()][b.length()] + " start mit : " + characters + "");
        int nr = 1;
        for (int i = 1; characters.size() > i - 1 || linkedList.size() > 0; nr++) {
            System.out.print(nr + ") ");

            //if (linkedList.size() > 0 && linkedList.getFirst().index == i) {
                System.out.print("Kosten : " + 1 + " " + linkedList.getFirst());
                i += linkedList.removeFirst().apply(characters);
            /*} else {
                System.out.print("Kosten : 0 Übernehme Zeichen@" + i + "  '" + characters.get(i - 1) + "'");
                i++;
            }*/
            System.out.println(" : " + characters);
        }
        System.out.println();
        for (int i = 0; i < b.length(); i++)
            if (characters.get(i) != b.charAt(i)) throw new IllegalStateException("UMFORMUNG FEHLERHAFT!");

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
