package SyncUtil;

/**
 * @author: gnixgnohuh
 * @date : 18-3-16
 * @time : 上午10:51
 * @desc : 生产者－消费者
 */
public class Resource {

    private int count = 0;
    private static final int SIZE = 10;


    public synchronized void add() {
        if (count < SIZE) {
            count++;
            notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void remove() {
        if (count > 0) {
            count--;
            notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
