#include "Copy.h"

Copy::Copy()
{
    //ctor
}

Copy::~Copy()
{
    //dtor
}
void Copy::doFilter(std::ifstream &in, std::ofstream &out)
{
    char ch;
    while(!in.eof())
    {
        in.get(ch);
        if(in.eof())
            break;
        out<<ch;
    }
}
