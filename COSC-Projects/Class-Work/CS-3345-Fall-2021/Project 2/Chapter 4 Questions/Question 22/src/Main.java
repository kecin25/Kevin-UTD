/*
    Name: Chapter 4, Question 22
    Question: Design a linear-time algorithm that verifies that the height information in an AVL
              tree is correctly maintained and that the balance property is in order.
    Made by: Kevin Boudreaux
    ID: KCB 180002
    Made on: 10/20/2021
 */
public class Main {

    public static void main(String[] args) { 
        //I hard code a small tree and test to see if it is balanced
        
        
        AVLTree tree = new AVLTree();
        Node temp = new Node(40);
        tree.setRoot(temp);
        tree.getRoot().setLeft(new Node(20));
        tree.getRoot().getLeft().setLeft(new Node(15));
        tree.getRoot().getLeft().setRight(new Node(30));
        tree.getRoot().setRight(new Node(45));
        tree.getRoot().getRight().setRight(new Node(75));
        System.out.println(tree.isBalanced());
        
        //I rebuild the tree, several times with certain nodes missing, to test when it becomes unbalanced
        
        tree = new AVLTree();
        tree.setRoot(new Node(40));
        tree.getRoot().setLeft(new Node(20));
        tree.getRoot().getLeft().setLeft(new Node(15));
        tree.getRoot().getLeft().setRight(new Node(30));
        tree.getRoot().setRight(new Node(45));
        //tree.getRoot().getRight().setRight(temp = new Node(75));
        System.out.println(tree.isBalanced());
        
        
        tree = new AVLTree();
        tree.setRoot(new Node(40));
        tree.getRoot().setLeft(new Node(20));
        tree.getRoot().getLeft().setLeft(new Node(15));
        tree.getRoot().getLeft().setRight(new Node(30));
        //tree.getRoot().setRight(temp = new Node(45));
        //tree.getRoot().getRight().setRight(temp = new Node(75));
        System.out.println(tree.isBalanced());

        tree = new AVLTree();
        tree.setRoot(new Node(40));
        tree.getRoot().setLeft(new Node(20));
        //tree.getRoot().getLeft().setLeft(temp = new Node(15));
        tree.getRoot().getLeft().setRight(new Node(30));
        tree.getRoot().setRight(new Node(45));
        tree.getRoot().getRight().setRight(new Node(75));
        System.out.println(tree.isBalanced());

        tree = new AVLTree();
        tree.setRoot(new Node(40));
        //tree.getRoot().setLeft(temp = new Node(20));
        //tree.getRoot().getLeft().setLeft(temp = new Node(15));
        //tree.getRoot().getLeft().setRight(temp = new Node(30));
        tree.getRoot().setRight(new Node(45));
        tree.getRoot().getRight().setRight(new Node(75));
        System.out.println(tree.isBalanced());
    }
    static class Node
    {
        int data;
        Node left;
        Node right;
        Node(int x)
        {
            data=x;
            left=null;
            right=null;
        }

        public void setData(int data)
        {
            this.data = data;
        }

        public void setLeft(Node left)
        {
            this.left = left;
        }

        public void setRight(Node right)
        {
            this.right = right;
        }

        public int getData()
        {
            return data;
        }

        public Node getLeft()
        {
            return left;
        }

        public Node getRight()
        {
            return right;
        }
    }
    public static class AVLTree{
        Node root;

        public void setRoot(Node root)
        {
            this.root = root;
        }

        public Node getRoot()
        {
            return root;
        }

        public boolean isBalanced()
        {
            return isBalanced(root);
        }
        private boolean isBalanced(Node temp)
        {
            int leftHeight;
            int rightHeight;
            if(temp == null)
                return true;
            leftHeight = height(temp.getLeft());
            rightHeight = height(temp.getRight());

            return Math.abs(leftHeight - rightHeight) <= 1 && isBalanced(temp.getLeft()) && isBalanced(temp.getRight());
            
        }
        public int height()
        {
            return height(root);
        }
        private int height(Node temp)
        {
            if(temp == null)
                return 0;
            
            int leftHeight = height(temp.getLeft());
            int rightHeight = height(temp.getRight());
            
            if(leftHeight>=rightHeight)
                return leftHeight+1;
            else
                return rightHeight+1;
        }
        
        
        
    }
    
}
