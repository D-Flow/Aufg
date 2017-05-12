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
        Set<Pair<Vec2D>> set=new HashSet<>();
        for(Pair<Vec2D> pair : pointPairs){
            boolean isValidLine=true;
            for(Point p : P){
                if(!hasValidPosition(p, pair)){
                    isValidLine=false;
                    break;
                }
            }
            if(isValidLine)
                set.add(pair);
        }
        //do magic
        return new LinkedList<>();
    }

    private boolean hasValidPosition(Point p, Pair<Vec2D> vec){
        Vec2D target=new Vec2D(p.get(1),p.get(2));
        target.subtract(vec.a);
        Vec2D base = vec.b.clone();
        base.subtract(vec.a);
        double relative_angle=target.angle()-base.angle();
        return target.inLineWith(base)||(relative_angle>0&&relative_angle<Math.PI);
    }
}
