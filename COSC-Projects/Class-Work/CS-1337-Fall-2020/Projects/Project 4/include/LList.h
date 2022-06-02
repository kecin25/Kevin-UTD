#ifndef LLIST_H
#define LLIST_H
#include "Node.h"

class LList
{
    public:
        //the constructors and destructor
        LList();
        LList(Node*);
       ~LList();

       //get functions used
        Node* getHead() const{return head;}

        //overloaded operators
        const Node* operator[] (int);
        friend std::ostream& operator<<(std::ostream& ,LList& );
        void operator>>( Node*);

        //set functions used
        void setHead(Node* val) { head = val; }
        void sort();
        void derive();
        void FixTrig();


    private:
        Node* head=nullptr;
};

#endif // LLIST_H
