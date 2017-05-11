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

    private Tuple<Vec2D>[] generateTuples(Point[] P){
        List<Tuple<Vec2D>> tupleList=new LinkedList<>();
        for(Point a : P)
            for(Point b : P)
                if(!a.equals(b))
                    tupleList.add(new Tuple<>(new Vec2D(a.get(1),a.get(2)),new Vec2D(b.get(1),b.get(2))));
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
        Tuple<Vec2D>[] pointTuples=generateTuples(P);
        Set<Tuple<Vec2D>> set=new HashSet<>();
        for(Tuple<Vec2D> tuple : pointTuples){
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

    private boolean hasValidPosition(Point p,Tuple<Vec2D> vec){
        Vec2D target=new Vec2D(p.get(1),p.get(2));
        target.subtract(vec.a);
        Vec2D base = vec.b.clone();
        base.subtract(vec.a);
        double relative_angle=target.angle()-base.angle();
        return target.inLineWith(base)||(relative_angle>0&&relative_angle<Math.PI);
    }
}
