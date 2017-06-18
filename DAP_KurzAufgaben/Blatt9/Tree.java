public class Tree {
    public Tree(int a) {
        value = a;
    }

    boolean empty = false;

    public Tree() {
        empty = true;
    }

    int value;
    Tree leftChild = null, rightChild = null;

    public void add(int b) {
        if (empty) {
            value = b;
            empty = false;
            return;
        }
        if (b <= value)
            if (leftChild != null) leftChild.add(b);
            else leftChild = new Tree(b);
        else if (rightChild != null) rightChild.add(b);
        else rightChild = new Tree(b);
    }

    public void inOrder() {
        if (leftChild != null) leftChild.inOrder();
        if (!empty) System.out.print(value + " ");
        if (rightChild != null) rightChild.inOrder();
    }

    public void preOrder() {
        if (!empty) System.out.print(value + " ");
        if (leftChild != null) leftChild.preOrder();
        if (rightChild != null) rightChild.preOrder();
    }

    public void postOrder() {
        if (leftChild != null) leftChild.postOrder();
        if (rightChild != null) rightChild.postOrder();
        if (!empty) System.out.print(value + " ");
    }

    public static void main(String... args) throws Exception {
        Tree tree = new Tree();
        for (int i = 0; i < args.length; i++) {
            tree.add(Integer.parseInt(args[i]));
            drawTree(tree);
        }
        System.out.print("Inorder : ");
        tree.inOrder();
        System.out.print("\nPreorder : ");
        tree.preOrder();
        System.out.print("\nPostorder : ");
        tree.postOrder();
        System.out.println();
    }

    static int n = 0;
    public static void drawTree(Tree t) throws Exception {
        FX_Tree ftree = FX_Tree.createFrom(t);
        if (ftree != null) FX_Wrapper.waitForWrapper().applyGroupTo(ftree::apply_FX, n);
        n++;
    }

}
