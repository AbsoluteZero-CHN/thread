package cn.noload.chapter_2;

import java.util.concurrent.CountDownLatch;

/**
 * @author caohao1209@gmail.com
 * @date 2020-02-18 18:22
 */
public class Main7 {


    /**
     * TODO 这里静态对象永远使用轻量级锁
     * */
    private static final Lock lock = new Lock();

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        Lock lock = new Lock();
        lock.hashCode();
        deviation(1000000000, lock);
        light(1000000000, lock);
    }

    /**
     * 偏向锁测试
     */
    private static void deviation(int time, Lock lock) {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            lock.execute();
        }
        Long end = System.currentTimeMillis();
        System.out.println("cost: " + (end - start) + " ms.");
    }

    /**
     * 轻量级锁测试
     */
    private static void light(int time, Lock lock) {
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

        @Override
        public int hashCode() {
            return 100;
        }
    }

    /**
     * TODO 实验证明, 如果重写了 hasCode 方法, 由于对象头不会记录 hashCode, 所以这里全都会采用偏向锁
     *
     * cost: 1801 ms.
     * cost: 1795 ms.
     * */
}
