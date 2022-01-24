#ifndef NODE_H
#define NODE_H
#include<string>
#include <iostream>

class Node
{
    public:
        //constructors used
        Node();
        Node(Node*);

        //get functions used
        Node* getNext() const { return next; }
        int getOuter() const { return OuterCoefficient; }
        int getInner() const { return InnerCoefficient; }
        int getExp() const { return Exponent; }
        bool getNegative() const { return Negative; }
        std::string getTrigID() const { return TrigID; }

        //set functions used
        void setOuter(int val) { OuterCoefficient = val; CheckNegative(); }
        void setInner(int val) { InnerCoefficient = val; }
        void setExp(int val) { Exponent = val; }
        void setTrigID(std::string val) { TrigID = val; }
        void setNext(Node* val) { next = val; }
        void setNegative(bool val) {Negative = val; }
        void CheckNegative();
        void derive();

        //overloaded operators used
        friend std::ostream& operator<<(std::ostream& ,const Node* );

    private:
        bool Negative=false;
        int OuterCoefficient;
        int InnerCoefficient;
        int Exponent;
        std::string TrigID;
        Node* next;
};

#endif // NODE_H
