#ifndef LLIST_H
#define LLIST_H
#include "Node.h"

class LList
{
    public:
        LList();
        LList(Node*);
       ~LList();
        Node* getHead() {return head;}

        void setHead(Node* val) { head = val; }
        void sort();
        void ShowEquation(Node*);

    private:
        Node *head=nullptr;
};

#endif // LLIST_H
