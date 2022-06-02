/*
    Name: Chapter 4, Question 37
    Description: Write a method that takes as input a binary search tree, T, and two keys k1 and k2,
    which are ordered so that k1 ≤ k2, and prints all elements X in the tree such that
    k1 ≤ Key(X) ≤ k2. Do not assume any information about the type of keys except
    that they can be ordered (consistently). Your program should run in O(K + logN)
    average time, where K is the number of keys printed. Bound the running time of
    your algorithm
    Made by: Kevin Boudreaux
    ID: KCB18002
    Made on: 10/21/2021
*/
public class Main {

    public static void main(String[] args) {
        BinaryTree tree =new BinaryTree();
        
        //building a binary tree
        tree.insert(20,String.valueOf(20));
        tree.insert(15,String.valueOf(15));
        tree.insert(17,String.valueOf(17));
        tree.insert(21,String.valueOf(21));
        tree.insert(30,String.valueOf(30));
        tree.insert(35,String.valueOf(35));
        tree.insert(25,String.valueOf(25));
        tree.insert(40,String.valueOf(40));
        tree.insert(39,String.valueOf(39));
        tree.insert(45,String.valueOf(45));
        tree.insert(5,String.valueOf(5));
        tree.insert(16,String.valueOf(16));
        
        
        PrintingTreeWithBounds(tree, 16, 35);
    }
    public static void PrintingTreeWithBounds(BinaryTree tree, Comparable k1, Comparable k2)
    {
        //if k1 > k2 flip the values of k1 and k2
        if(k1.compareTo(k2) > 0)
        {
            Comparable t = k2;
            k2 = k1;
            k1 = t;
        }
        PrintingTreeWithBoundsHelper(tree.getHead(), k1, k2);
    }
    private static void PrintingTreeWithBoundsHelper(node temp, Comparable k1, Comparable k2)
    {
        //if the node is null return
        if(temp ==  null)
            return;
        //if k1 <= temp's key get temp's left kid
        if(k1.compareTo(temp.getKey()) <=0)
            PrintingTreeWithBoundsHelper(temp.getLeft(),k1,k2);
        //if temp's key(a comparable) is between k1 and k2 print temp's data
        if(k1.compareTo(temp.getKey()) <= 0 && k2.compareTo(temp.getKey())>=0)
            System.out.print(temp.getData() + " ");
        //if k2 >= temp's key then get temp's right kid
        if(k2.compareTo(temp.getKey()) >= 0)
            PrintingTreeWithBoundsHelper(temp.getRight(),k1,k2);
    }
    
}
class BinaryTree
{
    node head;
    public node getHead() { return head; }
    public void setHead(node head) { this.head = head; }


    public void insert(int key,String dat)
    {
        if(head == null)
            head = new node(dat, key);
        else
            insertHelper(head,key,dat);
    }
    private void insertHelper(node temp,Comparable key,String dat)
    {
        if(temp.getKey().compareTo(key)>0)
        {
            if(temp.getLeft() == null)
                temp.setLeft(new node(dat,key));
            else
                insertHelper(temp.getLeft(),key, dat);
        }
        else
        {
            if(temp.getRight() == null)
                temp.setRight(new node(dat,key));
            else
                insertHelper(temp.getRight(),key,dat);
        }
    }
    
}
class node{
    String data;
    Comparable key;
    node left;
    node right;
    public node(String data)
    {
        this.data = data;
        this.key = 0;
        this.left = this.right = null;
    }
    public node(Comparable key)
    {
        this.key = key;
        this.data = null;
        this.left = this.right = null;
    }
    public node(String data,Comparable key)
    {
        this.key = key;
        this.data = data;
        this.left = this.right = null;
    }
    public node getLeft() { return left; }
    public Comparable getKey() { return key; }
    public node getRight() { return right; }
    public void setKey(Comparable key) { this.key = key; }
    public void setLeft(node left) { this.left = left; }
    public void setRight(node right) { this.right = right; }
    public void setData(String data) { this.data = data; }
    public String getData() { return data; }
}