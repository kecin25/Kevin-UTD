#include <iostream>
#include<fstream>
#include <string>
using namespace std;


bool  CheckDown(string);
int  CheckDistance(string);
char CheckDirection(string);
bool CheckBold(string);
bool CheckPrint(string);
int main()
{
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
    bool IsDown=false;
    char Direction='0';
    int Distance=0;
    bool Bold=false;
    bool Print=false;
    char Ink='*';

    string CommandLine;

    //repeating code that will read the commands and update the info for each line
    while(getline(Commands,CommandLine))
    {
        IsDown=CheckDown(CommandLine);
        Direction=CheckDirection(CommandLine);
        Distance=CheckDistance(CommandLine);
        Bold=CheckBold(CommandLine);
        Print=CheckPrint(CommandLine);

        if(Bold)
            Ink='#';
        else
            Ink='*';

        if(IsDown)
        {
            if(Direction=='N')
            {

                for(int i=0; i<Distance; i++)
                {
                  int pos=-53;
                    Paintfile.seekp(pos, ios::cur);
                    Paintfile<<Ink;
                    Paintfile.flush();
                }

            }
            if(Direction=='E')
            {
                for(int i=0; i<Distance; i++)
                {
                    Paintfile<<Ink;
                    Paintfile.flush();
                }
            }
            if(Direction=='S')
            {
                for(int i=0; i<Distance; i++)
                {
                    int pos=51;

                    Paintfile.seekp(pos, ios::cur);
                    Paintfile<<Ink;
                    Paintfile.flush();
                }
            }
            if(Direction=='W')
            {
                for(int i=0; i<Distance; i++)
                {
                   int pos=-2;
                    Paintfile.seekp(pos, ios::cur);
                    Paintfile<<Ink;
                    Paintfile.flush();
                }
            }
        }
        if(!IsDown)
        {
            if(Direction=='N')
            {
                for(int i=1; i<=Distance; i++)
                {
                    Paintfile.seekp(-52, ios::cur);
                }
            }
            if(Direction=='E')
            {
                Paintfile.seekp(+Distance, ios::cur);
            }
            if(Direction=='S')
            {
                for(int i=0; i<Distance; i++)
                {
                    Paintfile.seekp(52, ios::cur);
                }
            }
            if(Direction=='W')
            {
                Paintfile.seekp(-Distance, ios::cur);
            }
        }
        if(Print)
        {

              long pos2=Paintfile.tellp();
              Paintfile.seekp(ios_base::beg);
              string PrintingToConsol;
              while(getline(Paintfile,PrintingToConsol))
              {
                 if(PrintingToConsol!="")
                 cout<<PrintingToConsol<<endl;
              }
              Paintfile.close();
              Paintfile.open("paint.txt",ios::in | ios::out | ios::binary);
               Paintfile.seekp(pos2);
        }
    }
    Paintfile.close();

    return 0;
}
bool CheckDown(string CommandLine)
{
    //grabs the first character of the Command Line and checks to see if the pen is up or down
    string checker = CommandLine.substr(0,1);
    if(checker[0]=='1')
        return false;
    if(checker[0]=='2')
        return true;
    else
        return 404;

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
    else
        return ' ';

}
bool CheckBold(string CommandLine)
{
    //removes everything before the spot for Bold to appear then checks to see if the first spot of the string is B or P, this way if it prints but
    //not bold it will be able to stop
    for(int i=0; i<=CommandLine.length(); i++)
    {
        if(CommandLine[i]=='B'||CommandLine[i]=='b')
            return true;

    }
    return false;
    /*
    CommandLine.erase(CommandLine.begin()+0,CommandLine.end()-4);
    do
       {
          CommandLine.erase(0,1);
          if(CommandLine.length()==0)
             break;
       }while(CommandLine[1]!='B'||CommandLine[1]!='P');
    //CommandLine.erase(0,1);
    if(CommandLine[0]=='B')
       return true;
    else return false
    */
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
    else
        return false;
}
