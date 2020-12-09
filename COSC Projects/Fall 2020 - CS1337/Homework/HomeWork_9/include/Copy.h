#ifndef COPY_H
#define COPY_H

#include "FileFilter.h"


class Copy : public FileFilter
{
    public:
        Copy();
        virtual ~Copy();


        void doFilter(std::ifstream &in, std::ofstream &out);

    protected:

    private:
};

#endif // COPY_H
