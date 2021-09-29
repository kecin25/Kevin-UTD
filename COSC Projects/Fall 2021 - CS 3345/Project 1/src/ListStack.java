import java.util.EmptyStackException;
public class ListStack implements BKStack
{
    //default head for linked list is null
    Node head = null;

    @Override
    //push adds a new double to the top of the stack
    public boolean isEmpty()
    {
        //checks to see if head is null and if so returns true
        return head == null;
    }

    @Override
    //pop removes the top double in the stack and returns it to the user
    public void push(double d)
    {
        //checks to see if head is empty and if it is makes a new node holding the double d in it   
        if(isEmpty())
            head = new Node(d);
        //if not an empty linked list then makes a new head inserting the double d into data and setting past head as next
        else
            head = new Node(d, head);
    }

    @Override
    //if there is more than one value in the array then set up temp to be an array one slot smaller than array
    public double pop()
    {
        //if head is empty throws an empty stack exception
        if(isEmpty())
            throw new EmptyStackException();
        //makes a temp node equal head
        Node temp = head;
        //moves head to the next node in the list
        head = head.getNext();
        //removes the connection from temp (old head) to the new current head
        temp.setNext(null);
        //returns the value in temp and Java will clean up the dead node
        return temp.getData();
    }

    @Override
    //peek looks at the top double and returns it to the user
    public double peek()
    {
        //checks to see if the stack is empty and throws an empty stack exception if it is
        if(isEmpty())
            throw new EmptyStackException();
        
        //returns the data in head without removing head
        return head.getData();
    }
}
class Node
{   //next holds the next node in the list
    private Node next;
    //data holds the double that is used to flip files
    private double data;
    //default constructor used to make nodes
    public Node()
    {
        next = null;
        data = 0;
    }
    //overloaded constructor makes a node with data already set when a double is passed
    public Node(double temp)
    {
        next = null;
        data = temp;
    }
    //overloaded constructor makes a node with data and next already set when a double and a node is passed
    public Node(double tempData, Node tempNode)
    {
        next = tempNode;
        data = tempData;
    }
    //setters and getters used for node, notice setData is not used since when making new nodes the overloaded node constructor is used
    //recommend leaving setData in just in case class is altered and setData will be needed
    public Node getNext() {return next;}
    public double getData() {return data;}
    public void setNext(Node temp) {next = temp;}
    public void setData(Double temp) {data = temp;}
}