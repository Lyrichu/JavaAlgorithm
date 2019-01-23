package pers.lyrichu.CSAlgorithm.Sort;

public class HeapSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    public void sort(T[] nums){
        int length = nums.length;
        buildHeap(nums);
        for(int i=length;i>=2;i--){
            swap(nums,0,i-1);
            maxHeap(nums,i-1,1);
        }
    }

    public void buildHeap(T[] nums){
        // 构建最大堆,从最后一个非叶子节点开始，
        // 从右到左,从上到下进行调整
        // 第一个非叶子节点的位置为n/2,n是数组大小(从１开始计数)
        int length = nums.length;
        for(int i=length/2;i>=1;i--){
            maxHeap(nums,length,i);
        }
    }

    public void maxHeap(T[] nums,int heapSize,int index){
        // heapSize:堆大小
        // index:调整堆的位置
        int left = 2*index;
        int right = 2*index+1;
        int max = index;
        if(left <= heapSize && less(nums[max-1],nums[left-1])){
            max = left;
        }
        if(right <= heapSize && less(nums[max-1],nums[right-1])){
            max = right;
        }
        if(max != index){
            swap(nums,index-1,max-1);
            maxHeap(nums,heapSize,max);
        }
    }

    public static void main(String args[]){
        HeapSort<Integer> heapSort = new HeapSort<Integer>();
        Integer[] nums = new Integer[]{1,0,0,2,-2,-4,29,3,3};
        heapSort.sort(nums);
        for(int i:nums){
            System.out.print(i+" ");
        }
        System.out.println();
    }
}
