public class BinaryTree
{
    Node head;
    public Node getHead(){return head;}
    public void setHead(Node x){this.head=x;}
    public void printPreFix()
    {
        printPreFixHelper(head);
        System.out.println();
    }
    private void printPreFixHelper(Node temp)
    {
        if(temp == null)
            return;
        printPreFixHelper(temp.getLeftKid());
        printPreFixHelper(temp.getRightKid());
        System.out.print(temp.getData()+" ");
    }
    public void printInFix()
    {
        printInFixHelper(head);
        System.out.println();
    }
    private void printInFixHelper(Node temp)
    {
        if(temp == null)
            return;
        printInFixHelper(temp.getLeftKid());
        System.out.print(temp.getData()+" ");
        printInFixHelper(temp.getRightKid());
    }
    public void printPostFix()
    {
        printPostFixHelper(head);
        System.out.println();
    }
    private void printPostFixHelper(Node temp)
    {
        if(temp == null)
            return;
        System.out.print(temp.getData()+" ");
        printPostFixHelper(temp.getLeftKid());
        printPostFixHelper(temp.getRightKid());
    }
}
class Node
{
    char data;
    Node leftKid;
    Node rightKid;
    public Node(char x){this.data=x;}
    public void setData(char data)
    {
        this.data = data;
    }
    public void setLeftKid(Node leftKid)
    {
        this.leftKid = leftKid;
    }

    public void setRightKid(Node rightKid)
    {
        this.rightKid = rightKid;
    }

    public char getData()
    {
        return data;
    }
    public Node getLeftKid()
    {
        return leftKid;
    }
    public Node getRightKid()
    {
        return rightKid;
    }
}