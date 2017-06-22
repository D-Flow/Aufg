import java.util.Random;

/**
 *
 */
public class Application {
    public static void main(String ...ar){
        if(ar.length!=6&&ar.length!=0)
            return;

        Point[] arr=new Point[3];
        if(ar.length==0)
            for(int i = 0;i<arr.length;i++)
                arr[i]=new Point(genDouble(1000),genDouble(1000));
        else
            for(int i = 0;i<arr.length;i++)
                arr[i]=new Point(Double.parseDouble(ar[2*i]),Double.parseDouble(ar[2*i+1]));

        Triangle dreieck=new Triangle(arr);
        if(!dreieck.validate())
            System.out.println("Dreieck invalid");
        else
            System.out.println("Umfang : "+dreieck.perimeter());
    }
    static Random num=new Random();
    public static double genDouble(double limit){
        return num.nextDouble()*limit*(num.nextBoolean()?1:-1);
    }
}
