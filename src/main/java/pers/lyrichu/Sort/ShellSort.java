package pers.lyrichu.Sort;

public class ShellSort<T extends Comparable<T>> extends Sort<T>  {
    @Override
    public void sort(T[] nums){
        int length = nums.length;
        int N = length/2;
        while (N >= 1){
            for(int i=0;i<N;i++){
                for(int j=i;j<length-N;j+=N){
                    for(int k=j;k>=i;k-=N){
                        if(less(nums[k+N],nums[k])){
                            swap(nums,k+N,k);
                        }else{
                            break;
                        }
                    }
                }
            }
            N /= 2;
        }
    }

    public static void main(String args[]){
        ShellSort<Integer> shellSort = new ShellSort<Integer>();
        Integer[] nums = new Integer[]{0,2,-2,10,9,2,1,4,0,100,-3,-10,0,2,1};
        shellSort.sort(nums);
        for(int i:nums){
            System.out.print(i+" ");
        }
        System.out.println();
    }
}
