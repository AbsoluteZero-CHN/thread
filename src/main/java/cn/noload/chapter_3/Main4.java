package cn.noload.chapter_3;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @author caohao1209@gmail.com
 * @date 2020-02-23 17:21
 */
public class Main4 {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        List<Lock> lockList = new ArrayList<>(100);
        IntStream.range(0, 100).forEach(i -> {
            Lock lock = new Lock();
            synchronized (lock){
                lockList.add(lock);
            }
        });
        System.out.println("======================= 所有锁创建完成 =======================");
        CountDownLatch firstCount = new CountDownLatch(20);
        IntStream.range(0, 20).forEach(i -> {
            new Thread(() -> {
                synchronized (lockList.get(i)) {
                    firstCount.countDown();
                }
            }).start();
        });
        firstCount.await();
        System.out.println("======================= 前 20 个线程执行完毕, 后面 80 个线程准备批量重偏向 =======================");
        CountDownLatch lastCount = new CountDownLatch(80);
        IntStream.range(20, 100).forEach(i -> {
            new Thread(() -> {
                synchronized (lockList.get(i)) {
                    lastCount.countDown();
                }
            }).start();
        });
        lastCount.await();
        System.out.println("======================= 后面 80 个线程执行完毕, 现在进行 lock 对象头校验, 无锁: 轻量级锁, 偏向锁: 被批量重偏向 =======================");
        System.out.println(ClassLayout.parseInstance(lockList.get(99)).toPrintable());
    }

    /**
     * TODO 即使是多个线程竞争, 也会批量重偏向
     *
     * ======================= 所有锁创建完成 =======================
     * ======================= 前 20 个线程执行完毕, 后面 80 个线程准备批量重偏向 =======================
     * ======================= 后面 80 个线程执行完毕, 现在进行 lock 对象头校验, 无锁: 轻量级锁, 偏向锁: 被批量重偏向 =======================
     * cn.noload.chapter_3.Main4$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           05 21 7a 23 (00000101 00100001 01111010 00100011) (595206405)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     * */

    private static class Lock{}
}
