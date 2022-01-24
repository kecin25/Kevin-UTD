/*Name: Homework 1
Description: Creating a generic array list that can search through the array using a binary tree and convert binary numbers into decimal numbers
Created by: Kevin Boudreaux
Created on: 3/24/2021
ID: KCB180002
*/
import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Scanner input = new Scanner(System.in);

            String string = input.next();
            
            int Integer = bin2Dec(string);

            System.out.printf("decimal of %s is %d \n", string, Integer);
        }
        catch(BinaryFormatException i)
        {
            System.out.print(i);
        }
        
    }
    
    public static int bin2Dec(String string) throws BinaryFormatException
    {
        int decimal = 0;
        for(int i= string.length()-1, j=0; i>= 0; i--, j++)
        {
            if(string.charAt(i) == '1' || string.charAt(i) == '0')
            {
                if(string.charAt(i) == '1')
                    decimal = decimal+(int)Math.pow(2,j);
            }
            else
                throw new BinaryFormatException("Invalid Binary Number");
        }
        return decimal;
    }
}
