#include <iostream>
#include <string>
#include "LList.h"
#include <time.h>


using namespace std;

void test1();
void test2();
void test3();
void test5();
void test4();
int main()
{

    srand(time(NULL));
    test1();
    cout<<endl;
    test2();
    cout<<endl;
    test3();
    cout<<endl;
    test4();
    cout<<endl;
    test5();


    return 0;
}
void test1()
{
    Node *test1=new Node;
    Node *anc=test1;
    for(int i=0; i<5; i++)
    {
        if(i!=4)
            test1->setNext(new Node);
        test1->setExp(1+i);
        test1->setOuter(i+2);
        test1=test1->getNext();
    }
    test1=anc;

    cout<<"Unsorted Equeation: \n";

    for(int i=0; i<5; i++)
    {
        cout<<test1;
        cout<<" ";
        test1=test1->getNext();
    }
    test1=anc;
    LList *List1=new LList(test1);
    List1->sort();

    cout<<endl;
    Node* something=List1->getHead();

    cout<<"\nSorted Equation without +'s or -'s: \n";

    for(int i=0; i<5; i++)
    {
        cout<<something;
        cout<<" ";
        something=something->getNext();
    }


    List1->~LList();
}
void test2()
{
    Node *test1=new Node;
    Node *anc=test1;
    int x=5;
    for(int i=0; i<5; i++)
    {
        if(i!=4)
            test1->setNext(new Node);
        test1->setExp(x*i);
        test1->setOuter(x+i);
        if(i%2==0)
        {
            test1->setNegative(true);
        }
        test1=test1->getNext();
    }
    test1=anc;
    LList *List1=new LList(test1);
    List1->sort();

    cout<<endl;
    Node* something=List1->getHead();

    cout<<"Unsorted Equeation: \n";

    for(int i=0; i<5; i++)
    {
        cout<<(something);
        cout<<" ";
        something=something->getNext();
    }
    cout<<endl;
    something =List1->getHead();

    cout<<"\nSorted Equation shown as an equation: \n";

    List1->ShowEquation(something);


    List1->~LList();
}
void test3()
{
    Node *test1=new Node;
    Node *anc=test1;
    int x=5;
    for(int i=0; i<5; i++)
    {
        if(i!=4)
            test1->setNext(new Node);
        test1->setExp(x*i);
        test1->setOuter(x+i);
        if(i%2==0)
        {
            test1->setNegative(true);
            test1->setTrigID("cos");
            test1->setInner(x*i-i);
        }
        test1=test1->getNext();
    }
    test1=anc;
    LList *List1=new LList(test1);
    List1->sort();

    cout<<endl;
    Node* something=List1->getHead();

    cout<<"Unsorted Equeation: \n";

    for(int i=0; i<5; i++)
    {
        cout<<(something);
        cout<<" ";
        something=something->getNext();
    }
    cout<<endl;
    something =List1->getHead();

    cout<<"\nSorted Equation shown as an equation with some trig values: \n";

    List1->ShowEquation(something);
    List1->~LList();
}
void test4()
{
    Node *test1=new Node;
    Node *anc=test1;

    test1->setExp(-5);
    test1->setNext(new Node);
    test1=test1->getNext();
    test1->setExp(-4);
    test1->setNext(new Node);
    test1=test1->getNext();
    test1->setExp(-3);
    test1->setNext(new Node);
    test1=test1->getNext();
    test1->setExp(-2);
    test1->setNext(new Node);
    test1=test1->getNext();
    test1->setExp(-1);

    test1=anc;

    cout<<"Unsorted Equeation: \n";

    for(int i=0; i<5; i++)
    {
        cout<<(test1);
        cout<<" ";
        test1=test1->getNext();
    }
    test1=anc;
    LList *List1=new LList(test1);
    List1->sort();

    cout<<endl;
    Node* something=List1->getHead();

    cout<<"\nSorted Equation without +'s or -'s: \n";

    for(int i=0; i<5; i++)
    {
        cout<<(something);
        cout<<" ";
        something=something->getNext();
    }
    List1->~LList();
}
void test5()
{
    Node *test1=new Node;
    Node *anc=test1;
    for(int i=0; i<5; i++)
    {
        if(i!=4)
            test1->setNext(new Node);
        test1->setExp(rand()%200-100);
        test1->setOuter(i+2);
        test1=test1->getNext();
    }
    test1=anc;

    cout<<"Unsorted Equeation: \n";

    for(int i=0; i<5; i++)
    {
        cout<<(test1);
        cout<<" ";
        test1=test1->getNext();
    }
    test1=anc;
    LList *List1=new LList(test1);
    List1->sort();

    cout<<endl;
    Node* something=List1->getHead();

    cout<<"\nSorted Equation without +'s or -'s: \n";

    for(int i=0; i<5; i++)
    {
        cout<<(something);
        cout<<" ";
        something=something->getNext();
    }
    List1->~LList();
}
