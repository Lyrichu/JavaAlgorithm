package pers.lyrichu.Search;

// 旋转数组的最小值
// 设置两个指针begin,end分别指向数组开始和结尾
// 计算mid的值:
// if(end-begin == 1):这说明两个指针相邻,此时arr[end]就是最小元素
// if arr[mid] == arr[begin] == arr[end],这说明无法判断最小元素位于数组左边还是右边，此时需要顺序查找
// if arr[mid] > arr[end],中间元素仍然位于第一个递增数组,则最小元素位于mid的右边,令begin = mid;
// if arr[mid] < arr[begin],arr[mid]位于第二个递增数组，需要去左边找最小元素,令end = mid;
public class RotationArrayMin {
    public static int rotationArrayMin(int [] arr){
        if(arr.length == 0){
            return -1;
        }
        int begin = 0;
        int end = arr.length-1;
        int mid;
        int minIndex = 0;
        while (arr[begin]>=arr[end]){
            mid = (begin+end)/2;
            if(end-begin == 1){
                minIndex = end;
                break;
            }
            // 需要顺序查找
            if(arr[mid] == arr[begin] && arr[mid] == arr[end]){
                minIndex = linearSearchMin(arr,begin,end);
                break;
            }else if(arr[mid] >= arr[end]){
                begin = mid;
            }else if(arr[mid] <= arr[begin]){
                end = mid;
            }
        }
        return arr[minIndex];
    }

    public static int linearSearchMin(int[] arr,int begin,int end){
        if(begin > end){
            return -1;
        }
        int minIndex = begin;
        for(int i=0;i<=end;i++){
            if(arr[i] < arr[minIndex]){
                minIndex = i;
            }
        }
        return minIndex;
    }
}
