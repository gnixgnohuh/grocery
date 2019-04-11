package SyncUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author: gnixgnohuh
 * @date : 18-3-14
 * @time : 上午9:43
 * @desc :
 */
public class StaticTest {

    public synchronized static void doBus1() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("doBus1");
    }

    public synchronized static void doBus0() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("doBus0");
    }

    public static void main(String[] args) {
        new Thread(() -> {
            doBus1();
        }).start();
//        StaticTest staticTest1 = new StaticTest();
        doBus0();
    }
}
