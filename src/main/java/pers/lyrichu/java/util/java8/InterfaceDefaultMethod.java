package pers.lyrichu.java.util.java8;


import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class InterfaceDefaultMethod {
    interface Formula {
        double calculate(int a);
        // 使用default 关键字可以在接口中定义
        // 一个默认方法
        default double sqrt(int a) {
            return Math.sqrt(a);
        }
    }

    public static void main(String[] args) {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(100*a);
            }
        };
        double d = formula.calculate(1);
        System.out.println("d:"+d);
    }
}


