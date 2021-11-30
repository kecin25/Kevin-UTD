/*
    By: Kevin Boudreaux
    ID: KCB180002
    Made on: 11/17/2021
 */
public class Main {

    public static void main(String[] args) {
	Node r1n1 = new Node(13);
    
    Node r1n2 = new Node(23);
    r1n2.setFirstKid(new Node(51));
    r1n2.getFirstKid().setNextSibling(new Node(24));
    r1n2.getFirstKid().getNextSibling().setFirstKid(new Node(65));
    
    Node r1n3 = new Node(12);
    r1n3.setFirstKid(new Node(21));
    r1n3.getFirstKid().setNextSibling(new Node(24));
    r1n3.getFirstKid().getNextSibling().setNextSibling(new Node(14));
    r1n3.getFirstKid().getNextSibling().setFirstKid(new Node(65));
    r1n3.getFirstKid().getNextSibling().getNextSibling().setFirstKid(new Node(26));
    r1n3.getFirstKid().getNextSibling().getNextSibling().getFirstKid().setNextSibling(new Node(16));
    r1n3.getFirstKid().getNextSibling().getNextSibling().getFirstKid().getNextSibling().setFirstKid(new Node(18));
    
    Node r2n1 = new Node(4);
    
    Node r2n2 = new Node(15);
    r2n2.setFirstKid(new Node(18));
    
    Node r2n3 = new Node(2);
    r2n3.setFirstKid(new Node(11));
    r2n3.getFirstKid().setNextSibling(new Node(29));
    r2n3.getFirstKid().getNextSibling().setFirstKid(new Node(55));

    System.out.println("First combine row 1 number 1 with row 2 number 1:");
    r2n1.setFirstKid(r1n1);
    r2n1.print();
    System.out.println();

    System.out.println("then combine the new queue with the other sized two queue which is row 2 number 2:");
    r2n1.getFirstKid().setNextSibling(r2n2);
    r2n1.print();
    System.out.println();
    
    System.out.println("Now combine that new queue with row 2 number 3:");
    r2n1.setNextSibling(r2n3.getFirstKid());
    r2n3.setFirstKid(r2n1);
    r2n3.print();
    System.out.println();
        
    System.out.println("now combine that with row 1 number 3:");
    r2n3.getFirstKid().getNextSibling().getNextSibling().setNextSibling(r1n3);
    r2n3.print();
    System.out.println();
    
    System.out.println("First row only has the 2nd in queue left which is: ");
    r1n2.print();
    System.out.println();
    
    System.out.println("The rest has been combined into:");
    r2n3.print();
    System.out.println();
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
    Node(int key, Node t1, Node t2)
    {
        num = key;
        nextSibling = t1;
        firstKid = t2;
        
    }
    Node(int key, Node t1)
    {
        num = key;
        firstKid = t1;
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
        else if(firstKid != null && nextSibling ==  null)
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