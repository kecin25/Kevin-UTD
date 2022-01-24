#ifndef UPPERCASE_H
#define UPPERCASE_H

#include "FileFilter.h"


class Uppercase : public FileFilter
{
    public:
        Uppercase();
        virtual ~Uppercase();
        char transform(char ch);
        void doFilter(std::ifstream &in, std::ofstream &out);

    protected:

    private:
};

#endif // UPPERCASE_H
