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

    }
    private boolean hasValidPosition(Point p,Tuple<Point> line){
        //case auf grade
        // case links
        return false;
    }
}
