#include <fstream>
#include <string>
#include <iostream>
using namespace std;

int main()
{
    ifstream input("file.txt");
    string line, name, term;
    float x_array[10], y_array[10];
    int index, i=0;

    //sample line
    //Greedo 4,0 4,7.5 7,7.5 7,3 9,0 7,0 4,0

    while (getline(input,line))
    {
        //if we had a C-string
        //while(input.getline(char array,size))

        if (line == "") //process file with a newline at end of last line
            break;

        //parse name
        index = line.find(' '); //find the first space
        name = line.substr(0,index); //copy everything in front of the space
        line = line.substr(index+1); //break off the name and space from the string

        //loop through number pairs
        while (line.length() > 0) //length == 0 at end of string
        {
            //parse each pair
            index = line.find(' ');  //find the space after the pair
            // find function returns -1 if not found

            if (index != -1)    // if not last pair
            {
                term = line.substr(0,index);    //copy pair from string
                line = line.substr(index+1);    //break off pair
            }
            else    //if last pair
            {
                term = line;    //copy pair
                line = "";      //empty string to break loop
            }

            //parse term into x and y values
            index = term.find(','); //find comma

            //convert characters before comma to numerical value and store in array
            x_array[i] = stof(term.substr(0,index));

            //convert characters after comma to numerical value and store in array
            y_array[i] = stof(term.substr(index+1));

            i++;    //increment subscript counter
        }


        //display arrays
        cout << name << ' ';
        for (int j = 0; j <= i; j++)
            cout << x_array[j] << ',' << y_array[j] << ' ';
        cout << endl;

        i = 0;  //reset subscript counter

    }
}
