package pers.lyrichu.java.util.java8;

import java.util.Comparator;

public class ComparatorInterface {
    private static class Person {
        private String firstName;
        private String lastName;

        Person(String firstName,String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    public static void main(String[] args) {
        Comparator<Person> comparator = (p1,p2) -> p1.firstName.compareTo(p2.firstName);
        Person p1 = new Person("John","Doe");
        Person p2 = new Person("Alice","Wonderland");
        System.out.println(comparator.compare(p1,p2)); // > 0
        System.out.println(comparator.reversed().compare(p1,p2)); // < 0
    }
}
