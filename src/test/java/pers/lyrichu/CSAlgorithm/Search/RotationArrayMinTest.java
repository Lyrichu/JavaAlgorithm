package pers.lyrichu.CSAlgorithm.Search;

import org.junit.Test;

import static org.junit.Assert.*;
import pers.lyrichu.CSAlgorithm.Search.RotationArrayMin;

public class RotationArrayMinTest {

    @Test
    public void rotationArrayMin() {
        int[] arr = new int[]{1,2,4,0,1};
        int min = RotationArrayMin.rotationArrayMin(arr);
        assertEquals(min,0);
        arr = new int[]{0,1,1,1,0};
        min = RotationArrayMin.rotationArrayMin(arr);
        assertEquals(0,min);
        arr = new int[]{};
        min = RotationArrayMin.rotationArrayMin(arr);
        assertEquals(-1,min);
        arr = new int[]{1,1,1,1,2};
        min = RotationArrayMin.rotationArrayMin(arr);
        assertEquals(1,min);
    }

    @Test
    public void linearSearchMin() {
        int[] arr = new int[]{1,2,0,1,3};
        int index = RotationArrayMin.linearSearchMin(arr,0,arr.length-1);
        assertEquals(index,2);
    }
}