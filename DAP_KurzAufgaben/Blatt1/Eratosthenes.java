/**
 * Created by basti on 21.04.17.
 */
public class Eratosthenes {
    private final boolean[] isNotPrime_Array; // Inverted damit weniger initialisierung! Nutze Access Methoden!
    private int count=0;
    public Eratosthenes(int n){
        if(n<2)
            throw new IllegalStateException("n < 2 => No primes found!");
        isNotPrime_Array=new boolean[n];
        for(int i = 2;i!=n;i++){
            if(isPrime(i)){
                count++;
                for(int t = 2*i;t<n;t=t+i)
                    clearPrimeFlag(t);
            }
        }
    }
    public int countPrimes(){
        return count;
    }
    public void show(){
        System.out.println("Primes : \n");
        for(int i = 2;i!=isNotPrime_Array.length;i++)
            if(isPrime(i))
                System.out.print(i+" ");
        System.out.println();
    }
    public boolean isPrime(int i){
        if(i<=isNotPrime_Array.length&&i>0)
            return !isNotPrime_Array[i-1];
        else
            throw new IllegalStateException("Index out of Bounds");
    }
    private void clearPrimeFlag(int i){
        if(i<=isNotPrime_Array.length&&i>0)
            isNotPrime_Array[i - 1] = true;
        else
            throw new IllegalStateException("Index out of Bounds");
    }



    public static void main(String...args){
        int n = 0;
        boolean outputflag=false;
        if(args==null||args.length==0||args.length>2){
            System.out.println("Parameter invalid!");
            System.out.println("nutze 'Erathosthenes n [-o]'");
            System.out.println("mit n Zahl größer 1 und -o optinaler Parameter für die Ausgabe der Primzahlen");
            return;
        }else{
            try{
                n=Integer.parseInt(args[0]);
            }catch (Exception e){
                System.out.println("Fehler : Parameter ist keine Natürliche Zahl!");
            }
        }
        if(args.length==2)
            if(!args[1].equals("-o")){
                System.out.println("Fehler : "+args[1]+" ist nicht als Flagge definiert!");
                return;
            } else outputflag=true;
        if(n<=1){
            System.out.println("Fehler : n ist kleiner als 2");
            return;
        }
        // Alle Parameter sind nun valid
        Eratosthenes eratosthenes=new Eratosthenes(n);
        System.out.println("Es gibt : "+eratosthenes.countPrimes()+" Primzahlen zwischen : 2 bis "+n);
        if(outputflag)
            eratosthenes.show();

    }
}
