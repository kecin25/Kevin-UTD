/*Name: Project 3 - BinTree
Description: Generic Node Class that can be used with any data type given
Created by: Kevin Boudreaux
Created on: 3/31/2021
ID: KCB180002
*/
public class Node<T extends Comparable<T>> //generic node used to hold payload in the binary tree
{
    private Node<T> left; 
    private Node<T> right;
    private T data;

    
    public Node(){} //default constructor
    public Node(T x) //overloaded constructor with a generic data type
    {
        this.left=null;
        this.right=null;
        this.data=x;
    }
    
    public void setLeft(Node<T> x){left=x;}
    public void setRight(Node<T> x){right=x;}
    public void setData(T x){data=x;}
    
    public Node<T> getLeft(){return left;}
    public Node<T> getRight(){return right;}
    public T getData(){return data;}
    
    public int CompareTo(Node<T> x) {return this.data.compareTo(x.data);} //calls the generic data type's compare to function
    @Override public String toString(){return data.toString();} //calls the generic data type's to string function
    
}