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
        return null;
    }
    public static int[] latenessScheduling(ArrayList<Job> jobs){
        return null;
    }
    public static RandomAccessFile raf;
    public static void main(String[] args) throws IOException{
        if(args.length!=2)return;
        boolean INTERVAL=args[0].equalsIgnoreCase("interval");
        if(!INTERVAL&&!args[0].equalsIgnoreCase("lateness"))return;//Kein Intervall und lateness!
        try{
            raf=new RandomAccessFile(args[1],"rw");
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        List<Interval> list = new LinkedList<>();
        for(String s=raf.readLine();s!=null;s=raf.readLine()) {
            Interval it = tokenize(s);//s ist niemals null
            if(it==null) return;
            else list.add(it);
        }
        if(list.size()==0) {
            System.out.println("Keine Paare zur bearbeitung in der Eingabe!");
            return;
        }

        if(INTERVAL) interalScheduling(new ArrayList<>(list));
        else latenessScheduling(new ArrayList<>(createJoblist(list)));

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
            System.out.println("Zeile besitzt nicht genügent Tokens!");
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
}
