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

    int pointer=0;

    //repeating code that will read the commands and update the info for each line
    while(getline(Commands,CommandLine))
    {
        // PaintFile.open("paint.txt", ios::)
//
        IsDown=CheckDown(CommandLine);
        Direction=CheckDirection(CommandLine);
        Distance=CheckDistance(CommandLine);
        Bold=CheckBold(CommandLine);
        Print=CheckPrint(CommandLine);


        cout<<CommandLine.data()<<endl;
        cout<<endl<<"Pen is: "<<IsDown<<" Direction is: "<<Direction<<" Distance is: "<<Distance<<" Bold is: "<<Bold<<" Print is: "<<Print<<endl;

        if(Bold)
            Ink='#';
        else
            Ink='*';

        if(IsDown)
        {
            if(Direction=='N')
            {
                /*
                  Paintfile.seekp(-53, ios::cur);
                  pointer=pointer-53;
                  cout<<Paintfile.tellp()<<" ";
                  Paintfile<<Ink;
                  Paintfile.flush();
                  Paintfile.seekp(-1,ios::cur);
                  pointer--;
                  cout<<Paintfile.tellp()<<" "; */
                for(int i=0; i<Distance; i++)
                {
                    //if(first)
                    //-51                //change movement type for printing
                    //else -52
                     int pos=-53;
                    if(i==0)
                    {
                       pos=-53;
                    }
                    Paintfile.seekp(pos, ios::cur);
                    pointer=pointer+pos;
                    cout<<Paintfile.tellp()<<" ";
                    Paintfile<<Ink;
                    Paintfile.flush();
                }
                //Paintfile.seekp(-1,ios::cur);
               // pointer--;
               // cout<<Paintfile.tellp()<<" ";
            }
            if(Direction=='E')
            {
                for(int i=0; i<Distance; i++)
                {
                    cout<<Paintfile.tellp()<<" ";
                    Paintfile<<Ink;
                    Paintfile.flush();
                    pointer=pointer+1;
                }
                //Paintfile.seekp(-1,ios::cur);
                //pointer--;
                //cout<<Paintfile.tellp()<<" ";
            }
            if(Direction=='S')
            {
                for(int i=0; i<Distance; i++)
                {
                    int pos=51;

                    Paintfile.seekp(pos, ios::cur);
                    pointer=pointer+pos;
                    cout<<Paintfile.tellp()<<" ";
                    Paintfile<<Ink;
                    Paintfile.flush();
                    pointer++;
                    //Paintfile.seekp(0,ios::cur);pointer=pointer-0; cout<<Paintfile.tellp()<<" ";
                }/*Paintfile.seekp(-1,ios::cur)*/;
                cout<<Paintfile.tellp()<<" ";
            }
            if(Direction=='W')
            {
                for(int i=0; i<Distance; i++)
                {
                    Paintfile.seekp(-2, ios::cur);
                    pointer=pointer-2;
                    cout<<Paintfile.tellp()<<" ";
                    Paintfile<<Ink;
                    Paintfile.flush();
                    pointer++;
                }
               // Paintfile.seekp(-1,ios::cur);
               //pointer--;
                //cout<<Paintfile.tellp()<<" ";
            }
        }
        if(!IsDown)
        {
            if(Direction=='N')
            {
                for(int i=1; i<=Distance; i++)
                {
                    Paintfile.seekp(-52, ios::cur);
                    pointer=pointer-52;
                    cout<<Paintfile.tellp()<<" ";
                }
            }
            if(Direction=='E')
            {
                cout<<Paintfile.tellp()<<" ";
                Paintfile.seekp(+Distance, ios::cur);
                pointer=pointer+Distance;
                cout<<Paintfile.tellp()<<" ";
            }
            if(Direction=='S')
            {
                for(int i=0; i<Distance; i++)
                {
                    Paintfile.seekp(52, ios::cur);
                    pointer=pointer+52;
                    cout<<Paintfile.tellp()<<" ";
                }
            }
            if(Direction=='W')
            {
                Paintfile.seekp(-Distance, ios::cur);
                pointer=pointer-Distance;
                cout<<Paintfile.tellp()<<" ";
            }
        }
        if(Print)
        {
            /*
              Paintfile.close();
              Paintfile.open("paint.txt",ios::in);
              string PrintingToConsol;
              while(getline(Paintfile,PrintingToConsol))
              {
                 cout<<PrintingToConsol;
              }

              Paintfile.close();
              Paintfile.open("paint.txt",ios::in|ios::out|ios::app); //think its not saving when re opeing file *********************************************************************************
              Paintfile.tellp();

                                                     // make command file smaller to bug check for wrong command
                           //getline for line to line for consol *****************************************************************
            */
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
