package cache;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author: gnixgnohuh
 * @date : 19-4-11
 * @time : 下午15:57
 * @desc : 这是CacheUtil使用的一个示例类
 */
public class SampleCache {
    public static void main(String[] args) {
        List<Integer> keyList = Lists.newArrayList(1, 2, 3);
        Map<Integer, CacheBO> result = CacheUtil.getInstance().batchGetWithMissed(keyList, CacheBO.class, keys -> {
            Map<Integer, CacheBO> map = Maps.newHashMap();
            map.put(1, new CacheBO("小明"));
            map.put(2, new CacheBO("小红"));
            return map;
        });

        Map<Integer, List<CacheBO>> result1 = CacheUtil.getInstance().batchGetWithMissed(keyList, CacheBO.class, keys -> {
            Map<Integer, List<CacheBO>> map = Maps.newHashMap();
            map.put(1, Lists.newArrayList(new CacheBO("小明")));
            map.put(2, Lists.newArrayList(new CacheBO("小红")));
            return map;
        });

        System.out.println(1);
    }
}
