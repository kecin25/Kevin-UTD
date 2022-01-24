#include <iostream>
#include<fstream>
#include <string>
using namespace std;


int  CheckDown(string);
int  CheckDistance(string,int,bool);
char CheckDirection(string,bool);
int CheckBold(string,int);
int CheckPrint(string,int);
int CommaCounter(string,int);
bool PenChecker(string);
int DirectionLength(char,int,int,int);
bool BoldAndPrintChecker(string,int,bool,bool);
int CommaChecker(string,int,bool,bool,bool);
bool BadCommaChecker(string,bool,bool,bool);
int BadLengthFinder(string,int);
int main()
{
    /////////////////////////////////////////////////////////////////////////////////////////
////////////////////          DO  NOT CHANGE CODE BELOW THIS               //////////////
/////////////////////////////////////////////////////////////////////////////////////////

    ifstream infile("paint_base.txt");
    ofstream outfile("paint.txt", ios::binary);
    string c;
    if (infile)
        while (getline(infile, c))
            outfile << c << "\n";
    infile.close();
    outfile.close();
/////////////////////////////////////////////////////////////////////////////////////////
////////////////////          DO  NOT CHANGE CODE ABOVE THIS               //////////////
/////////////////////////////////////////////////////////////////////////////////////////


    fstream Paintfile("paint.txt",ios::in | ios::out | ios::binary);
    //Below Command is opening Sample Commands so we can read from the file
    string FileName;
    cout<<endl<<"What is the name of the input file?"<<endl;
    getline(cin,FileName);

    ifstream Commands(FileName);
    cout<<endl<<endl;

    if(!Commands)
    {
       cout<<"File not found";
      return 1;
    }


    //below is setting up all the data we will gather from the commands
    bool IsDown=false;
    char Direction='0';
    int Distance=0;
    bool Bold=false;
    bool Print=false;
    char Ink='*';
    int counter=0;
    int pos=static_cast<int>(Paintfile.tellp());




    string CommandLine;
    //used to get in correct position in file for ZYbooks
    Paintfile.seekp(1,ios::cur);

    //repeating code that will read the commands and update the info for each line
    while(getline(Commands,CommandLine))
    {
        int ComLen=static_cast<int>(CommandLine.length());  //look into type casting
        int BoldChecker=CheckBold(CommandLine,ComLen);
        int PrintChecker=CheckPrint(CommandLine,ComLen);
        if(BoldChecker==1)
            Bold=false;
        if(BoldChecker==2)
            Bold=true;
        if(PrintChecker==1)
            Print=false;
        if(PrintChecker==2)
            Print=true;
        bool BadLength=false;

        counter++;
        int errors=0;
        pos=static_cast<int>(Paintfile.tellp());

        if(ComLen<5)
            BadLength=true;

        if(BadLength)
        {
            int BadLengthSolver=BadLengthFinder(CommandLine,counter);
            errors=errors+BadLengthSolver;

        }



        bool BadComma=BadCommaChecker(CommandLine,Bold,Print,BadLength);
        if(!BadComma)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"You have too many commas in your command.";
            errors++;
        }





        Distance = CheckDistance(CommandLine,ComLen,BadComma);

        if(Distance==101)
        {
           cout<<endl<<"error on line: "<<counter<<endl<<"Distance is too large.";
           errors++;
        }

        Direction=CheckDirection(CommandLine,BadComma);

        if(Direction==' ')
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Direction must be a Capital N, E, S, or W.";
            errors++;
        }

        errors = errors+static_cast<int> (DirectionLength(Direction,Distance,counter,pos));

        int CommaLocation=CommaChecker(CommandLine,ComLen,Bold,Print,BadLength);

        if(CommaLocation==1)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Need a Comma after your first number.";
            errors++;
            BadComma=false;
        }
        if(CommaLocation==2)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Need a Comma after your Direction.";
            errors++;
            BadComma=false;
        }
        if(CommaLocation==3)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Need a Comma after your Distance.";
            errors++;
            BadComma=false;
        }
        if(CommaLocation==4)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Need a Comma after your Bold.";
            errors++;
            BadComma=false;
        }
        if(BadLength)
            BadComma=false;
        bool CorrectPen=PenChecker(CommandLine);

        if(!CorrectPen)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"First number needs to be 1 or 2 for Pen Down or Up.";
            errors++;
        }






        if(BoldChecker==3)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Bold must be a Capital B.";
            errors++;
        }

        if(PrintChecker==3)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Print must be a Capital P.";
            errors++;
        }

        bool BoldAndPrint =BoldAndPrintChecker(CommandLine,ComLen,Bold,Print);

        if(!BoldAndPrint)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Bold must come before Print.";
            errors++;
        }

        if(CheckDown(CommandLine)==1)
            IsDown=false;
        if(CheckDown(CommandLine)==2)
            IsDown=true;


        if(errors!=0)
        {
            IsDown=false;
            Distance=0;
            Direction='E';
            Bold=false;
            Print=false;
        }
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
                    int pos=-52;
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
                    int pos=50;

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
                    Paintfile.seekp(-51, ios::cur);
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
                    Paintfile.seekp(51, ios::cur);
                }
            }
            if(Direction=='W')
            {
                Paintfile.seekp(-Distance, ios::cur);
            }
        }
        if(Print)
        {


            pos=static_cast<int>(Paintfile.tellp());
            Paintfile.seekp(ios_base::beg);
            string PrintingToConsol;
            while(getline(Paintfile,PrintingToConsol))
            {
                if(PrintingToConsol!="")
                    cout<<PrintingToConsol<<endl;
            }
            Paintfile.close();
            Paintfile.open("paint.txt",ios::in | ios::out | ios::binary);
            Paintfile.seekp(pos);
        }
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
    if(!isdigit(checker[0]))
        return 4;
    else
        return 3;

}
int CheckDistance(string CommandLine,int ComLen,bool BadCommas)
{
    //Removes the up/down part and the direction then looks to see the distance


    if(!BadCommas)
        return 1;

    if(CommaCounter(CommandLine,ComLen)>=2)
    {



        string checker=CommandLine.substr(4);
        int i=checker.length();
        while(!isdigit(checker[--i]))
        {
            --i;
        }
        checker=checker.substr(0,i+1);
        int str=checker.length();
        if(str>2)
         return 101;
        else return stoi(checker);
    }
    return 404;
}
char CheckDirection(string CommandLine,bool BadComma)
{
    //removes pen up/down and looks for the direction

    if(!BadComma)
        return '|';
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
int CheckBold(string CommandLine,int ComLen)
{
    //removes everything before the spot for Bold to appear then checks to see if the first spot of the string is B or P, this way if it prints but
    //not bold it will be able to stop
    for(int i=0; i<=ComLen; i++)
    {
        if(CommandLine[i]=='B')
            return 2;
        if(CommandLine[i]=='b')
            return 3;

    }
    return 1;
}
int CheckPrint(string CommandLine,int ComLen)
{
    //same as CheckBold but without the bold part
    for(int i=0; i<=ComLen; i++)
    {
        if(CommandLine[i]=='P')
            return 2;
        if(CommandLine[i]=='p')
            return 3;
    }
    return 1;
}
int CommaCounter(string CommandLine, int ComLen)
{
    int amount=0;
    for(int i=0; i<=ComLen; i++)
    {
        if(CommandLine[i]==',')
            amount++;
    }
    return amount;
}
bool PenChecker(string CommandLine)
{
    if(CommandLine[0]!='1'&&CommandLine[0]!='2')
        return false;
    else
        return true;
}
int DirectionLength(char Direciton,int Distance,int counter,int pos)
{
    if(Direciton=='E')
    {
        if(pos%51+Distance>50)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Distance moved can not leave the 50x50 Painting box.";
            return 1;
        }
    }
    if(Direciton=='W')
    {
        if(pos%51-Distance<=0)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Distance moved can not leave the 50x50 Painting box.";
            return 1;
        }
    }
    if(Direciton=='S')
    {
        if(pos+(Distance*51)>2550)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Distance moved can not leave the 50x50 Painting box.";
            return 1;
        }
    }
    if(Direciton=='N')
    {
        if(pos-(Distance*51)<0)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Distance moved can not leave the 50x50 Painting box.";

            return 1;
        }
    }
    return 0;
}
bool BoldAndPrintChecker(string CommandLine,int ComLen,bool Bold,bool Print)
{
    if(!Bold||!Print)
        return true;
    int BoldCounter=0;
    int PrintCounter=0;

    for(int i=0; i<=ComLen; i++)
    {
        if(CommandLine[i]=='P'||CommandLine[i]=='p')
            PrintCounter=i;
        if(CommandLine[i]=='B'||CommandLine[i]=='b')
            BoldCounter=i;
    }
    if(PrintCounter>BoldCounter)
        return true;
    if(PrintCounter<BoldCounter)
        return false;

    return false;
}
int CommaChecker(string CommandLine,int ComLen,bool Bold,bool Print,bool BadString)
{
    if(BadString)
        return 50;
    int pos=0;
    if(Bold&&Print)
    {
        for(int i=0; i<ComLen; i++)
        {
            if(CommandLine[i]=='N')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[i]=='E')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[i]=='S')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[i]=='W')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[1]!=',')
                return 1;
            if(CommandLine[i]=='B'||CommandLine[i]=='b')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 4;
            }
            if(CommandLine[i]=='0'||CommandLine[i]=='1'||CommandLine[i]=='2'||CommandLine[i]=='3'||CommandLine[i]=='4'||CommandLine[i]=='5'||CommandLine[i]=='6'||CommandLine[i]=='7'||CommandLine[i]=='8'||CommandLine[i]=='9')
            {
                pos=i+1;
                if(!isdigit(CommandLine[pos])&&CommandLine[pos]!=',')
                {
                   return 3;
                }
                else if(isdigit(CommandLine[pos]))
                {
                   pos++;
                   if(CommandLine[pos]!=',')
                     return 3;
                   else
                     pos--;
                }
            }
        }
    }
    else if(Bold||Print)
    {
        for(int i=0; i<ComLen; i++)
        {
            if(CommandLine[i]=='N')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[i]=='E')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[i]=='S')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[i]=='W')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[1]!=',')
                return 1;
            if(CommandLine[i]=='0'||CommandLine[i]=='1'||CommandLine[i]=='2'||CommandLine[i]=='3'||CommandLine[i]=='4'||CommandLine[i]=='5'||CommandLine[i]=='6'||CommandLine[i]=='7'||CommandLine[i]=='8'||CommandLine[i]=='9')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                {
                    pos++;
                    if((CommandLine[pos]!=','))
                        return 3;
                }
            }
        }
    }
    else
    {
        for(int i=0; i<ComLen; i++)
        {
            if(CommandLine[i]=='N')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[i]=='E')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[i]=='S')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[i]=='W')
            {
                pos=i+1;
                if(CommandLine[pos]!=',')
                    return 2;
            }
            if(CommandLine[1]!=',')
                return 1;
        }
    }
    return 404;
}
bool BadCommaChecker(string CommandLine, bool Bold,bool Print,bool BadString)
{
    if(BadString)
        return true;

    bool BigNumber=false;
    if(isdigit(CommandLine[5]))
        BigNumber=true;
    if(Bold&&Print&&BigNumber)
    {
        if(CommandLine[0]==','||CommandLine[2]==','||CommandLine[4]==','||CommandLine[5]==','||CommandLine[7]==','||CommandLine[9]==',')
            return false;
    }
    if(Bold||Print)
    {
        if(BigNumber)
        {
            if(CommandLine[0]==','||CommandLine[2]==','||CommandLine[4]==','||CommandLine[5]==','||CommandLine[7]==',')
                return false;
        }
        else
        {
            if(CommandLine[0]==','||CommandLine[2]==','||CommandLine[4]==','||CommandLine[6]==',')
                return false;
        }
    }
    if(BigNumber)
    {
        if(CommandLine[0]==','||CommandLine[2]==','||CommandLine[4]==','||CommandLine[5]==',')
            return false;
    }
    if(!BigNumber)
    {
        if(CommandLine[0]==','||CommandLine[2]==','||CommandLine[4]==',')
            return false;
    }
    return true;
}
int BadLengthFinder(string CommandLine,int line)
{
    int counter=0;
    if(!isdigit(CommandLine[0]))
    {
        cout<<endl<<"error on line: "<<line<<endl<<"You are missing a number for Pen up/down.";
        counter++;
    }
    if(!isalpha(CommandLine[4]))
    {
        cout<<endl<<"error on line: "<<line<<endl<<"You are missing a Character for Direction.";
        counter++;
    }
    if(CommandLine[1]!=',')
    {
        cout<<endl<<"error on line: "<<line<<endl<<"You are missing a Comma,You need a comma after your first number.";
        counter++;
    }
    if(CommandLine[3]!=',')
    {
        cout<<endl<<"error on line: "<<line<<endl<<"You are missing a Comma,You need a comma after your direction.";
        counter++;
    }
    if(CommandLine[2]=='N'||CommandLine[2]=='E'||CommandLine[2]=='S'||CommandLine[2]=='W'||CommandLine[2]=='n'||CommandLine[2]=='e'||CommandLine[2]=='s'||CommandLine[2]=='w')
    {
        cout<<endl<<"error on line: "<<line<<endl<<"You are missing Your Direction.";
        counter++;
    }

    return counter;

}
