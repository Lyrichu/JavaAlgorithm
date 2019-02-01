package pers.lyrichu.Sort;

public class InsertSort<T extends Comparable<T>> extends Sort<T>{
    @Override
    public void sort(T[] nums){
        int length = nums.length;
        for(int i = 1;i<length;i++){
            for(int j = i-1;j>=0;j--){
                if(less(nums[j+1],nums[j])){
                    swap(nums,j,j+1);
                }else{
                    break;
                }
            }
        }
    }

    public static void main(String args[]){
        InsertSort<Integer> integerInsertSort = new InsertSort<Integer>();
        Integer[] nums = new Integer[]{1,0,10,2,-9,2,1,-2,10,0};
        integerInsertSort.sort(nums);
        for(int i:nums){
            System.out.print(i+" ");
        }
        System.out.println();
    }
}
