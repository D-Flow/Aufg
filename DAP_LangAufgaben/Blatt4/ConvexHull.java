import java.util.*;

/**
 *
 */
public class ConvexHull {
    public static class Pair<T> {
        public Pair(T a, T b) {
            this.a = a;
            this.b = b;
        }

        public String toString() {
            return "{"+a + "::" + b+"}";
        }

        public boolean equals(Object o) {
            if (o instanceof Pair)
                return ((Pair) o).a.equals(a) && ((Pair) o).b.equals(b);
            return super.equals(o);
        }

        T a, b;
    }

    private Pair<Vec2D>[] generatePairs(Point[] P) {
        List<Pair<Vec2D>> pairList = new LinkedList<>();
        for (Point a : P)
            for (Point b : P)
                if (!a.equals(b))
                    pairList.add(new Pair<>(new Vec2D(a.get(1), a.get(2)), new Vec2D(b.get(1), b.get(2))));
        if (P.length * (P.length - 1) != pairList.size())
            System.err.println("DOPPELTE PUNKTE VORHANDEN!");
        return pairList.toArray(new Pair[0]);
    }

    private boolean dimCheck(Point[] arr) {
        for (Point p : arr)
            if (p.dim() != 2)
                return false;
        return true;
    }


    public List<Point> simpleConvex(Point... P) {
        if (!dimCheck(P))
            throw new IllegalArgumentException("Punkte haben falsche Dimension!");

        Pair<Vec2D>[] pointPairs = generatePairs(P);    //Punktpaare generieren!
        List<Pair<Vec2D>> ValidPairList = new LinkedList<>();  //Liste der Validen paare
        for (Pair<Vec2D> pair : pointPairs) {
            boolean isValidLine = true;
            for (Point p : P) {
                if (p.equals(pair.a) || p.equals(pair.b))  //Falls p einer der Beiden Punkte ist
                    continue;
                if (!hasValidPosition(p, pair)) { //Falls p nicht links von der grade durch das Paar
                    isValidLine = false;
                    break;
                }
            }
            if (isValidLine)
                ValidPairList.add(pair);//Das Paar ist eine Valide grade für die Hülle da alle Punkte links liegen
        }

        List<Point> pointList = new LinkedList<>();//Endliste
        while (ValidPairList.size() > 0) {
            if (pointList.size() == 0) {
                //Anfangswerte gehören zur hülle
                Pair<Vec2D> v = ValidPairList.remove(0);
                pointList.add(v.a.asPoint());
                pointList.add(v.b.asPoint());
            } else {
                //Nehme letzten Hüllen endpunkt und suche ein Paar das mit diesem anfängt
                Point pointToSearch = pointList.get(pointList.size() - 1);
                Pair<Vec2D> pairFound = null;
                for (Pair<Vec2D> pair : ValidPairList)
                    if (pair.a.asPoint().equals(pointToSearch)) {
                        pairFound = pair;
                        break;
                    }
                //Ergänze hülle durch den Endpunkt des Paares
                ValidPairList.remove(pairFound);
                pointList.add(pairFound.b.asPoint());
            }
        }
        // Letze ist Erste => löschen
        pointList.remove(pointList.size() - 1);
        return pointList;
    }

    public static void main(String... afff) {
        ConvexHull cv = new ConvexHull();
        Point[] arr = new Point[1000];
        for (int i = 0; i < arr.length; i++)
            arr[i] = genPoint(1000, 1000);
        System.out.println(cv.simpleConvex(arr));
        System.out.println(genWithinHull(new Point(10, 10), new Point(10, 100), new Point(100, 10))+" liegt im Dreieck!");

    }

    static Random random = new Random();

    public static Point genPoint(double xLimit, double yLimit) {
        return new Point(Application.genDouble(xLimit), Application.genDouble(yLimit));
    }

    public static Point genPointBound(double xmin, double xmax, double ymin, double ymax) {
        double lx = xmax - xmin, ly = ymax - ymin;
        return new Point(random.nextDouble() * lx + xmin, random.nextDouble() * ly + ymin);
    }

    public static Point genWithinHull(Point a, Point b, Point c) {
        ConvexHull cv = new ConvexHull();
        Point p = genPointBound(-1000, 1000, -1000, 1000);
        //Generiere ein Punkt zwischen 10,10 und 100,100
        while (true) {
            List<Point> points = cv.simpleConvex(a, b, c, p);    //Berechne Hülle mit P
            //Falls Hülle 3 Punkte und diese a,b,c so ist P drin
            if (points.size() == 3 && points.contains(a) && points.contains(b) && points.contains(c))
                return p;
            else
                p = genPointBound(-1000, 1000, -1000, 1000);
        }

    }

    private boolean hasValidPosition(Point p, Pair<Vec2D> vec) {
        Vec2D target = new Vec2D(p.get(1), p.get(2));
        target.subtract(vec.a);     //Vektor AP
        Vec2D base = vec.b.clone();
        base.subtract(vec.a);       //Vektor AB
        //Messe winkel zwischen AP,AB mit AB die Grade

        //Winkel liegt zwischen -2PI und 2PI da jeder Winkel aus -PI,PI ist
        double relative_angle = target.angle() - base.angle();
        if (relative_angle < 0) relative_angle += Math.PI * 2;

        //Entweder auf linie oder   PI + base > target > base also links von base
        return (target.linearDependent(base) && target.isSmallerThan(base)) || (relative_angle > 0 && relative_angle < Math.PI);
    }
}
