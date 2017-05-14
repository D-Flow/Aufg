/**
 *
 */
public class Triangle extends Simplex{

    public Triangle(Point ... args){
        super(args);
    }
    @Override
    public boolean validate() {
        //Gibt es ein Element mit der Eigenschaft 'dim!=2'
        if (getPointStream().anyMatch((p) -> p.dim() != 2))
            return false;
        //Zähle elemente im Stream
        if (getPointStream().count() != 3)
            return false;
        return true;
    }
}
