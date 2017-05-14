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

    //i te Koordinate
    public double get(int i){
        i--;
        if(i<0||i>=arr.length)
            throw new IndexOutOfBoundsException();
        return arr[i];
    }

    public int dim(){return dimension;}
    public String toString(){
        String s = "(";
        for(int i = 1;i<dim();i++)
            s+=get(i)+";";
        s+=get(dim())+")";
        return s;
    }

    //Zwei Punkte sind gleich wenn dimensionen und werte gleich
    public boolean equals(Object o){
        if(o instanceof Point){
            if(((Point) o).dim()!=dim())
                return false;
            for(int i = 1;i<=dim();i++)
                if(((Point) o).get(i)!=get(i))
                    return false;
            return true;
        }
        return false;
    }
}
