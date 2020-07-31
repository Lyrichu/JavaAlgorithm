package pers.lyrichu.java.advance.new_features.lambda_expression;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * java8 方法引用
 */
public class MethodReferenceTest {

    @Test
    public void test1() {
        final Car car = Car.create(Car::new);
        List<Car> cars = Arrays.asList(car);
        // 类方法引用
        cars.forEach(Car::drive);
        // 普通的 lambda expression
        cars.forEach(car1 -> car1.drive());
        // 静态 方法调用
        cars.forEach(car1 -> Car.repair());
    }

    static class Car {

        public static Car create(final Supplier<Car> supplier) {
            return supplier.get();
        }

        public void drive(String personName) {
            System.out.printf("%s drive %s\n",personName,this.toString());
        }

        public void drive() {
            System.out.println("I'm driving car...");
        }

        public static void repair() {
            System.err.println("This broken car needs repair!");
        }

        @Override
        public String toString() {
            return "CAR";
        }
    }
}
