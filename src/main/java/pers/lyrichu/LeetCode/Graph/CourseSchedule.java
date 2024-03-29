package pers.lyrichu.LeetCode.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * 课程表问题,https://leetcode-cn.com/problems/course-schedule/
 * reference:https://www.cnblogs.com/grandyang/p/4484571.html
 */
public class CourseSchedule {
    /*
     * @param numCourses:课程总数
     * @param prerequisites:课程之间的依赖关系
     */
    public boolean solution(int numCourses,int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0;i < numCourses;i++) {
            graph.add(new ArrayList<>());
        }
        int[] in = new int[numCourses];
        for (int[] arr:prerequisites) {
            // arr[1] 之后可以学习的全部课程列表
            graph.get(arr[1]).add(arr[0]);
            // 入度递增
            in[arr[0]]++;
        }
        // 队列中保存的全部都是入度为0的课程,即没有依赖的课程
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0;i < numCourses;i++) {
            if (in[i] == 0) {
                // queue 添加元素使用offer方法而不要使用add方法
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            // 返回头部元素并且删除
            int a = queue.poll();
            //遍历课程a的全部后继课程
            for (int i:graph.get(a)) {
                // 每个后继课程的度都要减1，因为其前面的依赖课程a已经可以移除了
                in[i]--;
                if (in[i] == 0) {
                    queue.offer(i);
                }
            }
        }
        // 最后所有的课程都不应该再有依赖了，即全部课程的入度都应该为0,如果不是，则说明存在环形依赖
        for (int i = 0;i < numCourses;i++) {
            if (in[i] != 0) {
                return false;
            }
        }
        return true;
    }
}

