/**
 *
 */
public class Point {
    private final int dimension;
    private final double[] arr;
    public Point(double ... v){
        if(v==null||v.length==0)
            throw new IllegalArgumentException("Punkt werte Invalid");
        arr=v;
        dimension=v.length;
    }
    public double get(int i){
        i--;
        if(i<0||i>=arr.length)
            throw new IndexOutOfBoundsException();
        return arr[i];
    }
    public int dim(){return dimension;}
}
