#ifndef ENCRYPTION_H
#define ENCRYPTION_H

#include "FileFilter.h"


class Encryption : public FileFilter
{
    public:
        Encryption();
        Encryption(int);
        virtual ~Encryption();

        void setKey(int x){ key = x; }
        int getKey() {return key;}
        void doFilter(std::ifstream &in, std::ofstream &out);

    protected:

    private:
        int key;
};

#endif // ENCRYPTION_H
