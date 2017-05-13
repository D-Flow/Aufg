/**
 *
 */
public class Vec2D  extends Point{
    public static Vec2D NULLVEC=new Vec2D(0,0);
    private double x,y,len,arg;
    private boolean isNULLVEC(){
        return x==0&&y==0;
    };
    protected Vec2D(double x,double y,double len,double arg){
        super(x*len,y*len);
        this.x=x;this.y=y;
        this.len=len;this.arg=arg;
    }
    public Vec2D(double a,double b){
        super(a,b);
        if(a==0&&b==0){
            a=b=len=arg=0;
            return;
        }
        init(a,b);
    }
    public Point asPoint(){
        return new Point(x*len,y*len);
    }
    private void init(double a,double b){
        len=Math.sqrt(a*a+b*b);
        x=a/len;
        y=b/len;
        arg=Math.acos(x);
        if(b<0)
            arg=arg*-1;
    }
    public double getLen(){
        return len;
    }
    public double angle(){
        return arg;
    }
    public Vec2D clone(){
        if(isNULLVEC())
            return NULLVEC;
        return new Vec2D(x,y,len,arg);
    }
    public void subtract(Vec2D b){
        if(x*len==b.x*b.len&&y*len==b.y*b.len){
            y=x=arg=len=0;
        }
        init(x*len-b.x*b.len,y*len-b.y*b.len);
    }
    public boolean inLineWith(Vec2D b){
        if(isNULLVEC()||b.isNULLVEC())
            return false;
        if(x!=0) {
            double alpha = b.x / x;
            return alpha * y == b.y;
        }else {
            double alpha = b.y / y;
            return alpha * x == b.x;
        }
    }

    @Override
    public double get(int i) {
        if(i==1)
            return x*len;
        if(i==2)
            return y*len;
        throw new IllegalArgumentException("Falscher Koordinaten Index!");
    }
}
