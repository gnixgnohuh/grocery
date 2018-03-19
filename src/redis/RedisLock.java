package redis;

import java.util.concurrent.TimeUnit;

/**
 * @author: gnixgnohuh
 * @date : 18-3-15
 * @time : 上午10:21
 * @desc : redis分布式锁
 */
public class RedisLock {
    private static final long TIMEOUT = 1000 * 10;

    public static boolean lock(String key) {
        try {
            String oldVal;
            String newOldVal;
            while (true) {
                //一步成功，获取到锁
                if (MockRedis.setnx(key, String.valueOf(System.currentTimeMillis() + TIMEOUT)) == 1L) {
                    System.out.println("一步成功");
                    return true;
                }

                oldVal = MockRedis.get(key);

                if (oldVal != null && Long.parseLong(oldVal) < System.currentTimeMillis()) {
                    newOldVal = MockRedis.getSet(key, String.valueOf(System.currentTimeMillis() + TIMEOUT));
                    if (oldVal.equals(newOldVal)) {
                        System.out.println("捕获到过期，重置锁成功");
                        return true;
                    }
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            MockRedis.returnResource();
        }
    }

    public static void unlock(String key) {
        MockRedis.del(key);
        MockRedis.returnResource();
    }
}
