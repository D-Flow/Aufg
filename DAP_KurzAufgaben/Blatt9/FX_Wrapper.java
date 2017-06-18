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

    static {
        initApplication();
    }

    public static FX_Wrapper waitForWrapper() throws Exception {
        if (wrapper == null) {
            wrplock.lock();
            if (wrapper == null)
                wrp_ready.await();
            wrplock.unlock();
        }
        return wrapper;
    }

    public static void initApplication() {
        Thread tr = new Thread(() -> {
            Application.launch(FX_Wrapper.class);
        });
        tr.setName("FX_THREAD");
        tr.start();
    }

    //Tests
    public static void main(String[] args) throws Exception {

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
        switchlock.lock();
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
        l.lock();
        Platform.runLater(() -> {
            l.lock();
            synchronized (grp) {
                c.accept(grp);
            }
            cc.signal();
            l.unlock();
        });
        cc.await();
        l.unlock();
        unblockSwitching();
    }

}
