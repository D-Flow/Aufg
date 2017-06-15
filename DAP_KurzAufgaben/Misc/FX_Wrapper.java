import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class FX_Wrapper extends Application {
    public static FX_Wrapper wrapper;
    private static Lock wrplock = new ReentrantLock();
    private static Condition wrp_ready = wrplock.newCondition();

    public static FX_Wrapper waitForWrapper() throws Exception {
        if (wrapper == null) {
            wrplock.lock();
            wrp_ready.await();
            wrplock.unlock();
        }
        return wrapper;
    }

    //Tests
    public static void main(String[] args) throws Exception {
        Thread tr = new Thread(() -> {
            Application.launch(args);
        });
        tr.setName("FX_THREAD");
        tr.start();
        int[] a = new int[100], b = new int[100];
        for (int i = 0; i < 100; i++) {
            a[i] = i == 0 ? 5 : (int) ((1.2D) * a[i - 1]);
            b[i] = (int) (Math.random() * 5000);
            System.out.println(i + "Y = " + b[i]);
        }
        FX_Plott plott = new FX_Plott(a, b, "LISTEN");
        waitForWrapper().applyGroup(plott::add);
        for (int i = 0; i < 100; i++) {
            a[i] = i;
            b[i] = (int) (Math.random() * 5000);
            System.out.println(i + "Y = " + b[i]);
        }
        plott = new FX_Plott(a, b, "HEY");
        waitForWrapper().applyGroupTo(plott::add, 1);

        TREANT t = new TREANT((int) (Math.random() * 10000));
        for (int i = 0; i < 36; i++)
            t.add((int) (Math.random() * 10000));
        FX_Tree ffx = FX_Tree.createFromTree(t, TREANT::getL, TREANT::getR, TREANT::getV);
        waitForWrapper().applyGroupTo(ffx::apply_FX, 4);
    }

    private Group grp = new Group();
    private Stage stage = null;
    private int curren_grp = 0;
    private Map<Integer, Group> groupMap = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        wrapper = this;
        this.stage = stage;
        stage.setScene(new Scene(grp));
        groupMap.put(0, grp);
        stage.getScene().setOnKeyPressed((a) -> {
            try {
                int n, m;
                if (a.getCode().isArrowKey()) {
                    n = curren_grp + (a.getCode() == KeyCode.UP ? 1 : 0) + (a.getCode() == KeyCode.DOWN ? -1 : 0);
                    m = curren_grp + (a.getCode() == KeyCode.RIGHT ? 1 : 0) + (a.getCode() == KeyCode.LEFT ? -1 : 0);
                    if (n == curren_grp)
                        n = m;
                } else
                    n = Integer.parseUnsignedInt(a.getText(), 16);
                if (n == curren_grp || n < 0 || n > 15) return;
                semaphore.acquire();
                synchronized (groupMap) {
                    System.out.println("Loading Scene : " + n);
                    if (groupMap.containsKey(n))
                        grp = groupMap.get(n);
                    else {
                        grp = new Group();
                        groupMap.put(n, new Group());
                    }
                    curren_grp = n;
                    stage.getScene().setRoot(grp);
                    stage.getScene().getRoot().getStyleClass().clear();
                }
                semaphore.release();
            } catch (Exception e) {
            }
        });
        stage.setTitle("LOOK DOWN");
        stage.setHeight(800);
        stage.setWidth(800);
        wrplock.lock();
        wrp_ready.signalAll();
        wrplock.unlock();
        stage.show();
    }

    public final static int baseline = 700, x_limit = 800;

    Lock switchlock = new ReentrantLock();
    Semaphore semaphore = new Semaphore(1);

    private synchronized void switchGroup(int n) throws Exception {
        semaphore.acquire();
        if (curren_grp == n) return;
        Condition c = switchlock.newCondition();
        Platform.runLater(() -> {
            synchronized (groupMap) {
                switchlock.lock();
                if (groupMap.containsKey(n))
                    grp = groupMap.get(n);
                else {
                    grp = new Group();
                    groupMap.put(n, grp);
                }
                curren_grp = n;
                stage.getScene().getRoot().getStyleClass().clear();
                stage.getScene().setRoot(grp);
                c.signal();
                switchlock.unlock();
            }
        });
        switchlock.lock();
        c.await();
        switchlock.unlock();
    }

    private void unblockSwitching() throws Exception {
        semaphore.release();
    }

    public void applyGroup(Consumer<Group> c) throws Exception {
        applyGroupTo(c, curren_grp);
    }

    private Lock l = new ReentrantLock();
    private Condition cc = l.newCondition();

    public void applyGroupTo(Consumer<Group> c, int grpid) throws Exception {
        System.out.println("Switching to : " + grpid);
        switchGroup(grpid);

        Platform.runLater(() -> {
            l.lock();
            synchronized (grp) {
                c.accept(grp);
            }
            cc.signal();
            l.unlock();
        });
        l.lock();
        cc.await();
        l.unlock();
        unblockSwitching();
    }
}
