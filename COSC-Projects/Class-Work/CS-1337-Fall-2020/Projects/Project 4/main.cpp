// Made by: Kevin Boudreaux
// COSC-1337002
// 11/19/2020
// this code is used to find the derivative of functions that are basic trigs and or just X's
// this is the first time Classes was used and are used to show an understanding of how classes work in C++
#include <iostream>
#include <string>
#include "LList.h"
#include<fstream>
#include<sstream>

using namespace std;

void FindInfo(ifstream&,ofstream&);

int main()
{
    //finding and opening the file given by the user
    ofstream Derive("derive.txt",ios::binary);
    string FileName;
    cout<<"What is the name of the File you wish to derive?"<<endl;
    getline(cin,FileName);
    ifstream FunctionsFile(FileName);

    //simple loop that, if the file can not be found ask for the file name again
    if(!FunctionsFile)
    {
        do
        {
            cout<<"Incorrect file name, make the file is in the same folder as the .exe"<<endl;
            cout<<endl<<"What is the name of the input file?"<<endl;
            getline(cin,FileName);
            FunctionsFile.open(FileName);
        }while(!FunctionsFile);
    }

    //Calls the function that deals with the equations
    FindInfo(FunctionsFile,Derive);

    //close all files
    Derive.close();
    FunctionsFile.close();

    return 0;
}

void FindInfo(ifstream &FunctionsFile, ofstream &Derive)
{

    string Line;
    int WhileCounter=0;
    //Master while loop that gets each line of the file
    while(getline(FunctionsFile,Line))
    {
        WhileCounter++;
        cout<<endl<<endl<<Line<<endl;
        Node* Head=new Node;
        Node* temp=Head;
        int counter=0;
        string LinePart;

        //Line is used to hold a line from the file
        while(Line!="")
        {
            bool partRemoved=false;
            bool infoCollected=false;
            long long unsigned int Xloc=-2;
            //for loop passes through the Line looking for the first + or -, grabs everything to the right of the space right to the +/-
            for(long long unsigned int i=0;i<Line.length();i++)
            {
                if (Line[i] == ' ')
                {
                    if(Line[i+1]=='+')
                    {
                        if(Line[i+2]==' ')
                        {
                            LinePart=Line.substr(0,i);
                            Line=Line.erase(0,i+3);
                            partRemoved=true;
                            break;
                        }
                    }
                    if(Line[i+1]=='-')
                    {
                        if(Line[i+2]==' ')
                        {
                            //If the loop finds a negative sign, it sets up the next node and makes it negative so we don't miss the negative sign
                            Node* temp2 = new Node;
                            temp->setNext(temp2);
                            temp2->setNegative(true);
                            LinePart=Line.substr(0,i);
                            Line=Line.erase(0,i+3);
                            partRemoved=true;
                            break;
                        }
                    }
                }
            }
            //If we did not find a +/- we run this if statement, this runs for the last part part of the equation
            if(!partRemoved)
            {
                LinePart=Line;
                Line="";
            }
            //This for loop goes through the part of the line that we grabbed. Looking for a T,C,S, or X. This tells us if it is a trig function or not.
            for(long long unsigned int i=0;i<LinePart.length();i++)
            {
                //this if statement runs if the part is a trig function
                if(LinePart[i]=='t'||LinePart[i]=='s'||LinePart[i]=='c')
                {
                    //if the loop is not at the first spot, we grab everything to the left of the T,C,S and put it in setOuter
                    if(i!=0)
                        {
                            string temps =LinePart.substr(0,i);
                            int x=0;
                            stringstream a(temps);
                            a>>x;
                            temp->setOuter(x);
                        }
                    //the else statement if there is nothing to the left of the trig function
                    else
                        temp->setOuter(1);

                    //If the trig is a tangent, we run this if statement. Setting Exp to -5000-counter is used so when the List is sorted, trig will be at the end
                    if(LinePart[i]=='t')
                     {
                         temp->setTrigID("tan");
                         temp->setExp((-5000-counter));
                         counter++;
                     }
                    //If the trig is a sin, we run this if statement. Setting Exp to -5000-counter is used so when the List is sorted, trig will be at the end
                    else if(LinePart[i]=='s')
                     {
                         temp->setTrigID("sin");
                         temp->setExp((-5000-counter));
                         counter++;
                     }
                    //If the trig is a cos, we run this if statement. Setting Exp to -5000-counter is used so when the List is sorted, trig will be at the end
                    else if(LinePart[i]=='c')
                     {
                         temp->setTrigID("cos");
                         temp->setExp((-5000-counter));
                         counter++;
                     }

                    //after finding the trig part, we remove everything up to the space after the end of the trig part
                    LinePart=LinePart.substr(i+4);
                    //we are now looking for X in the for loop.
                    for(long long unsigned int j=0;j<LinePart.length();j++)
                    {
                        if(LinePart[j]=='x')
                        {
                            //if only have X in the LinePart then we set inner as 1
                            if(LinePart.length()==1)
                            {
                                temp->setInner(1);
                                Xloc=j;
                                break;
                            }
                            //else we grab everything up to X and put it in as inner
                            string temps=LinePart.substr(0,j);
                            int x=0;
                            stringstream a(temps);
                            a>>x;
                            temp->setInner(x);

                            Xloc=j;
                        }
                    }
                    //this if statement is used to see if X was not found, if not found then we grab everything left and put it in setInner
                    if(Xloc==-2)
                    {
                        int x=0;
                        stringstream a(LinePart);
                        a>>x;
                        temp->setInner(x);
                    }
                    infoCollected=true;
                    break;
                }
                //This if statement is used if there is no trig function and if there is not outer coefficient
                if(i==0&&LinePart[i]=='x')
                {
                    temp->setOuter(1);
                    LinePart=LinePart.erase(0,1);
                    //this if statement is used to check to see if there is a exponent and if so throw it into setExp
                    if(LinePart.length()!=0)
                    {
                        LinePart.erase(0,1);
                        int x=0;
                        stringstream a(LinePart);
                        a>>x;
                        temp->setExp(stoi(LinePart));
                    }
                    else
                        temp->setExp(1);
                    infoCollected=true;
                    break;
                }
                //this if statement is used if there is no trig and there is a outer coefficient that is larger than 1
                if(LinePart[i]=='x')
                {
                    //this part finds the outer coefficient
                    string temps=LinePart.substr(0,i);
                    int x=0;
                    stringstream a(temps);
                    a>>x;
                    temp->setOuter(x);
                    LinePart=LinePart.erase(0,i+1);
                    //if statement is used to find exponent if there is one
                    if(LinePart.length()!=0)
                    {
                        LinePart.erase(0,1);
                        int x=0;
                        stringstream a (LinePart);
                        a>>x;
                        temp->setExp(x);
                    }
                    else
                        temp->setExp(1);
                    infoCollected=true;
                    break;

                }
            }
            //this if statement is used if no trig or x was found, we grab what ever there is and throw it into the outer coefficient
            if(!infoCollected)
            {
                int x=0;
                stringstream a (LinePart);
                a>>x;
                temp->setOuter(x);
            }
            if(!(temp->getNext())&&Line!="")
                temp->setNext(new Node);
            temp=temp->getNext();

        }
    temp=Head;

    //we create a new List and pass through our anchor into it to make a deep copy
    LList* List=new LList(Head);
    cout<<(*List)<<endl;
    //the list gets sorted based on exponent
    List->sort();
    //fix trig is used to reset the trig exponents to 1
    List->FixTrig();
    cout<<(*List)<<endl;
    //derive is used find the derivative of the equation
    List->derive();
    cout<<(*List)<<endl;
    //this is used to print the derivative into the derive file
    if(WhileCounter!=1)
        Derive<<endl;
    Derive<<(*List)<<endl;
    // delete the list
    List->~LList();

    }
}
