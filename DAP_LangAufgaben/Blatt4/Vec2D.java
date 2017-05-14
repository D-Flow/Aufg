/**
 *  Normierter Vektor mit Arg
 */
public class Vec2D  extends Point{
    private double x,y,len,arg; //Polar Koordinaten und Normiert
    private double fullx,fully; //Wahre Koordinaten

    private boolean isNullVector(){
        return fullx==0&&fully==0;
    };
    protected Vec2D(double x,double y,double len,double arg,double fx,double fy){
        super(fx,fy);
        this.x=x;this.y=y;
        this.len=len;this.arg=arg;
        fullx=fx;fully=fy;
    }
    public Vec2D(double a,double b){
        super(a,b);
        if(a==0&&b==0){
            a=b=len=arg=fullx=fully=0;
            return;
        }
        init(a,b);
    }
    public Point asPoint(){
        return new Point(x*len,y*len);
    }
    private void init(double a,double b){
        len=Math.sqrt(a*a+b*b);
        fullx=a;
        fully=b;
        x=a/len;
        y=b/len;
        arg=Math.acos(x);
        if(b<0) arg=-arg;   // cos ist sym , sin nicht => falls b>0 so muss x>0 da sin(x) >0 in[0,PI]
    }
    public double getLength(){
        return len;
    }
    public double angle(){
        return arg;
    }
    public Vec2D clone(){
        if(isNullVector())
            return new Vec2D(0,0);
        return new Vec2D(x,y,len,arg,fullx,fully);
    }
    public void subtract(Vec2D b){
        if(fullx==b.fullx&&fully==b.fully)
            y=x=arg=len=fullx=fully=0;  //resultierender Vektor ist null
        else
            init(fullx-b.fullx,fully-b.fully);
    }
    public boolean linearDependent(Vec2D b){
        if(isNullVector()||b.isNullVector())
            return false;
        if(x!=0) {
            double alpha = b.x / x;
            return alpha * y == b.y;
        }else {
            double alpha = b.y / y;
            return alpha * x == b.x;
        }
    }
    public boolean isSmallerThan(Vec2D base){
        return len<=base.len;
    }
    @Override
    public double get(int i) {
        if(i==1)
            return fullx;
        if(i==2)
            return fully;
        throw new IllegalArgumentException("Falscher Koordinaten Index!");
    }
}
