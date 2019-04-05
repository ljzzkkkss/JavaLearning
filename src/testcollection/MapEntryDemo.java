package testcollection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by ljzzkkkss on 2019/4/5.
 */
public class MapEntryDemo {
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();

        map.put("A",1);
        map.put("B",2);
        map.put("C",3);
        
        demoMap(map);

        System.out.println(map);

        Map<String, Integer> skipMap = new ConcurrentSkipListMap<>();

        skipMap.put("A",1);
        skipMap.put("B",2);
        skipMap.put("C",3);

        demoMap(skipMap);
        System.out.println(map);

    }

    private static void demoMap(Map<String, Integer> map) {
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            slowApproach(entry,map);
            fastApproach(entry);
        }
    }

    private static void fastApproach(Map.Entry<String, Integer> entry) {
        //此时可以发现ConcurrentSkipListMap中entry.setValue不支持
        entry.setValue(entry.getValue() + 1);
    }

    private static void slowApproach(Map.Entry<String, Integer> entry,Map<String,Integer> map) {
        String key = entry.getKey();
        Integer value = entry.getValue();

        map.put(key,value + 1);

    }
}
