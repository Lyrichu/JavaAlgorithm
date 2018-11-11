package pers.lyrichu.java.util.java8;

public class StaticMethodReference {
    interface Converter<F,T> {
        T convert(F from);
    }

    interface PersonFactory<P extends Person> {
        P create(String firstName,String lastName);
    }

    static class Person {
        String firstName;
        String lastName;
        // 两个不同的构造函数
        Person(){}

        Person(String firstName,String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    public static void main(String[] args) {
        // 使用静态方法引用
        Converter<String,Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);
        // 构造函数的关键字引用
        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("chengchun","Hu");
        System.out.println("firstName:"+person.firstName);
        System.out.println("lastName:"+person.lastName);
    }
}
