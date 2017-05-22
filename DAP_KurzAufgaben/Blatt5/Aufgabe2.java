import java.util.Arrays;

public class Aufgabe2
{
    static int[] w;
    public static void main(String[] args)
    {
        if(args==null||args.length>3||args.length==0)
        {
            System.out.println("Falsche Parameter uebergabe");
        }
        else
        {
            if(args[0].equals("Euro"))
            {
                w= new int[]{200,100,50,20,10,5,2,1};
            }
            if(args[0].equals("Alternative"))
            {
                w= new int[]{200,100,50,20,10,5,4,2,1};
            }
            int[] z = change(Integer.parseInt(args[1]),w);
            for(int i = 0;i<z.length;i++)
                System.out.print(z[i]+" ");
            System.out.println();
        }
    }

    public static int[] change(int b, int[] w)
    {
        int[] z= new int[w.length];
        for(int i=0;b>0;i++)
        {
            if(b>=w[i])
            {
                z[i]=b/w[i];
                b=b-z[i]*w[i];
            }
        }
        return z;
    }
}