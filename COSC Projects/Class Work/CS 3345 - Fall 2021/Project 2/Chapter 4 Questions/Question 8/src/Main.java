/*
    Name: Chapter 4, Question 8
    Question: Give the prefix, infix, and postfix expressions corresponding to the tree in the given png
    Made by: Kevin Boudreaux
    ID: KCB 180002
    Made on: 10/20/2021
*/
public class Main {

    public static void main(String[] args) {
        //setting up the binary tree as it is in the given png
        BinaryTree tree = new BinaryTree();
        Node temp = new Node('-');
        tree.setHead(temp);
        temp = new Node('e');
        tree.getHead().setRightKid(temp);
        temp = new Node('*');
        tree.getHead().setLeftKid(temp);
        temp= new Node('+');
        tree.getHead().getLeftKid().setRightKid(temp);
        temp = new Node('c');
        tree.getHead().getLeftKid().getRightKid().setLeftKid(temp);
        temp = new Node('d');
        tree.getHead().getLeftKid().getRightKid().setRightKid(temp);
        temp = new Node('*');
        tree.getHead().getLeftKid().setLeftKid(temp);
        temp = new Node('a');
        tree.getHead().getLeftKid().getLeftKid().setLeftKid(temp);
        temp = new Node('b');
        tree.getHead().getLeftKid().getLeftKid().setRightKid(temp);
        
        //printing out the prefix, infix, and postfix
        System.out.println("PreFix:");
        tree.printPreFix();
        System.out.println("InFix:");
        tree.printInFix();
        System.out.println("PostFix:");
        tree.printPostFix();
        
    }
}
