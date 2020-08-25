package pers.lyrichu.java.thirdparty.hutool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionTest {

    private String name = "hcc";

    private int add (int a,int b) {
        return a + b;
    }

    public static void main(String[] args) throws Exception {
        Class cls = ReflectionTest.class;

        Field nameField = cls.getDeclaredField("name");

        ReflectionTest rt = (ReflectionTest) cls.newInstance();
        System.out.println(rt.name);

        nameField.set(rt,"pdd");
        System.out.println(rt.name);
    }
}
