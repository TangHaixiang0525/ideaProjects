package tria;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceArrayMap;
import junit.framework.TestCase;

import java.util.HashMap;

/**
 * Created by Hiram on 2016/9/27.
 */
public class HashTest extends TestCase {
    public void testHash(){
        long time = System.currentTimeMillis();
        Int2ReferenceArrayMap<Int2IntOpenHashMap> map = new Int2ReferenceArrayMap(1000000);
        for (int i = 0; i < 100000000; i++) {
            int k=i%10000;
            Int2IntOpenHashMap m = map.get(k);
            if (m == null) {
                m=new Int2IntOpenHashMap();
                m.addTo(k, 1);
                map.put(k, m);
            }else{
                m.addTo(k, 1);
            }
        }
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(map.get(5));
    }
}
