package cn.noload.chapter_2;

public class Main1 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.interrupted());
                    Thread.currentThread().interrupt();
                    System.out.println(Thread.interrupted());
                }
            }
        });

        thread.start();
        thread.interrupt();
        Thread.sleep(100);
    }
}
