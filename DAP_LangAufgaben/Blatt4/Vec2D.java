/**
 *
 */
public class Vec2D  extends Point{
    public static Vec2D NULLVEC=new Vec2D(0,0);
    private double x,y,len,arg;
    private boolean isNULLVEC=false;
    protected Vec2D(double x,double y,double len,double arg){
        super(x*len,y*len);
        this.x=x;this.y=y;
        this.len=len;this.arg=arg;
    }
    public Vec2D(double a,double b){
        super(a,b);
        if(a==0&&b==0){
            a=b=len=arg=0;
            isNULLVEC=true;
            return;
        }
        init(a,b);
    }
    public Point asPoint(){
        return new Point(x*len,y*len);
    }
    private void init(double a,double b){
        len=Math.sqrt(x*x+y*y);
        x=a/len;
        y=b/len;
        arg=cos_angle();
    }
    private double cos_angle(){
        return Math.acos(x);    // x normiert
    }
    public double getLen(){
        return len;
    }
    public double angle(){
        return arg;
    }
    public Vec2D clone(){
        if(isNULLVEC)
            return NULLVEC;
        return new Vec2D(x,y,len,arg);
    }

    public void multiplyComplx(Vec2D b){
        if(isNULLVEC||b.isNULLVEC){
            isNULLVEC=true;
            x=y=len=arg=0;
            return;
        }
        arg=arg+b.arg;
        while(arg>Math.PI*2)
            arg=arg-Math.PI*2;
        x=Math.cos(arg);
        y=Math.sin(arg);
    }
    public void multiplyScal(double b){
        if(b==0){
            isNULLVEC=true;
            x=y=len=arg=0;
            return;
        }
        if(b<0){
            b=Math.abs(b);
            arg=arg*-1;
            x=-x;
            y=-y;
        }
        len=len*b;
    }
    public void subtract(Vec2D b){
        if(x*len==b.x*b.len&&y*len==b.y*b.len){
            isNULLVEC=true;
            y=x=arg=len=0;
        }
        init(x*len-b.x*b.len,y*len-b.y*b.len);
    }
    public boolean inLineWith(Vec2D b){
        if(isNULLVEC||b.isNULLVEC)
            return false;
        double alpha=b.x/x;
        return alpha*x==b.y;
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
