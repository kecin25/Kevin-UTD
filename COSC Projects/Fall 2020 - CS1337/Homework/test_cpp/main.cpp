#include <fstream>
#include <iostream>
using namespace std;

int main()
{
    ifstream in("stuff.txt", ios::binary);
    in.seekg(9);
    char letter;
    int start = in.tellg();
    for (int i = 1; i <=4; i++)
    {
        in.get(letter);
        cout << letter;
    }
    int end = in.tellg();
    in.seekg(start - end, ios::cur);
    in.get(letter);
    cout << endl<< letter << endl;

    return 0;
}
