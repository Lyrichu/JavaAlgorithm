package pers.lyrichu.java.basic.thread;

import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * 一个简单的自定义 callable 多线程例子
 */
public class CallableTest {

    private static boolean done = false;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Date begin = new Date();
        Calculate calculate = new Calculate();
        int c = 0;

        //异步计算
        int two = 0;
        RunnableDemo runnableDemo = new RunnableDemo();
        runnableDemo.setCalculate(calculate);
        new Thread(runnableDemo, "有返回值的线程").start();
        int one = calculate.calOne();
        while (!runnableDemo.isDone()) {
        }
        two = runnableDemo.getResult();

        //求和
        c = one + two;
        Date end = new Date();
        System.out.println("计算结果：" + c);
        long aaa = end.getTime() - begin.getTime();
        System.out.println("计算耗时：" + aaa);
    }

    static class Calculate {

        public int calOne() throws InterruptedException {
            Thread.sleep(1000);
            return 10;
        }

        public int calTwo() throws InterruptedException {
            Thread.sleep(500);
            return 5;
        }

    }

    static class RunnableDemo implements Runnable {
        private Calculate calculate;

        private Integer result;


        @Override
        public void run() {
            try {
                result = calculate.calTwo();
                done = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public Calculate getCalculate() {
            return calculate;
        }

        public void setCalculate(Calculate calculate) {
            this.calculate = calculate;
        }

        public Integer getResult() {
            return result;
        }

        public void setResult(Integer result) {
            this.result = result;
        }

        public boolean isDone() {
            return done;
        }
    }
}

