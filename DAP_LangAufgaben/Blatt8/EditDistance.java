import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

public class EditDistance {
    public static class Pair {
        public Pair(String a, String b) {
            this.a = a;
            this.b = b;
        }

        String a, b;
    }

    public static int distance(String a, String b) {
        PrintStream stream = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int i) throws IOException {

            }
        }));
        int t = printEditOperations(a, b);
        System.setOut(stream);
        return t;
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
        if (raf == null || pairList.size() == 0) return;//Invalide Eingabe


        //Formatierungs bzw lese fehler werden nicht gefangen
        for (String s = raf.readLine(); s != null; s = raf.readLine())
            baseStrings.add(s);

        if (baseStrings.size() >= 1)
            pairList = combineStrings(baseStrings);
        for (Pair p : pairList)
            if (OFlag)
                printEditOperations(p.a, p.b);
            else
                System.out.println("Distance : " + distance(p.a, p.b));

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
