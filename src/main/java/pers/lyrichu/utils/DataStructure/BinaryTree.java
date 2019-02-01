package pers.lyrichu.utils.DataStructure;

import java.util.*;

// 二叉树
public class BinaryTree<v> {
    private TreeNode<v> head;
    private List<TreeNode<v>> treeList = new ArrayList<TreeNode<v>>();

    public BinaryTree(){
        setHead(new TreeNode<v>());
    }

    public BinaryTree(v data){
        setHead(new TreeNode<v>(data));
    }

    public BinaryTree(TreeNode<v> node){
        setHead(node);
    }

    public void setHead(TreeNode<v> head) {
        this.head = head;
    }

    public TreeNode<v> getHead() {
        return head;
    }

    // 二叉树插入数据
    public v insert(v data){
        if(head.getData() == null){
            head.setData(data);
            treeList.add(head);
            return data;
        }
        TreeNode<v> node = treeList.get(0);
        TreeNode<v> newNode = new TreeNode<v>(data);
        if(node.getLeft() == null){
            node.setLeft(newNode);
            treeList.add(newNode);
            return data;
        }else {
            node.setRight(newNode);
            treeList.add(newNode);
            // 根节点左右子树都添加了
            // 将根节点从队列移除
            treeList.remove(0);
            return data;
        }
    }

    public v insert(TreeNode<v> node){
       return insert(node.getData());
    }

    public List<v> insert(List<v> list){
        for(v data:list){
            insert(data);
        }
        return list;
    }

    public boolean isEmpty(){
        return getHead().getData() == null;
    }
    // 返回二叉树节点个数
    public int length(TreeNode<v> node){
        if(node == null){
            return 0;
        }
        return length(node.getLeft())+1+length(node.getRight());
    }

    public int height(TreeNode<v> node){
        if(node == null){
            return 0;
        }
        return Math.max(height(node.getLeft()),height(node.getRight()))+1;
    }

    public void firstTraversal(TreeNode<v> node,List<v> list){
        if(node == null){
            return;
        }
        list.add(node.getData());
        firstTraversal(node.getLeft(),list);
        firstTraversal(node.getRight(),list);
    }

    public void midTraversal(TreeNode<v> node,List<v> list){
        if(node == null){
            return;
        }
        midTraversal(node.getLeft(),list);
        list.add(node.getData());
        midTraversal(node.getRight(),list);
    }

    public void lastTraversal(TreeNode<v> node,List<v> list){
        if(node == null){
            return;
        }
        lastTraversal(node.getLeft(),list);
        lastTraversal(node.getRight(),list);
        list.add(node.getData());
    }

    public void levelTraversal(TreeNode<v> node,List<v> list){
        List<TreeNode<v>> tmpList = new ArrayList<TreeNode<v>>();
        if(node != null && node.getData() != null){
            list.add(node.getData());
            tmpList.add(node);
            while (!tmpList.isEmpty()){
                TreeNode<v> currentNode = tmpList.get(0);
                if(currentNode.getLeft() != null){
                    tmpList.add(currentNode.getLeft());
                }
                if(currentNode.getRight() != null){
                    tmpList.add(currentNode.getRight());
                }
                list.add(currentNode.getData());
                tmpList.remove(0);
            }
        }
    }







}

class TreeNode<v> {
    private v data;
    private TreeNode<v> left = null;
    private TreeNode<v> right = null;


    public TreeNode(v data){
        this.data = data;
    }
    public TreeNode(){
        this.data = null;
    }

    public void setData(v data) {
        this.data = data;
    }

    public v getData() {
        return data;
    }

    public void setLeft(TreeNode<v> left) {
        this.left = left;
    }

    public void setRight(TreeNode<v> right) {
        this.right = right;
    }

    public TreeNode<v> getLeft() {
        return left;
    }

    public TreeNode<v> getRight() {
        return right;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof TreeNode){
            return
                    this.getData().equals(((TreeNode) obj).getData())
                    && equals(this.getLeft(),((TreeNode) obj).getLeft())
                    && equals(this.getRight(),((TreeNode) obj).getRight());
        }
        return false;
    }

    public boolean equals(TreeNode<v> node1,TreeNode<v> node2){
        if(node1 == null && node2 == null){
            return true;
        }else if(node1 == null || node2 == null){
            return false;
        }else {
            return node1.getData().equals(node2.getData())
                    && equals(node1.getLeft(),node2.getLeft())
                    && equals(node1.getRight(),node2.getRight());
        }
    }

}
