package pers.lyrichu.java.util.scripts;

public class GetCurrentMethodName {
    public static void main(String[] args) {
        // 得到当前方法的名字
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("current merhod:"+methodName);
    }
}
