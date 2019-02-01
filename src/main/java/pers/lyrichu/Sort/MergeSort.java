package pers.lyrichu.Sort;

public class MergeSort<T extends Comparable<T>> extends Sort<T>{
    protected T[] tmp;

    public void setTmp(T[] tmp){
        this.tmp = tmp;
    }

    @Override
    public void sort(T[] nums){
        int length = nums.length;
        sort(nums,0,length-1);
    }

    public void sort(T[] nums,int begin,int end){
        if(begin >= end){
            return;
        }
        int middle = begin + (end-begin)/2;
        sort(nums,begin,middle);
        sort(nums,middle+1,end);
        merge(nums,begin,middle,end);
    }

    public void merge(T[] nums,int begin,int middle,int end){
        int i = begin;
        int j = middle+1;
        int k = begin;
        for(int a = begin;a<=end;a++){
            tmp[a] = nums[a];
        }
        while (i<=middle && j <= end){
            if(less(nums[i],nums[j])){
                tmp[k] = nums[i];
                i += 1;
            }else {
                tmp[k] = nums[j];
                j += 1;
            }
            k += 1;
        }
        while (i <= middle){
            tmp[k] = nums[i];
            k += 1;
            i += 1;
        }
        while (j <= end){
            tmp[k] = nums[j];
            k +=1;
            j += 1;
        }
        for(i=begin;i<=end;i++){
            nums[i] = tmp[i];
        }
    }

    public static void main(String args[]){
        MergeSort<Integer> mergeSort = new MergeSort<Integer>();
        Integer[] nums = new Integer[]{0,2,1,4,2,3,2,-10,2,5,6,1,-20,8,1,9,4,3,2,1};
        mergeSort.setTmp(new Integer[nums.length]);
        mergeSort.sort(nums);
        for(int i:nums){
            System.out.print(i+" ");
        }
        System.out.println();
    }
}
