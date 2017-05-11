/**
 *
 */
public class Point {
    public static final Point NULL_POINT_2D=new Point(0,0);
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

    public double angle_2D(){
        EuklidDistance euc=new EuklidDistance();
        double x1=get(1);
        System.out.println("LEN : "+euc.distance(NULL_POINT_2D,this));
        double cosan = Math.acos(x1/euc.distance(NULL_POINT_2D,this));
        System.out.println("Sin : "+Math.asin(get(2)/euc.distance(NULL_POINT_2D,this)));
        return cosan;
    }
    public static void main(String...ar){
        Point a = new Point(1,1),b=new Point(-5,3),c=new Point(0.1,-1.3);
        System.out.println(a.angle_2D());
        System.out.println(b.angle_2D());
        System.out.println(c.angle_2D());
    }
    public static Point subtract(Point a,Point b)
    {
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
