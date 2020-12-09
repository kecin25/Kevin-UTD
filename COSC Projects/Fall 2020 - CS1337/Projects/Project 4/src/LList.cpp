#include "LList.h"
#include<iostream>


LList::LList() ///default constructor
{
    head=NULL;
}

LList::LList(Node* val) ///Overloaded Constructor, makes a deep copy of the passed in Node list
{
    while(val)
    {
        Node* temp= new Node(val);
        if(head==nullptr)
        {
            head=temp;
        }
        else
        {
            Node* ptr = head;
            while(ptr->getNext()!=NULL)
            {
                ptr=ptr->getNext();
            }
            ptr->setNext(temp);
        }
        val=val->getNext();
    }
}


LList::~LList() ///deletes the given list by deleting Node by Node
{
    Node *temp=head->getNext();
    while(head)
    {
        head->setNext(nullptr);
        delete head;
        head=temp;
        if(head)
            temp=head->getNext();
    }
}

void LList::sort() ///sorts the function based on exponent
{
    int oldCounter=0;
    int newCounter=0;
    Node*oldPtr=head;

    //while loop checks to see how long the list is
    while(oldPtr)
    {
        oldCounter++;
        oldPtr=oldPtr->getNext();
    }
    oldPtr=head;

    Node* Current;
    Node* NewHead;

    //while loop used to resort the equation and making a new list while doing so
    while(newCounter<oldCounter)
    {
        //if the first time running the loop, will set the new head node to the first node in the list
        if(newCounter==0)
        {
            NewHead = oldPtr;
            newCounter++;
            oldPtr=oldPtr->getNext();
        }
        //if 2nd time running loop, will check to see if exponent of current is larger or less then new head node.
        //if larger then sets current's next to new head and have new head become the current
        //if less then sets new head's next to current
        else if (newCounter ==1)
        {
            Current =oldPtr;
            oldPtr=oldPtr->getNext();
            Current->setNext(nullptr);
            if(Current->getExp() > NewHead->getExp())
            {
                Node* oldTemp = Current->getNext();
                Current->setNext(NewHead);
                NewHead->setNext(oldTemp);
                NewHead=Current;
            }
            else
            {
                NewHead->setNext(Current);
            }
            newCounter++;
        }
        //if it is not the first or 2nd run of the loop this is else statement is ran.
        //checks to see if it is larger then the first or less then the last
        //if either of those are true then adds it to the front or end of the list.
        // else it will parse through the new list and find where it fits
        else
        {
            Current=oldPtr;
            oldPtr=oldPtr->getNext();
            Current->setNext(nullptr);
            Node* last = NewHead;
            for(int i=0; i<newCounter-1; i++)
            {
                last=last->getNext();
            }
            //this part is to see if it is larger then the head
            if(Current->getExp()>=NewHead->getExp())
            {
                Current->setNext(NewHead);
                NewHead=Current;
            }
            //this part is to see if it is less then the last node
            else if(Current->getExp() <= last->getExp())
            {
                last->setNext(Current);
            }
            //this part parses through the list to see where current fits in
            else
            {
                Node* ptr1=NewHead;
                Node* ptr2=NewHead->getNext();
                bool solved=false;
                while(solved!=true)
                {
                    if(ptr1->getExp() >= Current->getExp() && Current->getExp() >= ptr2->getExp() )
                    {
                        ptr1->setNext(Current);
                        Current->setNext(ptr2);
                        solved = true;
                    }
                    else
                    {
                        ptr1=ptr1->getNext();
                        ptr2=ptr2->getNext();
                    }
                }
            }
            newCounter++;
        }
    }
    head=NewHead;
}

const Node* LList::operator[](int pos) ///gives a single node in a list
{
    Node* temp=head;
    for(int i=0;i<pos;i++)
    {
        temp=temp->getNext();
    }
    return temp;
}

std::ostream& operator<<(std::ostream& OutPut,LList& base) ///overloaded output operator
{
    int counter =0;
    //while loop used to go node by node using the overloaded [] operators
    while((base)[counter])
    {
        //creates a temp node that is equal to one node of the list
        const Node* temp=(base)[counter];

        //if not at the first iteration of the loop and the outer coefficient is not 0 then we print out a +/-
        if(counter!=0 && temp->getOuter()!=0)
        {
           if(temp->getNegative()==false)
                OutPut<<" + ";
            else
                OutPut<<" - ";
        }
        //else if used if first part of the loop and is negative
        else if(temp->getNegative() && temp->getOuter()!=0)
            OutPut<<"-";
        //calls the Node overloaded operator to print the single part of the equation
        OutPut<<(temp);
        counter++;
    }
    OutPut<<std::endl;
    return OutPut;
}
void LList::operator>>(Node* temp) ///overloaded input operator used to add to the front of the list
{
    temp->setNext(head);
    head=temp;
}
void LList::derive() ///function that derives the entire equation
{
    Node* temp=head;
    while(temp)
    {
        //calls the Node derive function for each spot in the equation
        temp->derive();
        temp=temp->getNext();
    }
}
void LList::FixTrig() ///fix trig is used to set trig exponents back to 1
{
    Node* temp=head;
    while(temp)
    {
        if(temp->getTrigID()!="na")
        {
            temp->setExp(1);
        }
        temp=temp->getNext();
    }
}
