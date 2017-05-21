/**
 *
 */
public class Job {
    private int dauer,deadline;
    public Job(int a,int b){
        dauer=a;
        deadline=b;
        if(dauer>deadline) throw new IllegalArgumentException("Dauer größer als Deadline");
    }
    public int getDuration(){return dauer;}
    public int getDeadline(){return deadline;}
    public String toString(){return "["+dauer+", "+deadline+"]";}
    public int getDelay(int finish){return deadline<=finish?0:finish-deadline;}
    public int getDelayFromStart(int start){return getDelay(start+getDuration());}
}
