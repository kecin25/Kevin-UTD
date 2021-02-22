/*Name: Project 1
Description: Basic AI of ants and beetles, beetles need to eat ants to survive while ants try to run from beetles
Created by: Kevin Boudreaux
Created on: 2/4/2021
ID: KCB180002
*/
import java.io.FileNotFoundException;
import java.io.File;
import java.sql.SQLOutput;
import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        //makes the 10x10 grid
        Creature[][] array = new Creature[10][10];
        Character[][] chArray = new Character[10][10];
        //gets the file name and opens it
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine();
        File file = new File(fileName);
        
    //    System.out.println("What character do you want to represent the ants?");
        char ant = input.next().charAt(0);
    //    System.out.println("What character do you want to represent the beetles?");
        char beetle = input.next().charAt(0);
    //    System.out.println("How many turns do you want to run the simulation?");
        int turns = input.nextInt();
        
    //  Creates a scanner to parse through the file
        Scanner fileScanner = null;
        try
        {
            fileScanner = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        //populates the 10x10 grid with the beetles and ants
        int rowCreate = 0;
        if (fileScanner != null)
        {
            while (fileScanner.hasNextLine())
            {
                String line = fileScanner.nextLine();
                
                for (int col = 0; col < 10; col++)
                {
                    if (line.charAt(col) == 'a')
                    {
                        array[col][rowCreate] = new Ant();
                        array[col][rowCreate].setColLoco(col);
                        array[col][rowCreate].setRowLoco(rowCreate);
                        array[col][rowCreate].setCharacter(ant);
                        
                        chArray[col][rowCreate]=ant;
                    }
                    else if (line.charAt(col) == 'B')
                    {
                        array[col][rowCreate] = new Beetle();
                        array[col][rowCreate].setColLoco(col);
                        array[col][rowCreate].setRowLoco(rowCreate);
                        array[col][rowCreate].setCharacter(beetle);
                        
                        chArray[col][rowCreate]=beetle;
                    }
                }
                rowCreate++;
            }
        }
        
    //  Starts for loop that goes number of turns    
        for(int i=1; i<=turns;i++)
        {
            //checking Beetle Movement
            for(int col=0;col<10;col++)
            {
                for(int row=0;row<10;row++)
                {
                    //if statement checks to see if there is a beetle and if it has not moved
                    if(array[col][row] instanceof Beetle && !array[col][row].getHasMoved())
                    {
                        //calls the direction for which way the beetle should move
                        char direction = array[col][row].movement(chArray, ant, beetle);
                        
                        //switch statement for each direction as well as checking to see if the space is occupied by an ant, beetle, or nothing.
                        //does not move if occupied by a beetle
                        switch(direction)
                        {
                            case 'N':
                                if(row-1 < 0 || array[col][row-1] instanceof Beetle || array[col][row].getHasMoved())
                                    continue;
                                else if(array[col][row-1] instanceof Ant)
                                {
                                    ((Beetle) array[col][row]).antEaten();
                                    array[col][row-1]=array[col][row];
                                    array[col][row-1].setRowLoco( array[col][row].getRowLoco()-1);
                                    array[col][row-1].setHasMoved();
                                    array[col][row]=null;
                                    
                                    copy(chArray,array);
                                }
                                else
                                {
                                    array[col][row-1] = array[col][row];
                                    array[col][row-1].setRowLoco(array[col][row].getRowLoco()-1);
                                    array[col][row-1].setHasMoved();
                                    array[col][row]=null;

                                    copy(chArray,array);
                                }
                                break;
                            case 'E':
                                if(col+1 >= 10 || array[col+1][row] instanceof Beetle || array[col][row].getHasMoved())
                                    continue;
                                else if(array[col+1][row] instanceof Ant)
                                {
                                    ((Beetle) array[col][row]).antEaten();
                                    array[col+1][row]=array[col][row];
                                    array[col+1][row].setColLoco( array[col][row].getColLoco()+1);
                                    array[col+1][row].setHasMoved();
                                    array[col][row]=null;

                                    copy(chArray,array);
                                }
                                else
                                {
                                    array[col+1][row] = array[col][row];
                                    array[col+1][row].setColLoco(array[col][row].getColLoco()+1);
                                    array[col+1][row].setHasMoved();
                                    array[col][row]=null;

                                    copy(chArray,array);
                                }
                                break;
                            case 'S':
                                if(row+1 >= 10 || array[col][row+1] instanceof Beetle || array[col][row].getHasMoved())
                                    continue;
                                else if(array[col][row+1] instanceof Ant)
                                {
                                    ((Beetle) array[col][row]).antEaten();
                                    array[col][row+1]=array[col][row];
                                    array[col][row+1].setRowLoco(array[col][row].getRowLoco()+1);
                                    array[col][row+1].setHasMoved();
                                    array[col][row]=null;

                                    copy(chArray,array);
                                }
                                else
                                {
                                    array[col][row+1] = array[col][row];
                                    array[col][row+1].setRowLoco(array[col][row].getRowLoco()+1);
                                    array[col][row+1].setHasMoved();
                                    array[col][row]=null;

                                    copy(chArray,array);
                                }
                                break;
                            case 'W':
                                if(col-1 < 0 || array[col-1][row] instanceof Beetle || array[col][row].getHasMoved())
                                    continue;
                                else if(array[col-1][row] instanceof Ant)
                                {
                                    ((Beetle) array[col][row]).antEaten();
                                    array[col-1][row]=array[col][row];
                                    array[col-1][row].setColLoco(array[col][row].getColLoco()-1);
                                    array[col-1][row].setHasMoved();
                                    array[col][row]=null;

                                    copy(chArray,array);
                                }
                                else
                                {
                                    array[col-1][row] = array[col][row];
                                    array[col-1][row].setColLoco(array[col][row].getColLoco()-1);
                                    array[col-1][row].setHasMoved();
                                    array[col][row]=null;

                                    copy(chArray,array);
                                }
                                break;
                            default:
                                break;
                        }
                        
                    }
                }
            }
            
            //checking Ant movement
            for(int col=0;col<10;col++)
            {
                for(int row=0;row<10;row++)
                {
                    //checks to see if current spot is an ant
                    if(array[col][row] instanceof Ant)
                    {
                        //gets the direction the ant should move in
                        char direction = array[col][row].movement(chArray,ant, beetle);

                        //switch statement for each direction as well as checking to see if the space is occupied or not, if occupied does not move
                        switch(direction)
                        {
                            case 'N':
                                if((row-1) < 0 || array[col][row-1] instanceof Ant || array[col][row-1] instanceof Beetle || array[col][row].getHasMoved())
                                    continue;
                                else
                                {
                                    array[col][row-1] = array[col][row];
                                    array[col][row-1].setRowLoco(array[col][row].getRowLoco()-1);
                                    array[col][row-1].setColLoco(array[col][row].getColLoco());
                                    array[col][row-1].setHasMoved();
                                    array[col][row]=null;

                                    copy(chArray,array);
                                }
                                break;
                            case 'E':
                                if(col+1 >= 10 || array[col+1][row] instanceof Ant || array[col+1][row] instanceof Beetle || array[col][row].getHasMoved())
                                    continue;
                                else
                                {
                                    array[col+1][row] = array[col][row];
                                    array[col+1][row].setRowLoco(array[col][row].getRowLoco());
                                    array[col+1][row].setColLoco(array[col][row].getColLoco()+1);
                                    array[col+1][row].setHasMoved();
                                    array[col][row]=null;

                                    copy(chArray,array);
                                }
                                break;
                            case 'S':
                                if(row+1 >= 10 || array[col][row+1] instanceof Ant || array[col][row+1] instanceof Beetle || array[col][row].getHasMoved())
                                    continue;
                                else
                                {
                                    array[col][row+1] = array[col][row];
                                    array[col][row+1].setRowLoco(array[col][row].getRowLoco()+1);
                                    array[col][row+1].setColLoco(array[col][row].getColLoco());
                                    array[col][row+1].setHasMoved();
                                    array[col][row]=null;

                                    copy(chArray,array);
                                }
                                break;
                            case 'W':
                                if(col-1 < 0 || array[col-1][row] instanceof Ant || array[col-1][row] instanceof Beetle || array[col][row].getHasMoved())
                                    continue;
                                else
                                {
                                    array[col-1][row] = array[col][row];
                                    array[col-1][row].setRowLoco(array[col][row].getRowLoco());
                                    array[col-1][row].setColLoco(array[col][row].getColLoco()-1);
                                    array[col-1][row].setHasMoved();
                                    array[col][row]=null;

                                    copy(chArray,array);
                                }
                                break;
                            default:
                                break;
                        }
                        

                    }
                }
            }

            //checking for Beetle Starving
            for(int col=0;col<10;col++)
            {
                for(int row=0;row<10;row++)
                {
                    //if current spot is a beetle, checks to see if it starves and if so deletes the beetle
                    if(array[col][row] instanceof Beetle)
                        if(((Beetle) array[col][row]).starveCheck())
                            array[col][row] = null;
                }
            }
            copy(chArray,array);

            //checking for ant breeding
            for(int col=0;col<10;col++)
            {
                for(int row=0;row<10;row++)
                {
                    //checks to see if current spot is an ant and checks to see if it is time for the ant to breed
                    if(array[col][row] instanceof Ant)
                    {
                        if(array[col][row].breedChecker())
                        {
                            //checks north, east, south, and west to see if there is a free spot to for a new ant
                            if(row-1 >= 0)
                            {
                                if (array[col][row - 1] == null)
                                {
                                    array[col][row - 1] = new Ant();
                                    array[col][row - 1].setHasMoved();
                                    array[col][row - 1].setRowLoco(array[col][row].getRowLoco() - 1);
                                    array[col][row - 1].setColLoco(array[col][row].getColLoco());
                                    array[col][row - 1].setCharacter(ant);
                                    array[col][row - 1].setPastCheck();

                                    copy(chArray,array);
                                    continue;
                                }
                            }
                            if(col+1 < 10)
                            {
                                if (array[col + 1][row] == null)
                                {
                                    array[col + 1][row] = new Ant();
                                    array[col + 1][row].resetBreedCounter();
                                    array[col + 1][row].setHasMoved();
                                    array[col + 1][row].setColLoco(array[col][row].getColLoco() + 1);
                                    array[col + 1][row].setRowLoco(array[col][row].getRowLoco());
                                    array[col + 1][row].setCharacter(ant);
                                    array[col + 1][row].setPastCheck();

                                    copy(chArray,array);
                                    continue;
                                }
                            }
                            if(row+1 < 10)
                            {
                                if (array[col][row + 1] == null)
                                {
                                    array[col][row + 1] = new Ant();
                                    array[col][row + 1].resetBreedCounter();
                                    array[col][row + 1].setHasMoved();
                                    array[col][row + 1].setRowLoco(array[col][row].getRowLoco() + 1);
                                    array[col][row + 1].setColLoco(array[col][row].getColLoco());
                                    array[col][row + 1].setCharacter(ant);
                                    array[col][row + 1].setPastCheck();

                                    copy(chArray,array);
                                    continue;
                                }
                            }
                            if(col-1 >= 0)
                            {
                                if (array[col - 1][row] == null)
                                {
                                    array[col - 1][row] = new Ant();
                                    array[col - 1][row].setHasMoved();
                                    array[col - 1][row].setColLoco(array[col][row].getColLoco() - 1);
                                    array[col - 1][row].setRowLoco(array[col][row].getRowLoco());
                                    array[col - 1][row].setCharacter(ant);
                                    array[col - 1][row].setPastCheck();

                                    copy(chArray,array);
                                    
                                }
                            }
                        }
                    }
                }
            }

            //checking for Beetle breeding
            for(int col=0;col<10;col++)
            {
                for(int row=0;row<10;row++)
                {
                    //checks to see if current spot is a beetle
                    if(array[col][row] instanceof Beetle)
                    {
                        if(array[col][row].breedChecker())
                        {
                            //checks north, east, south, and west to see if there is a free spot to put the beetle
                            if(row-1 >= 0)
                            {
                                if (array[col][row - 1] == null)
                                {
                                    array[col][row - 1] = new Beetle();
                                    array[col][row - 1].resetBreedCounter();
                                    array[col][row - 1].setHasMoved();
                                    array[col][row - 1].setRowLoco(array[col][row].getRowLoco() - 1);
                                    array[col][row - 1].setColLoco(array[col][row].getColLoco());
                                    array[col][row - 1].setCharacter(beetle);
                                    array[col][row - 1].setPastCheck();

                                    copy(chArray,array);
                                    continue;
                                }
                            }
                            if(col+1 < 10)
                            {
                                if (array[col + 1][row] == null)
                                {
                                    array[col + 1][row] = new Beetle();
                                    array[col + 1][row].resetBreedCounter();
                                    array[col + 1][row].setHasMoved();
                                    array[col + 1][row].setColLoco(array[col][row].getColLoco() + 1);
                                    array[col + 1][row].setRowLoco(array[col][row].getRowLoco());
                                    array[col + 1][row].setCharacter(beetle);
                                    array[col + 1][row].setPastCheck();

                                    copy(chArray,array);
                                    continue;
                                }
                            }
                            if(row+1 < 10)
                            {
                                if (array[col][row + 1] == null)
                                {
                                    array[col][row + 1] = new Beetle();
                                    array[col][row + 1].resetBreedCounter();
                                    array[col][row + 1].setHasMoved();
                                    array[col][row + 1].setRowLoco(array[col][row].getRowLoco() + 1);
                                    array[col][row + 1].setColLoco(array[col][row].getColLoco());
                                    array[col][row + 1].setCharacter(beetle);
                                    array[col][row + 1].setPastCheck();

                                    copy(chArray,array);
                                    continue;
                                }
                            }
                            if(col-1 >= 0)
                            {
                                if (array[col - 1][row] == null)
                                {
                                    array[col - 1][row] = new Beetle();
                                    array[col - 1][row].resetBreedCounter();
                                    array[col - 1][row].setHasMoved();
                                    array[col - 1][row].setColLoco(array[col][row].getColLoco() - 1);
                                    array[col - 1][row].setRowLoco(array[col][row].getRowLoco());
                                    array[col - 1][row].setCharacter(beetle);
                                    array[col - 1][row].setPastCheck();

                                    copy(chArray,array);
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
            
            //for loop that resets two booleans used for checks
            for(int col=0;col<10;col++)
            {
                for(int row=0;row<10;row++)
                {
                    if(array[col][row] instanceof Beetle || array[col][row] instanceof Ant)
                    {
                        array[col][row].resetPastCheck();
                        array[col][row].resetHasMoved();
                    }
                }
            }
            System.out.println("TURN "+i);
            output(array);
        }
        

    }
    
    //function that prints out the Creature grid
    public static void output(Creature[][] array)
    {
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {
                if (array[j][i] != null)
                    System.out.print(array[j][i].getCharacter());
                else
                {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    //function that copies the creature grid into a character grid
    public static void copy(Character[][] chArray,Creature[][] array)
    {
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {
                if (array[j][i] != null)
                    chArray[j][i]=array[j][i].getCharacter();
                else
                {
                    chArray[j][i]=' ';
                }
            }
        }
    }
}