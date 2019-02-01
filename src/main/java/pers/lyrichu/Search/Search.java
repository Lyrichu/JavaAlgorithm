package pers.lyrichu.Search;

public abstract class Search<T extends Comparable<T>> {
    public abstract int search(T[]arr,T target,int begin,int end);

    public boolean less(T a,T b){
        return a.compareTo(b) < 0;
    }
}
