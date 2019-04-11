package SyncUtil;

import threadpool.BlockingThreadPoolExecutor;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: gnixgnohuh
 * @date : 18-1-16
 * @time : 上午11:09
 * @desc : CyclicBarrier实现对多个线程的屏障
 */
public class MyCyclicBarrier {
    public static void main(String[] args) {
        final ExecutorService pool = new BlockingThreadPoolExecutor().getBlockingThreadPoolExecutor();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("各个任务都执行完毕，屏障取消");
            pool.shutdownNow();
        });
        Runnable runnable = null;
        for (int i = 0; i < 3; i++) {
            runnable = () -> {
                System.out.println("线程" + Thread.currentThread().getName() + "开始运行");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("线程" + Thread.currentThread().getName() + "执行完毕，处于等待状态");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            };
            pool.execute(runnable);
        }
    }
}
