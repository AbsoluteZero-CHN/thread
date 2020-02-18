package cn.noload.chapter_1;

public class Main1 {

    private static Thread thread;


    public static void main(String[] args) throws InterruptedException {
        traditional();
        Thread.sleep(1000);
        thread.interrupt();
    }

    public static void traditional() {
        thread = new Thread() {
            @Override
            public void run() {
                while (!this.isInterrupted()) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("==================");
                }
            }
        };
        thread.start();
    }
}
