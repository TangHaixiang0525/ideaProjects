package gettingstarted;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Hiram on 2016/5/31.
 */
public class JProfilerUtils {
    public static void main(String[] args) throws IOException {
    }

    private int actionCount;
    private long allTime;

    public static void run(JProfilerAction action) {
        new JProfilerUtils().run0(action);
    }

    private void run0(JProfilerAction action) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        action.setUp();
        System.out.println("begin");
        while (true) {
            String cmd = null;
            try {
                cmd = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ("report".equals(cmd)) {
                System.out.println("avgTime:" + (allTime / (double)actionCount) + "actionCount:" + actionCount + " allTime:" + allTime);
                continue;
            }
            if (isRun(cmd)) {
                int times = getRunTimes(cmd);
                for (int i = 0; i < times; i++) {
                    actionOne(action);
                }
            }
            if ("c".equals(cmd)) {
                clearRecorder();
            }
            if ("gc".equals(cmd)) {
                System.gc();
            }
            if ("e".equals(cmd)) {
                return;
            }
        }
    }

    private int getRunTimes(String cmd) {
        int times = 1;
        if (cmd.length() > 1) {
            times = Integer.valueOf(cmd.substring(1));
        }
        return times;
    }

    private boolean isRun(String cmd) {
        return cmd != null && cmd.startsWith("r");
    }

    private void actionOne(JProfilerAction action) {
        long startTime = System.currentTimeMillis();
        action.action();
        long endTime = System.currentTimeMillis() - startTime;
        actionCount++;
        allTime += endTime;
        System.out.println("times: " + actionCount + " used time:" + endTime + "ms");
    }

    private void clearRecorder() {
        actionCount = 0;
        allTime = 0;
    }


}
