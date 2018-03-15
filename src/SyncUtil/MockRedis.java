package SyncUtil;

/**
 * @author: gnixgnohuh
 * @date : 18-3-15
 * @time : 上午11:19
 * @desc : 这是一个模拟Redis操作类
 */
public class MockRedis {

    /**
     * redis setnx指令
     * @param key
     * @param value
     * @return
     */
    public static Long setnx(final String key, final String value){
        return 1L;
    }

    /**
     * redis get指令
     * @param key
     * @return
     */
    public static String get(final String key){
        return null;
    }

    /**
     * redis getset指令
     * @param key
     * @param value
     * @return
     */
    public static String getSet(final String key, final String value){
        return null;
    }

    /**
     * redis delete指令
     * @param key
     * @return
     */
    public static Long del(String key){
        return 1L;
    }

    /**
     * 归还连接
     * @return
     */
    public static boolean returnResource(){
        return true;
    }
}
