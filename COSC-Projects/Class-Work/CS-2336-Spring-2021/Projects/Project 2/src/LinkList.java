public class LinkList
{
    private
    Node head;
    
    public
    LinkList()
    {
        head =  null;
    }
    LinkList(LinkList copy)
    {
        head = new Node(copy.getHead());
    }
    void setHead(Node node){head = node;}
    Node getHead(){return head;}
    
    //print function prints the entire list by calling the node's print function
    void print()
    {
        Node temp = head;
        int longest = 0;
        
        while(temp != null)
        {
            if(temp.getName().length()>longest)
                longest = temp.getName().length();
            temp=temp.getNext();
        }
        temp = head;
        
        while(temp != null)
        {
            temp.print(longest);
            temp = temp.getNext();
        }
    }
    //sort function sorts the list alphabetically 
    void sort()
    {
        int oldCounter=0;
        int newCounter=0;
        Node oldPtr=head;

        //while loops finds how big the list is
        while(oldPtr!=null)
        {
            oldCounter++;
            oldPtr=oldPtr.getNext();
        }
        oldPtr=head;

        Node Current;
        Node NewHead =null;

        //while loops goes the length of the list
        while(newCounter<oldCounter)
        {
            //if first time making this list then set the new head to the old head
            if(newCounter == 0)
            {
                NewHead = oldPtr;
                newCounter++;
                oldPtr=oldPtr.getNext();
            }

            //if this is the 2nd iteration then check to see if the new Ptr should come before or after the current
            else if (newCounter ==1)
            {
                Current = oldPtr;
                oldPtr = oldPtr.getNext();
                Current.setNext(null);
                
                //checks the current node with the new head to see which one is higher in the alphabetical list
                if(Current.getName().compareToIgnoreCase(NewHead.getName()) < 0)
                {
                    Node oldTemp = Current.getNext();
                    Current.setNext(NewHead);
                    NewHead.setNext(oldTemp);
                    NewHead=Current;
                }
                //if its not then set new head's next to the current node
                else
                {
                    NewHead.setNext(Current);
                }
                newCounter++;
            }
            
            //if its not the 1st or 2nd iteration then this else statement kicks in, 
            //and it checks to see if this current node needs to go to the front of the list
            //or the back of the list first and if not then goes node by node to see where it fits
            else
            {
                Current = oldPtr;
                oldPtr = oldPtr.getNext();
                Current.setNext(null);
                Node last = NewHead;
                //for loop that gets the last node
                for(int i = 0; i < newCounter-1; i++)
                {
                    last=last.getNext();
                }
                //checks to see if the current node should be in the front of the list
                if(Current.getName().compareToIgnoreCase(NewHead.getName()) < 0)
                {
                    Current.setNext(NewHead);
                    NewHead=Current;
                }
                //checks to see if the node should be at the end of the list
                else if(Current.getName().compareToIgnoreCase(last.getName()) > 0)
                {
                    last.setNext(Current);
                }
                //if not at the front or back, finds where the node belongs
                else
                {
                    Node ptr1=NewHead;
                    Node ptr2=NewHead.getNext();
                    boolean solved=false;
                    //while loop traverses the list to see where the current node belongs
                    while(!solved)
                    {
                        //if the node to the left is less than the current and the node to the right is more than the current node
                        //then insert the node between them
                        if(Current.getName().compareToIgnoreCase(ptr1.getName()) > 0 && Current.getName().compareToIgnoreCase(ptr2.getName()) < 0 )
                        {
                            ptr1.setNext(Current);
                            Current.setNext(ptr2);
                            solved = true;
                        }
                        else
                        {
                            ptr1=ptr1.getNext();
                            ptr2=ptr2.getNext();
                        }
                    }
                }
                newCounter++;
            }
        }
        head=NewHead;
    }
    //adds a node to the end of the list
    void setNextNode(Node newNode)
    {
        Node temp = head;
        while(temp.getNext() != null)
            temp=temp.getNext();
        temp.setNext(newNode);
    }
    //deletes a node at a given location 
    void delete(int loco)
    {
        if(loco ==0)
            head=head.getNext();
        else
        {
            Node current = head.getNext();
            Node past=head;
            //for loop goes to the location
            for(int i=1; i< loco; i++)
            {
                if(current.getNext() == null || past.getNext() == null)
                    System.out.println("Error, out of range");
                current=current.getNext();
                past=past.getNext();
            }
            //sets the node pointing to the current node to current node's next and sets current node's next to null
            past.setNext(current.getNext());
            current.setNext(null);
        }
    }
    
}
