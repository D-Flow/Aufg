import java.util.Iterator;

import java.util.stream.Stream;

/**
 *
 */
public abstract class Simplex {
    private final Point[] arr;
    private final int dim;
    public Simplex(Point ... p){
        arr=p;
        if(p==null||p.length<1)
            throw new IllegalArgumentException();
        dim=p.length-1;
    }

    //Benötigte Access Methode...
    public Point getPoint(int i){
        i--;
        if(i<0||i>=arr.length)
            throw new IndexOutOfBoundsException();
        return arr[i];
    }

    //Access Methoden
    public Stream<Point> getPointStream(){return Stream.of(arr);}
    public Iterator<Point> getIterator(){
        return new Iterator<Point>() {
            int i = 0;
            @Override
            public boolean hasNext() {
                return i<arr.length;
            }

            @Override
            public Point next() {
                if(hasNext()) {
                    Point p = arr[i];
                    i++;
                    return p;
                }else
                    throw new IllegalStateException();
            }
        };
    }

    private Distance euclid=new EuklidDistance();
    public int dim(){
        return dim;
    }
    public abstract boolean validate();
    public double perimeter(){
        double d = 0;
        if(dim==1)
            return d;
        //FALSCH
        Iterator<Point> it = getIterator();
        Point p1=it.next();
        for(Point p2 = it.next();it.hasNext();p2=it.next()){
            if(!it.hasNext())
                return d;
            d+=euclid.distance(p1,p2);
            p1=p2;
        }
        return d+euclid.distance(getPoint(1),p1);

    }
}
