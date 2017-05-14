/**
 *
 */
public class EuklidDistance implements Distance {
    @Override
    public double distance(Point p1, Point p2) {
        double d = 0;
        if(p1.dim()!=p2.dim())
            throw new IllegalArgumentException("Wrong dimensions!");
        for(int i = 1;i<=p1.dim();i++)
            d+=Math.pow(p1.get(i)-p2.get(i),2);
        return Math.sqrt(d);
    }
}
