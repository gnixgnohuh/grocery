package SyncUtil;

import threadpool.BlockingThreadPoolExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author: gnixgnohuh
 * @date : 18-1-16
 * @time : 上午10:46
 * @desc : Semaphore相当于一把锁，可以实现对资源池的管理，一把锁管理多个资源，
 * 如果资源都在占用状态，再通过acquire()申请就会阻塞线程，直到某一个资
 * 源release()
 */
public class MySemaphore {
    public static void main(String[] args) {
        final ExecutorService pool = new BlockingThreadPoolExecutor().getBlockingThreadPoolExecutor();
        final Semaphore semaphore = new Semaphore(3);
        Runnable runnable = null;
        for (int i = 0; i < 10; i++) {
            runnable = () -> {
                try {
                    semaphore.acquire();
                    System.out.println("线程" + Thread.currentThread().getName() + "执行，当前剩余资源数为" + semaphore.availablePermits());
                    TimeUnit.SECONDS.sleep(3);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            pool.execute(runnable);
        }
    }
}
