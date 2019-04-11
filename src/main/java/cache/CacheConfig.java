package cache;

import org.ho.yaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: gnixgnohuh
 * @date : 19-4-11
 * @time : 下午13:45
 * @desc : 这是一个配置读取类
 */
public class CacheConfig {
    private static Map config;

    static {
        InputStream inputStream = CacheConfig.class.getClassLoader().getResourceAsStream("cache.yml");
        try {
            config = Yaml.loadType(inputStream, HashMap.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static <T> T get(String key, Class<T> clz) {
        Map map = config;
        String[] s = key.split("\\.");
        for (String x : s) {
            Object o = map.get(x);
            if (o.getClass() == clz) {
                return (T) o;
            } else if (o instanceof Map) {
                map = (Map) o;
            } else {
                return null;
            }
        }
        return null;
    }

    public static String getDefaultNameSpace() {
        return get("gnixgnohuh.cache.namespace", String.class);
    }

    public static Integer getDefaultExpire() {
        return get("gnixgnohuh.cache.defaultExpire", Integer.class);
    }

    public static Integer getDefaultNilValueExpire() {
        return get("gnixgnohuh.cache.defaultNilValueExpire", Integer.class);
    }
}
