#include "Uppercase.h"

Uppercase::Uppercase()
{
    //ctor
}

Uppercase::~Uppercase()
{
    //dtor
}


char Uppercase::transform(char ch)
{
    if(ch>=97&&ch<=122)
    {
        ch-=32;
    }
    return ch;
}
void Uppercase::doFilter(std::ifstream &in, std::ofstream &out)
{
    char ch;
    while(!in.eof())
    {
        in.get(ch);
        if(in.eof())
            break;
        ch=transform(ch);
        out<<ch;
    }
}
