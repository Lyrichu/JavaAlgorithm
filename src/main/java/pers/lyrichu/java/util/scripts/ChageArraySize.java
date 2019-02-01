package pers.lyrichu.java.util.scripts;

public class ChageArraySize {
    public static void main(String[] args) {
        int[] arr = {1,3,2};
        arr = (int[]) resizeArray(arr,5);
        arr[3] = 10;
        arr[4] = 20;
        for (int i:arr) {
            System.out.println(i);
        }
    }

    private static Object resizeArray(Object oldArray,int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(elementType,newSize);
        int preserveLength = Math.min(oldSize,newSize);
        if (preserveLength > 0) {
            System.arraycopy(oldArray,0,newArray,0,preserveLength);
        }
        return newArray;
    }
}
