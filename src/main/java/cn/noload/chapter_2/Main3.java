package cn.noload.chapter_2;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author caohao1209@gmail.com
 * @date 2020-02-18 17:30
 */
public class Main3 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("线程休眠 5000ms...");
        Thread.sleep(5000);
        Lock lock = new Lock();
        System.out.println("====================================================== before =============================================================");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        synchronized (lock) {
            System.out.println("====================================================== running =============================================================");
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }
        System.out.println("====================================================== after =============================================================");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }
    private static class Lock {}

    /**
     * TODO 由于采用小端存储, 所以前 8 位对应着 Java 对象头的 unused(1) age(4) biased_lock(1) lock(2)
     *  age 表示当前对象被标记的次数, 由于只有 4bit 所以最大年龄为 15, 超过就会被移送老年代
     *  biased_lock 为偏向锁标识. 1.偏向锁, 0. 非偏向锁
     *  lock 2bit 的锁状态标识(无锁和偏向锁共用同一个标识, 并通过偏向锁标识来区分)
     *
     * TODO 当同步代码块只有一个线程来访问时候, 锁住的对象为偏向锁. 当在一个线程运行这个代码块时候, 另一个线程也来运行, 这个对象膨胀为轻量级锁
     * ====================================================== before =============================================================
     * cn.noload.chapter_2.Main3$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * ====================================================== running =============================================================
     * cn.noload.chapter_2.Main3$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           f8 f1 2f 03 (11111000 11110001 00101111 00000011) (53473784)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * ====================================================== after =============================================================
     * cn.noload.chapter_2.Main3$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     * */


    /**
     * TODO JVM 存在偏向锁延迟, 线程启动前会关闭偏向锁, 延迟 4 秒后开启(参数可调). 用于 JVM 启动延迟
     * 线程休眠 5000ms...
     * ====================================================== before =============================================================
     * cn.noload.chapter_2.Main3$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * ====================================================== running =============================================================
     * cn.noload.chapter_2.Main3$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           05 e8 7e 01 (00000101 11101000 01111110 00000001) (25094149)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * ====================================================== after =============================================================
     * cn.noload.chapter_2.Main3$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           05 e8 7e 01 (00000101 11101000 01111110 00000001) (25094149)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     * */
}

