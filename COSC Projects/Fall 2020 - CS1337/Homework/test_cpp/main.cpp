#include <fstream>
#include <iostream>
using namespace std;

int sumint(int);
int main()
{
  // cout<<"give a number to add all numbers under it:";
   //cin>>total;
   char c;
  do{
   cout<<"give a number that will add all other numbers below it starting at zero:"<<endl;
   int num;
   cin>>num;
cout<<endl<<sumint(num);
cout<<endl<<"want to go again? y or n"<<endl;
cin>>c;
    }while(c=='y');
    return 0;
}

int sumint(int i)
{


   if(i==1)
      return 1;
   /* total=*/return i+=sumint(i-1);
   //return total;
}

