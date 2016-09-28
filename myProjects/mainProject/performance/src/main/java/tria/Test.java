package tria;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Hiram on 2016/9/22.
 */
public class Test extends TestCase {
    public void testMem() {
        int[] ia = new int[1000000000];
        for (int i = 0; i < ia.length; i++) {
            ia[i] = i;
        }
        int sum = 0;
        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            sum += i;
//            sum+=ia[i];
        }
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(sum);
    }

    public void testMem2() {
        int count = 256 * 1024 * 1024;
        int[] arr = new int[count];
        int c = 0;
        int s=(int)System.currentTimeMillis();
        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++)  c*=3;
        System.out.println(System.currentTimeMillis() - time);

        time = System.currentTimeMillis();
        // Loop 2
        for (int i = 0; i < count; i++) arr[i] *= 3;
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(c);
    }
}
