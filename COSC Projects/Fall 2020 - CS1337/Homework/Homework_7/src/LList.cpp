#include "LList.h"
#include<iostream>


LList::LList() //default constructor
{
    head=NULL;
}

LList::LList(Node* val) //Overloaded Constructor
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


LList::~LList() //deletes stuff
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

void LList::sort()
{
    int oldCounter=0;
    int newCounter=0;
    Node*oldPtr=head;

    while(oldPtr)
    {
        oldCounter++;
        oldPtr=oldPtr->getNext();
    }
    oldPtr=head;

    Node* Current;
    Node* NewHead;

    while(newCounter<oldCounter)
    {
        if(newCounter==0)
        {
            NewHead = oldPtr;
            newCounter++;
            oldPtr=oldPtr->getNext();
        }
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
            if(Current->getExp()>=NewHead->getExp())
            {
                Current->setNext(NewHead);
                NewHead=Current;
            }
            else if(Current->getExp() <= last->getExp())
            {
                last->setNext(Current);
            }
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

void LList::ShowEquation(Node* val)
{
    int counter=0;
    while(val)
    {
        counter++;
        if(val->getTrigID()=="na")
        {
            if(val->getNegative())
                std::cout<<"- ";
            else if(counter!=1)
                std::cout<<"+ ";
            if(val->getExp()==0)
                std::cout<<val->getOuter()<<" ";
            else
                std::cout<<val->getOuter()<<"x^"<<val->getExp()<<" ";
        }
        else
        {
            if(val->getTrigID()!="na")
            {
                if(val->getTrigID()=="cos"&&val->getInner()==0)
                {
                    if(val->getNegative())
                        std::cout<<"- "<<val->getOuter()<<" ";
                    else
                        std::cout<<"+ "<<val->getOuter()<<" ";
                }
                else if(val->getTrigID()=="sin"&&val->getInner()==0)
                    std::cout<<"";
                else if(val->getTrigID()=="tan"&&val->getInner()==0)
                    std::cout<<"";
            }
            else
            {
                if(val->getNegative())
                    std::cout<<"- ";
                else if(counter!=1)
                    std::cout<<"+ ";
                //TODO: set up a bool in Node.h to see if derived and then set up a if statement that if derived & trigID==sec then add ^2 after sec

                std::cout<<val->getOuter()<<val->getTrigID()<<" "<<val->getInner()<<"x ";
            }
        }
        val=val->getNext();
    }
}
