#include <iostream>
#include <fstream>
#include <string>
#include <cstring>

using namespace std;


bool IsVowel(char);
int main()
{
    string FileName, FinalFileName,FileLine;


    cout<<"what is the name of the file?"<<endl;
    getline(cin,FileName);
    cout<<"File name is: "<<FileName<<endl;

    FinalFileName="Vowels_"+FileName;

    cout<<FinalFileName;

    fstream File(FinalFileName, ios::out|ios::binary);
    File.close();
    File.open(FileName, ios::in|ios::binary);
    File.close();

    int lineCounter = 0;

    while(File)
    {
        File.open(FileName);
        int lastLineCounter = lineCounter;
        do
         {
            getline(File,FileLine);
            if(FileLine.empty())
            {
               break;
            }
        }
        while (lastLineCounter-- > 0);
        //getline(File,FileLine);
            lineCounter++;
            if(FileLine.empty())
            {
               break;
            }

        long byte=2+File.tellp();

        File.seekg(byte,ios::beg);

        File.close();
        File.open(FinalFileName,ios::app);

        int length=0;
        length=FileLine.size();

        cout<<length<<" ";

        while(FileLine!="")
        {
            if(IsVowel(FileLine[0]))
            {
                cout<<FileLine;

                File.put(FileLine[0]);
                FileLine.erase(0,1);
            }

            else
                FileLine.erase(0,1);
            if(FileLine=="\r\n"||FileLine=="\n"||FileLine==" "||FileLine=="")
            {
               //File.put('\r\n');
               File << endl;
                               break;
            }

        }

        File.close();

    }

    return 0;
}
bool IsVowel(char Command)
{
    if(Command=='A'||Command=='a'||Command=='E'||Command=='e'||Command=='i'||Command=='I'||Command=='O'||Command=='o'||Command=='U'||Command=='u')
        return true;
    else
        return false;
}
