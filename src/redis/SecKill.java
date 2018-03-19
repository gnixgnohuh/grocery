package redis;

/**
 * @author: gnixgnohuh
 * @date : 18-3-19
 * @time : 下午4:46
 * @desc : 这是一个基于redis的秒杀系统模拟
 */
public class SecKill {

    private static final String GOODS = "goods";

    /**
     * 初始化所有商品，并且设置超时时间
     *
     * @param sourceCount
     * @param seconds
     */
    public SecKill(int sourceCount, int seconds) {
        for (int i = 0; i < sourceCount; i++) {
            MockRedis.lpush(GOODS, "1");
        }

        MockRedis.expire(GOODS, seconds);
    }

    /**
     * 基于redis单线程队列模式，保证商品源准确
     */
    public void doSecKill() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    String val = MockRedis.lpop(GOODS);
                    if (val == null) {
                        System.out.println("已经被抢空");
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();

        }
    }

}
