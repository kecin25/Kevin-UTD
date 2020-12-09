#include "Encryption.h"

Encryption::Encryption() //Constructor
{
    key=5;
}

Encryption::Encryption(int x) //Overloaded Constructor
{
    key=x;
}
Encryption::~Encryption() //Destructor
{

}


void Encryption::doFilter(std::ifstream &in, std::ofstream &out)
{
    char text;
    while(!in.eof())
    {
        in.get(text);
        if(in.eof())
            break;
        if(text<32)
        {
            out<<text;
            continue;
        }

        int temp=text;
        temp+=key;



        if(temp>126)
        {
            int i=temp-126;
            temp=31+i;
        }

        else if(temp<32)
           {
             int i =32-temp;
             temp=127-i;
           }

        text=temp;
        out<<text;
    }
}

