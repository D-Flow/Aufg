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
        return d;

    }
}
