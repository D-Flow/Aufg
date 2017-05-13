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
                arr[i]=new Point(Double.parseDouble(ar[i]),Double.parseDouble(ar[i+1]));
        Triangle dreik=new Triangle(arr);
        if(!dreik.validate())
            System.out.println("Dreieck invalid");
        else
            System.out.println("Umfang : "+dreik.perimeter());
    }
    public static double genDouble(double limit){
        Random num=new Random();
        return num.nextDouble()*limit*(num.nextBoolean()?1:-1);
    }
}
