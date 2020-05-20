package cn.noload.chapter_4;

/**
 * 缓存行测试
 *
 * Java8 以后提供了 {@link sun.misc.Contended} 注解来填充对象, 使缓存行不失效
 * @author hao.caosh@ttpai.cn
 * @date 2020-05-20 10:39
 */
public class CacheLine {
    static long[][] arr;

    private static final int ROW_LEN = 5 * 1024 * 1024;

    public static void main(String[] args) {
        arr = new long[ROW_LEN][8];
        long sum = 0L;
        // 横向遍历
        long marked = System.currentTimeMillis();
        for (int i = 0; i < ROW_LEN; i += 1) {
            for (int j = 0; j < 8; j++) {
                sum += arr[i][j];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked)+ "ms");

        marked = System.currentTimeMillis();
        // 纵向遍历
        // 由于缓存行大小为 64byte, arr 共 8 列, 遍历按列取数据导致缓存行失效, 所以速度很慢
        for (int i = 0; i < 8; i += 1) {
            for (int j = 0; j < ROW_LEN; j++) {
                sum += arr[j][i];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked)+ "ms");
    }
}
