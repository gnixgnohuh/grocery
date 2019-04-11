package threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: gnixgnohuh
 * @date : 18-1-15
 * @time : 上午10:04
 * @desc :　这是一个阻塞线程池
 */
public class BlockingThreadPoolExecutor {
    private ThreadPoolExecutor pool = null;

    public BlockingThreadPoolExecutor() {
        pool = new ThreadPoolExecutor(3, 5, 30, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(5), new RecordThreadFactory(), new RetryRejectedExecutionHandler());
    }

    public void destory() {
        if (pool != null) {
            pool.shutdownNow();
        }
    }

    public ExecutorService getBlockingThreadPoolExecutor() {
        return this.pool;
    }

    private class RecordThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = BlockingThreadPoolExecutor.class.getSimpleName() + count.addAndGet(1);
            System.out.println(threadName);
            t.setName(threadName);
            return t;
        }
    }

    private class RetryRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(99);
        BlockingThreadPoolExecutor exec = new BlockingThreadPoolExecutor();
        ExecutorService pool = exec.getBlockingThreadPoolExecutor();
        long begin = System.currentTimeMillis();
        for (int i = 1; i < 100; i++) {
            System.out.println("提交第" + i + "个任务!");
            pool.execute(() -> {
                try {
                    System.out.println(">>>task is running=====");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
            long end = System.currentTimeMillis();
            System.out.println(end - begin);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

