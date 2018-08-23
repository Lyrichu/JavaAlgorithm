package pers.lyrichu.Sort;

public class QuickSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void sort(T[] nums){
        int length = nums.length;
        sort(nums,0,length-1);
    }

    public void sort(T[] nums,int begin,int end){
        if(begin >= end){
            return;
        }
        int index = partition(nums,begin,end);
        sort(nums,begin,index-1);
        sort(nums,index+1,end);
    }

    public int partition(T[] nums,int begin,int end){
        int i = begin;
        int j = end+1;
        T key = nums[i];
        while (true){
            while (less(nums[++i],key) && i != end);
            while (less(key,nums[--j]) && j != begin);
            if(i>=j){
                break;
            }
            swap(nums,i,j);
        }
        swap(nums,begin,j);
        return j;
    }

    public static void main(String args[]){
        QuickSort<Integer> quickSort = new QuickSort<Integer>();
        Integer[] nums = new Integer[]{1,2,4,0,1,-10,2,3,6,2,1};
        quickSort.sort(nums);
        for(int i:nums){
            System.out.print(i+" ");
        }
        System.out.println();
    }
}
