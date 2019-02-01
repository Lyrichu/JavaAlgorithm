package pers.lyrichu.java.util.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

// 并行排序加快排序速度
public class ParallelStream {
    private static int max = 1000000;
    private static List<String> list = new ArrayList<>(max);


    public static void main(String[] args) {
        randomInit(list,max);
        // 采用普通的stream排序
        long t0 = System.nanoTime();
        list.stream().sorted().count(); // 原始的list不变,注意这里需要count来触发真正的排序操作
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1-t0);
        System.out.println(String.format("stream sort costs %d ms.",millis));

        t0 = System.nanoTime();
        list.parallelStream().sorted().count();
        t1 = System.nanoTime();
        millis = TimeUnit.NANOSECONDS.toMillis(t1-t0);
        System.out.println(String.format("parallel stream sort costs %d ms.",millis));
    }

    private static void randomInit(List<String> list,int max) {
        for (int i = 0;i < max;i++) {
            UUID uuid = UUID.randomUUID();
            list.add(uuid.toString());
        }
    }
}
