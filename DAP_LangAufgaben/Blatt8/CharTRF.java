import java.util.ArrayList;

//Zeichen Transformation
public class CharTRF {

    private char a, b;
    protected boolean replace = false, delete = false, add = false, keep = false;
    protected int index;

    public CharTRF(int i, char a, char b) {
        index = i;
        replace = a != b;
        keep = !replace;
        this.a = a;
        this.b = b;
    }

    public void ShiftIndex(int i) {
        index += i;
    }

    public CharTRF(int i, char a, boolean delete) {
        index = i;
        this.a = a;
        if (!delete) add = true;
        else this.delete = true;
    }

    //Pointer advancement, n heißt das die nächsten n Zeichen übersprugen werden können.
    //Also beim löschen keine, da das nächste das gleiche ist
    // i -> i-1, also list beginnt bei 0
    public int apply(ArrayList<Character> list) {
        if (replace)
            if (list.size() >= index) {
                assert list.get(index - 1) == a;
                list.set(index - 1, b);//Ersetzungsziel existiert 'wahre' ersetzung
                return 1;
            } else {
                list.add(index - 1, b);//Ersetze ''
                return 1;
            }
        if (delete && list.size() >= index) {
            list.remove(index - 1);
            return 0;
        }
        if (add) {
            list.add(index - 1, a);
            return 1;
        }
        return 1;   //keep
    }


    public String toString() {
        if (replace) return "Ersetze Zeichen@" + index + " : '" + a + "' -> '" + b + "'";
        if (delete) return "Lösche Zeichen@" + index + " '" + a + "'";
        if (add) return "Füge '" + a + "' als Zeichen@" + index + " an";
        if (keep) return "Übernehme '" + a + "' als Zeichen@" + index;
        return "FFFF";
    }
}
