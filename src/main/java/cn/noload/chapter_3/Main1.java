package cn.noload.chapter_3;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author caohao1209@gmail.com
 * @date 2020-02-19 16:36
 */
public class Main1 {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        Lock lock = new Lock();
        Long end = System.currentTimeMillis();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        System.out.println("cost: " + (end - start) + " ms.");
    }

    private static class Lock {}
}
