/**
 *
 */
public class Point {
    private final int dimension;
    private final double[] arr;
    public Point(double ... v){
        if(v==null||v.length==0)
            throw new IllegalArgumentException("Dim error!");
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





    public void inline_subtract(Point b){
        if(dimension!=b.dimension)
            throw new IllegalArgumentException("Falsche Dimension!");
        for(int i = 0;i<dimension;i++)
            arr[i]-=b.arr[i];
    }
    public boolean equals(Object o){
        if(o instanceof Point){
            if(((Point) o).dim()!=dim())
                return false;
            for(int i = 0;i<arr.length;i++)
                if(((Point) o).arr[i]!=arr[i])
                    return false;
            return true;
        }
        return false;
    }
    public Point subtract(Point a,Point b) {
        Point c;
        if(a.dimension==b.dimension)
            c=new Point(new double[a.dimension]);
        else
            throw new IllegalArgumentException("Falsche Dimension!");
        for(int i = 0;i<a.dimension;i++)
            c.arr[i]=a.arr[i]-b.arr[i];
        return c;
    }
}
