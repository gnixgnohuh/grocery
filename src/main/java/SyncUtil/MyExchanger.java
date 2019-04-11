package SyncUtil;

import threadpool.BlockingThreadPoolExecutor;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: gnixgnohuh
 * @date : 18-1-16
 * @time : 上午11:29
 * @desc : 两个线程之间交换数据，需要两个线程互相等待
 */
public class MyExchanger {
    public static void main(String[] args) {
        final ExecutorService pool = new BlockingThreadPoolExecutor().getBlockingThreadPoolExecutor();
        final Exchanger<String> exchanger = new Exchanger<>();
        Runnable runnable0 = () -> {
            String data0 = "abc";
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(data0 + "->" + exchanger.exchange(data0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        pool.execute(runnable0);
        Runnable runnable1 = () -> {
            String data0 = "def";
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(data0 + "->" + exchanger.exchange(data0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        pool.execute(runnable1);
    }
}
