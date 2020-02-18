package cn.noload.chapter_1;

public class Main2 {

    private static boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        traditional();
        Thread.sleep(100);
        stop = true;
    }

    private static void traditional() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (!stop) {

                    /**
                     * 这里 JVM 会对代码进行优化.
                     * 如果执行的方法体是空, 则会在方法内复制一份 stop 变量, while 条件只取这个变量.
                     * 导致主线程更改 stop 变量后, 子线程获取不到最新的变量值, 线程无法终结(比如 i++; 语句, 因为不存在方法调用, 所以也会优化).
                     * 而当方法内有方法的调用, JVM 为了防止栈溢出, 不会进行此优化.
                     * */
                    i++;
//                    call();
                }
            }
        };
        thread.start();
    }

    private static void call() {
        System.out.println("call...");
    }
}
