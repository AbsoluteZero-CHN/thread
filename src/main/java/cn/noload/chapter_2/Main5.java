package cn.noload.chapter_2;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.CountDownLatch;

/**
 * @author caohao1209@gmail.com
 * @date 2020-02-18 19:25
 */
public class Main5 {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new Lock();
        System.out.println("====================================================== 开始 =============================================================");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        Thread thread_1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(5000);
                    System.out.println("thread_1 release...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread_2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("thread_2 join...");
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
                countDownLatch.countDown();
            }
        });
        thread_1.start();
        Thread.sleep(1000);
        System.out.println("thread locking...");
        thread_2.start();
//        join(lock);
        countDownLatch.await();
        System.out.println("after main thread...");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        System.gc();
        System.out.println("after gc...");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }

    private static void join(Lock lock) {
        synchronized (lock) {
            System.out.println("main thread join...");
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }
    }

    private static class Lock {

    }


    /**
     * ====================================================== 开始 =============================================================
     * cn.noload.chapter_2.Main5$Lock object internals:
     * TODO 开始之前无锁
     *
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * thread locking...
     * thread_1 release...
     * main thread join...
     * cn.noload.chapter_2.Main5$Lock object internals:
     * TODO 锁膨胀为重量级锁
     *
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           5a 1d 01 22 (01011010 00011101 00000001 00100010) (570498394)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * after main thread...
     * cn.noload.chapter_2.Main5$Lock object internals:
     * TODO 退出同步块还是重量级锁
     *
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           5a 1d 01 22 (01011010 00011101 00000001 00100010) (570498394)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * after gc...
     * cn.noload.chapter_2.Main5$Lock object internals:
     * TODO 调用 GC 后, age + 1, 对象变为无锁
     *
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           09 00 00 00 (00001001 00000000 00000000 00000000) (9)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     * */
}
