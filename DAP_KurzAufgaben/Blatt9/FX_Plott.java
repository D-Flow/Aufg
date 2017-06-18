import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class FX_Plott {
    private final long[] x, y;
    private Text base_text = null, name_text = null;
    private String name;

    public FX_Plott(long[] index, long[] raw_values, String plot_name) {
        x = index;
        y = raw_values;
        name = plot_name;
    }

    public FX_Plott(Long[] a, Long[] b, String n) {
        name = n;
        x = new long[a.length];
        y = new long[b.length];
        for (int i = 0; i < a.length; i++) {
            x[i] = a[i];
            y[i] = b[i];
        }
    }

    public void add(Group grp) {
        if (x.length != y.length)
            throw new IllegalStateException("lenght not equal");
        int max = 0;
        for (int i = 0; i < x.length; i++)
            if (y[max] < y[i])
                max = i;
        long maxV = y[max];
        this.name += " Highest Value : " + maxV + " @Index : " + x[max];
        double scale_factor = (double) (FX_Wrapper.baseline) / maxV;
        for (int i = 0; i < y.length; i++)
            addBlock(grp, ((double) y[i]) * scale_factor, "" + x[i], y[i]);
    }

    private int blx = 0, bly = 0;
    private static ArrayList<Long> A = new ArrayList<>(), B = new ArrayList<>();

    public static void addPoint(long x, long y) {
        A.add(x);
        B.add(y);
    }

    static int n = 0;

    public synchronized static void create_FX_Plott(String name) throws Exception {
        FX_Plott pl = new FX_Plott(A.toArray(new Long[0]), B.toArray(new Long[0]), name);
        A.clear();
        B.clear();
        FX_Wrapper.waitForWrapper().applyGroupTo(pl::add, n);
        n++;
    }

    private synchronized void addBlock(Group grp, double amt, String name, long unscaled) {
        if (base_text == null) {
            base_text = new Text(0, FX_Wrapper.baseline + 30 + 50, "");
            grp.getChildren().add(base_text);
        }
        if (name_text == null) {
            name_text = new Text(20, 20, this.name);
            grp.getChildren().add(name_text);
        }
        Rectangle r = new Rectangle(blx, FX_Wrapper.baseline - amt + 50, 15, amt);
        r.setOnMouseEntered(((a) -> {
            synchronized (base_text) {
                base_text.setText(unscaled + "");
            }
        }));
        Text t = new Text(r.getX(), r.getY() + r.getHeight() + 10, name);
        if (t.minWidth(-1) > 15)
            r.setWidth(t.minWidth(-1) + 2);
        grp.getChildren().add(r);
        grp.getChildren().add(t);
        blx += r.getWidth() + 5;
    }
}