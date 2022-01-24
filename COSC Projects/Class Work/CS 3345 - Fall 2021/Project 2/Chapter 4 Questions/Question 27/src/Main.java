/*
   Name: Chapter 4, Question 27
   Description: Show the result of accessing the keys 3,9,1,5 in order in the splay tree in the given png
   Made by: Kevin Boudreaux
   ID: KCB180002
   Made on: 10/21/2021
*/
public class Main {

    public static void main(String[] args) 
    {
	    SplayTree tree = new SplayTree();
	    tree.insert(10);
        tree.insert(11);
        tree.insert(12);
        tree.insert(13);
        tree.insert(4);
        tree.insert(6);
        tree.insert(5);
        tree.insert(8);
        tree.insert(7);
        tree.insert(9);
        tree.insert(2);
        tree.insert(1);
        tree.insert(3);
        
	
	System.out.print("before search using pre-order: ");
	tree.preOrder(tree.getHead());
	System.out.print("\nafter search using pre-order: ");
	    tree.setHead(tree.search(tree.getHead(), 3));
        tree.setHead(tree.search(tree.getHead(), 9));
        tree.setHead(tree.search(tree.getHead(), 1));
        tree.setHead(tree.search(tree.getHead(), 5));
	    tree.preOrder(tree.getHead());
	
	
	
    }
    
}
class SplayTree{
    
    node head;
    public node getHead() { return head; }
    public void setHead(node head) { this.head = head; }
    
    
    public void insert(int key)
    {
        if(head == null)
            head = new node(key);
        else
            insertHelper(head,key);
    }
    private void insertHelper(node temp,int key)
    {
        if(key<temp.getData())
        {
            if(temp.getLeft() == null)
                temp.setLeft(new node(key));
            else
                insertHelper(temp.getLeft(),key);
        }
        else
        {
            if(temp.getRight() == null)
                temp.setRight(new node(key));
            else
                insertHelper(temp.getRight(),key);
        }
    }

    public node rightRotate(node x)
    {
        node y = x.getLeft();
        x.setLeft(y.getRight());
        y.setRight(x);
        return y;
    }
    public node leftRotate(node x)
    {
        node y = x.getRight();
        x.setRight(y.getLeft());
        y.setLeft(x);
        return y;
    }
    public node search(node temp, int key)
    {
        //if the tree is empty or if temp holds the key
        if(temp == null || temp.getData() == key)
            return temp;
        //if the key is in the left subtree
        if(temp.getData()>key)
        {
            //temp does not have a left kid
            if(temp.getLeft() == null)
                return temp;
            //Zig-Zig (left left)
            if(temp.getLeft().getData() > key)
            {
                //bring the key as root of left left
                temp.getLeft().setLeft(search(temp.getLeft().getLeft(),key));
                //first rotation for root, 2nd is done after else statement
                temp = rightRotate(temp);
            }
            //Zig-Zag (left right)
            else if( temp.getLeft().getData() < key)
            {
                //recursively bring key as root of left right
                temp.getLeft().setRight(search(temp.getLeft().getRight(),key));
                //first rotation for temp's left kid
                if(temp.getLeft().getRight() != null)
                    temp.setLeft(leftRotate(temp.getLeft()));
            }
            //2nd rotation for temp
            return (temp.getLeft() == null) ? temp : rightRotate(temp); 
        }
        //key is in right subtree (key is > temp.getData()
        else
        {
            //if temp has no right kid
            if(temp.getRight() == null)
                return temp;
            //Zag-Zig (right left)
            if(temp.getRight().getData() > key)
            {
                //move the key as root of right left
                temp.getRight().setLeft(search(temp.getRight().getLeft(),key));
                //first rotation for temp.getRight()
                if(temp.getRight().getLeft() != null)
                    temp.setRight(rightRotate(temp.getRight()));
            }
            //Zag-Zag (right right)
            else if(temp.getRight().getData() < key)
            {
                //move the key as temp of right right
                temp.getRight().setRight(search(temp.getRight().getRight(),key));
                //first rotation
                temp = leftRotate(temp);
            }
            //2nd rotation for temp
            return (temp.getRight() == null) ? temp : leftRotate(temp);
        }
    }
    
    public void preOrder(node temp)
    {
        if(temp != null)
        {
            System.out.print(temp.getData() + " ");
            preOrder(temp.getLeft());
            preOrder(temp.getRight());
        }
    }


}
 class node{
     int data;
    node left;
    node right;
    public node(int key)
    {
        this.data = key;
        this.left = this.right = null;
    }
    public node getLeft() { return left; }
    public int getData() { return data; }
    public node getRight() { return right; }
    public void setData(int data) { this.data = data; }
    public void setLeft(node left) { this.left = left; }
    public void setRight(node right) { this.right = right; }
}