package pers.lyrichu.utils.DataStructure;

import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void getData() {
        Node<Integer> node = new Node<Integer>(3);
        assertEquals(node.getData().intValue(),3);
    }

    @Test
    public void setData() {
        Node<Integer> node = new Node<Integer>(3);
        node.setData(4);
        assertEquals(node.getData().intValue(),4);
    }

    @Test
    public void getNext() {
        Node<Integer> node = new Node<Integer>(3);
        node.setNext(new Node<Integer>(10));
        assertEquals(node.getNext().getData().intValue(),10);
    }

    @Test
    public void setNext() {
        Node<Integer> node = new Node<Integer>(3);
        node.setNext(new Node<Integer>(10));
        node.setNext(new Node<Integer>(20));
        assertEquals(node.getNext().getData().intValue(),20);
    }

    @Test
    public void equals() {
        Node<Integer> node = new Node<Integer>(3);
        node.setNext(new Node<Integer>(10));
        Node<Integer> node1 = new Node<Integer>(3);
        node1.setNext(new Node<Integer>(20));
        assertTrue(!node.equals(node1));
        node1.getNext().setData(10);
        assertTrue(node.equals(node1));
    }
}