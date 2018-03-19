package redis;

/**
 * @author: gnixgnohuh
 * @date : 18-3-15
 * @time : 上午11:19
 * @desc : 这是一个模拟Redis操作类
 */
public class MockRedis {

    /**
     * redis setnx指令
     *
     * @param key
     * @param value
     * @return
     */
    public static Long setnx(final String key, final String value) {
        return 1L;
    }

    /**
     * redis get指令
     *
     * @param key
     * @return
     */
    public static String get(final String key) {
        return null;
    }

    /**
     * redis getset指令
     *
     * @param key
     * @param value
     * @return
     */
    public static String getSet(final String key, final String value) {
        return null;
    }

    /**
     * redis delete指令
     *
     * @param key
     * @return
     */
    public static Long del(String key) {
        return 1L;
    }

    /**
     * 归还连接
     *
     * @return
     */
    public static boolean returnResource() {
        return true;
    }

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
