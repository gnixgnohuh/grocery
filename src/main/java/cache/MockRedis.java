package cache;

/**
 * @author: gnixgnohuh
 * @date : 18-3-15
 * @time : 上午11:19
 * @desc : 这是一个模拟Redis操作类
 */
public class MockRedis {
    /**
     * 列表新增一条元素
     * @param key
     * @param strings
     * @return
     */
    public static Long lpush(final String key, final String... strings){
        return 1L;
    }

    /**
     * 列表remove一条元素
     * @param key
     * @return
     */
    public static String lpop(final String key){
        return "1";
    }

    /**
     * 设置超时时间
     * @param key
     * @param seconds
     * @return
     */
    public static Long expire(final String key, final int seconds){
        return 1L;
    }
}
