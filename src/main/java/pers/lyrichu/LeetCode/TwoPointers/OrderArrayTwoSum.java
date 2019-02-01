package pers.lyrichu.LeetCode.TwoPointers;

// 判断有序数组是否存在两个数的和为给定值
public class OrderArrayTwoSum {
    public static void main(String args[]){
        int[] arr = new int[]{1,2,4,5,10,12};
        int target = 8;
        int[] res;
        res = solution(arr,target);
        if(res == null){
            System.out.println("数组没有找到和为"+target+"的两个数!");
        }else{
            System.out.printf("arr[%d](=%d)+arr[%d](=%d) = %d!\n",
                    res[0]-1,
                    arr[res[0]-1],
                    res[1]-1,
                    arr[res[1]-1],
                    target
            );
        }
    }
    // 设定两个指针分别指向数组的首和尾
    public static int[] solution(int[] arr,int target){
        int i = 0;
        int j = arr.length-1;
        while (i < j){
            if(arr[i] + arr[j] == target){
                return new int[]{i+1,j+1};
            }else if(arr[i]+arr[j] < target){
                i++;
            }else{
                j--;
            }
        }
        return null;
    }
}
