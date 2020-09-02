#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>

int main()
{
    name();
    password();
    return 0;
}
void name ()
{
    char firstname[21], middlename[21], lastname[21], fullname[63];
    char space[2] = " ", comma[2] = ",";
    printf("please enter your first, middle, and last name.\n");
    scanf("%s %s %s", firstname, middlename, lastname);
    strcpy(fullname, lastname);
    strcat(fullname, ", ");
    strcat(fullname, firstname);
    strcat(fullname, " ");
    strcat(fullname, middlename);
    printf("%s", fullname);
}
void password()
{
    //initializing variables
    bool up = false, down = false, hasNum = false, hasPunct = false, length = false;
    char pass[100] = "";

    //requesting a password
    printf("\n**\n\n");
    printf("Please enter a password\n");
    scanf("%s", pass);

    //checks to make sure there is upper cases, lower cases, numbers, and punctuation
    int size = strlen(pass);
    int i = 0;
    while(i < size)
    {
        if(isupper(pass[i]))
            up = true;
        if(islower(pass[i]))
            down = true;
        if(isdigit(pass[i]))
            hasNum = true;
        if(ispunct(pass[i]))
            hasPunct = true;
        printf("%c ", pass[i]);
        i++;
    }
    printf("\n");

    //checks to make sure the size is correct
    if(strlen(pass) > 14)
        printf("\nyour password is longer than 14 charachters\n");
    else if(strlen(pass) < 6)
        printf("\nyour password is shorter than 6 charachters\n");
    else
        length = true;

    //tells you if there is errors, or perfect
    if(up == false)
        printf("You do not have an upper case letter\n");
    if(down == false)
        printf("You do not have a lower case letter\n");
    if(hasNum == false)
        printf("You do not have a number\n");
    if(hasPunct == false)
        printf("You do not have any punctuation\n");
    if(up == true && down == true && hasNum == true && hasPunct == true && length == true)
        printf("your password is perfect");
}













fstream file("file.txt");
//file.open("file.txt");
~~~~~~~~~
file.close("file.txt");
file.open("file2.txt");
~~~~~~~~~~~~~~
file.close();
