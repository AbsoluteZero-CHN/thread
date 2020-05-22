package cn.noload.chapter_4;

/**
 * 指令重排序测试
 * @author hao.caosh@ttpai.cn
 * @date 2020-05-22 14:42
 */
public class DirectOrder {

    private static int X = 0, Y = 0;
    private static int A = 0, B = 0;

    public static void main(String[] args) throws InterruptedException {
        int count = 0;
        // happens before 第一条原则保证: 单线程中书写在前面的操作一定 happens before 后面的操作
        while (true) {
            count++;
            X = 0; Y = 0; A = 0; B = 0;
            Thread thread_1 = new Thread(() -> {
                A = 1;
                X = B;
            });
            Thread thread_2 = new Thread(() -> {
                B = 1;
                Y = A;
            });
            thread_1.start(); thread_2.start();
            thread_1.join(); thread_2.join();
            if(X == 0 && Y == 0) {
                System.err.println(String.format("第 %d 次执行, (X = %d, Y = %d)", count, X, Y));
                break;
            }
        }
        // 第 112331 次执行, (X = 0, Y = 0)
    }
}
