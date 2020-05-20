package cn.noload.chapter_3;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

/**
 * 锁重偏向/撤销测试
 * @author hao.caosh@ttpai.cn
 * @date 2020-05-18 17:26
 *
 * 日志
 * -XX:+TraceBiasedLocking
 * 偏向锁延迟
 * -XX:BiasedLockingStartupDelay=0
 * -XX:+PrintSafepointStatistics
 * -XX:PrintSafepointStatisticsCount=1
 * 批量重偏向阈值
 * -XX:BiasedLockingBulkRebiasThreshold=1
 */
public class BiasLocking {

    private static final Unsafe U;
    private static final long OFFSET = 0L;

    static {

        try {
            Field unsafe = Unsafe.class.getDeclaredField("theUnsafe");
            unsafe.setAccessible(true);
            U = (Unsafe) unsafe.get(null);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    public static void main(String[] args) throws Exception {

        ExecutorService thread = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 40; i++) {
            Monitor a = new Monitor();
            synchronized (a) {
                out.println("Main thread \t\t" + printHeader(a));
            }

            thread.submit(() -> {
                synchronized (a) {
                    out.println("Work thread \t\t" + printHeader(a));
                }
                return null;
            }).get();
        }

        thread.shutdown();
    }

    private static String printHeader(Object a) {
        int word = U.getInt(a, OFFSET);
        return Integer.toHexString(word);
    }

    private static class Monitor {
        // mutex object
    }
}
