package cn.noload.chapter_3;

import org.openjdk.jol.info.ClassLayout;

/**
 * 单个锁的重偏向
 * @author caohao1209@gmail.com
 * @date 2020-02-21 16:44
 */
public class Main2 {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        Lock lock = new Lock();
        synchronized (lock) {
            System.out.println("主线程锁定 lock 对象");
            // 偏向锁
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }

        Thread thread_1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("thread_1 锁定 lock 对象");
                // 轻量级锁
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        });
        Thread thread_2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("thread_2 锁定 lock 对象");
                // 轻量级锁
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        });
        thread_1.start();
        thread_1.join();
        thread_2.start();
    }


    private static class Lock{}
}
