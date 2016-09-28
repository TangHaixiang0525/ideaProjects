package gettingstarted;

import com.github.lwhite1.tablesaw.api.ColumnType;
import com.github.lwhite1.tablesaw.api.IntColumn;
import com.github.lwhite1.tablesaw.api.Table;
import com.github.lwhite1.tablesaw.columns.Column;
import com.github.lwhite1.tablesaw.io.csv.CsvReader;
import com.github.lwhite1.tablesaw.table.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.util.Random;


/**
 * Created by Hiram on 2016/9/18.
 */
public class Main {
    public static void main(String[] args) throws IOException {
//        create();
        test();
    }

    public static void test() {
//        JProfilerUtils.run(new JProfilerAction() {
//            @Override
//            public void action() {
        long time = System.currentTimeMillis();
        Table t = Table.readTable("D:\\t\\t.saw");
        String[] sortKey = new String[6];
        for (int i = 0; i < 6; i++) {
            sortKey[i] = "Col" + i;
        }
        System.out.println(System.currentTimeMillis() - time);
        time = System.currentTimeMillis();
        Table sortT = t.sortOn(sortKey);
        System.out.println("sortTime:" + (System.currentTimeMillis() - time));
        System.out.println(sortT.rowCount());
        System.out.println(t.rowCount());
//            }
//
//            @Override
//            public void setUp() {
//
//            }
//        });
    }


    public static void create() throws IOException {
        int columnsSize = 7;
        IntColumn[] columns = new IntColumn[columnsSize];
        int rowSize = (int) Math.pow(10, columnsSize);
        assert rowSize <= 1000000;
        for (int i = 0; i < columns.length; i++) {
            columns[i] = new IntColumn("Col" + i);
        }
        Random random = new Random();

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columns.length; j++) {
                columns[j].add(i % ((int) Math.pow(10, j+1)));
            }
        }
        Table t = Table.create("t", columns);
        System.out.println(t.rowCount());
        String save = t.save("D:\\t");
        System.out.println(save);
    }
}
