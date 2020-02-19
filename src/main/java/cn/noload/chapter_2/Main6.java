package cn.noload.chapter_2;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author caohao1209@gmail.com
 * @date 2020-02-19 10:50
 */
public class Main6 {


    public static void main(String[] args) {
        Lock lock = new Lock();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        lock.hashCode();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }

    private static class Lock {
        @Override
        public int hashCode() {
            return 100;
        }
    }

    /**
     * TODO 当重写了 hashCode 后, hashCode 不会保存在对象头中
     * cn.noload.chapter_2.Main6$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * cn.noload.chapter_2.Main6$Lock object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     * */
}
