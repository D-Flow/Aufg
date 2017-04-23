/**
 *
 */
public class Euclid {
    public static void main(String...args){
        int a,b=0;
        //Überprüfung der Parameter anzahl
        if(args==null||args.length!=2){
            System.out.println("Fehler: Zwei Positive Zahlen erwartet!");
            return;
        }
        try{
            //Versuche ersten beiden Parameter in Zahlen zu casten
            a=Integer.parseInt(args[0]);
            b=Integer.parseInt(args[1]);
            //Siehe Aufg. a,b müssen Natürliche Zahlen sein
            if(a<=0||b<=0){
                System.out.println("Fehler: Positive Zahlen erwartet!");
                return;
            }
        }catch (Exception e){
            System.out.println("Fehler: Min. ein Parameter ist keine Natürliche Zahl!");
            return;
        }

        System.out.println("GGT ist : "+euclid(a,b));
    }

    //Siehe Aufgabe; Euklidischer Algoritmus mit ggt(a,b)=ggt(b, a mod b) bis rest=0
    public static int euclid(int a,int b){
        if(b==0)
            return a;
        else
            return euclid(b,a%b);
    }
}
