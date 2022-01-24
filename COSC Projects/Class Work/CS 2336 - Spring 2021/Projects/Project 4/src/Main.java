/*Name: Project 4
Description: Using Hashmaps to get inputs and quick sort and find them when dealing with a baseball game
Created by: Kevin Boudreaux
Created on: 4/12/2021
ID: KCB180002
*/
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Collections;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        //3 Hashmaps, one for the key to check play by play input with
        //one for away team and one for home team
        GenericHashMap<String,String> keyMap = new GenericHashMap<>();
        GenericHashMap<String, Player> homeTeam = new GenericHashMap<>();
        GenericHashMap<String, Player> awayTeam = new GenericHashMap<>();
        
        //Lists are used to keep track of who is in what team
        ArrayList<String> homeList = new ArrayList<>();
        ArrayList<String> awayList = new ArrayList<>();
        
        //key is used to get access to the key file
        File key = new File("keyfile.txt");
        Scanner keyScanner = null;
        try
        {
            keyScanner = new Scanner(key);
        }
        catch (FileNotFoundException e)
        {
            System.out.print("File 'keyFile.txt' not found");
            e.printStackTrace();
        }
        
        String line;
        if(keyScanner != null)
            keyScanner.nextLine();
        
        int counter = 1;
        //large while loop that gets every line from the key file and converts it into the different stats that will be needed
        while(keyScanner != null && keyScanner.hasNextLine())
        {
            line = keyScanner.nextLine();
            
            //if the line starts with a # or if its empty then skip it and move on to the next line
            if(line.length() == 0 || line.charAt(0) == '#')
                continue;
            //first 21 valid lines will be equivalent to out
            if(counter <=21)
                keyMap.insert(line,"Out");
            //the next valid line will be the only strikeout
            else if(counter == 22)
                keyMap.insert(line,"StrikeOut");
            //the next 4 lines will be considered hits
            else if(counter <27)
                keyMap.insert(line,"Hit");
            //the next valid line will be the only Walk
            else if( counter == 27)
                keyMap.insert(line,"Walk");
            //the next 7 valid lines will be sacrifices
            else if( counter < 35)
                keyMap.insert(line,"Sacrifice");
            //the next valid line will be the only HBP
            else if( counter ==  35)
                keyMap.insert(line,"Hit By Pitch");
            //every other line after that is an error
            else
                keyMap.insert(line,"Error");
            
            //counter used to keep track of the number of valid lines
            counter++;
        }
        //close the key file since it is no longer needed        
        if (keyScanner != null)
        {
            keyScanner.close();
        }

        //opens a scanner to scan input
        Scanner input = new Scanner(System.in);
        //gets the filename inputted by the user
        String fileName =input.nextLine();
        File file = new File(fileName);
        Scanner fileScanner = null;
        //attempts to open the file named by the user, if not found then print error
        try
        {
            fileScanner = new Scanner(file);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        
        //while loop that goes through the play by play of the given file
        while(fileScanner != null && fileScanner.hasNext())
        {
            //line grabs a single line from the file
            line = fileScanner.nextLine();
            //make a temp Hash map that will be used to bounce between the away team and home team
            //same thing with the temp list
            GenericHashMap<String,Player> tempTeam;
            ArrayList<String> tempList;
            
            //if the first character is an A then deal with the away team, else its dealing with the home team
            if(line.charAt(0) == 'A')
            {
                tempTeam = awayTeam;
                tempList = awayList;
                
            }
            else
            {
                tempTeam = homeTeam;
                tempList = homeList;
            }
            //remove the A and the first space from the line
            line = line.substring(2);
            //everything up to the next space is the name of the player
            String playerName = line.substring(0, line.indexOf(" "));
            line = line.substring(line.indexOf(" ")+1);
            //after that all is left is the play that happened
            String playKey = line;
            
            //make a temp player that is used to hold the value of the play as well as the player name
            Player tempPlayer = new Player();
            tempPlayer.setName(playerName);
            //play is used to find the type of play that occurred in the key hash map
            String play = keyMap.getStorage(playKey);
            //switch statement used to set the correct stat of the temp player to 1
            switch (play)
            {
                case "Out": tempPlayer.setOut(1); break;
                case "StrikeOut" : tempPlayer.setStrikeout(1);break;
                case "Hit" : tempPlayer.setHits(1);break;
                case "Walk" : tempPlayer.setWalks(1);break;
                case "Sacrifice" : tempPlayer.setSacrifice(1);break;
                case "Hit By Pitch" : tempPlayer.setHBP(1);break;
                default : tempPlayer.setError(1);break;
            }
            
            boolean nameFound = false;
            //check to see if there is already a copy of the player's stats in the Hash map by using the array names list
            for (String s : tempList)
            {
                if (playerName.equals(s))
                {
                    nameFound = true;
                    break;
                }
            }
            //if there was no player that matched the name then add the new player into the teams hash map as well as the names list
            if(!nameFound)
            {
                tempTeam.insert(playerName,tempPlayer);
                tempList.add(playerName);
            }
            //if the name was found then add the stat of the new play to the player's stats
            else
            {
                Player player = tempTeam.getStorage(playerName);
                player.combine(tempPlayer);
            }
            
        }
        //sort the away and home names by alphabetical order
        Collections.sort(awayList);
        Collections.sort(homeList);
        
        //prints out the away team in alphabetical order
        System.out.println("AWAY");
        for (String s : awayList)
        {
            Player temp = awayTeam.getStorage(s);
            System.out.print(temp.toString());
        }
        //prints out the home team in alphabetical order
        System.out.println("HOME");
        for (String s : homeList)
        {
            Player temp = homeTeam.getStorage(s);
            System.out.print(temp.toString());
        }
        //calls function to print out the league leaders
        System.out.println("LEAGUE LEADERS");
        PrintLeagueLeaders(awayList,homeList,awayTeam,homeTeam);
    }
    static void PrintLeagueLeaders(ArrayList<String> awayList,ArrayList<String> homeList, GenericHashMap<String,Player> awayTeam, GenericHashMap<String,Player> homeTeam)
    {
        //prints out what the leaders are a leader in then the leaders and their scores by calling a function that searches for the leaders
        System.out.println("BATTING AVERAGE");
        printBattingAverage(awayList,homeList,  awayTeam,  homeTeam);
        System.out.println();
        
        //prints out what the leaders are a leader in then the leaders and their scores by calling a function that searches for the leaders
        System.out.println("ON-BASE PERCENTAGE");
        printOBP(awayList,homeList,  awayTeam,  homeTeam);
        System.out.println();

        //prints out what the leaders are a leader in then the leaders and their scores by calling a function that searches for the leaders
        System.out.println("HITS");
        printHits(awayList,homeList,  awayTeam,  homeTeam);
        System.out.println();

        //prints out what the leaders are a leader in then the leaders and their scores by calling a function that searches for the leaders
        System.out.println("WALKS");
        printWalks(awayList,homeList,  awayTeam,  homeTeam);
        System.out.println();

        //prints out what the leaders are a leader in then the leaders and their scores by calling a function that searches for the leaders
        System.out.println("STRIKEOUTS");
        printStrikeOuts(awayList,homeList,  awayTeam,  homeTeam);
        System.out.println();
        
        //prints out what the leaders are a leader in then the leaders and their scores by calling a function that searches for the leaders
        System.out.println("HIT BY PITCH");
        printHBP(awayList,homeList,  awayTeam,  homeTeam);
        System.out.println();
    }
    static void printBattingAverage(ArrayList<String> awayList,ArrayList<String> homeList, GenericHashMap<String,Player> awayTeam, GenericHashMap<String,Player> homeTeam)
    {
        //creates a copy of the home and away team so we can remove those names and keep the original lists safe
        ArrayList<String> tempAwayList = new ArrayList<>(awayList);
        ArrayList<String> tempHomeList = new ArrayList<>(homeList);

        //peopleCounter keeps track of the number of leaders counted already
        int peopleCounter = 0;

        //master for loop that will run 3 times if one unique person per place is found but will stop early if more than 3 people are found
        for(int j=0;j<3;j++)
        {
            //starts the highest score with the alphabetical person so we have a base to start with
            Player highestScore = awayTeam.getStorage(tempAwayList.get(0));
            StringBuilder LeaderName = new StringBuilder(highestScore.getName());
            Player temp;

            //for loop is used to go through the entire away team and find the highest scoring person/people that can be compared
            //against the home team's highest scoring person/people
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                //since the first person is already the leader there is no need to check that person again
                if (i != 0)
                {
                    //set temp to the person in the list to be compared with the leader
                    temp = awayTeam.getStorage(tempAwayList.get(i));

                    //if the person is not found for some reason then continue 
                    if(temp == null)
                        continue;
                    //compares the stat and if temp is higher sets the new highest person as the temp
                    if (temp.getBA() > highestScore.getBA())
                    {
                        highestScore = temp;
                        LeaderName = new StringBuilder(highestScore.getName());
                    }
                    //if temp and the highest have the same score then add temp's name to the leader list
                    else if (temp.getBA() == highestScore.getBA())
                    {
                        LeaderName.append(", ").append(temp.getName());
                    }
                }
            }
            //for loop that checks the away team's leader(s) against the home team
            for (String s : tempHomeList)
            {
                //sets temp as the person to be compared against
                temp = homeTeam.getStorage(s);
                //if the person can not be found for some reason 
                if (temp == null) continue;
                //compares the stat and if temp is higher sets the new highest person as the temp
                if (temp.getBA() > highestScore.getBA())
                {
                    highestScore = temp;
                    LeaderName = new StringBuilder(highestScore.getName());
                }
                //if temp and the highest have the same score then add temp's name to the leader list
                else if (temp.getBA() == highestScore.getBA())
                {
                    LeaderName.append(", ").append(temp.getName());
                }
            }
            //checks the away teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                temp = awayTeam.getStorage(tempAwayList.get(i));
                if(temp == null)
                    continue;
                if (temp.getBA() == highestScore.getBA())
                {
                    tempAwayList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //checks the home teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempHomeList.size(); i++)
            {
                temp = homeTeam.getStorage(tempHomeList.get(i));
                if(temp == null)
                    continue;
                if (temp.getBA() == highestScore.getBA())
                {
                    tempHomeList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //prints out the best score and the leader(s) with the best score
            System.out.println(String.format("%.3f", highestScore.getBA()) + "\t" + LeaderName);
            //if there have been more than 3 leaders before 3 places were awarded then ends the loop early
            if (peopleCounter >= 3) return;
        }
    }
    static void printOBP(ArrayList<String> awayList,ArrayList<String> homeList, GenericHashMap<String,Player> awayTeam, GenericHashMap<String,Player> homeTeam)
    {
        //creates a copy of the home and away team so we can remove those names and keep the original lists safe
        ArrayList<String> tempAwayList = new ArrayList<>(awayList);
        ArrayList<String> tempHomeList = new ArrayList<>(homeList);

        //peopleCounter keeps track of the number of leaders counted already
        int peopleCounter = 0;

        //master for loop that will run 3 times if one unique person per place is found but will stop early if more than 3 people are found
        for(int j=0;j<3;j++)
        {
            //starts the highest score with the alphabetical person so we have a OBPse to start with
            Player highestScore = awayTeam.getStorage(tempAwayList.get(0));
            StringBuilder LeaderName = new StringBuilder(highestScore.getName());
            Player temp;

            //for loop is used to go through the entire away team and find the highest scoring person/people that can be compared
            //against the home team's highest scoring person/people
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                //since the first person is already the leader there is no need to check that person again
                if (i != 0)
                {
                    //set temp to the person in the list to be compared with the leader
                    temp = awayTeam.getStorage(tempAwayList.get(i));

                    //if the person is not found for some reason then continue 
                    if(temp == null)
                        continue;
                    //compares the stat and if temp is higher sets the new highest person as the temp
                    if (temp.getOBP() > highestScore.getOBP())
                    {
                        highestScore = temp;
                        LeaderName = new StringBuilder(highestScore.getName());
                    }
                    //if temp and the highest have the same score then add temp's name to the leader list
                    else if (temp.getOBP() == highestScore.getOBP())
                    {
                        LeaderName.append(", ").append(temp.getName());
                    }
                }
            }
            //for loop that checks the away team's leader(s) against the home team
            for (String s : tempHomeList)
            {
                //sets temp as the person to be compared against
                temp = homeTeam.getStorage(s);
                //if the person can not be found for some reason 
                if (temp == null) continue;
                //compares the stat and if temp is higher sets the new highest person as the temp
                if (temp.getOBP() > highestScore.getOBP())
                {
                    highestScore = temp;
                    LeaderName = new StringBuilder(highestScore.getName());
                }
                //if temp and the highest have the same score then add temp's name to the leader list
                else if (temp.getOBP() == highestScore.getOBP())
                {
                    LeaderName.append(", ").append(temp.getName());
                }
            }
            //checks the away teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                temp = awayTeam.getStorage(tempAwayList.get(i));
                if(temp == null)
                    continue;
                if (temp.getOBP() == highestScore.getOBP())
                {
                    tempAwayList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //checks the home teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempHomeList.size(); i++)
            {
                temp = homeTeam.getStorage(tempHomeList.get(i));
                if(temp == null)
                    continue;
                if (temp.getOBP() == highestScore.getOBP())
                {
                    tempHomeList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //prints out the best score and the leader(s) with the best score
            System.out.println(String.format("%.3f", highestScore.getOBP()) + "\t" + LeaderName);
            //if there have been more than 3 leaders before 3 places were awarded then ends the loop early
            if (peopleCounter >= 3) return;
        }
    }
    static void printHits(ArrayList<String> awayList,ArrayList<String> homeList, GenericHashMap<String,Player> awayTeam, GenericHashMap<String,Player> homeTeam)
    {
        //creates a copy of the home and away team so we can remove those names and keep the original lists safe
        ArrayList<String> tempAwayList = new ArrayList<>(awayList);
        ArrayList<String> tempHomeList = new ArrayList<>(homeList);

        //peopleCounter keeps track of the number of leaders counted already
        int peopleCounter = 0;

        //master for loop that will run 3 times if one unique person per place is found but will stop early if more than 3 people are found
        for(int j=0;j<3;j++)
        {
            //starts the highest score with the alphabetical person so we have a Hits to start with
            Player highestScore = awayTeam.getStorage(tempAwayList.get(0));
            StringBuilder LeaderName = new StringBuilder(highestScore.getName());
            Player temp;

            //for loop is used to go through the entire away team and find the highest scoring person/people that can be compared
            //against the home team's highest scoring person/people
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                //since the first person is already the leader there is no need to check that person again
                if (i != 0)
                {
                    //set temp to the person in the list to be compared with the leader
                    temp = awayTeam.getStorage(tempAwayList.get(i));

                    //if the person is not found for some reason then continue 
                    if(temp == null)
                        continue;
                    //compares the stat and if temp is higher sets the new highest person as the temp
                    if (temp.getHits() > highestScore.getHits())
                    {
                        highestScore = temp;
                        LeaderName = new StringBuilder(highestScore.getName());
                    }
                    //if temp and the highest have the same score then add temp's name to the leader list
                    else if (temp.getHits() == highestScore.getHits())
                    {
                        LeaderName.append(", ").append(temp.getName());
                    }
                }
            }
            //for loop that checks the away team's leader(s) against the home team
            for (String s : tempHomeList)
            {
                //sets temp as the person to be compared against
                temp = homeTeam.getStorage(s);
                //if the person can not be found for some reason 
                if (temp == null) continue;
                //compares the stat and if temp is higher sets the new highest person as the temp
                if (temp.getHits() > highestScore.getHits())
                {
                    highestScore = temp;
                    LeaderName = new StringBuilder(highestScore.getName());
                }
                //if temp and the highest have the same score then add temp's name to the leader list
                else if (temp.getHits() == highestScore.getHits())
                {
                    LeaderName.append(", ").append(temp.getName());
                }
            }
            //checks the away teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                temp = awayTeam.getStorage(tempAwayList.get(i));
                if(temp == null)
                    continue;
                if (temp.getHits() == highestScore.getHits())
                {
                    tempAwayList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //checks the home teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempHomeList.size(); i++)
            {
                temp = homeTeam.getStorage(tempHomeList.get(i));
                if(temp == null)
                    continue;
                if (temp.getHits() == highestScore.getHits())
                {
                    tempHomeList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //prints out the best score and the leader(s) with the best score
            System.out.println(highestScore.getHits() + "\t" + LeaderName);
            //if there have been more than 3 leaders before 3 places were awarded then ends the loop early
            if (peopleCounter >= 3) return;
        }
    }
    static void printWalks(ArrayList<String> awayList,ArrayList<String> homeList, GenericHashMap<String,Player> awayTeam, GenericHashMap<String,Player> homeTeam)
    {
        //creates a copy of the home and away team so we can remove those names and keep the original lists safe
        ArrayList<String> tempAwayList = new ArrayList<>(awayList);
        ArrayList<String> tempHomeList = new ArrayList<>(homeList);

        //peopleCounter keeps track of the number of leaders counted already
        int peopleCounter = 0;

        //master for loop that will run 3 times if one unique person per place is found but will stop early if more than 3 people are found
        for(int j=0;j<3;j++)
        {
            //starts the highest score with the alphabetical person so we have a Walks to start with
            Player highestScore = awayTeam.getStorage(tempAwayList.get(0));
            StringBuilder LeaderName = new StringBuilder(highestScore.getName());
            Player temp;

            //for loop is used to go through the entire away team and find the highest scoring person/people that can be compared
            //against the home team's highest scoring person/people
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                //since the first person is already the leader there is no need to check that person again
                if (i != 0)
                {
                    //set temp to the person in the list to be compared with the leader
                    temp = awayTeam.getStorage(tempAwayList.get(i));

                    //if the person is not found for some reason then continue 
                    if(temp == null)
                        continue;
                    //compares the stat and if temp is higher sets the new highest person as the temp
                    if (temp.getWalks() > highestScore.getWalks())
                    {
                        highestScore = temp;
                        LeaderName = new StringBuilder(highestScore.getName());
                    }
                    //if temp and the highest have the same score then add temp's name to the leader list
                    else if (temp.getWalks() == highestScore.getWalks())
                    {
                        LeaderName.append(", ").append(temp.getName());
                    }
                }
            }
            //for loop that checks the away team's leader(s) against the home team
            for (String s : tempHomeList)
            {
                //sets temp as the person to be compared against
                temp = homeTeam.getStorage(s);
                //if the person can not be found for some reason 
                if (temp == null) continue;
                //compares the stat and if temp is higher sets the new highest person as the temp
                if (temp.getWalks() > highestScore.getWalks())
                {
                    highestScore = temp;
                    LeaderName = new StringBuilder(highestScore.getName());
                }
                //if temp and the highest have the same score then add temp's name to the leader list
                else if (temp.getWalks() == highestScore.getWalks())
                {
                    LeaderName.append(", ").append(temp.getName());
                }
            }
            //checks the away teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                temp = awayTeam.getStorage(tempAwayList.get(i));
                if(temp == null)
                    continue;
                if (temp.getWalks() == highestScore.getWalks())
                {
                    tempAwayList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //checks the home teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempHomeList.size(); i++)
            {
                temp = homeTeam.getStorage(tempHomeList.get(i));
                if(temp == null)
                    continue;
                if (temp.getWalks() == highestScore.getWalks())
                {
                    tempHomeList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //prints out the best score and the leader(s) with the best score
            System.out.println(highestScore.getWalks() + "\t" + LeaderName);
            //if there have been more than 3 leaders before 3 places were awarded then ends the loop early
            if (peopleCounter >= 3) return;
        }
    }
    static void printStrikeOuts(ArrayList<String> awayList,ArrayList<String> homeList, GenericHashMap<String,Player> awayTeam, GenericHashMap<String,Player> homeTeam)
    {
        //creates a copy of the home and away team so we can remove those names and keep the original lists safe
        ArrayList<String> tempAwayList = new ArrayList<>(awayList);
        ArrayList<String> tempHomeList = new ArrayList<>(homeList);

        //peopleCounter keeps track of the number of leaders counted already
        int peopleCounter = 0;

        //master for loop that will run 3 times if one unique person per place is found but will stop early if more than 3 people are found
        for(int j=0;j<3;j++)
        {
            //starts the highest score with the alphabetical person so we have a StrikeOuts to start with
            Player highestScore = awayTeam.getStorage(tempAwayList.get(0));
            StringBuilder LeaderName = new StringBuilder(highestScore.getName());
            Player temp;

            //for loop is used to go through the entire away team and find the highest scoring person/people that can be compared
            //against the home team's highest scoring person/people
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                //since the first person is already the leader there is no need to check that person again
                if (i != 0)
                {
                    //set temp to the person in the list to be compared with the leader
                    temp = awayTeam.getStorage(tempAwayList.get(i));

                    //if the person is not found for some reason then continue 
                    if(temp == null)
                        continue;
                    //compares the stat and if temp is higher sets the new highest person as the temp
                    if (temp.getStrikeOut() < highestScore.getStrikeOut())
                    {
                        highestScore = temp;
                        LeaderName = new StringBuilder(highestScore.getName());
                    }
                    //if temp and the highest have the same score then add temp's name to the leader list
                    else if (temp.getStrikeOut() == highestScore.getStrikeOut())
                    {
                        LeaderName.append(", ").append(temp.getName());
                    }
                }
            }
            //for loop that checks the away team's leader(s) against the home team
            for (String s : tempHomeList)
            {
                //sets temp as the person to be compared against
                temp = homeTeam.getStorage(s);
                //if the person can not be found for some reason 
                if (temp == null) continue;
                //compares the stat and if temp is higher sets the new highest person as the temp
                if (temp.getStrikeOut() < highestScore.getStrikeOut())
                {
                    highestScore = temp;
                    LeaderName = new StringBuilder(highestScore.getName());
                }
                //if temp and the highest have the same score then add temp's name to the leader list
                else if (temp.getStrikeOut() == highestScore.getStrikeOut())
                {
                    LeaderName.append(", ").append(temp.getName());
                }
            }
            //checks the away teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                temp = awayTeam.getStorage(tempAwayList.get(i));
                if(temp == null)
                    continue;
                if (temp.getStrikeOut() == highestScore.getStrikeOut())
                {
                    tempAwayList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //checks the home teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempHomeList.size(); i++)
            {
                temp = homeTeam.getStorage(tempHomeList.get(i));
                if(temp == null)
                    continue;
                if (temp.getStrikeOut() == highestScore.getStrikeOut())
                {
                    tempHomeList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //prints out the best score and the leader(s) with the best score
            System.out.println(highestScore.getStrikeOut() + "\t" + LeaderName);
            //if there have been more than 3 leaders before 3 places were awarded then ends the loop early
            if (peopleCounter >= 3) return;
        }
    }
    static void printHBP(ArrayList<String> awayList,ArrayList<String> homeList, GenericHashMap<String,Player> awayTeam, GenericHashMap<String,Player> homeTeam)
    {
        //creates a copy of the home and away team so we can remove those names and keep the original lists safe
        ArrayList<String> tempAwayList = new ArrayList<>(awayList);
        ArrayList<String> tempHomeList = new ArrayList<>(homeList);

        //peopleCounter keeps track of the number of leaders counted already
        int peopleCounter = 0;

        //master for loop that will run 3 times if one unique person per place is found but will stop early if more than 3 people are found
        for(int j=0;j<3;j++)
        {
            //starts the highest score with the alphabetical person so we have a HBPse to start with
            Player highestScore = awayTeam.getStorage(tempAwayList.get(0));
            StringBuilder LeaderName = new StringBuilder(highestScore.getName());
            Player temp;

            //for loop is used to go through the entire away team and find the highest scoring person/people that can be compared
            //against the home team's highest scoring person/people
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                //since the first person is already the leader there is no need to check that person again
                if (i != 0)
                {
                    //set temp to the person in the list to be compared with the leader
                    temp = awayTeam.getStorage(tempAwayList.get(i));

                    //if the person is not found for some reason then continue 
                    if(temp == null)
                        continue;
                    //compares the stat and if temp is higher sets the new highest person as the temp
                    if (temp.getHBP() > highestScore.getHBP())
                    {
                        highestScore = temp;
                        LeaderName = new StringBuilder(highestScore.getName());
                    }
                    //if temp and the highest have the same score then add temp's name to the leader list
                    else if (temp.getHBP() == highestScore.getHBP())
                    {
                        LeaderName.append(", ").append(temp.getName());
                    }
                }
            }
            //for loop that checks the away team's leader(s) against the home team
            for (String s : tempHomeList)
            {
                //sets temp as the person to be compared against
                temp = homeTeam.getStorage(s);
                //if the person can not be found for some reason 
                if (temp == null) continue;
                //compares the stat and if temp is higher sets the new highest person as the temp
                if (temp.getHBP() > highestScore.getHBP())
                {
                    highestScore = temp;
                    LeaderName = new StringBuilder(highestScore.getName());
                }
                //if temp and the highest have the same score then add temp's name to the leader list
                else if (temp.getHBP() == highestScore.getHBP())
                {
                    LeaderName.append(", ").append(temp.getName());
                }
            }
            //checks the away teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempAwayList.size(); i++)
            {
                temp = awayTeam.getStorage(tempAwayList.get(i));
                if(temp == null)
                    continue;
                if (temp.getHBP() == highestScore.getHBP())
                {
                    tempAwayList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //checks the home teams people to see if their score matches with the highest
            //if so removes them from the copy of the team name list
            for (int i = 0; i < tempHomeList.size(); i++)
            {
                temp = homeTeam.getStorage(tempHomeList.get(i));
                if(temp == null)
                    continue;
                if (temp.getHBP() == highestScore.getHBP())
                {
                    tempHomeList.remove(temp.getName());
                    peopleCounter++;
                }
            }
            //prints out the best score and the leader(s) with the best score
            System.out.println(highestScore.getHBP() + "\t" + LeaderName);
            //if there have been more than 3 leaders before 3 places were awarded then ends the loop early
            if (peopleCounter >= 3) return;
        }
    }
}
