#ifndef FILEFILTER_H
#define FILEFILTER_H
#include <iostream>
#include <fstream>

class FileFilter
{
    public:
        FileFilter() {}
        virtual ~FileFilter() {}


        virtual void doFilter(std::ifstream &in, std::ofstream &out)=0;

    protected:

    private:
};

#endif // FILEFILTER_H
