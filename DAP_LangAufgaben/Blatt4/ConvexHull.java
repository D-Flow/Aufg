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
        System.out.println(pointPairs.length);
        List<Pair<Vec2D>> set=new LinkedList<>();
        for(Pair<Vec2D> pair : pointPairs){
            boolean isValidLine=true;
            for(Point p : P){
                if(!p.equals(pair.a.asPoint())&&!p.equals(pair.b.asPoint())&&!hasValidPosition(p, pair)){
                    System.out.println("Point inv : "+p+" : "+pair);
                    isValidLine=false;
                    break;
                }
            }
            if(isValidLine)
                set.add(pair);
        }
        System.out.println("SET : "+set);
        List<Point> pointList=new LinkedList<>();
        while(set.size()>0){
            if(pointList.size()==0) {
                Pair<Vec2D> v = set.remove(0);
                pointList.add(v.a.asPoint());
                pointList.add(v.b.asPoint());
            }else{
                Point lastPoint=pointList.get(pointList.size()-1);
                Pair<Vec2D> pairToInsert=null;
                for(Pair<Vec2D> pair : set){
                    if(pair.a.equals(lastPoint)){
                        pairToInsert=pair;
                        break;
                    }
                }
                set.remove(pairToInsert);
                pointList.add(pairToInsert.b.asPoint());
            }
        }
        //do magic
        return pointList;
    }
    public static void main(String...afff){
        ConvexHull cv=new ConvexHull();
        Point[] points=new Point[5];
        points[0]=new Point(-1,0);
        points[1]=new Point(1,0);
        points[2]=new Point(0.5,10);
        points[3]=new Point(0.6,0.1);
        points[4]=new Point(0.4,0.1);
        List<Point> c = cv.simpleConvex(points);
        System.out.println(c);
        //System.out.println(cv.hasValidPosition(c,new Pair<Vec2D>(new Vec2D(a.get(1),a.get(2)),new Vec2D(b.get(1),b.get(2)))));
        //System.out.println(cv.hasValidPosition(c,new Pair<Vec2D>(new Vec2D(b.get(1),b.get(2)),new Vec2D(a.get(1),a.get(2)))));
    }

    private boolean hasValidPosition(Point p, Pair<Vec2D> vec){
        Vec2D target=new Vec2D(p.get(1),p.get(2));
        target.subtract(vec.a);
        Vec2D base = vec.b.clone();
        base.subtract(vec.a);
        double relative_angle=target.angle()-base.angle();
        System.out.println("P : "+target+"a : "+target.angle()+" against "+base+" a:"+base.angle()+" ANGLE : "+relative_angle);
        return target.inLineWith(base)||(relative_angle>=0&&relative_angle<=Math.PI);
    }
}
