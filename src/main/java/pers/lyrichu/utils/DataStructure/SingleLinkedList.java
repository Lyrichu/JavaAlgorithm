package pers.lyrichu.utils.DataStructure;

import org.omg.CORBA.NO_IMPLEMENT;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SingleLinkedList<v> {
    private Node<v> head;

    public SingleLinkedList(v data){
        setHead(new Node<v>(data));
    }

    public SingleLinkedList(){
        setHead(new Node<v>());
    }

    public SingleLinkedList(List<v> list){
        insert(list);
    }
    public SingleLinkedList(Node<v> node){setHead(node);}

    public int length(){
        int len = 0;
        Node<v> node = head;
        if(isEmpty()){
            return 0;
        }
        len++;
        while (node.getNext() != null){
            len++;
            node = node.getNext();
        }
        return len;
    }

    public void setHead(Node<v> head) {
        this.head = head;
    }

    public Node<v> getHead() {
        return head;
    }

    public void print(){
        if(isEmpty()){
            System.out.println("Empty LinkedList!");
        }else {
            Node<v> node = head;
            System.out.print(node.getData()+" ");
            while (node.getNext() != null){
                node = node.getNext();
                System.out.print(node.getData()+" ");
            }
            System.out.println();
        }
    }

    public void insert(v data){
        if(isEmpty()){
            head.setData(data);
        }else {
            Node<v> node = head;
            while (node.getNext() != null){
                node = node.getNext();
            }
            Node<v> newNode = new Node<v>(data);
            node.setNext(newNode);
        }
    }


    public void insert(List<v> list){
        for(v data:list){
            insert(data);
        }
    }

    public Node<v> delete(v data){
        if(isEmpty()){
            // 空链表
            return null;
        }
        Node<v> currentNode = head;
        if(currentNode.getData().equals(data)){
            currentNode.setData(null);
            return currentNode;
        }
        while (currentNode.getNext() != null){
            Node<v> preNode = currentNode;
            currentNode = currentNode.getNext();
            if(currentNode.getData().equals(data)){
                preNode.setNext(currentNode.getNext());
                return currentNode;
            }
        }
        return null;
    }

    public Node<v> delete(Node<v> node){
        return delete(node.getData());
    }

    // 删除链表的第i个元素
    public v remove(int i){
        if(isEmpty()){
            return null;
        }
        int len = length();
        int index,count;
        count = 0;
        if(i >= len || i < -len){
            return null;
        }
        if(i < 0){
            index = len+i;
        }else{
            index = i;
        }
        Node<v> node = head;
        if(index == count){
            v data = head.getData();
            if(node.getNext() == null){
                head.setData(null);
            }else {
                setHead(head.getNext());
            }
            return data;
        }
        Node<v> preNode;
        while (node.getNext() != null){
            preNode = node;
            node = node.getNext();
            count++;
            if(count == index){
                preNode.setNext(node.getNext());
                return node.getData();
            }
        }
        return null;
    }

    // 删除链表第i到第j个元素
    public List<v> remove(int i,int j){
        int len = length();
        if(i < 0){
            if(i < -len){
                i = 0;
            }else {
                i = len+i;
            }
        }
        if(i > len){
            i = len;
        }
        if(j < 0){
            if(j < -len){
                j = 0;
            }else{
                j = len+j;
            }
        }
        if(j > len){
            j = len;
        }
        if(i > j){
            int tmp = i;
            i = j+1;
            j = tmp+1;
        }

        List<v> list = new ArrayList<v>();
        if(isEmpty() || i == j || i ==len){
            return list;
        }
        Node<v> node = head;
        int index = 0;
        if(i == index){
            list.add(node.getData());
            while (node.getNext() != null){
                index++;
                node = node.getNext();
                if(index == j){
                    setHead(node);
                    return list;
                }
                list.add(node.getData());
            }
            return list;
        }
        Node<v> currentNode,preNode;
        currentNode = new Node<v>();
        while (node.getNext() != null){
            preNode = node;
            node = node.getNext();
            index++;
            if(index == i){
                currentNode = preNode;
                list.add(node.getData());
            }
            if(index > i && index < j){
                list.add(node.getData());
            }
            if(index == j ){
                currentNode.setNext(node);
                return list;
            }
            if(node.getNext() == null){
                currentNode.setNext(null);
                return list;
            }
        }
        return list;
    }

    public boolean isEmpty(){
        return head.getData() == null;
    }

    public v get(int i){
        if(isEmpty()){
            return null;
        }
        int index = 0;
        Node<v> node = head;
        if(i == index){
            return node.getData();
        }
        while (node.getNext() != null){
            index++;
            node = node.getNext();
            if(index == i){
                return node.getData();
            }
        }
        return null;
    }

    // 获取从i开始长度为len的list data
    public List<v> get(int i,int len){
        return slice(i,i+len);
    }

    // 获取全部数据
    public List<v> get(){
        return get(0,length());
    }

    // 获取LinkedList i,j之间的元素
    // 返回List
    public List<v> slice(int i,int j){
        // 使i<= j
        if(i > j){
            int tmp = i;
            i = j;
            j = tmp;
        }
        Node<v> node = head;
        List<v> list = new ArrayList<v>();
        if(isEmpty()){
            return list;
        }
        int index = 0;
        if(i == index){
            list.add(node.getData());
        }
        while (node.getNext() != null){
            node = node.getNext();
            index++;
            if(index >= i && index <= j-1){
                list.add(node.getData());
            }
        }
        return list;
    }

    public v set(int i,v data){
        if(isEmpty()){
            head.setData(data);
            return data;
        }
        int index = 0;
        Node<v> node = head;
        if(index == i){
            node.setData(data);
            return data;
        }
        while (node.getNext() != null){
            index++;
            node = node.getNext();
            if(index==i){
                node.setData(data);
                return data;
            }
        }
        node.setNext(new Node<v>(data));
        return data;
    }


    public int indexOf(v data){
        if(isEmpty()){
            return -1;
        }
        Node<v> node = head;
        int index = 0;
        if(node.getData().equals(data)){
            return index;
        }
        while (node.getNext() != null){
            index++;
            node = node.getNext();
            if(node.getData().equals(data)){
                return index;
            }
        }
        return -1;
    }

    // reverse LinkedList
    public void reverse(){
        if(!isEmpty()){
            Stack<Node<v>> stack = new Stack<Node<v>>();
            Node<v> node = head;
            stack.push(node);
            while (node.getNext() != null){
                node = node.getNext();
                stack.push(node);
            }
            node = stack.pop();
            setHead(node);
            node = head;
            while (!stack.isEmpty()){
                node.setNext(stack.pop());
                node = node.getNext();
            }
            node.setNext(null);
        }
    }

    // make a copy of LinkedList
    public Node<v> copy(){
        if(isEmpty()){
            return new Node<v>();
        }
        Node<v> node = new Node<v>();
        Node<v> currentNode = head;
        node.setData(currentNode.getData());
        Node<v> headNode = node;
        while (currentNode.getNext() != null){
            currentNode = currentNode.getNext();
            node.setNext(new Node<v>(currentNode));
            node = node.getNext();
        }
        return headNode;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SingleLinkedList || obj instanceof Node){
            SingleLinkedList singleLinkedList;
            if(obj instanceof Node){
                singleLinkedList  = new SingleLinkedList((Node) obj);
            }else {
                singleLinkedList = (SingleLinkedList) obj;
            }

            if(this.isEmpty() && singleLinkedList.isEmpty()){
                return true;
            }else if(this.isEmpty() || singleLinkedList.isEmpty()){
                return false;
            }else {
                Node node1 = this.head;
                Node node2 = singleLinkedList.head;
                if(!node1.getData().equals(node2.getData())){
                    return false;
                }
                while (node1.getNext() != null && node2.getNext() != null){
                    node1 = node1.getNext();
                    node2 = node2.getNext();
                    if(!node1.getData().equals(node2.getData())){
                        return false;
                    }
                }
                return true;
            }
        }else {
            return false;
        }
    }
}


class Node<v> {
    private v data;
    private Node<v> next = null;

    public Node(v data){
        this.data = data;
    }
    public Node(Node<v> node){this.data = node.getData();}
    public Node(){
        this.data = null;
    }
    public v getData() {
        return data;
    }

    public void setData(v data) {
        this.data = data;
    }

    public Node<v> getNext() {
        return next;
    }

    public void setNext(Node<v> next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Node){
            return (new SingleLinkedList<v>(this)).equals(new SingleLinkedList<v>((Node) obj));
        }else if(obj instanceof SingleLinkedList){
            return ((SingleLinkedList) obj).equals(new SingleLinkedList<v>(this));
        }else {
            return false;
        }
    }
}

