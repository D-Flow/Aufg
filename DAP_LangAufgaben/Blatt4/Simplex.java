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
    public int dim(){
        return dim;
    }
    public abstract boolean validate();
    public double perimeter(){
        double d = 0;
        for(int i = 1;i<=dim()+1;i++)
    }
}
