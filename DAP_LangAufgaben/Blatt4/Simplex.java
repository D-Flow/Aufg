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
    public Point getPoint(int i){
        i--;
        if(i<0||i>=arr.length)
            throw new IndexOutOfBoundsException();
        return arr[i];
    }

    public Stream<Point> getPointStream(){
        return Stream.of(arr);
    }


    public Iterator<Point> getIterator(){
        return new Iterator<Point>() {
            int i = 0;
            @Override
            public boolean hasNext() {
                return i<dim+1;
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
        Point A = getPoint(1);
        for(int i = 2;i<=dim+1;i++){
            Point B = getPoint(i);
            d+=euclid.distance(A,B);
            A=B;
        }
        d+=euclid.distance(getPoint(1),A);  //A ist letzter punkt

        Iterator<Point> it = getIterator();
        Point p1=it.next();
        for(Point p2 = it.next();it.hasNext();p2=it.next()){
            if(!it.hasNext())
                return d;
            d+=euclid.distance(p1,p2);
            p1=p2;
        }
        return d;

    }
}
