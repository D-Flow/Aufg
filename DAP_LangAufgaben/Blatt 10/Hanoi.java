public class Hanoi 
{
    public static void main(String... args) throws Exception
    {
        int n = Integer.parseInt(args[0]);
        move(n,'A','B','C');
    }

    public static void move(int quantity, char start, char help, char target)
    {
        if(quantity==1)
        {
            System.out.println("Verschiebe oberste Scheibe von "+start+" nach "+target);
        }
        else
        {
            move(quantity-1,start,target,help);
            System.out.println("Verschiebe oberste Scheibe von "+start+" nach "+target);
            move(quantity-1,help,start,target);
        }
    }

}
