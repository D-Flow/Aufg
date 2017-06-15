//LOCK AWAY NOTHING TO SEE HERE
public class TREANT {
    int v;
    TREANT l, r;

    TREANT(int v) {
        this.v = v;
        l = r = null;
    }

    public int getV() {
        return v;
    }

    public TREANT getL() {
        return l;
    }

    public TREANT getR() {
        return r;
    }

    public void add(int z) {
        if (z <= v) {
            if (l == null)
                l = new TREANT(z);
            else
                l.add(z);
        } else {
            if (r == null)
                r = new TREANT(z);
            else
                r.add(z);
        }
    }
}
