/*Name: Project 2
Description:
Created by: Kevin Boudreaux
Created on: 3/5/2021
ID: KCB180002
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        //Scanner is used to get the input file
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine();
        File file = new File(fileName);
        Scanner fileScanner = null;
        try
        {
            fileScanner = new Scanner(file);
        }
        //catch statement is used if the file is not found
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        String Line;
        if(fileScanner != null)
            fileScanner.nextLine();

        //counts the number of lines in the given file, used to make correct sized arrays
        int numLines = 1;
        if(fileScanner != null)
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
        
        LinkList list = new LinkList();
        
        //for loop runs for number of lines in the file
        for(int i = 0; i < numLines; i++)
        {
            assert fileScanner != null;
            Line = fileScanner.nextLine();
            //if the line does not have a space for the name, skip it
            if(Line.indexOf(' ') == -1)
                continue;
            //creates a new temp node and calls all the functions needed to fill in the stats
            Node temp = new Node();
            temp.setName(Line.substring(0,Line.indexOf(' ')));
            Line=Line.substring(Line.indexOf(' ')+1);
            temp.setHits(findHits(Line));
            temp.setOut(findOuts(Line));
            temp.setStrikeOut(findStrikeOuts(Line));
            temp.setWalks(findWalks(Line));
            temp.setHBP(findHBP(Line));
            temp.setSacrifice(findSacrifice(Line));
           
            //listTemp is used to traverse through the list and compare temp to the other players,
            //if there is a match, then it combines the stats into the players data that is already in the list
            Node listTemp = list.getHead();
            boolean match = false;
            while(listTemp != null && !match)
            {
                //if statement is used to check to see if the names are the same
                if(listTemp.getName().compareToIgnoreCase(temp.getName()) == 0)
                {
                    match = true;
                    listTemp.setHits(listTemp.getHits()+temp.getHits());
                    listTemp.setOut(listTemp.getOut()+temp.getOut());
                    listTemp.setStrikeOut(listTemp.getStrikeOut()+temp.getStrikeOut());
                    listTemp.setWalks(listTemp.getWalks()+temp.getWalks());
                    listTemp.setHBP(listTemp.getHBP()+temp.getHBP());
                    listTemp.setSacrifice(listTemp.getSacrifice()+temp.getSacrifice());
                }
                listTemp = listTemp.getNext();
            }
            //if there was no match then checks to see if the list is empty 
            //and if not adds the new name to the end of the list
            if(!match)
            {
                if(list.getHead() == null)
                    list.setHead(temp);
                else
                {
                    list.setNextNode(temp);
                }
            }

        }
        //sorts then prints the list in alphabetical order
        list.sort();
        list.print();
        //creates new linked lists based off the leaders and finds the leaders for that given list
        LinkList BAList = findBALeader(list);
        LinkList OBPList = findOBPLeader(list);
        LinkList HitsList = findHitLeader(list);
        LinkList WalksList = findWalkLeader(list);
        LinkList StrikeOutList = findStrikeOutLeader(list);
        LinkList HBPList = findHBPLeader(list);
        
        //prints out all the leaders found
        System.out.println();
        System.out.println("LEAGUE LEADERS");
        System.out.println("BATTING AVERAGE");
        printBALeaders(BAList);
        System.out.println();
        System.out.println("ON-BASE PERCENTAGE");
        printOBPLeaders(OBPList);
        System.out.println();
        System.out.println("HITS");
        printHitsLeaders(HitsList);
        System.out.println();
        System.out.println("WALKS");
        printWalksLeaders(WalksList);
        System.out.println();
        System.out.println("STRIKEOUTS");
        printStrikeOutsLeaders(StrikeOutList);
        System.out.println();
        System.out.println("HIT BY PITCH");
        printHBPLeaders(HBPList);
        System.out.println();
    }

    //finds the number of hits in a string
    static int findHits(String string)
    {
        int counter = 0;
        for(int i = 0; i < string.length(); i++)
        {
            //if the sting has an H in it then it adds one to the Hit counter
            if(string.charAt(i) == 'H')
                counter++;
        }
        return counter;
    }
    //finds the number of Outs in a string
    static int findOuts(String string)
    {
        int counter = 0;
        for(int i = 0; i < string.length(); i++)
        {
            //if the sting has an O in it then it adds one to the Out counter
            if(string.charAt(i) == 'O')
                counter++;
        }
        return counter;
    }

    //finds the number of StrikeOuts in a string
    static int findStrikeOuts(String string)
    {
        int counter = 0;
        for(int i = 0; i < string.length(); i++)
        {
            //if the sting has an K in it then it adds one to the StrikeOut counter
            if(string.charAt(i) == 'K')
                counter++;
        }
        return counter;
    }
    //finds the number of Walks in a string
    static int findWalks(String string)
    {
        int counter = 0;
        for(int i = 0; i < string.length(); i++)
        {
            //if the sting has an W in it then it adds one to the Walks counter
            if(string.charAt(i) == 'W')
                counter++;
        }
        return counter;
    }
    
    //finds the number of Hits-By-Pitch in a string
    static int findHBP(String string)
    {
        int counter = 0;
        for(int i = 0; i < string.length(); i++)
        {
            //if the sting has an P in it then it adds one to the Hits-By-Pitch counter
            if(string.charAt(i) == 'P')
                counter++;
        }
        return counter;
    }

    //finds the number of Sacrifices in a string
    static   int findSacrifice(String string)
    {
        int counter = 0;
        for(int i = 0; i < string.length(); i++)
        {
            //if the sting has an S in it then it adds one to the Sacrifice counter
            if(string.charAt(i) == 'S')
                counter++;
        }
        return counter;
    }

    //finds the Batting Average Leaders
    static LinkList findBALeader(LinkList OGList)
    {
        //starts the counter at 1 because there will always be at least one person
        int personCounter=1;
        
        LinkList List = new LinkList(OGList);
        Node Leader = List.getHead();
        
        //temp is used to compare the first player against the others
        Node temp;
        
        LinkList BALeader=new LinkList();
        BALeader.setHead(Leader);

        double HighestBA;
        //for loop only runs three times because at minimum there will be 3 leaders
        for(int i=0; i < 3 && List.getHead() != null; i++)
        {
            //Leader starts at the head of the list
            Leader = new Node(List.getHead());
            //if statement is used to see if there is more than one player
            if(List.getHead().getNext() != null)
                temp = new Node(List.getHead().getNext());
            else break;
            
            HighestBA = Leader.getBA();
            
            //while loop goes through the list to find the highest leader(s)
            while(temp != null)
            {
                if(temp.getBA() > HighestBA)
                {
                    Leader = temp;
                    HighestBA=temp.getBA();
                }
                //if there are two players that share the same score then they share the rank
                else if(temp.getBA() == HighestBA)
                {
                    Leader.setName(Leader.getName() + ", " + temp.getName());
                }
                temp=temp.getNext();
            }
            Leader.setNext(null);
            //for loop counts number of players that are considered leaders
            for(int j=0;j<Leader.getName().length();j++)
            {
                if (Leader.getName().charAt(j) == ',')
                    personCounter++;
            }
            //for the first iteration of the 3 time for loop
            if(i==0)
                BALeader.setHead(Leader);
            //for the second iteration of the 3 time for loop
            else if(i==1)
                BALeader.getHead().setNext(Leader);
            //for the third iteration of the 3 time for loop
            else if(i==2)
                BALeader.getHead().getNext().setNext(Leader);

            int counter=0;
            Node spot=List.getHead();
            
            //while loop removes the leaders from the list to prevent duplicates
            while(spot != null)
            {
                if(spot.getBA() == HighestBA)
                {
                    spot=spot.getNext();
                    List.delete(counter);
                }
                else
                {
                    counter++;
                    spot = spot.getNext();
                }
            }
            //if we find 3 leaders before the end of the 3 time loop then we break
            if(personCounter>=3)
                break;
            //if we are not on the last iteration then we add another Node to the end of the list
            if(i!=2)
            {
                Leader.setNext(new Node());
            }
            personCounter++;
        }
        return BALeader;
    }

    //finds the On-Base-Percentage Leaders
    static   LinkList findOBPLeader(LinkList OGList)
    {
        //starts the counter at 1 because there will always be at least one person
        int personCounter=1;
        
        LinkList List = new LinkList(OGList);
        Node Leader = List.getHead();
     
        //temp is used to compare the first player against the others
        Node temp;
        LinkList OBPLeader = new LinkList();
        
        OBPLeader.setHead(Leader);
        //for loop only runs three times because at minimum there will be 3 leaders
        for(int i=0; i < 3 && List.getHead() != null; i++)
        {
            //Leader starts at the head of the list
            Leader = new Node(List.getHead());
            //if statement is used to see if there is more than one player
            if(List.getHead().getNext() != null)
                temp = new Node(List.getHead().getNext());
            else break;
            double HighestOBP = Leader.getOBP();
            //while loop goes through the list to find the highest leader(s)
            while(temp != null)
            {
                if(temp.getOBP() > HighestOBP)
                {
                    Leader = temp;
                    HighestOBP = temp.getOBP();
                }
               //if there are two players that share the same score then they share the rank
                else if(temp.getOBP() == HighestOBP)
                {
                    Leader.setName(Leader.getName() + ", " + temp.getName());
                }
                temp=temp.getNext();
            } 
            Leader.setNext(null);
            //for loop counts number of players that are considered leaders
            for(int j=0;j<Leader.getName().length();j++)
            {
                if (Leader.getName().charAt(j) == ',')
                    personCounter++;
            }
            int counter = 0;
            Node spot = List.getHead();
            //for the first iteration of the 3 time for loop
            if(i == 0)
                OBPLeader.setHead(Leader);
            //for the second iteration of the 3 time for loop
            if(i == 1)
                OBPLeader.getHead().setNext(Leader);
            //for the third iteration of the 3 time for loop
            if(i == 2)
                OBPLeader.getHead().getNext().setNext(Leader);
            //while loop removes the leaders from the list to prevent duplicates
            while(spot != null)
            {
                if(spot.getOBP() == HighestOBP)
                {
                    spot = spot.getNext();
                    List.delete(counter);
                }
                else
                {
                    counter++;
                    spot = spot.getNext();
                }
            }

             //if we find 3 leaders before the end of the 3 time loop then we break
            if(personCounter >= 3)
                break;
            //if we are not on the last iteration then we add another Node to the end of the list
            if(i != 2)
            {
                Leader.setNext(new Node());
            }
            personCounter++;
        }
        return OBPLeader;
    }
    //finds the Hit Leaders
    static  LinkList findHitLeader(LinkList OGList)
    {
        int personCounter = 1;
        LinkList List = new LinkList(OGList);
        Node Leader = List.getHead();
        Node temp;
        LinkList HitLeader = new LinkList();
        HitLeader.setHead(Leader);

        double HighestHit;
        //for loop only runs three times because at minimum there will be 3 leaders
        for(int i=0; i < 3 && List.getHead() != null; i++)
        {
            //Leader starts at the head of the list
            Leader = new Node(List.getHead());
            //if statement is used to see if there is more than one player
            if(List.getHead().getNext() != null)
                temp = new Node(List.getHead().getNext());
            else break;
            HighestHit = Leader.getHits();
            //while loop goes through the list to find the highest leader(s)
            while(temp != null)
            {
                if(temp.getHits() > HighestHit)
                {
                    Leader = temp;
                    HighestHit = temp.getHits();
                }
               //if there are two players that share the same score then they share the rank
                else if(temp.getHits() == HighestHit)
                {
                    Leader.setName(Leader.getName() + ", " + temp.getName());
                }
                temp=temp.getNext();
            }
            Leader.setNext(null);
            //for loop counts number of players that are considered leaders
            for(int j = 0;j < Leader.getName().length(); j++)
            {
                if (Leader.getName().charAt(j) == ',')
                    personCounter++;
            }
            //for the first iteration of the 3 time for loop
            if(i == 0)
                HitLeader.setHead(Leader);
            //for the second iteration of the 3 time for loop
            if(i == 1)
                HitLeader.getHead().setNext(Leader);
            //for the third iteration of the 3 time for loop
            if(i == 2)
                HitLeader.getHead().getNext().setNext(Leader);
            int counter = 0;
            Node spot = List.getHead();
            //while loop removes the leaders from the list to prevent duplicates
            while(spot != null)
            {
                if(spot.getHits() == HighestHit)
                {
                    spot = spot.getNext();
                    List.delete(counter);
                }
                else
                {
                    counter++;
                    spot = spot.getNext();
                }
            }
             //if we find 3 leaders before the end of the 3 time loop then we break
            if(personCounter >= 3)
                break;
            //if we are not on the last iteration then we add another Node to the end of the list
            if(i != 2)
            {
                Leader.setNext(new Node());
            }
            personCounter++;
        }
        return HitLeader;
    }
    //finds the Walk Leaders
    static  LinkList findWalkLeader(LinkList OGList)
    {
        int personCounter = 1;
        LinkList List = new LinkList(OGList);
        Node Leader = List.getHead();
        Node temp;
        LinkList WalksLeader = new LinkList();
        WalksLeader.setHead(Leader);

        double HighestWalks;
        //for loop only runs three times because at minimum there will be 3 leaders
        for(int i=0; i < 3 && List.getHead() != null; i++)
        {
            //Leader starts at the head of the list
            Leader = new Node(List.getHead());
            //if statement is used to see if there is more than one player
            if(List.getHead().getNext() != null)
                temp = new Node(List.getHead().getNext());
            else break;
            HighestWalks = Leader.getWalks();
            //while loop goes through the list to find the highest leader(s)
            while(temp != null)
            {
                if(temp.getWalks() > HighestWalks)
                {
                    Leader = temp;
                    HighestWalks = temp.getWalks();
                }
               //if there are two players that share the same score then they share the rank
                else if(temp.getWalks() == HighestWalks)
                {
                    Leader.setName(Leader.getName() + ", " + temp.getName());
                }
                temp=temp.getNext();
            }
            Leader.setNext(null);
            //for loop counts number of players that are considered leaders
            for(int j = 0;j < Leader.getName().length(); j++)
            {
                if (Leader.getName().charAt(j) == ',')
                    personCounter++;
            }
            
            //for the first iteration of the 3 time for loop
            if(i == 0)
                WalksLeader.setHead(Leader);
            //for the second iteration of the 3 time for loop
            if(i == 1)
                WalksLeader.getHead().setNext(Leader);
            //for the third iteration of the 3 time for loop
            if(i == 2)
                WalksLeader.getHead().getNext().setNext(Leader);
            int counter = 0;
            Node spot = List.getHead();
            //while loop removes the leaders from the list to prevent duplicates
            while(spot != null)
            {
                if(spot.getWalks() == HighestWalks)
                {
                    spot = spot.getNext();
                    List.delete(counter);
                }
                else
                {
                    counter++;
                    spot = spot.getNext();
                }
            }
            //if we find 3 leaders before the end of the 3 time loop then we break
            if(personCounter >= 3)
                break;

            //if we are not on the last iteration then we add another Node to the end of the list
            if(i != 2)
            {
                Leader.setNext(new Node());
            }
            personCounter++;
        }
        return WalksLeader;
    }
    //finds the StrikeOut Leaders
    static  LinkList findStrikeOutLeader(LinkList OGList)
    {
        int personCounter = 1;
        LinkList List = new LinkList(OGList);
        Node Leader = List.getHead();
        Node temp;
        LinkList StrikeOutLeader = new LinkList();
        StrikeOutLeader.setHead(Leader);

        int LowestStrikeOut;
        //for loop only runs three times because at minimum there will be 3 leaders
        for(int i=0; i < 3 && List.getHead() != null; i++)
        {
            //Leader starts at the head of the list
            Leader = new Node(List.getHead());
            //if statement is used to see if there is more than one player
            if(List.getHead().getNext() != null)
                temp = new Node(List.getHead().getNext());
            else break;
            LowestStrikeOut = Leader.getStrikeOut();
            //while loop goes through the list to find the highest leader(s)
            while(temp != null)
            {
                if(temp.getStrikeOut() < LowestStrikeOut)
                {
                    Leader = temp;
                    LowestStrikeOut = temp.getStrikeOut();
                }
               //if there are two players that share the same score then they share the rank
                else if(temp.getStrikeOut() == LowestStrikeOut)
                {
                    Leader.setName(Leader.getName() + ", " + temp.getName());
                }
                temp = temp.getNext();
            }
            Leader.setNext(null);
            //for loop counts number of players that are considered leaders
            for(int j = 0;j < Leader.getName().length(); j++)
            {
                if (Leader.getName().charAt(j) == ',')
                    personCounter++;
            }
            //for the first iteration of the 3 time for loop
            if(i == 0)
                StrikeOutLeader.setHead(Leader);
            //for the second iteration of the 3 time for loop
            if(i == 1)
                StrikeOutLeader.getHead().setNext(Leader);
            //for the third iteration of the 3 time for loop
            if(i == 2)
                StrikeOutLeader.getHead().getNext().setNext(Leader);
            int counter = 0;
            Node spot = List.getHead();
            //while loop removes the leaders from the list to prevent duplicates
            while(spot != null)
            {
                if(spot.getStrikeOut() == LowestStrikeOut)
                {
                    spot = spot.getNext();
                    List.delete(counter);
                }
                else
                {
                    counter++;
                    spot = spot.getNext();
                }
            }
             //if we find 3 leaders before the end of the 3 time loop then we break
            if(personCounter >= 3)
                break;
            //if we are not on the last iteration then we add another Node to the end of the list
            if(i != 2)
            {
                Leader.setNext(new Node());
            }
            personCounter++;
        }
        return StrikeOutLeader;
    }
    //finds the Hit-By-Pitch Leaders
    static   LinkList findHBPLeader(LinkList OGList)
    {
        int personCounter = 1;
        LinkList List = new LinkList(OGList);
        Node Leader = List.getHead();
        Node temp;
        LinkList HBPLeader = new LinkList();
        HBPLeader.setHead(Leader);

        int HighestHBP;
        //for loop only runs three times because at minimum there will be 3 leaders
        for(int i=0; i < 3 && List.getHead() != null; i++)
        {
            //Leader starts at the head of the list
            Leader = new Node(List.getHead());
            //if statement is used to see if there is more than one player
            if(List.getHead().getNext() != null)
                temp = new Node(List.getHead().getNext());
            else break;
            HighestHBP = Leader.getHBP();
            //while loop goes through the list to find the highest leader(s)
            while(temp != null)
            {
                if(temp.getHBP() > HighestHBP)
                {
                    Leader = temp;
                    HighestHBP = temp.getHBP();
                }
               //if there are two players that share the same score then they share the rank
                else if(temp.getHBP() == HighestHBP)
                {
                    Leader.setName(Leader.getName() + ", " + temp.getName());
                }
                temp = temp.getNext();
            }
            Leader.setNext(null);
            //for loop counts number of players that are considered leaders
            for(int j = 0;j < Leader.getName().length(); j++)
            {
                if (Leader.getName().charAt(j) == ',')
                    personCounter++;
            }
            //for the first iteration of the 3 time for loop
            if(i == 0)
                HBPLeader.setHead(Leader);
            //for the second iteration of the 3 time for loop
            if(i == 1)
                HBPLeader.getHead().setNext(Leader);
            //for the third iteration of the 3 time for loop
            if(i == 2)
                HBPLeader.getHead().getNext().setNext(Leader);
            int counter = 0;
            Node spot = List.getHead();
            //while loop removes the leaders from the list to prevent duplicates
            while(spot != null)
            {
                if(spot.getHBP() == HighestHBP)
                {
                    spot = spot.getNext();
                    List.delete(counter);
                }
                else
                {
                    counter++;
                    spot = spot.getNext();
                }
            }
             //if we find 3 leaders before the end of the 3 time loop then we break
            if(personCounter >= 3)
                break;
            //if we are not on the last iteration then we add another Node to the end of the list
            if(i != 2)
            {
                Leader.setNext(new Node());
            }
            personCounter++;
        }
        return HBPLeader;
    }



    //prints the Batting Average Leaders
    static void printBALeaders(LinkList temp)
    {
        System.out.println(String.format("%.3f",temp.getHead().getBA())+"\t"+temp.getHead().getName());
        //calls a recursive function to print everything after the head
        printBALeaders(temp.getHead().getNext());
    }
    //prints the On-Base-Percentage Leaders
    static void printOBPLeaders(LinkList temp)
    {
        System.out.println(String.format("%.3f",temp.getHead().getOBP())+"\t"+temp.getHead().getName());
        printOBPLeaders(temp.getHead().getNext());
    }
    //prints the Hit Leaders
    static void printHitsLeaders(LinkList temp)
    {
        System.out.println(temp.getHead().getHits()+"\t"+temp.getHead().getName());  
        //calls a recursive function to print everything after the head
        printHitsLeaders(temp.getHead().getNext());
    }
    //prints the Walk Leaders
    static void printWalksLeaders(LinkList temp)
    {
        System.out.println(temp.getHead().getWalks()+"\t"+temp.getHead().getName());
        //calls a recursive function to print everything after the head
        printWalksLeaders(temp.getHead().getNext());
    }
    //prints the StrikeOut Leaders
    static void printStrikeOutsLeaders(LinkList temp)
    {
        System.out.println(temp.getHead().getStrikeOut()+"\t"+temp.getHead().getName());
        //calls a recursive function to print everything after the head
        printStrikeOutsLeaders(temp.getHead().getNext());
    }
    //prints the Hit-By-Pitch Leaders
    static void printHBPLeaders(LinkList temp)
    {
        System.out.println(temp.getHead().getHBP()+"\t"+temp.getHead().getName());
        //calls a recursive function to print everything after the head
        printHBPLeaders(temp.getHead().getNext());
    }



    //Recursive version of the Batting Average Leaders print function
    static void printBALeaders(Node ptr)
    {
        if(ptr == null)
            return;
        System.out.println(String.format("%.3f",ptr.getBA())+"\t"+ptr.getName());
        printBALeaders(ptr.getNext());
    }
    //Recursive version of the On-Base-Percentage Leaders print function
    static void printOBPLeaders(Node ptr)
    {
        if(ptr == null)
            return;
        System.out.println(String.format("%.3f",ptr.getOBP())+"\t"+ptr.getName());
        printOBPLeaders(ptr.getNext());
    }
    //Recursive version of the Hit Leaders print function
    static void printHitsLeaders(Node ptr)
    {
        if(ptr == null)
            return;
        System.out.println(ptr.getHits()+"\t"+ptr.getName());
        printHitsLeaders(ptr.getNext());
    }
    //Recursive version of the Walk Leaders print function
    static void printWalksLeaders(Node ptr)
    {
        if(ptr == null)
            return;
        System.out.println(ptr.getWalks()+"\t"+ptr.getName());
        printWalksLeaders(ptr.getNext());
    }
    //Recursive version of the StrikeOut Leaders print function
    static void printStrikeOutsLeaders(Node ptr)
    {
        if(ptr == null)
            return;
        System.out.println(ptr.getStrikeOut()+"\t"+ptr.getName());
        printStrikeOutsLeaders(ptr.getNext());
    }
    //Recursive version of the Hit-By-Pitch Leaders print function
    static void printHBPLeaders(Node ptr)
    {
        if(ptr == null)
            return;
        System.out.println(ptr.getHBP()+"\t"+ptr.getName());
        printHBPLeaders(ptr.getNext());
    }
}
