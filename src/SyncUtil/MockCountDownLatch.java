package SyncUtil;

/**
 * @author: gnixgnohuh
 * @date : 18-3-19
 * @time : 下午2:46
 * @desc :
 */
class MockCountDownLatch {
    private final Object lock = new Object();

    private int num;

    MockCountDownLatch(int num) {
        this.num = num;
    }

    void await() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
    }

    void countDown() {
        synchronized (lock) {
            num--;
            if (num == 0) {
                lock.notifyAll();
            }
        }
    }
}
