/*
        Name: Wall Position Convertor
        Description: Program used to convert the location of a wall from (X,Y) to just X
        Made by: Kevin Boudreaux
        Made on: 11/3/2021
        ID: KCB180002
*/
import com.sun.jdi.IntegerValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        File file = new File("Positions of Walls Pre Conversion.txt");
        String line = "";
        String temp = "";
        int tempC = 0;
        //64 lines in the given file
        Scanner fileScanner = null;
        try
        {
            fileScanner = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        for(int i=0; i<64; i++)
        {
            
            line = fileScanner.nextLine();
            //while line is not empty convert the current line
            while(line != "")
            {
                
                //temp grabs next part of the line
                temp = line.substring(0, line.indexOf(","));
                //if a space in front then removes it from the string
                if(temp.charAt(0) == ' ')
                    temp = temp.substring(1);
                //removes the part of the line just grabbed
                line = line.substring(line.indexOf(",")+1);
                
                if(temp.length() == 1 || temp.length() == 2)
                {
                    int intTemp = Integer.parseInt(temp);
                    System.out.print((intTemp+(i*64)-1) + ", ");
                    tempC++;
                    continue;
                }
                
                //checks to see if it is several walls connected together
                if(temp.charAt(1) == '-' || temp.charAt(2) == '-')
                {
                    //if so grabs either side and creates a for loop to loop through wall segment 
                    String temp1 = temp.substring(0, temp.indexOf("-"));
                    String temp2 = temp.substring(temp.indexOf("-")+1);
                    for(int left = Integer.parseInt(temp1), right = Integer.parseInt(temp2); left <= (right); left++)
                    {
                        // i*64 is to keep track of what line the wall is on
                        System.out.print((left+(i*64)-1) + ", ");
                        tempC++;
                    }
                }
                else
                {
                    int intTemp = Integer.parseInt(temp);
                    System.out.print((intTemp+(i*64)-1) + ", ");
                    tempC++;
                }
                
                
            }
            //to help keep things organized, prints a new line after each line of the file is parsed
            System.out.println();

        }
        System.out.println("number of walls: " + tempC);
    }
    
}
