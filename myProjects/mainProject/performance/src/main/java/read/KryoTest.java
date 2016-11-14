package read;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.xerial.snappy.Snappy;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;
import sun.misc.Unsafe;

import java.io.*;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 * Created by THX on 2016/11/12.
 */
public class KryoTest {
    private static int size = 10 * 1024 * 1024;

    private static String fileName = "D:/test2.data";
    private static String fileName2 = "D:/test3.data";
    private static String fileName3 = "D:/test4.data";
    private static String fileName4 = "D:/test5.data";
    private static String fileName5 = "D:/test6.data";
    private static RandomAccessFile raf = null;
    private static RandomAccessFile raf2 = null;
    private static RandomAccessFile raf3 = null;
    private static RandomAccessFile raf4 = null;
    private static RandomAccessFile raf5 = null;

    static {

        try {
            raf = new RandomAccessFile(fileName, "rw");
            raf2 = new RandomAccessFile(fileName2, "rw");
            raf3 = new RandomAccessFile(fileName3, "rw");
            raf4 = new RandomAccessFile(fileName4, "rw");
            raf5 = new RandomAccessFile(fileName5, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        File file = new File("D:/test2.data");
//        Kryo kryo = new Kryo();
        long time = System.currentTimeMillis();
        write();
//        read();
        System.out.println(System.currentTimeMillis() - time);
    }

    private static void read() throws IOException {
        int size = 100 * 1024 * 1024;
        double sum = 0 ;
        Input input = new Input(new FileInputStream(raf.getFD()), size);
        Input input2 = new Input(new FileInputStream(raf2.getFD()), size);
        Input input3 = new Input(new FileInputStream(raf3.getFD()), size);
        Input input4 = new Input(new FileInputStream(raf4.getFD()), size);
        Input input5 = new Input(new FileInputStream(raf5.getFD()), size);
        for (int i = 0; i < 100000000; i++) {
//            sum+=input.readInt(true);
//            sum+=input2.readInt(true);
//            sum+=input3.readInt(true);
//            sum+=input4.readInt(true);
            sum+=input5.readDouble();
        }
        System.out.println(sum);
    }

    private static void write() throws FileNotFoundException {
        int Y = 100000000;
        Output output = new Output(new FileOutputStream(fileName), size);
        Output output2 = new Output(new FileOutputStream(fileName2), size);
        Output output3 = new Output(new FileOutputStream(fileName3), size);
        Output output4 = new Output(new FileOutputStream(fileName4), size);
        Output output5 = new Output(new FileOutputStream(fileName5), size);
        for (int i = 0; i < Y; i++) {
            output.writeInt((i/1000)%5000, true);
            output2.writeInt((i/100)%10, true);
            output3.writeInt((i/10)%10, true);
            output4.writeInt((i)%10, true);
            output5.writeDouble((i%10000));
        }
        output.close();
        output2.close();
        output3.close();
        output4.close();
        output5.close();
    }

    static private void bean3() {
        Kryo kryo = new Kryo();
// kryo.setReferences(true);
        // kryo.setRegistrationRequired(true);
// kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
//注册类
        Registration registration = kryo.register(int[].class);
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
//序列化
            Output output = null;
// ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//output = new Output( outStream , 4096);
            output = new Output(1, 4096);
//            kryo.writeObject(output, student);
            byte[] bb = output.toBytes();
// System.out.println(bb.length);
            output.flush();

//反序列化
            Input input = null;
// input = new Input(new
            // ByteArrayInputStream(outStream.toByteArray()),4096);
            input = new Input(bb);
//            Student s = (Student) kryo.readObject(input, registration.getType());
//            System.out.println(s.getName() + "," + s.getSex());
            input.close();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time:" + time);
        ResultSet set = null;
        Unsafe unsafe= null;
    }
}
