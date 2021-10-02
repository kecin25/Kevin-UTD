#include <iostream>
#include "Encryption.h"
#include "Uppercase.h"
#include "Copy.h"
#include <fstream>
#include <string>
using namespace std;

int main()
{
    string inner;
    getline(cin,inner);

 ofstream out("EncryptionDefault.txt",ios::binary);
 ifstream in(inner);

 Encryption first;

 first.doFilter(in,out);

 out.close();
 in.close();


 out.open("Encryption12.txt",ios::binary);
 in.open(inner);

 first.setKey(12);
 first.doFilter(in,out);

 out.close();
 in.close();

 out.open("Encryption-4.txt",ios::binary);
 in.open(inner);

 first.setKey(-4);
 first.doFilter(in,out);

 out.close();
 in.close();

 out.open("Uppercase.txt",ios::binary);
 in.open(inner);
 Uppercase second;
 second.doFilter(in,out);
 out.close();
 in.close();

 out.open("Copy.txt",ios::binary);
 in.open(inner);
 Copy third;
 third.doFilter(in,out);
 out.close();
 in.close();

    return 0;
}
