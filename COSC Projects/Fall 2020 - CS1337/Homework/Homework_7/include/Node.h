#ifndef NODE_H
#define NODE_H
#include<string>
#include <iostream>

class Node
{
    public:
        Node();
        Node(Node*);
        Node* getNext() { return next; }
        int getOuter() { return OuterCoefficient; }
        int getInner() { return InnerCoefficient; }
        int getExp() { return Exponent; }
        bool getNegative() { return Negative; }
        std::string getTrigID() { return TrigID; }

        void setOuter(int val) { OuterCoefficient = val; }
        void setInner(int val) { InnerCoefficient = val; }
        void setExp(int val) { Exponent = val; }
        void setTrigID(std::string val) { TrigID = val; }
        void setNext(Node* val) { next = val; }
        void setNegative(bool val) {Negative = val; }
        friend std::ostream& operator<<(std::ostream& ,Node* );
        // void operator= (const Node*);

    private:
        bool Negative=false;
        int OuterCoefficient;
        int InnerCoefficient;
        int Exponent;
        std::string TrigID;
        Node* next;
};

#endif // NODE_H
