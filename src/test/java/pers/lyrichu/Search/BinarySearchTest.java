package pers.lyrichu.Search;

import org.junit.Test;

import static org.junit.Assert.*;

import pers.lyrichu.Search.BinarySearch;

public class BinarySearchTest {

    @Test
    public void search() {
        BinarySearch<Integer> binarySearch = new BinarySearch<Integer>();
        Integer[] arr = new Integer[]{1,3,5,18,20};
        int target = 5;
        int index;
        index = binarySearch.search(arr,target,0,arr.length-1);
        assertEquals(index,2);
        target = 1;
        index = binarySearch.search(arr,target,0,arr.length-1);
        assertEquals(index,0);
        index = binarySearch.search(arr,10,0,arr.length-1);
        assertEquals(index,-1);
        arr = new Integer[]{};
        index = binarySearch.search(arr,10,0,arr.length-1);
        assertEquals(index,-1);
    }
}