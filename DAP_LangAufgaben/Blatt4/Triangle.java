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
        Vec2D a = new Vec2D(getPoint(1).get(1),getPoint(1).get(2));
        Vec2D b = new Vec2D(getPoint(2).get(1),getPoint(2).get(2));
        Vec2D c = new Vec2D(getPoint(3).get(1),getPoint(3).get(2));

        a.subtract(b);  //Vektor BA
        c.subtract(b);  //Vektor BC
        return a.linearDependent(c);//falls lin abh. so bilden sie eine grade
    }
}
