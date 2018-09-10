package pers.lyrichu.utils.DataStructure;

import org.junit.Test;

import static org.junit.Assert.*;
import pers.lyrichu.utils.DataStructure.TreeNode;
import pers.lyrichu.utils.DataStructure.BinaryTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryTreeTest {

    @Test
    public void setHead() {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>();
        binaryTree.setHead(new TreeNode<Integer>(3));
        assertEquals(binaryTree.getHead().getData().intValue(),3);
    }

    @Test
    public void getHead() {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>();
        binaryTree.insert(10);
        assertEquals(binaryTree.getHead().getData().intValue(),10);
    }

    @Test
    public void insert() {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>();
        binaryTree.insert(10);
        binaryTree.insert(Arrays.asList(1,3,10,2,4));
        List<Integer> list = new ArrayList<Integer>();
        binaryTree.levelTraversal(binaryTree.getHead(),list);
        for(int i:list){
            System.out.println(i);
        }
//        assertTrue(Arrays.asList(10,1,3,10,2,4).equals(list));
    }

    @Test
    public void insert1() {

    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void length() {
    }

    @Test
    public void height() {
    }

    @Test
    public void firstTraversal() {
    }

    @Test
    public void midTraversal() {
    }

    @Test
    public void lastTraversal() {
    }

    @Test
    public void levelPrint() {
    }
}