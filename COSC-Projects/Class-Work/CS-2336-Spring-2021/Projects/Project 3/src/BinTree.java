/*Name: Project 3 - BinTree
Description: Generic Binary Tree that can be used with any data type given
Created by: Kevin Boudreaux
Created on: 3/31/2021
ID: KCB180002
*/
public class BinTree<T extends Comparable<T>> //makes the tree generic
{
    Node<T> root; //generic root that is of type node
    
    public BinTree() //default constructor 
    {
        this.root=null;
    }
    public BinTree(T x) //overloaded constructor used copy another binary tree
    {
        super();
        this.root = new Node<>(x);
    }
    
    public void insert(T data) //inserts a new node into the tree recursively 
    {
        Node<T> temp = new Node<>(data); //turns the generic datatype into a node that can hold the data type
        if (root==null) //checks to see if the root is null and if so sets the root to the new node
            root = temp;
        else //root is not null and calls the recursive insert function that is private
        {
            Insert(root,temp);
        }
    }
    private void Insert(Node<T> current, Node<T> New) //recursive insert function that is private
    {
        if(New.CompareTo(current) > 0 ) //compares the two nodes generically and checks to see if the new node is bigger then the current node
        {
            if(current.getRight() == null) //if current node does not have a right kid then sets the new node as the right kid
            {
                current.setRight(New);
            }
            else //calls the recursive insert function again with the current's right kid
            {
                current=current.getRight();
                Insert(current,New);
            }
        }
        else if(New.CompareTo(current) < 0) //compares the two nodes generically and checks to see if the new node is smaller then the current node
        {
            if(current.getLeft() == null) //if the current node does not have a left kid then sets the new node as current's left kid
            {
                current.setLeft(New);
            }
            else //calls the recursive insert function with current's left kid
            {
                current=current.getLeft();
                Insert(current,New);
            }
        }
    }
    
    public Node<T> Search(T x) //searches for a node in the binary tree and if found returns it
    {
        if(root == null) //checks to see if the tree is empty
            return null;
        if(root.getData().compareTo(x) == 0) //checks to see if the root is the node being looked for
            return root;
        else if(root.getData().compareTo(x) < 0) //checks to see if the node being looked for is larger then the root
            return Search(root.getRight(), x);
        else //else the node is smaller then the root so checks to the left of the root
            return Search(root.getLeft(),x);
    }
    private Node<T> Search(Node<T> temp,T current) //recursive search function
    {
        if(temp == null) //if the current node is null, means the searched for node was not found
            return null;
        if(temp.getData().compareTo(current) == 0) //checks to see if current node is the node being looked for
            return temp;
        else if(temp.getData().compareTo(current) < 0) //checks to see if the searched for node is larger then the current node
            return Search(temp.getRight(),current);
        else //if not equal to or larger than the current node then the searched for node is smaller then the current node
            return Search(temp.getLeft(),current);
    }
    
    public String Print() //recursive print function that returns a string
    {
        String temp = "";
        return Print(root,temp); //calls the nodes recursive print function that returns strings for each node in the tree and combines them
        
    }
    private String Print(Node<T> current,String temp) //recursive print function
    {
        if(current == null) //if the current node is null
            return "";
        temp =Print(current.getRight(),temp); //calls the right kid of the current node
        
        temp = temp + current.toString(); //adds the current nodes print out function to the string
        temp += Print(current.getLeft(),temp); //calls the left kid of the current node
        return temp; //returns all the nodes from the binary tree after combining them
    }
    
    public Node<T> Delete(Node<T> current)
    {
        if(current.getData().compareTo(root.getData()) == 0)
        {
            Node<T> temp = root.getLeft();
            Node<T> oldRoot = root;
            root=root.getRight();
            oldRoot.setRight(null);
            if(temp != null)
                Insert(temp,root);
            oldRoot.setLeft(null);
        }
        else
            Delete(current,root);
        return current;
    }
    private Node<T> Delete(Node<T> current, Node<T> parent)
    {
        if(parent.getLeft()== current || parent.getRight() == current)
        {
            if(current.getLeft() != null ^ current.getRight() != null) //child has one kid
            {
                if(parent.getLeft() == current)
                {
                    if(current.getLeft()!=null)
                        parent.setLeft(current.getLeft());
                    else
                        parent.setLeft(current.getRight());
                }
                else
                {
                    if(current.getLeft()!=null)
                        parent.setRight(current.getLeft());
                    else
                        parent.setRight(current.getRight());
                }
            }
            else if(current.getLeft() == null && current.getRight() == null) //child has no kids
            {
                if(parent.getLeft() == current)
                    parent.setLeft(null);
                else
                    parent.setRight(null);
            }
            else // child has two kids
            {
                if(parent.getLeft() == current)
                {
                    parent.setLeft(current.getLeft());
                    current.setLeft(null);
                    Insert(root,current.getRight());
                    current.setRight(null);
                }
                else if(parent.getRight() == current)
                {
                    parent.setRight(current.getLeft());
                    current.setLeft(null);
                    Insert(root,current.getRight());
                    current.setRight(null);
                }
            }
        }
        else if(current.getData().compareTo(parent.getData()) < 0)
            Delete(current, parent.getLeft());
        else
            Delete(current, parent.getRight());
        
        return current;
    }
}