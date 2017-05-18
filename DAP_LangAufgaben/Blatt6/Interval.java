/**
 *
 */
public class Interval {
    private int start,end;
    public Interval(int start,int end){
        if(end<start)throw new IllegalArgumentException("Endpunkt unter Start");
        this.start=start;
        this.end=end;
    }
    public int getStart(){return start;}
    public int getEnd(){return end;}
    public String toString(){return "["+start+", "+end+"]";}
}
