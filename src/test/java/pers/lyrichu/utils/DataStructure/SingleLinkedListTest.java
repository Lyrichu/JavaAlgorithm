package pers.lyrichu.utils.DataStructure;

import org.junit.Test;

import static org.junit.Assert.*;
import pers.lyrichu.utils.DataStructure.SingleLinkedList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleLinkedListTest {

    @Test
    public void length() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(3);
        list.add(1);
        singleLinkedList.insert(list);
        assertEquals(singleLinkedList.length(),3);
    }

    @Test
    public void setHead() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        Node<Integer> node = new Node<Integer>(3);
        singleLinkedList.setHead(node);
        assertEquals(singleLinkedList.getHead().getData().intValue(),3);
    }

    @Test
    public void getHead() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>(4);
        assertEquals(singleLinkedList.getHead().getData().intValue(),4);
    }

    @Test
    public void print() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(3);
        list.add(1);
        singleLinkedList.insert(list);
        singleLinkedList.print();
    }

    @Test
    public void insert() { // insert list of data
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(3);
        list.add(1);
        singleLinkedList.insert(list);
        List<Integer> list1 = singleLinkedList.get(0,3);
        assertTrue(list.equals(list1));
    }

    @Test
    public void insert1() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>(10);
        singleLinkedList.insert(3);
        assertTrue(singleLinkedList.slice(0,2).equals(new ArrayList<Integer>(Arrays.asList(10,3))));
    }

    @Test
    public void delete() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = Arrays.asList(10,9,1,20,3);
        singleLinkedList.insert(list);
        singleLinkedList.delete(new Node<Integer>(20));
        assertTrue(singleLinkedList.get(0,4).equals(Arrays.asList(10,9,1,3)));
    }

    @Test
    public void delete1(){
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = Arrays.asList(10,9,1,20,3);
        singleLinkedList.insert(list);
        singleLinkedList.delete(9);
        assertTrue(singleLinkedList.get(0,4).equals(Arrays.asList(10,1,20,3)));
    }

    @Test
    public void isEmpty() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        assertTrue(singleLinkedList.isEmpty());
        singleLinkedList.insert(10);
        assertTrue(!singleLinkedList.isEmpty());
    }

    @Test
    public void get() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = Arrays.asList(10,9,1,20,3);
        singleLinkedList.insert(list);
        assertEquals(singleLinkedList.get(2).intValue(),1);
        assertEquals(singleLinkedList.get(10),null);
    }

    @Test
    public void get1() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = Arrays.asList(10,9,1,20,3);
        singleLinkedList.insert(list);
        assertEquals(singleLinkedList.get(1,2),Arrays.asList(9,1));
    }

    @Test
    public void slice() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = Arrays.asList(10,9,1,20,3);
        singleLinkedList.insert(list);
        assertEquals(Arrays.asList(9,1,20),singleLinkedList.slice(1,4));
    }

    @Test
    public void set() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = Arrays.asList(10,9,1,20,3);
        singleLinkedList.insert(list);
        singleLinkedList.set(4,30);
        assertEquals(singleLinkedList.get(4).intValue(),30);
    }

    @Test
    public void indexOf() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = Arrays.asList(10,9,1,20,3);
        singleLinkedList.insert(list);
        assertEquals(singleLinkedList.indexOf(1),2);
    }

    @Test
    public void remove(){
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        assertNull(singleLinkedList.remove(3));
        List<Integer> list = Arrays.asList(10,9,1,20,3);
        singleLinkedList.insert(list);
        singleLinkedList.remove(2);
        assertEquals(singleLinkedList.get(0,singleLinkedList.length()),Arrays.asList(10,9,20,3));
    }

    @Test
    public void remove1(){
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = Arrays.asList(10,9,1,20,3);
        singleLinkedList.insert(list);
        singleLinkedList.remove(1,3);
        assertEquals(singleLinkedList.get(0,singleLinkedList.length()),Arrays.asList(10,20,3));
        singleLinkedList.insert(Arrays.asList(1,2,10)); // [10,20,3,1,2,10]
        singleLinkedList.remove(-1,-3);//[10,20,3,1]
        assertEquals(singleLinkedList.get(),Arrays.asList(10,20,3,1));
        singleLinkedList.remove(-4,2); //[3,1]
        assertEquals(Arrays.asList(3,1),singleLinkedList.get());
        singleLinkedList.remove(0); //[1]
        assertEquals(Arrays.asList(1),singleLinkedList.get());
    }

    @Test
    public void reverse(){
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = Arrays.asList(10,9,1,20,3);
        singleLinkedList.insert(list);
        singleLinkedList.reverse();
        assertEquals(Arrays.asList(3,20,1,9,10),singleLinkedList.get());
    }

    @Test
    public void copy(){
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();
        List<Integer> list = Arrays.asList(10,9,1,20,3);
        singleLinkedList.insert(list);
        Node<Integer> node = singleLinkedList.copy();
        assertEquals(list,(new SingleLinkedList<Integer>(node)).get());
    }


}