package tria;

import it.unimi.dsi.fastutil.ints.Int2DoubleRBTreeMap;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by Hiram on 2016/9/22.
 */
public class SetTest extends TestCase {
    public static void main(String[] args) {
//        Int2DoubleRBTreeMap map = new Int2DoubleRBTreeMap();
//        map.put(1, 1);
////        IntSortedSet integers = map.keySet();
////        integers.toIntArray();
//
//        System.out.println(map.size());
    }

    public void testTreeMap() {
        Random random = new Random();
        int[] ia = new int[100000000];
        for (int i = 0; i < 100000000; i++) {
            ia[i] = random.nextInt(1);
        }
        Int2DoubleRBTreeMap map = new Int2DoubleRBTreeMap();
        long time = System.currentTimeMillis();
        for (int i = 0; i < ia.length; i++) {
            map.put(ia[i], map.get(ia[i]) + 1.0);
//            map.addTo(ia[i], 1);
        }
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(1);
    }

    public void testAdd() {
        int c = 0;
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            c += i;
        }
        System.out.println(System.currentTimeMillis() - time);
    }

    public void testAddList() {
        int c = 0;
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            c += i;
        }
        System.out.println(System.currentTimeMillis() - time);
    }
}
