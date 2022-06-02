#include <iostream>
#include<fstream>
#include <string>
using namespace std;

//the structure below is used to hold a character and a structure pointer to the locations that are next to it in the grid
struct node
{
    char box=' ';
    node *north=nullptr;
    node *east=nullptr;
    node *south=nullptr;
    node *west=nullptr;
};
int  CheckDown(string);
int  CheckDistance(string,int,bool);
char CheckDirection(string,bool);
int CheckBold(string,int);
int CheckPrint(string,int);
int CommaCounter(string,int);
bool PenChecker(string);
int DirectionLength(char,int,int,node*);
bool BoldAndPrintChecker(string,int,bool,bool);
int CommaChecker(string,int,bool,bool,bool);
bool BadCommaChecker(string,bool,bool,bool);
int BadLengthFinder(string,int);

//Recursive function that prints the memory that holds the grid
void PrintMem (ostream &var,node *&head, int row=0)
{
    if(head)
    {
//the first for loop prints the entire row and moves the pointer one right
        for(int i=0; i<50; i++)
        {
            var<<head->box;
            if(i!=49)
                head=head->east;
        }
//After printing the whole row, if not on the last row it adds an endl
        if(row!=49)
            var<<endl;
//this for loop goes back to the leftmost spot in the row
        for(int i=0; i<50; i++)
        {
            if(i!=49)
                head=head->west;
        }
//move row one forward to keep track of which row the function is currently on
        row++;
//calls the function again with the pointer being one row lower, repeats until it does not exist
        PrintMem(var,head->south,row);
    }
}
//deletes the memory grid to free up the memory once the code is finished
void DelMem(node *&head)
{
    node *temp1=head;
    node *temp2=head;
    node *temp3=head;
//outside for loop deals with rows
    for(int j=0; j<50; j++)
    {
//inner for loop goes throw the row, deleting the nodes and freeing up space
        temp3=temp2->south;
        for(int i=0; i<50; i++)
        {
            temp1=temp2->east;
            delete temp2;
            temp2=temp1;
        }
        delete temp2;
        temp2=temp3;
    }
}
int main()
{
    ofstream Paintfile("paint.txt",ios::binary);
    //Below Command is opening Sample Commands so we can read from the file
    string FileName;
    cout<<endl<<"What is the name of the input file?"<<endl;
    getline(cin,FileName);

    ifstream Commands(FileName);
    cout<<endl<<endl;

//if statement below checks to see if the input file is valid and if not ask for a valid input
    if(!Commands)
    {
        do
        {
            cout<<"Incorrect file name, make the file is in the same folder as the .exe"<<endl;
            cout<<endl<<"What is the name of the input file?"<<endl;
            getline(cin,FileName);
            Commands.open(FileName);
        }
        while(!Commands);
    }
    //below is setting up all the data we will gather from the commands
    bool IsDown=false;
    char Direction='0';
    int Distance=0;
    bool Bold=false;
    bool Print=false;
    char Ink='*';
    int counter=0;
    node* body=new node;
    node* head=body;
    node* temp;

//for loop below creates memory spaces for the grid
//outside for loop is used to deal with the rows
    for(int i=0; i<50; i++)
    {
//for the first row we already have one of the 50 spaces needed so we run the 2nd command loop one less
//in the inner for loops body->east makes a new pointer and temp moves to it, body and temp then point to eachother
        if(i==0)
        {
            for(int j=0; j<49; j++)
            {
                body->east=new node;
                temp=body->east;
                temp->west=body;
                body=body->east;
            }
        }
        else
        {
            for(int j=0; j<50; j++)
            {
                body->east=new node;
                temp=body->east;
                temp->west=body;
                body=body->east;
            }
        }
    }
//reseting body and temp so they can be used to deal with North and South pointers
    temp=head;
    body=head;
    int row=0;
//outer loop deals with rows
    for(int i=0; i<50; i++)
    {
        row++;
        for(int j=0; j<50; j++)
        {
//inner for loop deals with setting North and South to the correct nodes
//the first run of the inner loop is used to move one pointer to the correct spot that is below another point
            if(row!=1)
            {
                body->north=temp;
                temp->south=body;
                temp=temp->east;
            }
            body=body->east;
        }
    }
//reseting body and temp to deal with out of bound connections
    temp=head;
    body=head;
//goes down the left side of the grid and removes the west pointer
    while(body)
    {
        if(!(body->south))
            break;
        body=body->south;
        body->west=nullptr;

    }
//moves the pointer to the bottom right corner
    for(int i=0; i<49; i++)
    {
        body=body->east;
    }
//once at the bottom right corner, the for loop goes up and removes the east pointing pointer
    for(int i=0; i<49; i++)
    {
        body=body->north;
        body->east=nullptr;
    }
//reset body and temp to the starting position (top left corner)
    body=head;
    temp=head;

    string CommandLine;
//used to get in correct position in file for ZYbooks
    Paintfile.seekp(1,ios::cur);
//repeating code that will read the commands and update the info for each line
    while(getline(Commands,CommandLine))
    {
//first part of the while loop checks to make sure the input given is correct
        int ComLen=static_cast<int>(CommandLine.length());
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

        errors = errors+static_cast<int> (DirectionLength(Direction,Distance,counter,temp));

        int CommaLocation=CommaChecker(CommandLine,ComLen,Bold,Print,BadLength);

        if(CommaLocation==1)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Need a Comma after your first number.";
            errors++;
            BadComma=false;
        }
        else if(CommaLocation==2)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Need a Comma after your Direction.";
            errors++;
            BadComma=false;
        }
        else if(CommaLocation==3)
        {
            cout<<endl<<"error on line: "<<counter<<endl<<"Need a Comma after your Distance.";
            errors++;
            BadComma=false;
        }
        else if(CommaLocation==4)
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
        else if(CheckDown(CommandLine)==2)
            IsDown=true;

        if(!IsDown&&Bold)
        {
            errors++;
            cout<<endl<<"error on line: "<<counter<<endl<<"Bold can not be true when not printing.";
        }

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
//after checking to see if the input is correct, if the pen is down following code is run
        if(IsDown)
        {
//checks direction of the command and then moves as far as the given distance and prints the Ink into the grid
            if(Direction=='N')
            {
                for(int i=0; i<Distance; i++)
                {
                    temp=temp->north;
                    if(temp->box!='#')
                        temp->box=Ink;
                }
            }
//checks direction of the command and then moves as far as the given distance and prints the Ink into the grid
            if(Direction=='E')
            {
                for(int i=0; i<Distance; i++)
                {
                    temp=temp->east;
                    if(temp->box!='#')
                        temp->box=Ink;
                }
            }
//checks direction of the command and then moves as far as the given distance and prints the Ink into the grid
            if(Direction=='S')
            {
                for(int i=0; i<Distance; i++)
                {
                    temp=temp->south;
                    if(temp->box!='#')
                        temp->box=Ink;
                }
            }
//checks direction of the command and then moves as far as the given distance and prints the Ink into the grid
            if(Direction=='W')
            {
                for(int i=0; i<Distance; i++)
                {
                    temp=temp->west;
                    if(temp->box!='#')
                        temp->box=Ink;
                }
            }
        }
//if the pen is not printing this part of the code will run
        if(!IsDown)
        {
//checks direction of the command and then moves as far as the given distance without printing to the grid
            if(Direction=='N')
            {
                for(int i=1; i<=Distance; i++)
                {
                    temp=temp->north;
                }
            }
//checks direction of the command and then moves as far as the given distance without printing to the grid
            if(Direction=='E')
            {
                for(int i=0; i<Distance; i++)
                {
                    temp=temp->east;
                }
            }
//checks direction of the command and then moves as far as the given distance without printing to the grid
            if(Direction=='S')
            {
                for(int i=0; i<Distance; i++)
                {
                    temp=temp->south;
                }

            }
//checks direction of the command and then moves as far as the given distance without printing to the grid
            if(Direction=='W')
            {
                for(int i=0; i<Distance; i++)
                {
                    temp=temp->west;
                }
            }
        }
//if the command tells the code to print out the grid then the following part is ran
        if(Print)
        {
//calls the Print memory function for cout and for the file
            PrintMem(cout,head);
            head=body;
            Paintfile.seekp(ios_base::beg);
            PrintMem(Paintfile,head);
            head=body;
            cout<<endl;
        }
    }
//once the final command is given, the function then prints out one last time to the file and command window
    PrintMem(cout,head);
    head=body;
    Paintfile.seekp(ios_base::beg);
    PrintMem(Paintfile,head);
    head=body;
    cout<<endl;
    Paintfile.close();
    DelMem(head);

    return 0;
}
int CheckDown(string CommandLine)
{
    //grabs the first character of the Command Line and checks to see if the pen is up or down
    if(CommandLine.length()<1)
        return 3;
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


        if(CommandLine.length()<4)
            return 404;
        string checker=CommandLine.substr(4);
        long long unsigned int i=checker.length();
        if(i==0)
            return 1;
        while(!isdigit(checker[--i]))
        {
            --i;
        }
        if(checker.length()<i+1)
            return 404;
        checker=checker.substr(0,i+1);
        long long unsigned int str=checker.length();
        if(str>2)
            return 101;
        else return stoi(checker);
    }
    return 404;
}
char CheckDirection(string CommandLine,bool BadComma)
{
    //removes pen up/down and looks for the direction

    if((!BadComma)||((CommandLine.length())<5))
        return '|';
    if(CommandLine.length()<2)
        return ' ';
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
int DirectionLength(char Direciton,int Distance,int counter,node *body)
{
    node *temp=body;
    if(Direciton=='E')
    {
        for(int i=0; i<Distance; i++)
        {
            temp=temp->east;
            if(!temp)
            {
                cout<<endl<<"error on line: "<<counter<<endl<<"Distance moved can not leave the 50x50 Painting box."<<endl;
                return 1;
            }
        }
    }
    if(Direciton=='W')
    {
        for(int i=0; i<Distance; i++)
        {
            temp=temp->west;
            if(!temp)
            {
                cout<<endl<<"error on line: "<<counter<<endl<<"Distance moved can not leave the 50x50 Painting box."<<endl;
                return 1;
            }
        }
    }
    if(Direciton=='N')
    {
        for(int i=0; i<Distance; i++)
        {
            temp=temp->north;
            if(!temp)
            {
                cout<<endl<<"error on line: "<<counter<<endl<<"Distance moved can not leave the 50x50 Painting box."<<endl;
                return 1;
            }
        }
    }
    if(Direciton=='S')
    {
        for(int i=0; i<Distance; i++)
        {
            temp=temp->south;
            if(!temp)
            {
                cout<<endl<<"error on line: "<<counter<<endl<<"Distance moved can not leave the 50x50 Painting box."<<endl;
                return 1;
            }
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
