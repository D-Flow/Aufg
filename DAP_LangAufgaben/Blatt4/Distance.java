/**
 *
 */
public interface Distance {
    public double distance(Point p1,Point p2);
    public default double scalarProduct(Point p1,Point p2){
        if(p1.dim()!=p2.dim())
            throw new IllegalArgumentException("Dimensionen sind nicht identisch!");
        double d = 0;
        for(int i = 1;i<= p1.dim();i++)
            d=d+p1.get(i)*p2.get(i);
    }
}
