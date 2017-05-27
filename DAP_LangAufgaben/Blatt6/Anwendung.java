import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 */
public class Anwendung {
    public static ArrayList<Interval> interalScheduling(ArrayList<Interval> intervals){
        ArrayList<Interval> list = new ArrayList<>(intervals.size());
        int n = intervals.size() - 1;
        list.add(intervals.get(0));
        int j = 0;
        for (int i = 1; i <= n; i++) {
            int startOfNext = intervals.get(i).getStart();
            int endOfLast = list.get(list.size() - 1).getEnd();
            if (startOfNext >= endOfLast) {
                list.add(intervals.get(i));
                j = i;
            }
        }
        return list;
    }
    public static int[] latenessScheduling(ArrayList<Job> jobs){
        int[] arr = new int[jobs.size()];
        int n = arr.length - 1;
        int st = 0;
        for (int i = 0; i <= n; i++) {
            arr[i] = st;
            st += jobs.get(i).getDuration();
        }
        return arr;
    }

    public static RandomAccessFile raf;
    public static void main(String[] args) throws IOException{
        if(args.length!=2)return;
        boolean INTERVAL=args[0].equalsIgnoreCase("interval");
        if(!INTERVAL&&!args[0].equalsIgnoreCase("lateness"))return;//Kein Intervall und lateness!

        raf=new RandomAccessFile(args[1],"rw");

        List<Interval> intervalList = new LinkedList<>();
        for(String s=raf.readLine();s!=null;s=raf.readLine()) {
            Interval it = tokenize(s);//s ist niemals null
            if(it==null) return;
            else intervalList.add(it);
        }
        raf.close();
        if(intervalList.size()==0) {
            System.out.println("Keine Paare zur bearbeitung in der Eingabe!");
            return;
        }
        System.out.println("Interval raw : ");
        print(intervalList.toArray(new Interval[0]));

        if(INTERVAL) {
            sortIntervals(intervalList);
            System.out.println("Sortiert : ");
            print(intervalList.toArray(new Interval[0]));

            ArrayList<Interval> scheduledI = interalScheduling(new ArrayList<>(intervalList));
            System.out.println("Folge Intervalle wurden ausgewählt : \n"+scheduledI.toString());
        }else {
            ArrayList<Job> jobArrayList=new ArrayList<>(createJoblist(intervalList));
            sortJobs(jobArrayList);

            System.out.println("Sortiert : ");
            print(jobArrayList.toArray(new Job[0]));

            int[] startT = latenessScheduling(jobArrayList);
            System.out.println("Lateness Scheduling : ");
            print(startT);

            System.out.println("Maximale Verspätung ist : "+maxDelay(startT,jobArrayList));
        }

    }
    public static void sortIntervals(List<Interval> intervalList){intervalList.sort((Interval a,Interval b)->{return a.getEnd()-b.getEnd();});}
    public static void sortJobs(List<Job> jobList){jobList.sort((A,B)->A.getDeadline()-B.getDeadline());}
    public static int maxDelay(int[] arr,List<Job> jobList){
        int max = 0;
        //der job i startet bei arr[i] und endet in arr[i]+joblänge
        for(int i = 0;i<jobList.size();i++){
            Job job=jobList.get(i);
            if(job.getDelayFromStart(arr[i])>max)
                max=job.getDelayFromStart(arr[i]);
        }
        return max;
    }
    public static List<Job> createJoblist(List<Interval> list){
        List<Job> joblist=new LinkedList<Job>();
        for(Interval interval : list)
            joblist.add(new Job(interval.getStart(),interval.getEnd()));
        return joblist;
    }
    public static Interval tokenize(String s){
        StringTokenizer tokenizer=new StringTokenizer(s,",");
        if(tokenizer.countTokens()<2) {
            System.out.println("Zeile besitzt nicht genug Tokens!");
            return null;
        }
        try {
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            return new Interval(a,b);
        }catch (Exception e){
            System.out.println("Min. eine Eingabe ist keine Zahl!");
        }
        return null;
    }

    public static void print(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length - 1; i++)
            System.out.print(arr[i] + ", ");
        System.out.println(arr[arr.length - 1] + "]");
    }

    public static <T> void print(T[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length - 1; i++)
            System.out.print(arr[i] + ", ");
        System.out.println(arr[arr.length - 1] + "]");
    }
}
