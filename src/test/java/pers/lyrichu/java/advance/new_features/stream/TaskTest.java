package pers.lyrichu.java.advance.new_features.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskTest {

    @Test
    public void test01() {

        List<Task> tasks = new ArrayList<>(Arrays.asList(
             new Task(Status.OPENED,10),
             new Task(Status.CLOSED,2),
             new Task(Status.OPENED,5)
        ));
        // 计算 status = opened 的 总 points
        int sumOpenedPoints = tasks.stream()
                .filter(task -> task.getStatus() == Status.OPENED)
                .mapToInt(Task::getPoint)
                .sum();
        System.out.println("sumOpenedPoints:" + sumOpenedPoints);
        // using map_reduce
        int totalPoints = tasks.stream()
                .parallel()
                .map(Task::getPoint)
                .reduce(0,Integer::sum);
        System.out.println("totalPoints:" + totalPoints);
        // group by status
        Map<Status,List<Task>> tasksMap = tasks.stream().collect(Collectors.groupingBy(Task::getStatus));
        System.out.println(tasksMap);

        // 计算每个task的百分比
        List<String> tasksPercent = tasks.stream()
                .mapToInt(Task::getPoint)
                .asLongStream()
                .mapToDouble(p -> p / (double) totalPoints)
                .boxed()
                .mapToLong(weight -> (long) (weight * 100))
                .mapToObj(w -> w + "%")
                .collect(Collectors.toList());
        System.out.println(tasksPercent);

    }


    @AllArgsConstructor
    @Data
    class Task {
        private Status status;
        private int point;
    }

    enum Status {
        OPENED,
        CLOSED
    }
}
