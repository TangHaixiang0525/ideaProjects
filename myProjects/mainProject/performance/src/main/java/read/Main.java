package read;

import java.io.*;
import java.util.function.ObjIntConsumer;

/**
 * Created by THX on 2016/11/11.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("D:/test2.data");
//        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
//        for (int i = 0; i < 100000000; i++) {
//            out.writeInt(i);
//        }
        long time = System.currentTimeMillis();
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
        for (int i = 0; i < 100000000; i++) {
            in.read();
        }
        System.out.println(System.currentTimeMillis() - time);

    }
}
