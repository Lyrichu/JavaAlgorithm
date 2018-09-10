package pers.lyrichu.Search;

import pers.lyrichu.Search.Search;

// 二分查找
public class BinarySearch<T extends Comparable<T>> extends Search<T> {
    @Override
    public int search(T[] arr, T target,int begin,int end) {
        while (begin <= end){
            int mid = (begin+end)/2;
            if(arr[mid].equals(target)){
                return mid;
            }else if(less(target,arr[mid])){
                end = mid-1;
            }else {
                begin = mid+1;
            }
        }
        return -1;
    }
}
