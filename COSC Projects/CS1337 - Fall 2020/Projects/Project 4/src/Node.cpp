#include "Node.h"
#include <iostream>
#include<cmath>

Node::Node() ///default constructor
{
    OuterCoefficient=0;
    InnerCoefficient=0;
    Exponent=0;
    TrigID="na";
    next=nullptr;
    CheckNegative();

}

Node::Node(Node *val) ///Overloaded Copy Constructor
{
    Negative=val->getNegative();
    OuterCoefficient=val->getOuter();
    InnerCoefficient=val->getInner();
    Exponent=val->getExp();
    TrigID=val->getTrigID();
    next=nullptr;
}

void Node::CheckNegative() ///checks to see if the part of the equation is negative
{
    if(OuterCoefficient<0)
    {
        if(!Negative)
            Negative=true;
        else
        {
            Negative =false;

        }
        OuterCoefficient*=-1;
    }
}
std::ostream& operator<<(std::ostream &outPut,const Node* val ) ///shows single Node information, is dereferenced to show info correctly when spitting out info
{
    //if there is no trig then runs the if statement
    if(val->getTrigID()=="na")
    {
        if(val->getOuter()==0)
        {}
        else if(val->getExp()==0)
            outPut<<abs(val->getOuter());
        else if(val->getExp()==1 && val->getOuter()!=1)
            outPut<<abs(val->getOuter())<<"x";
        else if(val->getExp()==1)
            outPut<<"x";
        else if(val->getOuter()!=1)
            outPut<<abs(val->getOuter())<<"x^"<<val->getExp();
        else
            outPut<<"x^"<<val->getExp();
    }
    // else statement if there is a trig function
    else
    {
        if(val->getTrigID()=="sin")
        {
            //if the exponent is 0 then print out the absolute value of the outer coefficient
            if(val->getExp()==0)
                outPut<<abs(val->getOuter());
            //if the exponent is 1 run this else if part
            else if(val->getExp()==1)
            {
                if(val->getInner()!=0 && val->getOuter()!=1 &&val->getInner()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<" "<<val->getInner()<<"x";
                else if(val->getInner()!=0 && val->getOuter()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<" x";
                else if(val->getInner()!=0 && val->getInner()!=1)
                    outPut<<val->getTrigID()<<" "<<val->getInner()<<"x";
                else if(val->getInner()==1 && ( val->getOuter()==1 || val->getOuter()==-1 ) )
                    outPut<<val->getTrigID()<<" x";
            }
            //if the exponent is not 1 or 0 run this else part
            else
            {
                if(val->getInner()!=0 && val->getOuter()!=1 &&val->getInner()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<"^"<<val->getExp()<<" "<<val->getInner()<<"x";
                else if(val->getInner()!=0 && val->getInner()!=1)
                    outPut<<val->getTrigID()<<"^"<<val->getExp()<<" "<<val->getInner()<<"x";
                else if(val->getInner()!=0 && val->getOuter()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<"^"<<val->getExp()<<" x";
            }
        }
        else if(val->getTrigID()=="cos")
        {
            //if the exponent is 0 then print out the absolute value of the outer coefficient
            if(val->getExp()==0)
                outPut<<abs(val->getOuter());
            //if the exponent is 1 run this else if part
            else if(val->getExp()==1)
            {
                if(val->getInner()!=0 && val->getOuter()!=1 &&val->getInner()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<" "<<val->getInner()<<"x";
                else if(val->getInner()!=0 && val->getOuter()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<" x";
                else if(val->getInner()!=0 && val->getInner()!=1)
                    outPut<<val->getTrigID()<<" "<<val->getInner()<<"x";
                else if(val->getInner()==1 && ( val->getOuter()==1 || val->getOuter()==-1))
                    outPut<<val->getTrigID()<<" x";
            }
            //if the exponent is not 1 or 0 run this else part
            else
            {
                if(val->getInner()!=0 && val->getOuter()!=1 &&val->getInner()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<"^"<<val->getExp()<<" "<<val->getInner()<<"x";
                else if(val->getInner()!=0 && val->getInner()!=1)
                    outPut<<val->getTrigID()<<"^"<<val->getExp()<<" "<<val->getInner()<<"x";
                else if(val->getInner()!=0 && val->getOuter()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<"^"<<val->getExp()<<" x";
                else if(val->getInner()==1 && ( val->getOuter()==1 || val->getOuter()==-1 ) )
                    outPut<<val->getTrigID()<<"^"<<val->getExp()<<" x";
            }
        }
        else
        {
            //if the exponent is 0 then print out the absolute value of the outer coefficient
            if(val->getExp()==0)
                outPut<<abs(val->getOuter());
            //if the exponent is 1 run this else if part
            else if(val->getExp()==1)
            {
                if(val->getInner()!=0 && val->getOuter()!=1 &&val->getInner()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<" "<<val->getInner()<<"x";
                else if(val->getInner()!=0 && val->getOuter()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<" x";
                else if(val->getInner()!=0 && val->getInner()!=1)
                    outPut<<val->getTrigID()<<" "<<val->getInner()<<"x";
                else if(val->getInner()==1 && ( val->getOuter()==1 || val->getOuter()==-1))
                    outPut<<val->getTrigID()<<" x";
            }
            //if the exponent is not 1 or 0 run this else part
            else
            {
                if(val->getInner()!=0 && val->getOuter()!=1 &&val->getInner()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<"^"<<val->getExp()<<" "<<val->getInner()<<"x";
                else if(val->getInner()!=0 && val->getInner()!=1)
                    outPut<<val->getTrigID()<<"^"<<val->getExp()<<" "<<val->getInner()<<"x";
                else if(val->getInner()!=0 && val->getOuter()!=1 && val->getOuter()!=-1)
                    outPut<<abs(val->getOuter())<<val->getTrigID()<<"^"<<val->getExp()<<" x";
                else if(val->getExp()!=1)
                    outPut<<val->getTrigID()<<"^"<<val->getExp()<<" x";
            }
        }
    }
    return outPut;
}
void Node::derive() ///finds the derivative of the given Node
{
    //if there is a trig function then switches the trigID and multiplies the outer coefficient by the inner coefficient
    if(TrigID!="na")
    {
        if(TrigID=="sin")
        {
            TrigID="cos";
            OuterCoefficient*=InnerCoefficient;
        }
        else if(TrigID=="cos")
        {
            TrigID="sin";
            OuterCoefficient*=InnerCoefficient;
            OuterCoefficient*=-1;
            CheckNegative();
        }
        else if(TrigID=="tan")
        {
            TrigID="sec";
            OuterCoefficient*=InnerCoefficient;
            Exponent=2;
        }

    }
    //if there is no trig then multiplies the outer coefficient by the exponent and updates the negative if need to
    else
    {
        OuterCoefficient*=Exponent;
        CheckNegative();
        if(Exponent!=0)
            Exponent--;
    }

}
