/*
Code is used to get a user's name and re-arrange it so that it is ordered in Last, First Middle.

It will also get a password between 6 and 17 characters long and make sure is has
a lowercase and uppercase letter, a number, a punctuation symbol

Made by Kevin Boudreaux on 8/24/2020


*/
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include<ctype.h>


void FullNameFunction();
void PasswordFunction();
int main()
{

   FullNameFunction();
   PasswordFunction();

    return 0;
}
void FullNameFunction()
{

   /*
   making all the strings that will be needed to refromat the name.
   */
      char FullName[61]="";
      char FirstName[21],MiddleName[21],LastName[21];
      char Space[3]=" ",Comma[3]=",";

    printf("Please give me your first, middle, and last name: \n");
    scanf("%s %s %s",FirstName,MiddleName,LastName);
    printf("\n");

/*
   Formating the name to be Last, First Middle.
*/

      strcat(FullName,LastName);

      strcat(FullName,Comma);

      strcat(FullName,Space);

      strcat(FullName,FirstName);

      strcat(FullName,Space);

      strcat(FullName,MiddleName);

      printf("User: %s\n",FullName);

return;
}

void PasswordFunction()
{
   /*
Below is start of asking for the password and checking to make sure it meets the criteria
*/

char Password[17];

bool Length=false,UpperCase=false,LowerCase=false,Digit=false,Punctuation=false,Perfection=false;

do
{
   printf("\nPlease enter a password \n(make sure the password is between 6 and 14 characters long,\n");
   printf("has one upper and lower case character,\n");
   printf("has at least one digit and punctuation symbol):\n");

   scanf("%s",Password);

   /*
   testing the length of the password
   */
   int len=strlen(Password);

   if(len<6||len>14)
      Length=false;
   else
      Length=true;
   /*
   testing to see if there is an uppercase letter
   */

   for(int i =0;i<15;i++)
      if(isupper(Password[i]))
         UpperCase=true;
   if(UpperCase!=true)
      UpperCase=false;

   /*
   testing to see if there is a lowercase letter
   */
   for(int i=0;i<15;i++)
      if(islower(Password[i]))
         LowerCase=true;
   if(LowerCase!=true)
      LowerCase=false;

   /*
   testing to see if there is a digit in the password
   */
   for(int i=0;i<15;i++)
      if(isdigit(Password[i]))
         Digit=true;
   if(Digit!=true)
      Digit=false;
   /*
   testing to see if there is a punctuation symbol in the password
   */
   for(int i=0;i<15;i++)
      if(ispunct(Password[i]))
         Punctuation=true;
   if(Punctuation!=true)
      Punctuation=false;

   if(Length==false)
   {
      printf("password needs to be between 6 and 16 characters long.\n");
      Perfection=Length;
   }
   if(UpperCase==false)
   {
      printf("password needs to have at least one Uppercase letter.\n");
      Perfection=UpperCase;
   }

   if(LowerCase==false)
   {
      printf("password needs to have at least one lowercase letter.\n");
      Perfection=LowerCase;
   }
   if(Digit==false)
   {
      printf("password needs to have at least one number.\n");
      Perfection=Digit;
   }
   if(Punctuation==false)
   {
      printf("password needs to have at least one punctuation symbol.\n");
      Perfection=Punctuation;
   }

   if(Length==true&&UpperCase==true&&LowerCase==true&&Digit==true&&Punctuation==true)
      {
         printf("The password you entered meets all the criteria!\n");
         Perfection=true;
      }

}while(Perfection!=true);

return;
}
