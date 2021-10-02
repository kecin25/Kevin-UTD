#include <iostream>
#include<iomanip>
#include<fstream>
#include<string>
#include<memory>
using namespace std;


int main()
{
   string FileName;
   cout<<"Give me the name of the File: ";
   getline(cin,FileName);
   ifstream InFile(FileName);
   string temp;
   getline(InFile,temp);
   int NumOfAnswers=temp.length();
   unique_ptr<char>Answer(new char[NumOfAnswers]);

   for(int i=0;i<NumOfAnswers;i++)
      {
         *(Answer.get()+i)=temp.at(i);
      }
   cout<<setprecision(2)<<fixed;
   string student_answers;
   string student_ID;
   while(getline(InFile,temp))
   {
      int pos=temp.find(' ');
      student_ID=temp.substr(0,pos);
      student_answers=temp.substr(pos+1);

      int NumRight=0,NumWrong=0,NumClear=0;
      for(int i=0;i<NumOfAnswers;i++)
      {
         if(student_answers.at(i)==' ')
            NumClear++;
         else if(student_answers.at(i)==*(Answer.get()+i))
            NumRight++;
         else if(student_answers.at(i)!=*(Answer.get()+i))
            NumWrong++;
      }
      cout<<student_ID<<endl;

      float student_score=((NumRight*2)-(NumWrong));
      if(student_score<0)
         student_score=0;

      float student_percentage_score=(student_score/(NumOfAnswers*2))*100;
      cout<<student_percentage_score<<endl;

      if(student_percentage_score>=90)
         cout<<"A"<<endl;
      else if(student_percentage_score<90&&student_percentage_score>=80)
         cout<<"B"<<endl;
      else if(student_percentage_score<80&&student_percentage_score>=70)
         cout<<"C"<<endl;
      else if(student_percentage_score<70&&student_percentage_score>=60)
         cout<<"D"<<endl;
      else if(student_percentage_score<60)
         cout<<"F"<<endl;
   }
    return 0;
}
