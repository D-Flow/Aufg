/**
 *
 */
public class Eratosthenes {
    private final boolean[] isPrime_Array;
    // es gilt: isPrime_Array[LENGHT-1] steht für die zahl n... somit isPrime_Array[0] für 1
    // die Zugriffsberechnung machen die Methoden(isPrime(int),clearPrimeFlag(int))

    private int count = 0;    //Anzahl der Primzahlen

    public Eratosthenes(int n){

        isPrime_Array = new boolean[n];    //Siehe Aufgabe; da 'invertiert' sind alles Primzahlen
        for (int i = 0; i < n; i++)
            isPrime_Array[i] = true;      //Alle Zahlen sind Primzahlen;'Array ist true'

        for (int i = 2; i <= n; i++) {        //Sieb des Erathosthenes;alle zahlen von 2 bis inklusive n
            if(isPrime(i)){
                count++;
                for (int t = 2 * i; t <= n; t = t + i)  //Vielfache von i als nicht Primzahl makieren
                    clearPrimeFlag(t);  //t ist keine primzahl da vielfaches von i
            }
        }
    }

    public int countPrimes(){
        return count;
    }

    public void show(){
        //Ausgabe für alle i die Primzahlen sind
        System.out.println("Primzahlen : \n");
        // alle zahlen zwischen 2 und n+1 also von 2 bis inklusive n
        for (int i = 2; i <= isPrime_Array.length; i++)
            if(isPrime(i))
                System.out.print(i+" ");
        System.out.println();
    }

    public boolean isPrime(int i){
        // Es gilt arr[i-1] ist für die Zahl i da arrays von 0 bis n-1 definiert => müssen von i = 1 bis i = n zugreifen
        if (i <= isPrime_Array.length && i > 0)
            return isPrime_Array[i - 1];
        else
            throw new IllegalStateException("Index out of Bounds");
    }

    private void clearPrimeFlag(int i){
        // siehe isPrime; makiert die zahl i als nicht Primzahl
        if (i <= isPrime_Array.length && i > 0)
            isPrime_Array[i - 1] = false;
        else
            throw new IllegalStateException("Index out of Bounds");
    }



    public static void main(String...args){
        int n = 0;
        boolean outputflag=false;
        // Grobe Überprüfung von Parametern (Anzahl)
        if(args==null||args.length==0||args.length>2){
            System.out.println("Parameter invalid!");
            System.out.println("Nutze 'Erathosthenes n [-o]'");
            System.out.println("Mit n Zahl größer 1 und -o optinaler Parameter für die Ausgabe der Primzahlen");
            return; //Parameter Anzahl falsch
        }else{
            try{
                //Ersten Parameter 'versuchen' in eine Zahl zu casten
                n=Integer.parseInt(args[0]);
            }catch (Exception e){
                System.out.println("Fehler : Parameter ist keine Natürliche Zahl!");
                return;
            }
        }
        // falls es 2 Parameter gibt kann dies nur die output flag sein
        if(args.length==2)
            if(!args[1].equals("-o")){  // Fehler falls dies nicht -o ist
                System.out.println("Fehler : "+args[1]+" ist nicht als Flagge definiert!");
                return;
            } else outputflag=true;
        if(n<=1){
            System.out.println("Fehler : n ist kleiner als 2");
            return;
        }

        // Alle Parameter sind nun valid
        Eratosthenes eratosthenes=new Eratosthenes(n);
        System.out.println("Es gibt : " + eratosthenes.countPrimes() + " Primzahlen zwischen : 2 bis (inklusive)" + n);
        if(outputflag)
            eratosthenes.show();

    }
}
