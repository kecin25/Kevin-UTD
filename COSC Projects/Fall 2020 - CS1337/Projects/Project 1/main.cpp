#include <iostream>
#include<fstream>
#include <string>
using namespace std;


int  CheckDown(string);
int  CheckDistance(string);
char CheckDirection(string);
bool CheckBold(string);
bool CheckPrint(string);
int main(){
   /////////////////////////////////////////////////////////////////////////////////////////
   ////////////////////          DO  NOT CHANGE CODE BELOW THIS               //////////////
   /////////////////////////////////////////////////////////////////////////////////////////

   ifstream infile("paint_base.txt");
   ofstream outfile("paint.txt");
   char c;
   if (infile)
      while (infile.get(c))
         outfile << c;

   infile.close();
   outfile.close();

   /////////////////////////////////////////////////////////////////////////////////////////
   ////////////////////          DO  NOT CHANGE CODE ABOVE THIS               //////////////
   /////////////////////////////////////////////////////////////////////////////////////////

   fstream Paintfile("paint.txt",ios::in | ios::out | ios::binary);
   //Below Command is opening Sample Commands so we can read from the file
   string FileName;
   cout<<"What is the name of the input file?"<<endl;
   getline(cin,FileName);

   ifstream Commands(FileName);
   cout<<endl<<endl;

 //  fstream PaintFile("paint.txt")

      //below is setting up all the data we will gather from the commands
      int IsDown=1;
      char Direction='0';
      int Distance=0;
      bool Bold=false;
      bool Print=false;
      char Ink='*';


      //repeating code that will read the commands and update the info for each line
     while(!Commands.eof()) //change to get line*****************************************************************
   {
        // PaintFile.open("paint.txt", ios::)

         string CommandLine;
         CommandLine.clear();
         Commands>>CommandLine;


         cout<<CommandLine.data()<<endl;


         IsDown=CheckDown(CommandLine);

         Direction=CheckDirection(CommandLine);

         Distance=CheckDistance(CommandLine);

         Bold=CheckBold(CommandLine);

         Print=CheckPrint(CommandLine);


   cout<<endl<<"Pen is: "<<IsDown<<" Direction is: "<<Direction<<" Distance is: "<<Distance<<" Bold is: "<<Bold<<" Print is: "<<Print<<endl;

   if(Bold)
      Ink='#';
   else
      Ink='*';

   //while(outfile) Loop is not needed *****************************************************************
   //{

      if(IsDown)
      {
         if(Direction=='N')
         {
            for(int i=0;i<Distance;i++)
            {
             //if(first)
               //-51                //change movement type for printing
            //else -52

               Paintfile.seekp(-52, ios::cur);
                  Paintfile<<Ink;
            }

            //need to also go back on Unit to go back where the last mark was
         }
         if(Direction=='E')
         {
            for(int i=0;i<Distance;i++)
            {
               Paintfile.seekp(1, ios::cur);
               Paintfile<<Ink;
            }
         }
         if(Direction=='S')
         {
            for(int i=0;i<Distance;i++)
            {
               Paintfile.seekp(50, ios::cur);
               Paintfile<<Ink;
            }
         }
         if(Direction=='W')
         {
            for(int i=0;i<Distance;i++)
            {
               Paintfile.seekp(-1, ios::cur);
               Paintfile<<Ink;
            }
         }
      }
      if(!IsDown)
      {
         if(Direction=='N')
         {
            for(int i=0;i<Distance;i++)
            {
               Paintfile.seekp(-51, ios::cur);

            }
         }
         if(Direction=='E')
         {
            for(int i=0;i<Distance;i++)
            {
               Paintfile.seekp(1, ios::cur);

            }
         }
         if(Direction=='S')
         {
            for(int i=0;i<Distance;i++)
            {
               Paintfile.seekp(51, ios::cur);
            }
         }
         if(Direction=='W')
         {
            for(int i=0;i<Distance;i++)
            {
               Paintfile.seekp(-1, ios::cur);
            }
         }
      }
   if(Print)
   {
      char paint[50][50];

      for(int i=0;i<=50;i++)
      {
         for(int j=0;j<=50;j++)
         {
                                          // make command file smaller to bug check for wrong command
           //  outfile>>paint[i][j];      //getline for line to line for consol *****************************************************************
         }
      }

   }


  // }



   }
   Paintfile.close();

return 0;
}
int CheckDown(string CommandLine)
{
   //grabs the first character of the Command Line and checks to see if the pen is up or down
   string checker = CommandLine.substr(0,1);
   if(checker[0]=='1')
      return 1;
   if(checker[0]=='2')
      return 2;
   else return 404;

}
int CheckDistance(string CommandLine)
{
   //Removes the up/down part and the direction then looks to see the distance
   CommandLine.erase(0,4);

  if(isalnum(CommandLine[1]))
      CommandLine.erase(2);
   else
      CommandLine.erase(1);

   int number =stoi(CommandLine);

   return number;

}
char CheckDirection(string CommandLine)
{
   //removes pen up/down and looks for the direction
   string checker = CommandLine.substr(2,1);

   if(checker[0]=='N')
      return 'N';
   if(checker[0]=='E')
      return 'E';
   if(checker[0]=='S')
      return 'S';
   if(checker[0]=='W')
      return 'W';
   else return ' ';

}

bool CheckBold(string CommandLine)
{
   //removes everything before the spot for Bold to appear then checks to see if the first spot of the string is B or P, this way if it prints but
   //not bold it will be able to stop
   CommandLine.erase(CommandLine.begin()+0,CommandLine.end()-3);
   if(CommandLine[0]!='B'||CommandLine[0]!='P')
      CommandLine.erase(0,1);

   CommandLine.erase(0,1);

   if(CommandLine[0]=='B')
      return true;
   else return false;
}
bool CheckPrint(string CommandLine)
{
   //same as CheckBold but without the bold part
   CommandLine.erase(CommandLine.begin()+0,CommandLine.end()-3);
   if(CommandLine[0]!='P')
      CommandLine.erase(0,1);

   CommandLine.erase(0,1);

   if(CommandLine[0]=='P')
      return true;
   else return false;
}
