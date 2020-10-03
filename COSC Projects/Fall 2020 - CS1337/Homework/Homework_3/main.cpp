#include <iostream>
#include <string>
using namespace std;

int OddSum(int Array[], int length, int i=1)
{
    static int sum=0;
    if(i>=length)
        return 0;
    sum=sum+Array[i]+OddSum(Array,length,i+2);
    return sum;
}
void Backwards(string str)
{
static int i=str.length()-1;
if(i<0)
   return;
cout<<str[i];
i--;
Backwards(str);
}
int main()
{
    int ListLength;
    cout<<"How many numbers do you have?"<<endl;
    cin>>ListLength;
    int *ListNum=new int[ListLength];
    cout<<"give all the numbers that you want to put in the array."<<endl;
    for(int i=0; i<ListLength; i++)
    {
        cin>>ListNum[i];
    }
    cout<<endl<<"all the odd numbers added together is: "<<OddSum(ListNum,ListLength);
    string str;
    cout<<endl<<"enter a string"<<endl;
    cin.ignore();
    getline(cin,str);
    Backwards(str);
    return 0;
}
