package pers.lyrichu.CSAlgorithm.Sort;

// 选择排序
public class Selection<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void sort(T nums[]){
        int length = nums.length;
        for(int i=0;i<length-1;i++){
            for(int j=i+1;j<length;j++){
                if(less(nums[j],nums[i])){
                    swap(nums,i,j);
                }
            }
        }
    }

    public static void main(String args[]){
        Selection<Integer> selection = new Selection<Integer>();
        Integer[] nums = new Integer[]{1,3,10,2,5,7};
        selection.sort(nums);
        for(int i:nums){
            System.out.print(i+" ");
        }
        System.out.println();
    }
}
