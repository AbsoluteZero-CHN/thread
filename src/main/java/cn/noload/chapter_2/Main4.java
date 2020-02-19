package cn.noload.chapter_2;

import java.util.concurrent.CountDownLatch;

/**
 * @author caohao1209@gmail.com
 * @date 2020-02-18 18:22
 */
public class Main4 {

    public static void main(String[] args) throws InterruptedException {
//        deviation(1000000000);
//        light(1000000000);
        heavy(1000000000);
    }

    /**
     * 偏向锁测试
     * */
    private static void deviation(int time) throws InterruptedException {
        Thread.sleep(5000L);
        Lock lock = new Lock();
        Long start = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            lock.execute();
        }
        Long end = System.currentTimeMillis();
        System.out.println("cost: " + (end - start) + " ms.");
    }

    /**
     * 重量级锁测试
     * */
    private static void heavy(int time) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(time);
        Lock_2 lock = new Lock_2(count);
        Thread thread_1 = new Thread(lock);
        Thread thread_2 = new Thread(lock);
        Long start = System.currentTimeMillis();
        thread_1.start();
        thread_2.start();
        count.await();
        Long end = System.currentTimeMillis();
        System.out.println("cost: " + (end - start) + " ms.");
    }

    /**
     * 轻量级锁测试
     * */
    private static void light(int time) {
        Lock lock = new Lock();
        Long start = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            lock.execute();
        }
        Long end = System.currentTimeMillis();
        System.out.println("cost: " + (end - start) + " ms.");
    }

    private static class Lock {
        private int i = 0;
        public synchronized void execute() {
            i++;
        }
    }

    private static class Lock_2 implements Runnable {

        private int i = 0;
        private final CountDownLatch count;

        private Lock_2(CountDownLatch count) {
            this.count = count;
        }

        public synchronized void execute() {
            i++;
        }

        @Override
        public void run() {
            while (count.getCount() > 0) {
                execute();
                count.countDown();
            }
        }
    }

    /**
     * TODO 偏向锁:
     *  cost: 1958 ms.
     *
     * TODO 轻量级锁:
     *  cost: 21523 ms.
     *
     * TODO 重量级锁:
     *  cost: 56945 ms.
     * */
}
