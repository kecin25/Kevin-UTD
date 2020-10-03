//  Made by: Kevin Boudreaux
//  Cosc-1337.002
//  10/2/2020
//  This program takes a basic* matrix, up to three variables, from a file and gives the user an interface to solve the matrix.
// *The matrix is able to deal with linear equations and is not able to handle several variables of the same type in several locations.
//  basic setup for the program must be (coefficient)(variable1)+/-(coefficient)(variable2)+-(coefficient)(variable3)=(some_number).

#include <iostream>
#include <string>
#include <iomanip>
#include <fstream>


using namespace std;


float *FindXValue(string*,int);
float *FindYValue(string*,int);
float *FindZValue(string*,int);
float *FindNumValue(string*,int);
bool solved(float*,float*,float*,int);
int main()
{
//  string is used to get the files name and the boolean is used for when the user wants to quite the program without solving the matrix
    string fileName;
    bool exit=false;
//  Option is used to get the users input for what they want to do to the matrix
    int Option;
    //cout<<"Enter the file name of the matrix: ";
    getline(cin,fileName);

//  below is opening the file and if the file can not be opened there is a basic loop that will try to keep opening the filename that the user gives
    ifstream matrixFile(fileName,ios::in);

    if(!matrixFile)
        do
        {
            cout<<endl<<"File not found. make sure it is in the same folder as the .exe and it is spelled the same as the input."<<endl;
            cout<<"Enter the file name of the matrix: ";
            getline(cin,fileName);
            matrixFile.open(fileName,ios::in);
        }
        while(!matrixFile);

//  below is used to find the number of equations in the text file and moves them from the file into a string pointer
    int num_equations=0;
    string temp;
    while(getline(matrixFile,temp))
    {
        num_equations++;
    }
    matrixFile.close();
    matrixFile.open(fileName,ios::in);
    string *Eptr=new string[num_equations];
    string *Eanc;
    Eanc=Eptr;


    while(getline(matrixFile,*Eptr))
    {
        Eptr++;
    }
    Eptr-=num_equations;

//  below is used to output the strings into the console so the user can confirm that they have the correct file and the equations were read correctly.
    for(int i=1; i<=num_equations; i++)
    {
       //cout<<*Eptr<<endl;
        Eptr++;
    }

    Eptr-=num_equations;

//  below is setting up float pointers to get the coefficients from the equations and the running several functions to find and get the coefficients
    float *Xptr=new float[num_equations];
    float *Yptr=new float[num_equations];
    float *Zptr=new float[num_equations];
    float *Numptr=new float[num_equations];

//    the code below are anchor points used when deleting the arrays;
    float *Xanc;
    Xanc=Xptr;
    float *Yanc;
    Yanc=Yptr;
    float *Zanc;
    Zanc=Zptr;
    float *Numanc;
    Numanc=Numptr;


    Xptr=FindXValue(Eptr,num_equations);
    Yptr=FindYValue(Eptr,num_equations);
    Zptr=FindZValue(Eptr,num_equations);
    Numptr=FindNumValue(Eptr,num_equations);

//  the cout below this line is used to make decimal points always show up
    cout<<endl<<endl<<setprecision(2)<<fixed;

//  the for loop down below is used to ouput the coefficients from the equations, forming the matrix
    for(int i=1; i<=num_equations; i++)
    {
        cout<<*Xptr<<" "<<*Yptr<<" "<<*Zptr<<" "<< /*" = "<<*/ *Numptr<<endl;
        Xptr++;
        Yptr++;
        Zptr++;
        Numptr++;
    }
        Xptr-=num_equations;
        Yptr-=num_equations;
        Zptr-=num_equations;
        Numptr-=num_equations;

//  the do while loop is the part where the user gets to interact with the matrix, being able to move parts around and multiply rows,
//  as well as multiply one row and add it to a 2nd row
    do
    {
        exit=solved(Xptr, Yptr, Zptr, num_equations);

//      exit is used to check to see if the user wants to leave or if the matrix is solved
        if(exit)
        {
            cout<<"x = "<<*Numptr<<endl;
            Numptr++;
            cout<<"y = "<<*Numptr<<endl;
            Numptr++;
            Zptr+=(num_equations-1);
            if(*Zptr!=0)
            {
                cout<<"z = "<<*Numptr<<endl;
                break;
            }
            Zptr--;
            if(*Zptr!=0)
                cout<<"z = "<<*Numptr<<endl;
            break;
        }

        //cout<<"You have four options. \nOption 1: Switch two rows \nOption 2: Multiply a row by a non-zero number \nOption 3: Add Scalar Multiple of one row to another row \n"
        //<<"Option 4: Quit"<<endl;
        //cout<<"What Option do you want to do?"<<endl<<"Option: ";
        cin>>Option;
        cout<<Option<<endl;

        switch(Option)
        {
//      case one is switching two rows in the matrix
        case 1:
        {
            int row1=0,row2=0;
            float *temp1=new float[num_equations];
            float *temp2=new float[num_equations];

            //cout<<"What is the first row you want to switch? ";
            cin>>row1;
            cout<<row1<<endl;

//          the loop below is used to make sure the user enters a valid input for the row numbers so the pointers do not go out of range
            if((row1-1)>num_equations||(row1-1)<0)
            {
                do
                {
                    cout<<"Enter a valid row number"<<endl;
                    cin>>row1;
                }
                while((row1-1)>num_equations&&(row1-1)<0);
            }
            //cout<<endl<<"What is the second row you want to switch? ";
            cin>>row2;
            cout<<row2<<endl;

//          the loop below is used to make sure the user enters a valid input for the row numbers so the pointers do not go out of range
            if((row2-1)>num_equations||(row2-1)<0)
            {
                do
                {
                    cout<<"Enter a valid row number"<<endl;
                    cin>>row2;
                }
                while((row1-1)>num_equations&&(row1-1)<0);
            }
            row1--;
            row2--;

//          the code below moves the pointers to the line selected by the user
            Xptr+=row1;
            Yptr+=row1;
            Zptr+=row1;
            Numptr+=row1;

//          temp1 is used to hold onto the first row so the program wont lose a row when switching two of them
            *temp1=*Xptr;
            temp1++;
            *temp1=*Yptr;
            temp1++;
            *temp1=*Zptr;
            temp1++;
            *temp1=*Numptr;
            temp1-=3;

//          the code below are used to move the pointers back to their resting positions for future use
            Xptr-=row1;
            Yptr-=row1;
            Zptr-=row1;
            Numptr-=row1;

//          the code below are moving the pointer to the 2nd row given by the user
            Xptr+=row2;
            Yptr+=row2;
            Zptr+=row2;
            Numptr+=row2;

//          temp2 is used so the program will not lose a line of the matrix when switching two of them
            *temp2=*Xptr;
            temp2++;
            *temp2=*Yptr;
            temp2++;
            *temp2=*Zptr;
            temp2++;
            *temp2=*Numptr;
            temp2-=3;

//          the code below are used to move the pointers back to their starting postions
            Xptr-=row2;
            Yptr-=row2;
            Zptr-=row2;
            Numptr-=row2;

//          the code below is used to move back to the first row given to start the switching of the two ros
            Xptr+=row1;
            Yptr+=row1;
            Zptr+=row1;
            Numptr+=row1;

//          the code below starts trasnfering over temp2 into the first row given by the user
            *Xptr=*temp2;
            temp2++;
            *Yptr=*temp2;
            temp2++;
            *Zptr=*temp2;
            temp2++;
            *Numptr=*temp2;
            temp2-=3;

//          the code below is resetting the pointers to their starting positions
            Xptr-=row1;
            Yptr-=row1;
            Zptr-=row1;
            Numptr-=row1;

//          code below is used to move to the 2nd row given by the user
            Xptr+=row2;
            Yptr+=row2;
            Zptr+=row2;
            Numptr+=row2;

//          the code below is used to move the first row given into the 2nd rows spot
            *Xptr=*temp1;
            temp1++;
            *Yptr=*temp1;
            temp1++;
            *Zptr=*temp1;
            temp1++;
            *Numptr=*temp1;
            temp2-=3;

//          the code below is resetting the pointers to their starting positions
            Xptr-=row2;
            Yptr-=row2;
            Zptr-=row2;
            Numptr-=row2;

//          the for loop below print out the new matrix and the code under it resets the pointers to the start of the matrix
            for(int i=1; i<=num_equations; i++)
            {
                cout<<*Xptr<<" "<<*Yptr<<" "<<*Zptr<<" "<</*" = "<<*/*Numptr<<endl;
                Xptr++;
                Yptr++;
                Zptr++;
                Numptr++;
            }

            Xptr-=num_equations;
            Yptr-=num_equations;
            Zptr-=num_equations;
            Numptr-=num_equations;

//          below code is checking to see if the code is finished and if so printing out the values of X, Y, and Z
            exit=solved(Xptr, Yptr, Zptr, num_equations);
            if(exit)
            {
                cout<<"x = "<<*Numptr<<endl;
                Numptr++;
                cout<<"y = "<<*Numptr<<endl;
                Numptr++;
                cout<<"z = "<<*Numptr<<endl;
            }
            break;
        }
//      case two is multiplying a single row by a non-zero number
        case 2:
        {
//          multiple is used to get the number the user wants to multiply by and row is used to get what row the user wants to multuiply
            float multiple=0;
            int row=0;
            //cout<<"What row do you want to multiply? \nrow: ";
            cin>>row;
            cout<<row<<endl;
            row--;
//          the if do while loop below checks to see if the user entered a correct row number
            if(row>=num_equations||row<0)
            {
                do
                {
                    cout<<"The number you entered is bigger then the number of equations."<<endl;
                    cin>>row;
                }
                while((row-1)>num_equations&&(row-1)<0);
            }
            row++;
            //cout<<endl<<"What number do you want to multiply row "<<row<<" by? \n";
            row--;
            cin>>multiple;
            cout<<multiple<<endl;
//          the if do while loop below checks to see if the user entered a number for the to multiply the row by, only zero is not allowed
            if(multiple==0)
            {
                do
                {
                    cout<<"Enter a non-zero number"<<endl;
                    cin>>multiple;
                }
                while(multiple==0);
            }

//          the code below moves the pointers to the given row
            Xptr+=row;
            Yptr+=row;
            Zptr+=row;
            Numptr+=row;

//          the code below multiplies the coefficient by the given multiple number
            *Xptr=((*Xptr)*(multiple)+0);
            *Yptr=((*Yptr)*(multiple)+0);
            *Zptr=((*Zptr)*(multiple)+0);
            *Numptr=((*Numptr)*(multiple)+0);

//          the code below resets the pointers to the start of the code
            Xptr-=row;
            Yptr-=row;
            Zptr-=row;
            Numptr-=row;

//          the below for loop prints out the new matrix and right below that resets the pointers to the start of the arrays
            for(int i=1; i<=num_equations; i++)
            {
                cout<<*Xptr<<" "<<*Yptr<<" "<<*Zptr<<" "<</*" = "<<*/*Numptr<<endl;
                Xptr++;
                Yptr++;
                Zptr++;
                Numptr++;
            }
            Xptr-=num_equations;
            Yptr-=num_equations;
            Zptr-=num_equations;
            Numptr-=num_equations;

//          code below checks to see if the matrix is solved
            exit=solved(Xptr, Yptr, Zptr, num_equations);
            if(exit)
            {
                cout<<"x = "<<*Numptr<<endl;
                Numptr++;
                cout<<"y = "<<*Numptr<<endl;
                Numptr++;
                cout<<"z = "<<*Numptr<<endl;
            }
            break;
        }
//      case three is multiplying a row by a number and then adding it to a 2nd row
        case 3:
        {
//          setting up the temp array and the float to hold the multiple as well as two ints two hold the two given rows
            float multiple=0;
            float *temp=new float[num_equations];
            int row1=0,row2=0;
            //cout<<"What row do you want to multiply? \nrow: ";
            cin>>row1;
            cout<<row1<<endl;

//          after grabbing the first row they want to use the code below checks to make sure its a valid row
            if(row1>num_equations)
            {
                do
                {
                    cout<<"The number you entered is bigger then the number of equations."<<endl;
                    cin>>row1;
                }
                while(row1>(num_equations-1)||row1<1);
            }
            row1--;
            //cout<<"\nWhat do you want to multiply the row by? \n";
            cin>>multiple;
            cout<<multiple<<endl;

//          after getting the number they want to multiply by the code below makes sure it is a non-zero number
            if(multiple==0)
            {
                do
                {
                    cout<<endl<<"Enter a non-zero number"<<endl;
                    cin>>multiple;
                }
                while(multiple==0);
            }
            //cout<<"\nWhat row do you want add the first row to? \nrow: ";
            cin>>row2;
            cout<<row2<<endl;

//            after getting the 2nd row the user wants to use the code below makes sure it is a valid row
            if((row2-1)>num_equations||(row2-1)<0)
            {
                do
                {
                    cout<<endl<<"The number you entered is bigger then the number of equations."<<endl;
                    cin>>row2;
                }
                while(row2>(num_equations-1)||row2<1);
            }
            row2--;

//          code below moves the pointer to the first given row
            Xptr+=row1;
            Yptr+=row1;
            Zptr+=row1;
            Numptr+=row1;

//          code below grabs the coefficients and multiplies them by the given number and throws it into a temp array
            *temp=((*Xptr)*(multiple));
            temp++;
            *temp=(*Yptr)*(multiple);
            temp++;
            *temp=(*Zptr)*(multiple);
            temp++;
            *temp=(*Numptr)*(multiple);
            temp-=3;

//          code below moves back to the start of the arrays
            Xptr-=row1;
            Yptr-=row1;
            Zptr-=row1;
            Numptr-=row1;

//          code below moves to the 2nd given row
            Xptr+=row2;
            Yptr+=row2;
            Zptr+=row2;
            Numptr+=row2;

//          code below multiplies the 2nd row coefficients by the temp array then adds zero to the array to make sure there are no -0s
            *Xptr+=*temp;
            *Xptr+=0;
            temp++;
            *Yptr+=*temp;
            *Yptr+=0;
            temp++;
            *Zptr+=*temp;
            *Zptr+=0;
            temp++;
            *Numptr+=*temp;
            *Numptr+=0;
            temp-=3;

//          resets the pointers to the start of the matrix
            Xptr-=row2;
            Yptr-=row2;
            Zptr-=row2;
            Numptr-=row2;

//          deletes temp and outputs the new matrix into the console
            delete[]temp;
            for(int i=1; i<=num_equations; i++)
            {
                cout<<*Xptr<<" "<<*Yptr<<" "<<*Zptr<<" "<</*" = "<<*/*Numptr<<endl;
                Xptr++;
                Yptr++;
                Zptr++;
                Numptr++;
            }

            Xptr-=num_equations;
            Yptr-=num_equations;
            Zptr-=num_equations;
            Numptr-=num_equations;

//          checks to see if the code is solved
            exit=solved(Xptr, Yptr, Zptr, num_equations);
            if(exit)
            {
                cout<<"x = "<<*Numptr<<endl;
                Numptr++;
                cout<<"y = "<<*Numptr<<endl;
                Numptr++;
                cout<<"z = "<<*Numptr<<endl;
            }
            break;
        }
//      case 4 is available to the user if they want to quite the program without solving the matrix
        case 4:
        {
            exit=true;
            break;
        }
//      default is used for if the user did not enter a valid input for the switch statement
        default:
        {
            //cout<< "Input a 1, 2, 3, or 4"<<endl;
            break;
        }
        }
    }
    while(exit!=true);

//  deletes all the pointers used since the matrix is solved or if the user wants to quite since they are no longer needed.
    Xptr=Xanc;
    Yptr=Yanc;
    Zptr=Zanc;
    Numptr=Numanc;
    Eptr=Eanc;

    delete[]Xptr;
    delete[]Yptr;
    delete[]Zptr;
    delete[]Numptr;
    delete[]Eptr;

    return 0;
}
//FindXValue is used for finding the X coefficients, converting them into floats, and putting them in array to be used in int main
float *FindXValue(string *Eptr,int num_equations)
{
//  setting up the float pointer array for the X coefficients
    float *Xvalues=new float[num_equations];

//  master for loop used to get the X coefficient for each equation in the text file
    for(int i=1; i<=num_equations; i++)
    {
//      numofX is used to see if there is an X value in the string, locationX gives the location of where X was found, plusminusloco is used
//      for finding if X is positive or negative
        int numofX=0,locationX,plusminusloco=0;
        bool negative=false;
        string equation=*Eptr;

//      for loop below is used to find the location of x and how many X's there are
        for(int k=0; k<equation.length(); k++)
        {
            if(equation.at(k)=='X'||equation.at(k)=='x')
            {
                numofX++;
                locationX=k;
            }
        }
//      if there are no found X's then we know that X's coefficient is zero
        if(numofX==0)
        {
            equation="0";
            locationX=1;

        }
//      if X is not the first position in the string then we remove everything after it
//      else the equation is equal to X, we do this when X's coefficient is 1
        if(locationX!=0)
            equation=equation.substr(0,locationX+1);
        else
            equation='X';
//      the for loop below is used to find the location of the + or - (if there is one) and to see if the X coefficient is negative
        for(int l=equation.length()-1; l>=0; l--)
        {
            if(equation.length()==1)
            {
                plusminusloco=0;
                break;
            }
            if(equation.at(l)=='+'||equation.at(l)=='-')
            {
                plusminusloco=l;
                if(equation.at(l)=='-')
                    negative=true;
                break;
            }
        }
//      if there is a plus or minus or if the coefficient is negative then we remove it
        if(plusminusloco!=0||negative)
            equation=equation.substr(plusminusloco+1);

//      if the equation is only character and its not 0 then we set equation to 1
        else if(!(equation.length()>1))
        {
            if(equation!="0")
                equation="1";
        }
//      if the equation is not equal to 0 then we remove the X
        if(equation!="0")
            equation=equation.substr(0,equation.length()-1);
//      if the equation is empty and its not 0 then we set equation to 1
        if(equation.length()==0)
        {
            if(equation!="0")
                equation="1";
        }

//      converts the string into a float and puts it in the float pointer array
        *Xvalues=stof(equation);

//      if x was negative then we make the pointer array position of where we put the coefficient negative
        if(negative)
        {
            *Xvalues=*Xvalues*(-1);
        }

//      we move the string and float pointers one farther into the array
        Eptr++;
        Xvalues++;
    }
//      resets the float pointer to the start of the array so we can send it to int main without the pointer leaving the assigned memory
        Xvalues-=num_equations;

    return Xvalues;;

}
//FindYValue is used for finding the Y coefficients, converting them into floats, and putting them in array to be used in int main
float *FindYValue(string *Eptr,int num_equations)
{
//  setting up the float pointer array for the Y coefficients
    float *Yvalues=new float[num_equations];

//  master for loop used to get the Y coefficient for each equation in the text file
    for(int i=1; i<=num_equations; i++)
    {
//      numofY is used to see if there is an Y value in the string, locationY gives the location of where Y was found, plusminusloco is used
//      for finding if Y is positive or negative
        int numofY=0,locationY,plusminusloco=0;
        bool negative=false;
        string equation=*Eptr;
//      for loop below is used to find the location of Y and how many Y's there are
        for(int k=0; k<equation.length(); k++)
        {
            if(equation.at(k)=='Y'||equation.at(k)=='y')
            {
                numofY++;
                locationY=k;
            }
        }
//      if there are no found Y's then we know that Y's coefficient is zero
        if(numofY==0)
        {
            equation="0";
            locationY=1;

        }
//      if Y is not the first position in the string then we remove everything after it
//      else the equation is equal to Y, we do this when Y's coefficient is 1
        if(locationY!=0)
            equation=equation.substr(0,locationY+1);
        else
            equation='Y';
//      the for loop below is used to find the location of the + or - (if there is one) and to see if the Y coefficient is negative
        for(int l=equation.length()-1; l>=0; l--)
        {
            if(equation.length()==1)
            {
                plusminusloco=0;
                break;
            }
            if(equation.at(l)=='+'||equation.at(l)=='-')
            {
                plusminusloco=l;
                if(equation.at(l)=='-')
                    negative=true;
                break;
            }
        }
//      if there is a plus or minus or if the coefficient is negative then we remove it
        if(plusminusloco!=0||negative)
            equation=equation.substr(plusminusloco+1);

//      if the equation is only character and its not 0 then we set equation to 1
        else if(!(equation.length()>1))
        {
            if(equation!="0")
                equation="1";
        }

//      if the equation is not equal to 0 then we remove the Y
        if(equation!="0")
            equation=equation.substr(0,equation.length()-1);

//      if the equation is empty and its not 0 then we set equation to 1
        if(equation.length()==0)
        {
            if(equation!="0")
                equation="1";
        }

//      converts the string into a float and puts it in the float pointer array
        *Yvalues=stof(equation);

//      if x was negative then we make the pointer array position of where we put the coefficient negative
        if(negative)
        {
            *Yvalues=*Yvalues*(-1);
        }

//      we move the string and float pointers one farther into the array
        Eptr++;
        Yvalues++;


    }
//      resets the float pointer to the start of the array so we can send it to int main without the pointer leaving the assigned memory
        Yvalues-=num_equations;

    return Yvalues;
}
//FindZValue is used for finding the Z coefficients, converting them into floats, and putting them in array to be used in int main
float *FindZValue(string *Eptr,int num_equations)
{
//  setting up the float pointer array for the Z coefficients
    float *Zvalues=new float[num_equations];

//  master for loop used to get the Y coefficient for each equation in the text file
    for(int i=1; i<=num_equations; i++)
    {
//      numofZ is used to see if there is an Z value in the string, locationY gives the location of where Z was found, plusminusloco is used
//      for finding if Z is positive or negative
        int numofZ=0,locationZ,plusminusloco=0;
        bool negative=false;
        string equation=*Eptr;

//      for loop below is used to find the location of Z and how many Z's there are
        for(int k=0; k<equation.length(); k++)
        {
            if(equation.at(k)=='Z'||equation.at(k)=='z')
            {
                numofZ++;
                locationZ=k;
            }
        }
//      if there are no found Z's then we know that Z's coefficient is zero
        if(numofZ==0)
        {
            equation="0";
            locationZ=1;

        }
//      if the equation is not equal to 0 then we remove the Z
        if(locationZ!=0)
            equation=equation.substr(0,locationZ+1);
        else
            equation='Z';

//      the for loop below is used to find the plus or minus location of the Z coefficient if there is one
        for(int l=equation.length()-1; l>=0; l--)
        {
            if(equation.length()==1)
            {
                plusminusloco=0;
                break;
            }
            if(equation.at(l)=='+'||equation.at(l)=='-')
            {
                plusminusloco=l;
                if(equation.at(l)=='-')
                    negative=true;
                break;
            }
        }
//      if there is a plus or minus or if the coefficient is negative then we remove it
        if(plusminusloco!=0||negative)
            equation=equation.substr(plusminusloco+1);

        else if(!(equation.length()>1))
        {
            if(equation!="0")
                equation="1";
        }
        if(equation!="0")
            equation=equation.substr(0,equation.length()-1);
        if(equation.length()==0)
        {
            if(equation!="0")
                equation="1";
        }

//      converts the string into a float and puts it in the float pointer array
        *Zvalues=stof(equation);
        if(negative)
        {
            *Zvalues=*Zvalues*(-1);
        }

//      we move the string and float pointers one farther into the array
        Eptr++;
        Zvalues++;


    }
//      resets the float pointer to the start of the array so we can send it to int main without the pointer leaving the assigned memory
        Zvalues-=num_equations;

    return Zvalues;
}
// FindNumValue is used for finding the number right to the equal sign and puts them into the float pointer array to be used in int main
float *FindNumValue(string *Eptr,int num_equations)
{
//  setting up the float pointer array for the value
    float *NumValue=new float[num_equations];
    for(int i=1; i<=num_equations; i++)
    {
//      equalfinder is used for finding the equal sign
        int equalfinder=0;
        string equation=*Eptr;
//      the for loop below finds the equal sign
        for(int k=0; k<equation.length(); k++)
        {
            if(equation.at(k)=='=')
            {
                equalfinder=k;
                break;
            }
        }
//      removes every to the left of the equation sign
        equation=equation.substr(equalfinder,equation.length());

//      removes the equation sign and turns the string into a float and puts it into the float pointer array
        equation=equation.substr(1);
        *NumValue=stof(equation);

//      moves the pointers into the next spot in the array
        NumValue++;
        Eptr++;
    }
//      moves the pointer back to the start of the array
        NumValue-=num_equations;

    return NumValue;
}
//solved checks to see if the matrix is solved
bool solved(float* Xptr,float* Yptr,float* Zptr,int num_equations)
{
//  setting up anchor points to make sure the pointers don't leave the allocated memory
    float *Xanchor;
    Xanchor=Xptr;
    float *Yanchor;
    Yanchor=Yptr;
    float *Zanchor;
    Zanchor=Zptr;

//  if there is one equation then it returns true since the equation cannot be changed
    if(num_equations==1)
    {
        return true;
    }
//  if there are two equations then this part of the checker runs
    else if(num_equations==2)
    {
//      checks to see if the first part of matrix is solved and then moves to the 2nd part
        if(*Xptr==1&&*Yptr==0)
        {
            Xptr++;
            Yptr++;
//          checks the 2nd part of the matrix to see if it is solved
            if(*Xptr==0&&*Yptr==1)
            {
                return true;
            }
            else return false;
        }
    }
//  if there are three equations this part of the checker is used
    else if(num_equations==3)
    {
//      sets the pointers to the anchor points  and checks to see if the first row of the matrix is solved
        Xptr=Xanchor;
        Yptr=Yanchor;
        Zptr=Zanchor;
        if(*Xptr==1&&*Yptr==0&&*Zptr==0)
        {
//          moves the pointers to the next row in the matrix
            Xptr++;
            Yptr++;
            Zptr++;
//          checks to see if the 2nd row is solved
            if(*Xptr==0&&*Yptr==1&&*Zptr==0)
            {
//              moves the pointers to the next row in the matrix
                Xptr++;
                Yptr++;
                Zptr++;
//              checks to see if the 3rd row is solved
                if(*Xptr==0&&*Yptr==0&&*Zptr==1)
                {
                    return true;
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }
//  this part of the code if used if there are 4 lines in the matrix
    else if(num_equations==4)
    {
//sets the pointers to the start of the array and moves them to the end of the array and checks it in reverse
        Xptr=Xanchor;
        Yptr=Yanchor;
        Zptr=Zanchor;
        Xptr+=3;
        Yptr+=3;
        Zptr+=3;
//      checks to see if the 4th part of the array is solved
        if(*Xptr==0&&*Yptr==0&&*Zptr==0)
        {
//          moves the pointers to the 3rd part of the array
            Xptr--;
            Yptr--;
            Zptr--;
//          at this point there could be 2 possibilities so there are two different paths the code can go,
//          this path is used if there are only two variables
            if(*Xptr==0&&*Yptr==0&&*Zptr==0)
            {
//              moves the pointer to the 2nd line of the matrix
                Xptr--;
                Yptr--;
                Zptr--;
//              checks to see if the 2nd line of the matrix is solved
                if(*Xptr==0&&*Yptr==1)
                {
//                  moves the pointers to the 1st line of the matrix
                    Xptr--;
                    Yptr--;
                    Zptr--;
//                  checks to see if the first line of the matrix is solved
                    if(*Xptr==1&&*Yptr==0)
                    {
                        return true;
                    }
                }
            }
//          2nd path that could be taken starts here, this is used if there are three variables
//          checks to see if the 3rd row is correct
            else if(*Xptr==0&&*Yptr==0&&*Zptr==1)
            {
//              moves the pointers to the 2nd line in the matrix
                Xptr--;
                Yptr--;
                Zptr--;
//              checks to see if the 2nd line in the matrix is solved
                if(*Xptr==0&&*Yptr==1&&*Zptr==0)
                {
//                  moves the pointers to the 1st line in the matrix
                    Xptr--;
                    Yptr--;
                    Zptr--;
//                  checks to see if the 1st line in the matrix is solved
                    if(*Xptr==1&&*Yptr==0&&*Zptr==0)
                    {
                        return true;
                    }
                }
            }
        }
//      resets the pointers to the start of the matrix
        Xptr=Xanchor;
        Yptr=Yanchor;
        Zptr=Zanchor;
//      this checks the 1st line of the matrix to see if its correct
        if(*Xptr==1&&*Yptr==0&&*Zptr==0)
        {
//          moves the pointers to the 2nd line of the matrix
            Xptr++;
            Yptr++;
            Zptr++;
//          checks to see if the 2nd line in the matrix is correct
            if(*Xptr==0&&*Yptr==1&&*Zptr==0)
            {
//              moves the pointers to the 3rd line in the matrix
                Xptr++;
                Yptr++;
                Zptr++;
//              checks to see if the 3rd line of the matrix is correct
                if(*Xptr==0&&*Yptr==0&&*Zptr==1)
                {
//                  moves the pointers to the 4th line in the matrix
                    Xptr++;
                    Yptr++;
                    Zptr++;
//                  checks to see if the 4th line of the matrix is correct
                    if(*Xptr==0&&*Yptr==0&&*Zptr==0)
                    {
                        return true;
                    }
                    else return false;
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }
    return false;
}
