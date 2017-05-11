import java.util.*;

/**
 *
 */
public class ConvexHull {
    public static class Tuple<T>{
        public Tuple(T a,T b){
            this.a=a;
            this.b=b;
        }
        T a,b;
    }

    private Tuple<Point>[] generateTuplex(Point[] P){
        List<Tuple<Point>> tupleList=new LinkedList<>();
        for(Point a : P)
            for(Point b : P)
                if(!a.equals(b))
                    tupleList.add(new Tuple<>(a,b));
        return tupleList.toArray(new Tuple[0]);
    }
    private boolean dimCheck(Point[] arr){
        for(Point p : arr)
            if(p.dim()!=2)
                return false;
        return true;
    }
    public List<Point> simpleConvex(Point[] P){
        dimCheck(P);
        Tuple<Point>[] pointTuples=generateTuplex(P);
        Set<Tuple<Point>> set=new HashSet<>();
        for(Tuple<Point> tuple : pointTuples){
            boolean isValidLine=true;
            for(Point p : P){
                if(!hasValidPosition(p,tuple)){
                    isValidLine=false;
                    break;
                }
            }
            if(isValidLine)
                set.add(tuple);
        }
        //do magic
        return new LinkedList<>();
    }
    private boolean isOnLine(Point p,Tuple<Point> vec){
        return false;
    }
    private strictfp double angle(Point p,Tuple<Point> vec){
        Point pointA = Point.subtract(p,vec.a);
        Point pointB = Point.subtract(vec.b,vec.a);
        Point nullPoint=new Point(0,0);
        EuklidDistance euc=new EuklidDistance();
        double x1=pointA.get(1),x2=pointB.get(1);
        double cosan = Math.acos(x1/euc.distance(nullPoint,pointA));
        System.out.println(Math.asin(pointA.get(2)/euc.distance(nullPoint,pointA)));
        return cosan;
    }
    private strictfp double fast_appr(Point p,Tuple<Point> vec){
        return 0;
    }
    private boolean hasValidPosition(Point p,Tuple<Point> vec){
        double angle = angle(p,vec);
        if(isOnLine(p,vec)||(angle<Math.PI&&angle>0))
            return true;
        return false;
    }
}
