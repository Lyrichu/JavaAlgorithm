package pers.lyrichu.LeetCode.Graph;

import org.junit.Test;

import static org.junit.Assert.*;

public class CourseSchedule2Test {
    @Test
    public void courseSchedule2Test() {
        CourseSchedule2 courseSchedule2 = new CourseSchedule2();
        int numCourses;
        int[][] prerequisites;
        numCourses = 2;
        prerequisites = new int[][]{{0,1}};
        assertArrayEquals(courseSchedule2.solution(numCourses,prerequisites),
                new int[]{1,0});
        numCourses = 5;
        prerequisites = new int[][]{{0,1},{1,2},{2,3},{3,4}};
        assertArrayEquals(courseSchedule2.solution(numCourses,prerequisites),
                new int[]{4,3,2,1,0});
    }
}