import java.util.*;

/**
 *
 */
public class ConvexHull {
    public static class Pair<T>{
        public Pair(T a, T b){
            this.a=a;
            this.b=b;
        }
        public String toString(){
            return a+"::"+b;
        }
        public boolean equals(Object o){
            if(o instanceof Pair)
                return ((Pair) o).a.equals(a)&&((Pair) o).b.equals(b);
            return super.equals(o);
        }
        T a,b;
    }

    private Pair<Vec2D>[] generatePairs(Point[] P){
        List<Pair<Vec2D>> pairList =new LinkedList<>();
        for(Point a : P)
            for(Point b : P)
                if(!a.equals(b))
                    pairList.add(new Pair<>(new Vec2D(a.get(1),a.get(2)),new Vec2D(b.get(1),b.get(2))));
        return pairList.toArray(new Pair[0]);
    }

    private boolean dimCheck(Point[] arr){
        for(Point p : arr)
            if(p.dim()!=2)
                return false;
        return true;
    }

    public List<Point> simpleConvex(Point[] P){
        dimCheck(P);
        Pair<Vec2D>[] pointPairs = generatePairs(P);
        List<Pair<Vec2D>> set=new LinkedList<>();
        for(Pair<Vec2D> pair : pointPairs){
            boolean isValidLine=true;
            for(Point p : P){
                if(p.equals(pair.a)||p.equals(pair.b))
                    continue;
                if(!hasValidPosition(p, pair)){
                    isValidLine=false;
                    break;
                }
            }
            if(isValidLine) {
                System.out.println("PAIR : "+pair+" inserted!");
                set.add(pair);
            }
        }
        System.out.println("SET of Pairs : "+set);
        List<Point> pointList=new LinkedList<>();
        while(set.size()>0){
            if(pointList.size()==0) {
                Pair<Vec2D> v = set.remove(0);
                pointList.add(v.a.asPoint());
                pointList.add(v.b.asPoint());
            }else{
                Point pointToSearch=pointList.get(pointList.size()-1);
                Pair<Vec2D> pairFound=null;
                for(Pair<Vec2D> pair : set){
                    if(pair.a.asPoint().equals(pointToSearch)){
                        pairFound=pair;
                        break;
                    }
                }
                System.out.println("Searching : "+pointToSearch);
                System.out.println("FOUND : "+pairFound.b.asPoint());
                set.remove(pairFound);
                pointList.add(pairFound.b.asPoint());
            }
        }
        //do magic
        return pointList;
    }

    public static void main(String...afff){
        ConvexHull cv=new ConvexHull();
        Point[] arr = new Point[1000];
        for(int i = 0;i<arr.length;i++)
            arr[i]=genPoint(100000,100000);
        System.out.println(cv.simpleConvex(arr));

    }
    public static Point genPoint(double xLimit,double yLimit){
        return new Point(Application.genDouble(xLimit),Application.genDouble(yLimit));
    }
    private boolean hasValidPosition(Point p, Pair<Vec2D> vec){
        Vec2D target=new Vec2D(p.get(1),p.get(2));
        target.subtract(vec.a);
        Vec2D base = vec.b.clone();
        base.subtract(vec.a);

        //Winkel liegt zwischen -2PI und 2PI da jeder Winkel aus -PI,PI ist
        double relative_angle=target.angle()-base.angle();
        if(relative_angle<0)relative_angle+=Math.PI*2;

        //Entweder auf linie oder   PI + a > b > a also liegt ist b's winkel zwischen 0,180 zu a
        return target.linearDependent(base)||(relative_angle>0&&relative_angle<Math.PI);
    }
}
