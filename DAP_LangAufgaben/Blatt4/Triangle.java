/**
 *
 */
public class Triangle extends Simplex{
    public Triangle(Point ... args){
        super(args);
    }
    @Override
    public boolean validate() {
        if(getPointStream().anyMatch((p)->p.dim()!=2))
            return false;
        if(getPointStream().count()!=3)
            return false;
        return true;
    }
}
