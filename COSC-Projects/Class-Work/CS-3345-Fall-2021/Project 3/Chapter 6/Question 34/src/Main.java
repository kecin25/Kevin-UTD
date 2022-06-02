/*
    By: Kevin Boudreaux
    ID: KCB180002
    Made on: 11/17/2021
 */
public class Main {

    public static void main(String[] args) {
        LList list = new LList();
        for(int i = 0; i<10; i++)
            list.insert(new Node(i));
        list.print();
    }
}
class LList
{
    private Node[] head;
    
    LList()
    {
        head = new Node[1];
    }
    LList(int size)
    {
        head = new Node[size];
    }
    public void print()
    {
        for (Node node : head)
        {
            if (node != null)
            {
                node.print();
                System.out.println();
            }
        }
    }
    
    public void insert(Node temp)
    {
        if(temp.getHeight() >= head.length)
        {
            LList ltemp = new LList(head.length+1);
            for (Node node : head)
                if (node != null) ltemp.insert(node);
                head = ltemp.head;
        }
        
        if(head[temp.getHeight()] == null)
        {
            head[temp.getHeight()] = temp;
        }
        else
        {
            Node current = head[temp.getHeight()];
            head[temp.getHeight()] = null;
            
            if(temp.getNum() <= current.getNum())
            {
                if(temp.getFirstKid() != null && temp.getFirstKid().getNum() <= current.getNum())
                {
                    current.setNextSibling(temp.getFirstKid().getNextSibling());
                    temp.getFirstKid().setNextSibling(current);
                }
                else if(temp.getFirstKid() != null && temp.getFirstKid().getNum() > current.getNum())
                {
                    current.setNextSibling(temp.getFirstKid());
                    temp.setFirstKid(current);
                }
                else
                {
                    temp.setFirstKid(current);
                }
                current = temp;
            }
            else
            {
                if(current.getFirstKid() != null && temp.getNum() <= current.getFirstKid().getNum())
                {
                    temp.setNextSibling(current.getFirstKid());
                    current.setFirstKid(temp);
                }
                else if(current.getFirstKid() != null && temp.getNum() > current.getFirstKid().getNum())
                {
                    temp.setNextSibling(current.getFirstKid().getNextSibling());
                    current.getFirstKid().setNextSibling(temp);
                }
                else
                    current.setFirstKid(temp);
            }
            insert(current);
            
        }
    }
    
}
class Node
{
    private Node firstKid;
    private Node nextSibling;
    private int num;

    Node()
    {
        firstKid = null;
        nextSibling = null;
        num = 0;
    }
    
    Node(int key)
    {
        num = key;
    }
    public void setFirstKid(Node firstKid)
    {
        this.firstKid = firstKid;
    }
    public void setNextSibling(Node nextSibling)
    {
        this.nextSibling = nextSibling;
    }
    public void setNum(int num)
    {
        this.num = num;
    }
    public int getNum()
    {
        return num;
    }
    public Node getFirstKid()
    {
        return firstKid;
    }
    public Node getNextSibling()
    {
        return nextSibling;
    }
    public int getHeight(){return this.height();}
    private int height()
    {

        if(firstKid != null && nextSibling != null)
        {
            if(firstKid.getHeight() > nextSibling.getHeight())
                return firstKid.getHeight()+1;
            else
                return nextSibling.getHeight();
        }
        else if(firstKid == null && nextSibling !=  null)
            return nextSibling.getHeight();
        else if(firstKid != null )
            return firstKid.getHeight()+1;
        return 1;
    }
    public void print()
    {
        System.out.print(this.num+ " ");
        if(nextSibling != null)
            nextSibling.print();
        if(firstKid != null)
            firstKid.print();
    }
}