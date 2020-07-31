package pers.lyrichu.java.advance.new_features.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class BasicTest {

    /**
     * 通过反射在运行时获取参数名称,javac -parameters 编译才能正确输出 args
     */
    public static void main(String[] args) throws Exception {
        // 反射获取main
        Method method = BasicTest.class.getMethod("main",String[].class);
        for (Parameter parameter : method.getParameters()) {
            System.out.println(parameter.getName());
        }
    }

}
