/*Name: Homework 2
Description: First use of using an array as a hash table
Created by: Kevin Boudreaux
Created on: 4/19/2021
ID: KCB180002
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main
{
    public static final double loadFactor =.5;
    public static void main(String[] args)
    {
        
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        
        int[] array = new int[11];
        String Line = null;
        
        try
        {
            Line = input.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        int numOfInsert = Integer.parseInt(Line);

            //inserting line into array
            for (int i = 0; i < numOfInsert; i++)
            {
                try
                {
                    Line = input.readLine();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                //checking to see if array is over load factor
                if ((double) i / array.length > loadFactor)
                {
                    int size = array.length;
                    size *= 2;
                    size = nextPrime(size);
                    array = reSize(array, size);
                }

                //inserting into array
                int currentNum;
                if (Line.indexOf(' ') == -1)
                {
                    currentNum = Integer.parseInt(Line);
                    Line = "";
                }
                else
                {
                    currentNum = Integer.parseInt(Line.substring(0, Line.indexOf(' ')));
                    Line = Line.substring(Line.indexOf(' ') + 1);
                }

                insert(array, currentNum);
            }

            //printing array
            for (int i = 0; i < array.length; i++)
            {
                if (array[i] == 0)
                {
                    System.out.print("_ ");
                }
                else System.out.print(array[i] + " ");
            }
            System.out.println();

    }
    public static int nextPrime(int size)
    {
        boolean prime = false;
        size++;
        do
        {
            for (int i = 2; i < size; i++)
            {
                if(size%i == 0)
                {
                    prime = false;
                    break;
                }
            }
            prime = true;
        }while(!prime);
        return size;
    }
    public static int[] reSize(int[] array, int size)
    {
        int[] newArray = new int[size];
        
        for(int i=0;i<array.length;i++)
        {
            if(array[i]!=0)
                insert(newArray,array[i]);
        }
        return newArray;
    }
    public static void insert(int[] array, int num)
    {
        int loco;
        for(int i=0;i<array.length;i++)
        {
            loco =( (num %array.length) );
            if(i!=0)
            {
                loco += Math.pow(i, 2);
                loco= loco%array.length;
            }
            if(array[loco] == 0)
            {
                array[loco] = num;
                break;
            }
        }
    }
}