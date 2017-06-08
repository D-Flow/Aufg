import java.util.ArrayList;

public class CharChange {

    char a, b;
    boolean replace = false, delete = false, add = false;
    int index;

    public CharChange(int i, char a, char b) {
        index = i;
        replace = true;
        this.a = a;
        this.b = b;
    }

    public void increment() {
        index++;
    }

    public void decrement() {
        index--;
    }

    public CharChange(int i, char a, boolean delete) {
        index = i;
        this.a = a;
        if (!delete) add = true;
        else this.delete = true;
    }

    //Pointer advancement, n heißt das die nächsten n Zeichen übersprugen werden können.
    //Also beim löschen keine, da das nächste das gleiche ist
    public int apply(ArrayList<Character> list) {
        if (replace)
            if (list.size() >= index) {
                list.set(index - 1, b);
                return 1;
            } else {
                list.add(index - 1, b);
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
        return 0;
    }

    public String toString() {
        if (replace) return "Ersetze Zeichen@" + index + " : '" + a + "' -> '" + b + "'";
        if (delete) return "Lösche Zeichen@" + index + " '" + a + "'";
        if (add) return "Füge '" + a + "' als Zeichen@" + index + " an";
        return "F";
    }
}
