package cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: gnixgnohuh
 * @date : 19-4-11
 * @time : 下午13:45
 * @desc : 这是一个缓存工具类，提供未命中缓存时通过回调函数写入缓存，动态配置缓存过期时间，防止缓存击穿等功能。
 */
public class CacheUtil {

    /**
     * 存放CacheUtil instance
     */
    private static Map<String, CacheUtil> instanceMap = Maps.newConcurrentMap();

    private static final String SPLITER = "-";

    /**
     * 空值
     */
    private static final String NULL_VALUE = "GNIXGNOHUH";

    /**
     * key的命名空间
     */
    private String namespace;

    /**
     * value的过期时间，以分钟为单位
     */
    private int expire;

    /**
     * 防止缓存穿透，空value的过期时间，以分钟为单位
     */
    private int nilExpire;

    private CacheUtil(String namespace, int expire, int nilExpire) {
        this.namespace = namespace;
        this.expire = expire;
        this.nilExpire = nilExpire;
    }

    private CacheUtil() {
    }

    public static CacheUtil getInstance() {
        return getInstance(CacheConfig.getDefaultNameSpace(), CacheConfig.getDefaultExpire(), CacheConfig.getDefaultNilValueExpire());
    }

    public static CacheUtil getInstance(int expire) {
        return getInstance(CacheConfig.getDefaultNameSpace(), expire, CacheConfig.getDefaultNilValueExpire());
    }

    /**
     * 创建一个CacheUtil实例
     *
     * @param namespace
     * @param expire
     * @param nilExpire
     * @return
     */
    public static CacheUtil getInstance(String namespace, int expire, int nilExpire) {
        String instanceKey = namespace + SPLITER + expire + SPLITER + nilExpire;
        if (instanceMap.get(instanceKey) == null) {
            instanceMap.putIfAbsent(instanceKey, new CacheUtil(namespace, expire, nilExpire));
        }
        return instanceMap.get(instanceKey);
    }

    /**
     * @param keyList        ket集合
     * @param type           value的具体类型
     * @param missedFunction 未命中缓存执行此function重新获取数据种入缓存
     * @param <T>            key的类型
     * @param <V>            value的类型
     * @return
     */
    public <T, V> Map<T, V> batchGetWithMissed(List<T> keyList, Class<?> type, Function<List<T>, Map<T, V>> missedFunction) {
        if (CollectionUtils.isEmpty(keyList)) {
            return Maps.newHashMap();
        }
        Map<T, V> map = Maps.newHashMap();

        List<String> cacheKeyList = keyList.stream().distinct().map(this::combineKey).collect(Collectors.toList());
        Map<String, String> result = MockRedis.mget(cacheKeyList);
        List<T> missedKeyList = Lists.newArrayList();
        keyList.forEach(key -> {
            String k = this.combineKey(key);
            String v = result.get(k);
            if (v == null) {
                missedKeyList.add(key);
            } else if (!NULL_VALUE.equals(v)) {
                if (type == String.class) {
                    map.put(key, (V) v);
                } else if (v.startsWith("[")) {
                    map.put(key, (V) JSON.parseArray(v, type));
                } else {
                    map.put(key, (V) JSON.parseObject(v, type));
                }
            }
        });

        //处理未命中缓存的情况
        if (missedFunction == null || CollectionUtils.isEmpty(missedKeyList)) {
            return map;
        }
        Map<T, V> dataMap = missedFunction.apply(missedKeyList);
        missedKeyList.forEach(key -> {
            V v = dataMap.get(key);
            if (v == null) {
                MockRedis.set(this.combineKey(key), NULL_VALUE, nilExpire);
            } else {
                MockRedis.set(this.combineKey(key), v.getClass() == String.class ? (String) v : JSON.toJSONString(v), expire);
                map.put(key, v);
            }
        });
        return map;
    }

    private <T> String combineKey(T key) {
        return namespace + "." + key;
    }
}
