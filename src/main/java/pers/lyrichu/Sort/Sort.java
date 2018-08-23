package pers.lyrichu.Sort;

public abstract class Sort<T extends Comparable<T>> {
    public  abstract void sort(T[] nums);

    protected boolean less(T a,T b){
        return a.compareTo(b) < 0;
    }

    protected void swap(T[] nums,int i,int j){
        T tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

}
