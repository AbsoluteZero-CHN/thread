package cn.noload.chapter_1;

public class Main3 {

    private static final Object lock = new Object();

    public static void main(String[] args) {
        Main3 main3 = new Main3();
        main3.start();
    }

    private void start() {
        Thread thread_1 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                    sync();
                } catch (InterruptedException e) {

                }
            }
        });

        Thread thread_2 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                    sync();
                } catch (InterruptedException e) {

                }
            }
        });

        thread_1.setName("Thread-1");
        thread_2.setName("Thread-2");

        thread_1.start();
        thread_2.start();
    }

    private void sync() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
