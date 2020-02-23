package cn.noload.chapter_3;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author caohao1209@gmail.com
 * @date 2020-02-23 17:04
 */
public class Main3 {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        List<Lock> lockList = new ArrayList<>(100);
        IntStream.range(0, 100).forEach(i -> {
            Lock lock = new Lock();
            synchronized (lock) {
                lockList.add(lock);
            }
        });
        System.out.println("======================= 所有锁创建完成 =======================");
        System.out.println(ClassLayout.parseInstance(lockList.get(0)).toPrintable());
        FirstRunner firstRunner = new FirstRunner(lockList);
        new Thread(firstRunner).start();
        System.out.println("======================= 前 20 个线程执行完毕, 检查第一个 lock 对象头. 后面 80 个线程准备批量重偏向 =======================");
        System.out.println(ClassLayout.parseInstance(lockList.get(0)).toPrintable());
        SecondRunner secondRunner = new SecondRunner(lockList);
        new Thread(secondRunner).start();
        System.out.println("======================= 后面 80 个线程执行完毕, 现在进行 lock 对象头校验, 无锁: 轻量级锁, 偏向锁: 被批量重偏向 =======================");
        System.out.println(ClassLayout.parseInstance(lockList.get(99)).toPrintable());
    }

    /**
     * TODO 当同一个 class 的多个实例, 撤销锁的操作超过 20 次后, 会发生批量重新偏向
     *
     * ======================= 所有锁创建完成 =======================
     * cn.noload.chapter_3.Main3$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           05 e8 04 01 (00000101 11101000 00000100 00000001) (17098757)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * ======================= 前 20 个线程执行完毕, 检查第一个 lock 对象头. 后面 80 个线程准备批量重偏向 =======================
     * cn.noload.chapter_3.Main3$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * ======================= 后面 80 个线程执行完毕, 现在进行 lock 对象头校验, 无锁: 轻量级锁, 偏向锁: 被批量重偏向 =======================
     * cn.noload.chapter_3.Main3$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           05 e9 b5 24 (00000101 11101001 10110101 00100100) (615901445)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     * */

    private static class Lock{}

    private static class FirstRunner implements Runnable {

        private int index = 0;

        private final List<Lock> lockList;

        private FirstRunner(List<Lock> lockList) {
            this.lockList = lockList;
        }

        @Override
        public void run() {
            if(index <= 19) {
                Lock lock = lockList.get(index);
                synchronized (lock) {
                    index++;
                }
                run();
            }
        }
    }

    private static class SecondRunner implements Runnable {

        private int index = 20;

        private final List<Lock> lockList;

        private SecondRunner(List<Lock> lockList) {
            this.lockList = lockList;
        }

        @Override
        public void run() {
            if(index <= 99) {
                Lock lock = lockList.get(index);
                synchronized (lock) {
                    index++;
                }
                run();
            }
        }
    }
}
