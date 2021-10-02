#include <iostream>

using namespace std;


struct Node
{
    int num;
    Node* next=nullptr;
};

void enQueue(int number, Node *&head)
{
    Node *cur=new Node;
    cur->num=number;
    if(!head)
    {
        head=cur;
    }
    else
    {
        Node *temp=head;
        while(temp->next)
        {
            temp=temp->next;
        }
        temp->next=cur;
    }
}
Node* deQueue(Node *&head)
{
    if(head)
    {
        Node *del = head;
        head=del->next;
        del->next=nullptr;
        return del;
    }
    else
        return head;
}
void printQueue(Node *&head)
{
    if(head!=NULL)
    {
        cout<<head->num<<" ";
        printQueue(head->next);
    }
    else
        cout<<endl;
}
int main()
{

    Node *head=NULL;
    enQueue(3,head);
    printQueue(head);
    enQueue(2,head);
    printQueue(head);
    enQueue(1,head);
    printQueue(head);
    enQueue(0,head);
    printQueue(head);
    deQueue(head);
    printQueue(head);
    deQueue(head);
    printQueue(head);
    deQueue(head);
    printQueue(head);
    deQueue(head);



    return 0;
}
