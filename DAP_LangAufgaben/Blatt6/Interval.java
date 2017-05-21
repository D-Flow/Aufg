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

    //Symmetrisch, also a~b => b~a
    public boolean compatible(Interval b){
        if(getStart()<=b.getStart())    //wenn a vor b startet muss a enden bevor b startet
            return getEnd()<=b.getStart();
        else
            return b.compatible(this);
    }
    public String toString(){return "["+start+", "+end+"]";}
}
