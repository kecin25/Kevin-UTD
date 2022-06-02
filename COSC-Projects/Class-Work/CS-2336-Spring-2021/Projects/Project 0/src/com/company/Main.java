/*
Name: Project 0
Made by: Kevin Boudreaux
ID: KCB 180002
Made on: 1/20/2021
*/
package com.company;



import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
// import.io.*
//* uses the entire io library, use if need more than one thing

public class Main
{


    public static void main(String[] args)
    {


        //Calls the getFile function and sets it as a file object
        File file = getFile();

        //makes a Scanner object and set it up to open the given file and if a valid file Line is used to grab the first
        //line
        Scanner fileScanner = null;
        try
        {
            fileScanner = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        String Line = null;
        if (fileScanner != null)
        {
            Line = fileScanner.nextLine();
        }

        //counts the number of lines in the given file, used to make correct sized arrays
        int numLines = 1;
        if(fileScanner !=null)
        {
            while (fileScanner.hasNextLine())
            {
                numLines++;
                fileScanner.nextLine();
            }
        }

        //resets the FileScanner object to the start of the file
        try
        {
            fileScanner = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        //all the arrays that are used are initialized below
        String[] nameArray = new String[numLines];
        int[] hitArray = new int[numLines];
        int[] strikeoutArray = new int[numLines];
        int[] walkArray = new int[numLines];
        int[] hitByPitchArray = new int[numLines];
        float[] battingAverageArray = new float[numLines];
        float[] onBaseAverageArray = new float[numLines];
        //for loop that calls the calculations function for each line and breaks apart the returned string and stores
        //the data into the correct arrays
        for (int i = 0; i < numLines; i++)
        {
            assert Line != null;
            String Info = calculations(Line);
            if (i == 0) Line = fileScanner.nextLine();
            if (fileScanner.hasNextLine()) Line = fileScanner.nextLine();

            nameArray[i] = Info.substring(0, Info.indexOf(" "));
            Info = Info.substring(Info.indexOf(" ") + 1);
            hitArray[i] = Integer.parseInt(Info.substring(0, Info.indexOf(" ")));
            Info = Info.substring(Info.indexOf(" ") + 1);
            walkArray[i] = Integer.parseInt(Info.substring(0, Info.indexOf(" ")));
            Info = Info.substring(Info.indexOf(" ") + 1);
            strikeoutArray[i] = Integer.parseInt(Info.substring(0, Info.indexOf(" ")));
            Info = Info.substring(Info.indexOf(" ") + 1);
            hitByPitchArray[i] = Integer.parseInt(Info.substring(0, Info.indexOf(" ")));
            Info = Info.substring(Info.indexOf(" ") + 1);
            battingAverageArray[i] = Float.parseFloat(Info.substring(0, Info.indexOf(" ")));
            Info = Info.substring(Info.indexOf(" ") + 1);
            onBaseAverageArray[i] = Float.parseFloat(Info);
        }
        //calls the output function with the needed arrays to correctly print out the gathered information
        Output(nameArray, hitArray, strikeoutArray, walkArray, hitByPitchArray,
               battingAverageArray, onBaseAverageArray, numLines);

        //calls the output function with the needed arrays to correctly print out the gathered information
        LeagueLeaders(nameArray, hitArray, walkArray, strikeoutArray, hitByPitchArray,
                      battingAverageArray, onBaseAverageArray, numLines);
        //closes the file
        fileScanner.close();
    }

    //getFile function takes in a string from the user and uses it to open the file with the same name
    public static File getFile()
    {

        Scanner input = new Scanner(System.in);
        System.out.println("What is the name of the file?");
        String fileName = input.nextLine();
        System.out.println("File is : " + fileName);

        return new File(fileName);
    }

    //calculations is used to find the batting stats of each player, code runs through the passed in string and
    //counts how many of each type of stat that is being tracked and finds the needed percentages
    public static String calculations(String Line)
    {

        int spaceLoco = Line.indexOf(' ');
        String name = Line.substring(0, spaceLoco);
        Line = Line.substring(spaceLoco + 1);

        int length = Line.length();
        int hit = 0, strikeOut = 0, walk = 0, hitByPitch = 0, at_bat = 0, plateAppearances = 0;

        //for loop that counts each type of plate appearance
        for (int i = 0; i < length; i++)
        {
            if (Line.charAt(i) == 'H')
            {
                hit++;
                at_bat++;
                plateAppearances++;
            }
            else if (Line.charAt(i) == 'O')
            {
                at_bat++;
                plateAppearances++;
            }
            else if (Line.charAt(i) == 'K')
            {
                strikeOut++;
                at_bat++;
                plateAppearances++;
            }
            else if (Line.charAt(i) == 'W')
            {
                walk++;
                plateAppearances++;
            }
            else if (Line.charAt(i) == 'P')
            {
                hitByPitch++;
                plateAppearances++;
            }
            else if (Line.charAt(i) == 'S')
            {
                plateAppearances++;
            }
        }

        float battingAverage = (float) hit / at_bat;
        if(at_bat == 0)
            battingAverage = 0;
        float onBaseAverage = (float) (hit + walk + hitByPitch) / plateAppearances;
        if(plateAppearances == 0)
            onBaseAverage = 0;

        return name + " " + hit + " " + walk + " " + strikeOut + " " + hitByPitch + " " + battingAverage + " " + onBaseAverage;
    }

    //Output prints out all the information gathered from the file
    //Also rounds the averages to 3 decimal points
    public static void Output(String[] nameArray, int[] hitArray, int[] strikeoutArray, int[] walkArray, int[] hitByPitchArray, float[] battingAverageArray, float[] onBaseAverageArray, int count)
    {
        for(int i=0;i<count;i++)
        {
            System.out.println(nameArray[i]);
            System.out.println("BA: "+String.format("%.3f",battingAverageArray[i]));
            System.out.println("OB%: "+String.format("%.3f",onBaseAverageArray[i]));
            System.out.println("H: "+hitArray[i]);
            System.out.println("BB: "+walkArray[i]);
            System.out.println("K: "+strikeoutArray[i]);
            System.out.println("HBP: "+hitByPitchArray[i]);
            System.out.print("\n");

        }

    }

    //League Leaders function finds the leaders for each stat and if it is tied, says who is tied for the leading spot
    public static void LeagueLeaders(String[] nameArray, int[] hitArray, int[] walkArray, int[] strikeoutArray, int[] hitByPitchArray, float[] battingAverageArray,float[] onBaseAverageArray, int count)
    {
        StringBuilder BALeader = new StringBuilder();
        StringBuilder OBLeader = new StringBuilder();
        StringBuilder HLeader = new StringBuilder();
        StringBuilder BBLeader = new StringBuilder();
        StringBuilder KLeader = new StringBuilder();
        StringBuilder HBPLeader = new StringBuilder();

        float BANum = 0,OBNum = 0;
        int HNum = 0,BBNum = 0,KNum = 100000000,HBPNum = 0;

        //for loop that finds the leader for each stat
        for(int i=0;i<count;i++)
        {
            if(battingAverageArray[i]>BANum)
            {
                BALeader = new StringBuilder(nameArray[i]);
                BANum=battingAverageArray[i];
            }
            else if (battingAverageArray[i] == BANum && BALeader.length() != 0)
                BALeader.append(", ").append(nameArray[i]);
            else if (battingAverageArray[i] == BANum && BALeader.length() == 0)
                BALeader.append(nameArray[i]);

            if(onBaseAverageArray[i]>OBNum)
            {
                OBLeader = new StringBuilder(nameArray[i]);
                OBNum=onBaseAverageArray[i];
            }
            else if (onBaseAverageArray[i] == OBNum && OBLeader.length() != 0)
                OBLeader.append(", ").append(nameArray[i]);
            else if(onBaseAverageArray[i] == OBNum && OBLeader.length() == 0)
                OBLeader.append((nameArray[i]));

            if(hitArray[i]>HNum)
            {
                HLeader = new StringBuilder(nameArray[i]);
                HNum=hitArray[i];
            }
            else if (hitArray[i] == HNum && HLeader.length() != 0)
                HLeader.append(", ").append(nameArray[i]);
            else if (hitArray[i] == HNum && HLeader.length() == 0)
                HLeader.append(nameArray[i]);

            if(walkArray[i]>BBNum)
            {
                BBLeader = new StringBuilder(nameArray[i]);
                BBNum=walkArray[i];
            }
            else if (walkArray[i] == BBNum && BBLeader.length() != 0)
                BBLeader.append(", ").append(nameArray[i]);
            else if (walkArray[i] == BBNum && BBLeader.length() == 0)
                BBLeader.append(nameArray[i]);

            if(strikeoutArray[i]<KNum)
            {
                KLeader = new StringBuilder(nameArray[i]);
                KNum=strikeoutArray[i];
            }
            else if (strikeoutArray[i] == KNum && KLeader.length() != 0)
                KLeader.append(", ").append(nameArray[i]);
            else if (strikeoutArray[i] == KNum && KLeader.length() == 0)
                KLeader.append(nameArray[i]);

            if(hitByPitchArray[i]>HBPNum)
            {
                HBPLeader = new StringBuilder(nameArray[i]);
                HBPNum=hitByPitchArray[i];
            }
            else if (hitByPitchArray[i] == HBPNum && HBPLeader.length() != 0)
                HBPLeader.append(", ").append(nameArray[i]);
            else if (hitByPitchArray[i] == HBPNum && HBPLeader.length() == 0)
                HBPLeader.append(nameArray[i]);

        }
        System.out.println("LEAGUE LEADERS");
        System.out.println("BA: " + BALeader + " " + String.format("%.3f",BANum));
        System.out.println("OB%: " + OBLeader + " " + String.format("%.3f",OBNum));
        System.out.println("H: " + HLeader + " " + HNum);
        System.out.println("BB: " + BBLeader + " " + BBNum);
        System.out.println("K: " + KLeader + " " + KNum);
        System.out.println("HBP: " + HBPLeader + " " + HBPNum);
    }

}