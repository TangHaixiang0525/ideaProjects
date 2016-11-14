package tria;

import alluxio.client.file.FileInStream;
import com.esotericsoftware.kryo.io.Input;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceArrayMap;
import junit.framework.TestCase;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Hiram on 2016/9/27.
 */
public class HashTest extends TestCase {
    public void testHash() {
        long time = System.currentTimeMillis();
        Int2ReferenceArrayMap<Int2IntOpenHashMap> map = new Int2ReferenceArrayMap(1000000);
        for (int i = 0; i < 100000000; i++) {
            int k = i % 10000;
            Int2IntOpenHashMap m = map.get(k);
            if (m == null) {
                m = new Int2IntOpenHashMap();
                m.addTo(k, 1);
                map.put(k, m);
            } else {
                m.addTo(k, 1);
            }
        }
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(map.get(5));
    }


    static int g;

    public void testHashMap() throws IOException, InterruptedException {
            Thread.sleep(1000);
            long time = System.currentTimeMillis();
            HashMap<MyKey, MyInt> map = new HashMap(100);
            MyKey key = new MyKey(-1);
            for (int i = 0; i < 100000000; i++) {

//            g = i % 2;
                int k = 1;
                key.k[0] = k;
                MyInt m = map.get(key);
                if (m == null) {
                    m = new MyInt();
                    m.i++;
                    map.put(new MyKey(k), m);
                } else {
                    m.i++;
                }
            }
            System.out.println(System.currentTimeMillis() - time);
            System.out.println(map.get(new MyKey(1)).i);
    }


    public static void main(String[] args) throws IOException {
//        int read = 0;
//        while ((read = System.in.read()) != 'e') {
//            if (read != 'r') {
//                continue;
//            }
//            for (int i = 0; i < 1; i++) {
//                new Thread1().start();
//            }
//        }
        int[] ia = new int[10];
        ia[0]=1;
        int count = 0;
        int len = ia.length;
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            for (int j = 0; j < len; j++) {
                if (ia[j] == 1) {
                    count++;
                }
            }
        }
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(count);
    }

    static void r() throws IOException {

            RandomAccessFile raf = new RandomAccessFile("D:/test2.data", "rw");
            RandomAccessFile raf2 = new RandomAccessFile("D:/test3.data", "rw");
            RandomAccessFile raf3 = new RandomAccessFile("D:/test4.data", "rw");
            RandomAccessFile raf4 = new RandomAccessFile("D:/test5.data", "rw");
            RandomAccessFile raf5 = new RandomAccessFile("D:/test6.data", "rw");
//        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
//        for (int i = 0; i < 100000000; i++) {
//            out.writeInt(i);
//        }
            Input input = new Input(new FileInputStream(raf.getFD()), 4<<20);
            Input input2 = new Input(new FileInputStream(raf2.getFD()), 4<<20);
            Input input3 = new Input(new FileInputStream(raf3.getFD()), 4<<20);
            Input input4 = new Input(new FileInputStream(raf4.getFD()), 4<<20);
            Input input5 = new Input(new FileInputStream(raf5.getFD()), 4<<20);
            long time = System.currentTimeMillis();
            HashMap<MyKey, MyInt> map = new HashMap(20000);
            MyKey key = new MyKey(-14,4,4,4);
            for (int i = 0; i < 10000000; i++) {

//            g = i % 2;
//                int k = i%10000;
//                if (k != 5) {
//                    continue;
//                }

                double v1 = input5.readDouble();
                input.skip(1);
//                if (v1 >6||v1 <2) {
//                    continue;
//                }
                int v = input.readInt(true);
//                if (v != 6) {
//                    continue;
//                }
                key.k[0] = v;
//                double v1 = 1;
                key.k[1] = input2.readInt(true);
                key.k[2] = input3.readInt(true);
//                key.k[2] = v;
                key.k[3] = input4.readInt(true);
                MyInt m = map.get(key);
                if (m == null) {
                    m = new MyInt();
                    m.i = v1;
                    MyKey myKey = new MyKey(0);
                    myKey.k = key.k.clone();
                    map.put(myKey, m);
                } else {
                    m.i+=v1;
                }
            }
            System.out.println(System.currentTimeMillis() - time);
            System.out.println(map.get(new MyKey(6,8,8,8)));
            System.out.println(map.size());
            input.close();
            input2.close();
            input3.close();
            input4.close();

        }
}

class MyKey {
    int[] k;

    public MyKey(int... k) {
        this.k = k;
    }

    @Override
    public boolean equals(Object o) {
        MyKey myKey = (MyKey) o;
        return Arrays.equals(k,myKey.k);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(k);
    }


}

class MyInt {
    double i = 0;


    public String toString(){
        return String.valueOf(i);
    }
}

class Thread1 extends Thread{
    public void run(){
        try {
            HashTest.r();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}