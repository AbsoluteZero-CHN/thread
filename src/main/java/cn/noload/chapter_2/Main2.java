package cn.noload.chapter_2;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class Main2 {

    public static void main(String[] args) {
        InnerClass innerClass = new InnerClass();
        // 1956725890 0x74a14482
        System.out.println(System.identityHashCode(innerClass));
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseInstance(innerClass).toPrintable());
    }


    private static class InnerClass {}

    /**
     * # Running 64-bit HotSpot VM.
     * # Using compressed oop with 3-bit shift.
     * # Using compressed klass with 3-bit shift.
     * # Objects are 8 bytes aligned.
     * TODO 对应 Java 中 [OOP(Ordinary Object Pointer)], boolean, byte, char, short, int, float, long, double 大小
     * TODO 采用小端存储, 存储按字节倒着存储的.
     *  比如这里: 第一个字节 00000001 这8位对应 unused(1) + age(4) + biased_lock(1) + lock(1)
     *  第二个字节 10000010 对应 innerClass 十六进制 hashCode 的 82, 01000100 对应 44 10100001 对应 a1, 依次类推
     * # Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
     * # Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
     *
     * TODO 前二三行为对象的对象头. 第一行为 Mark Word, 第三行为 Klass
     * cn.noload.chapter_2.Main2$InnerClass object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           01 82 44 a1 (00000001 10000010 01000100 10100001) (-1589345791)
     *       4     4        (object header)                           74 00 00 00 (01110100 00000000 00000000 00000000) (116)
     *       8     4        (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     * */
}
