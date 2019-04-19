package pers.lyrichu.LeetCode.Graph;

import org.junit.Test;

import static org.junit.Assert.*;

public class CourseScheduleTest {
    @Test
    public void courseScheduleTest() {
        CourseSchedule courseSchedule = new CourseSchedule();
        int numCourses;
        int[][] prerequisites;
        numCourses = 2;
        prerequisites = new int[][]{{1,0}};
        assertTrue(courseSchedule.solution(numCourses,prerequisites));
        numCourses = 3;
        prerequisites = new int[][]{{0,1},{1,2}};
        assertTrue(courseSchedule.solution(numCourses,prerequisites));
        numCourses = 4;
        prerequisites = new int[][]{{1,0},{0,2},{2,3},{3,1}};
        assertFalse(courseSchedule.solution(numCourses,prerequisites));
    }
}