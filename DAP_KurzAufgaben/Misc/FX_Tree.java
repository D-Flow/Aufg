import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.function.Function;

public class FX_Tree {
    private FX_Tree left = null, right = null;
    private Text base_text;
    private boolean create_T = true;
    int root = 0;

    public FX_Tree(int value) {
        root = value;
    }

    public void link(FX_Tree l, FX_Tree r) {
        left = l;
        right = r;
    }

    public void setLeft(FX_Tree l) {
        left = l;
    }

    public void setRight(FX_Tree r) {
        right = r;
    }

    public double baseY = 50, left_lim = 20, right_lim = 1000, baseX = right_lim / 2;

    public void apply_FX(Group grp) {
        if (base_text == null) {
            base_text = new Text(20, 20, "");
            grp.getChildren().add(base_text);
        }
        Circle c = new Circle(baseX, baseY, 20);
        c.setStroke(Color.BLACK);
        c.setOnMouseEntered((a) -> {
            base_text.setText("Value : " + root);
        });

        if (left != null) {
            left.baseX = Math.abs(baseX - left_lim) / 2 + left_lim;
            left.base_text = base_text;
            left.baseY = baseY + 100;
            left.right_lim = baseX;
            left.left_lim = left_lim;
        }
        if (right != null) {
            right.baseX = Math.abs(right_lim - baseX) / 2 + baseX;
            right.left_lim = baseX;
            right.right_lim = right_lim;
            right.base_text = base_text;
            right.baseY = baseY + 100;
        }
        if (left != null) {
            Line l = new Line(baseX, baseY, left.baseX, left.baseY);
            grp.getChildren().add(l);
            left.apply_FX(grp);
        }
        if (right != null) {
            Line l = new Line(baseX, baseY, right.baseX, right.baseY);
            grp.getChildren().add(l);
            right.apply_FX(grp);
        }
        c.setFill(Color.WHITE);
        grp.getChildren().add(c);
    }

    public static <T> FX_Tree createFromTree(T root, Function<T, T> left, Function<T, T> right, Function<T, Integer> value) {
        if (root == null) return null;

        FX_Tree tree = new FX_Tree(value.apply(root));
        tree.link(createFromTree(left.apply(root), left, right, value), createFromTree(right.apply(root), left, right, value));
        return tree;
    }
}
