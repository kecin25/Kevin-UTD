public class LinkedList
{
    Node head;
    public void push(Node temp)
    {
        if(head != null)
            temp.setNext(head);
        head = temp;
    }
    public Node pop()
    {
        Node temp = head;
        head = head.getNext();
        return temp;
    }
    public String peek(){return head.getInfo();}
    public Node getHead(){return head;}
}
class Node
{
    Node next;
    String info;
    int lineNum;

    public Node(String x){info = x;}
    public Node(Node x){next = x;}
    public Node(int x){lineNum=x;}
    public Node(String x, int y){info = x; lineNum = y;}
    public Node(String x, Node y){info = x; next = y;}
    public Node(int x, Node y){lineNum = x; next = y;}
    public Node(String x, Node y, int z){info = x; next = y; lineNum=z;}

    public void setNext(Node x){next = x;}
    public Node getNext(){return next;}
    public void setInfo(String x){info = x;}
    public String getInfo(){return info;}
    public void setLineNum(int x){lineNum=x;}
    public int getLineNum(){return lineNum;}
}
