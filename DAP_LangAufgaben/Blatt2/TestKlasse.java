/**
 * Nicht zum Vorstellen gedacht!
 * Klasse um Durchschnittszeiten zu messen
 * Keine Parameter checks
 */
public class TestKlasse {
    public static void main(String[] args)throws Exception{
        System.out.println("Nutze 'TestKlasse ID ARRAY_SIZE RUNS' mit ID=0 für MergeSort, ID!=0 für Insertion");
        int sortID=Integer.parseInt(args[0]);
        int amount=Integer.parseInt(args[1]);
        int runs=Integer.parseInt(args[2]);
        if(sortID==0) {
            System.out.println("Mergesort mit : " + amount + " Elementen und " + runs + " Durchläufen:");
            System.out.println("Durchschnittszeit : " + testMergeSort(amount, runs)+"ms");
        }else{
            System.out.println("Insertionsort mit : " + amount + " Elementen und " + runs + " Durchläufen:");
            System.out.println("Durchschnittszeit : " + testInsertSort(amount, runs)+"ms");
        }
    }
    public static long testMergeSort(int amt,int runs){
        if(runs<=0)
            return -1;
        long time = 0;
        for(int i = 0;i<runs;i++){
            int[] array = Sortierung.fillRandom(amt);
            long start=System.currentTimeMillis();
            Sortierung.mergeSort(array);
            long end = System.currentTimeMillis();
            if(!Sortierung.isSorted(array))
                throw new IllegalStateException("Nicht Sortiert!");
            else
                time+=(end-start);
        }
        return time/runs;
    }
    public static long testInsertSort(int amt,int runs){
        if(runs<=0)
            return -1;
        long time = 0;
        for(int i = 0;i<runs;i++){
            int[] array = Sortierung.fillRandom(amt);
            long start=System.currentTimeMillis();
            Sortierung.insertionSort(array);
            long end = System.currentTimeMillis();
            if(!Sortierung.isSorted(array))
                throw new IllegalStateException("Nicht Sortiert!");
            else
                time+=(end-start);
        }
        return time/runs;
    }
}
