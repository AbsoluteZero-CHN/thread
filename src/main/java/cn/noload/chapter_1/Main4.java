package cn.noload.chapter_1;

import java.util.concurrent.TimeUnit;

public class Main4 {

    private String lock_1 = "lock";
    private String lock_2 = "lock";

    public void test1() {
        synchronized (lock_1) {
            System.out.println("thread-1 start...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {

            }
            System.out.println("thread-1 end...");
        }
    }

    public void test2() {
        synchronized (lock_2) {
            System.out.println("thread-2 start...");
        }
    }

    public static void main(String[] args) {
        Main4 main4 = new Main4();

        System.out.println(main4.lock_1 == main4.lock_2);
        /**
         * 虽然这里定义了两个变量, 但是指向的内存地址都是 hello
         * 字符串的内存地址, 所以两个线程依然会产生同步
         * */
        new Thread(main4::test1, "thread-1").start();
        new Thread(main4::test2, "thread-2").start();
    }
}
