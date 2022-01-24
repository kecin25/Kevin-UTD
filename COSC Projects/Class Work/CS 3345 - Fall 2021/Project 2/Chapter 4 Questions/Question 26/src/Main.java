/*
    Name: Chapter 4, Question 26
    Question: Write the methods to preform the double rotation without the inefficiency of doing two single rotations
    by: Kevin Boudreaux
    ID: KCB180002
    Date: 10/21/2021

*/
public class Main {
    public static void main(String[] args) {}
    
    //class node is used for the set left and right functions used in the two functions for double rotate with left / right
    static class node{
        node left;
        node right;
        int data;

        public void setRight(node right)
        { this.right = right; }

        public void setLeft(node left)
        { this.left = left; }

        public void setData(int data)
        { this.data = data; }

        public int getData()
        { return data; }

        public node getLeft()
        { return left;   }

        public node getRight()
        { return right; }
    }
    
    static node DoubleRotationWithLeft(node n3)
    {
        node n1 = n3.getLeft();
        node n2 = n1.getRight();
        n1.setRight(n2.getLeft());
        n3.setLeft(n2.getRight());
        n2.setLeft(n1);
        n2.setRight(n3);
        
        return n2;
    }
    static node DoubleRotationWithRight(node n1)
    {
        node n3 = n1.getRight();
        node n2 = n3.getLeft();
        n1.setRight(n2.getLeft());
        n3.setLeft(n2.getRight());
        n2.setLeft(n1);
        n2.setRight(n3);
        return n2;
    }
}
