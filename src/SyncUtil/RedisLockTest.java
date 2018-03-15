package SyncUtil;

import java.util.concurrent.CountDownLatch;

/**
 * @author: gnixgnohuh
 * @date : 18-3-15
 * @time : 上午11:24
 * @desc : 这里借助redis分布式锁实现了一个类似于AtomicInteger.getAndIncrement()的方法，不同公司redis客户端封装不太一样，使用完记得归还连接
 */
public class RedisLockTest {

    private static int a = 0;
    private static final String REDIS_KEY = "REDIS_KEY";

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);

        /**
         * 这里没有释放锁，测试超时用
         */
        new Thread(() -> {
            if (RedisLock.lock(REDIS_KEY)) {
                a++;
                latch.countDown();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CountDownLatch latch0 = new CountDownLatch(3000);

        /**
         * 正确用法
         */
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    if (RedisLock.lock(REDIS_KEY)) {
                        a++;
                        RedisLock.unlock(REDIS_KEY);
                        latch0.countDown();
                    }
                }
            }).start();
        }

        try {
            latch0.await();
            System.out.println(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
