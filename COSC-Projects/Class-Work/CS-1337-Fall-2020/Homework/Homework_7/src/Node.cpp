#include "Node.h"
#include <iostream>

Node::Node() //default constructor
{
    OuterCoefficient=0;
    InnerCoefficient=0;
    Exponent=0;
    TrigID="na";
    next=nullptr;

}

Node::Node(Node *val) //Overloaded Copy Constructor
{
    Negative=val->getNegative();
    OuterCoefficient=val->getOuter();
    InnerCoefficient=val->getInner();
    Exponent=val->getExp();
    TrigID=val->getTrigID();
    next=nullptr;
}

std::ostream& operator<<(std::ostream &outPut,Node* val ) //shows single Node information, is dereferenced to show info correctly when spitting out info
{
    if(val->getTrigID()=="na")
    {
        if(val->getNegative())
        {
            outPut<<"-"<<val->getOuter()<<"x^"<<val->getExp();
        }
        else
            outPut<<val->getOuter()<<"x^"<<val->getExp();
    }
    else
    {
        if(val->getNegative())
        {
            if(val->getExp()!=0)
                outPut<<"-"<<val->getOuter()<<val->getTrigID()<<"^"<<val->getExp()<<" "<<val->getInner()<<"x";
            else
                outPut<<"-"<<val->getOuter()<<val->getTrigID()<<" "<<val->getInner()<<"x";
        }
        else
        {
            if(val->getExp()!=0)
                outPut<<val->getOuter()<<val->getTrigID()<<"^"<<val->getExp()<<" "<<val->getInner()<<"x";
            else
                outPut<<val->getOuter()<<val->getTrigID()<<" "<<val->getInner()<<"x";
        }
    }
}
/*
to overload a +, -, = operator


void Node::operator=(const Node &val) // only happens for a simple assignment, both objects have already been created
{
    Negative=val.getNegative();
    OuterCoefficient=val.getOuter();
    InnerCoefficient=val.getInner();
    Exponent=val.getExp();
    TrigID=val.getTrigID();
    next=nullptr;
}
*/
