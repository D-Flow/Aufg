import java.util.ArrayList;
import java.util.List;

public class Rucksackproblem {
    public static void main(String... args) throws Exception {
        int n = Integer.parseInt(args[0]);
        int G = Integer.parseInt(args[1]);
        int p = Integer.parseInt(args[2]);


        List<Ware> waren = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            waren.add(Ware.gen(p));
        }
        waren.sort((wareA, wareB) -> (int) -(wareA.ratio() - wareB.ratio()));
        //System.out.println(waren);
        System.out.println("Algo Vorl : " + rucksack(G, waren));

        int g = 0, sum = 0;
        for (int i = 0; i < waren.size(); i++) {
            Ware w = waren.get(i);
            if (G - g >= w.gewicht) {
                sum += w.wert;
                g += w.gewicht;
            }
        }
        System.out.println("Greedy : " + sum);
        int GSkalar = 500, NSkalar = 500;
        for (g = 1; g <= 16; g++) {
            for (n = 1; n <= 30; n++)
                messureTime(n * NSkalar, g * GSkalar, p);
            FX_Plott.create_FX_Plott("Algorithmus abhängig in n für G=" + g * GSkalar + " P=" + p);
        }
    }

    static int retrys = 6;

    public static void messureTime(int n, int g, int p) {
        long sum = 0;
        for (int t = 0; t < retrys; t++) {
            List<Ware> waren = new ArrayList<>(n);
            for (int i = 0; i < n; i++)
                waren.add(Ware.gen(p));
            long start = System.currentTimeMillis();
            rucksack(g, waren);
            sum += System.currentTimeMillis() - start;
        }
        FX_Plott.addPoint(n, sum / retrys);
    }

    public static class Ware {
        public int wert, gewicht;

        public int ratio() {
            return (int) (1000 * (((double) wert) / gewicht));
        }

        public String toString() {
            return "W : " + wert + " G " + gewicht + "\n";
        }

        public static Ware gen(int p) {
            Ware w = new Ware();
            w.gewicht = (int) (Math.random() * 900 + 100);
            w.wert = (int) (p * ((Math.random() * 0.45D) + 0.8D));
            return w;
        }
    }

    public static int rucksack(int G, List<Ware> warenList) {
        int[][] arr = new int[warenList.size() + 1][G + 1];
        for (int j = 0; j < G + 1; j++)
            arr[0][j] = 0;
        for (int i = 1; i < warenList.size() + 1; i++)
            for (int j = 0; j < G + 1; j++) {
                Ware w = warenList.get(i - 1);
                if (j < w.gewicht)
                    arr[i][j] = arr[i - 1][j];
                else
                    arr[i][j] = Math.max(arr[i - 1][j], arr[i - 1][j - w.gewicht] + w.wert);
            }
        return arr[warenList.size()][G];
    }
}
