package pers.lyrichu.CSAlgorithm.Sort;

// 冒泡排序
public class BubbleSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void sort(T[] nums){
        int length = nums.length;
        for(int i = length-1;i>=1;i--){
            for(int j = 0;j<i;j++){
                if(less(nums[j+1],nums[j])){
                    swap(nums,j,j+1);
                }
            }
        }
    }

    public static void main(String args[]){
        BubbleSort<Integer> bubbleSort = new BubbleSort<Integer>();
        Integer[] nums = new Integer[]{10,2,4,0,1,-5,1};
        bubbleSort.sort(nums);
        for(int i:nums){
            System.out.print(i+" ");
        }
        System.out.println();
    }
}
