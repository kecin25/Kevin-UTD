/*Name: Project 3 - Main
Description: Derives a given equation from a text file, able to handle simple boundless and bounded integrals as well as Cos and Sin
Created by: Kevin Boudreaux
Created on: 3/31/2021
ID: KCB180002
*/
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    { 
        
        //sets up the the input to get the file name
        Scanner input = new Scanner(System.in);
        //String used to hold the file name
        String fileName = input.nextLine();
        //File used to get access to the file
        File file = new File(fileName);
        //fileScanner used to pull data from file
        Scanner fileScanner =null;
        
        //attempts to access the file, if no file is found then throws a catch statement
        try
        {
            fileScanner = new Scanner(file);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not Found");
            input.close();
        }
        
        String Line;
        //grabs the next line from the file
        if(fileScanner != null)
            fileScanner.nextLine();
        
        int numLines=1;
        
        //while loop that will count the number of lines in a file
        if(fileScanner != null)
        {
            while(fileScanner.hasNextLine())
            {
                numLines++;
                fileScanner.nextLine();
            }
        }
        
        //try catch resets the file so the program can access the beginning of the file
        try
        {
            fileScanner = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            input.close();
        }
        
        //Master for loop that will parse through the lines in the file and handle the parsing of data as well as printing the integrals
        for(int i=0; i< numLines;i++)
        {
            //makes a generic binary tree of type payload
            BinTree<Payload> BinaryTree = new BinTree<>();
            int LowerBounds = 0;
            int UpperBounds = 0;
            double LowerBoundsValue=0;
            double UpperBoundsValue=0;
            double BoundsValue =0;

            assert fileScanner != null;
            Line = fileScanner.nextLine(); //grabs the equation from the file
            
            if (Line.charAt(0) == '|') //checks to see if there is bounds for the grabbed equation
            {
                Line = Line.substring(1);
            }
            else  //if there is a bounds then grabs the upper and lower bounds and stores those numbers for future use
            {
                LowerBounds = Integer.parseInt(Line.substring(0, Line.indexOf('|')));
                Line = Line.substring(Line.indexOf('|') + 1); //removes everything up to the and including |
                UpperBounds = Integer.parseInt(Line.substring(0, Line.indexOf(' ')));
                Line = Line.substring(Line.indexOf(' ')); // removes everything up to the first space after the upper bounds limit
            }
            Line = Line.replaceAll(" ", ""); //removes all the spaces in the string so strings will always be uniform
            Line = Line.substring(0, Line.length() - 2); // removes the dx at the end of the string
            int TrigCounter = 0; // trig counter is used to help keep track of trig values
            while (!Line.equals("")) //while loop that runs until the string is empty
            {
                int plusMinusLoco = 0; //location of the first plus or minus
                for (int j = 0; j < Line.length(); j++)
                {
                    if (Line.charAt(j) == '+' || Line.charAt(j) == '-') 
                    {
                        if (j != 0 && Line.charAt(j - 1) == '^') //checks to see if the - is not from an exponent value or the first character in the string
                            continue;
                        else if (j != 0)
                        {
                            plusMinusLoco = j; //sets the location to the value of j
                            break;
                        }
                    }
                }
                String subString; //substring holds the current part of the equation the program is working with
                if (plusMinusLoco != 0) //if the location of the first plus or minus is found
                {
                    subString = Line.substring(0, plusMinusLoco);
                    Line = Line.substring(plusMinusLoco); // removes the part of the line that the substring grabbed
                }
                else //else that means we are on the last part of the Line so the substring becomes the entire line
                {
                    subString = Line;
                    Line = ""; //since this is the last part of the line we set line to nothing
                }
                if (!Line.equals("") && Line.charAt(0) == '+') //checks to see if there is a + at the start of the line and if so removes it
                    Line = Line.substring(1);

                Payload payload = new Payload(); //creates a new payload that will be later put into the binary tree
                int Xloco = subString.indexOf('x'); //finds the location of the x value
                int TrigLoco = subString.indexOf('s'); //since both sin and cos have an s in them finds the location of s if there is one

                if (Xloco > TrigLoco && TrigLoco == -1) //if a trig was not found
                {
                    if (subString.charAt(0) == '-' && subString.charAt(1) == 'x') //checks to see if the coefficient is -1
                    {
                        payload.setCoefficientNum(-1);
                        subString = subString.substring(2); // removes the - and the x
                    }
                    else
                    {
                        if(Xloco!=0) //checks to see if the coefficient is one or not
                            payload.setCoefficientNum(Integer.parseInt(subString.substring(0, Xloco))); //sets the coefficient as the number in front of x
                        else
                           payload.setCoefficientNum(1);
                        subString = subString.substring(Xloco + 1); //removes the number in front of x and x itself
                    }

                    if (subString.length() != 0) //checks to see if the exponent is not 1
                    {
                        subString = subString.substring(1); // removes the ^ at the front of the string
                        payload.setExponent(Integer.parseInt(subString)); //the rest of the string is just numbers that represent the exponent
                    }
                    else //if the string is empty then sets the exponent to 1
                        payload.setExponent(1); 
                }
                else if (Xloco == -1) // if no x found
                {
                    payload.setCoefficientNum(Integer.parseInt(subString));
                }
                else // if a trig value was found
                {
                    if (subString.charAt(0) == '-' && (subString.charAt(1) == 's' || subString.charAt(1) == 'c')) //checks to see if the coefficient is -1
                    {
                        payload.setCoefficientNum(-1);
                        subString = subString.substring(1);
                    }
                    else if(subString.charAt(0) !='c' && subString.charAt(0) !='s') //checks to see if the coefficient is not 1
                    {
                        int Sloco = subString.indexOf('s'); //finds the location of s
                        int Cloco = subString.indexOf('c'); //finds the location of c if there is one
                        if(Cloco != -1) //if no c is found then we know we are dealing with sin
                        {
                            payload.setCoefficientNum(Integer.parseInt(subString.substring(0, Cloco))); //grabs the coefficient up to the c location
                            subString = subString.substring(Cloco);
                        }
                        else //if a c is found then we are dealing with cos
                        {
                            payload.setCoefficientNum(Integer.parseInt(subString.substring(0, Sloco))); //grabs the coefficient up to the s location
                            subString = subString.substring(Sloco);
                        }
                    }
                    else //if the coefficient is one then sets it to one
                        payload.setCoefficientNum(1);
                    if (subString.charAt(0) == 'c') //checks to see if the trig is cos
                        payload.setTrigID("cos");
                    else //if not cos then it is sin
                        payload.setTrigID("sin");
                    subString = subString.substring(3); //removes the trig in the substring

                    if (subString.charAt(0) == 'x') //checks to see if there is no inner coefficient for the x
                        payload.setInnerCoefficient(1);
                    else if (subString.charAt(0) == '-' && subString.charAt(1) == 'x') //checks to see if that inner coefficient is negative
                        payload.setInnerCoefficient(-1);
                    else //there is a inner coefficient that is not -1
                        payload.setInnerCoefficient(Integer.parseInt(subString.substring(0, subString.indexOf('x'))));
                    payload.setExponent(-500 - TrigCounter); //keeps the order that the trig parts of the equation were found in
                    TrigCounter++;
                }
                if (payload.getTrigID().equals("")) //derives the exponent for non trig parts of the equation
                {
                    payload.setExponent(payload.getExponent() + 1); //adds one to the exponent
                    payload.setCoefficientDen(payload.getExponent()); //sets the denominator to the value of the exponent

                }
                else //derives the trig parts of the equation
                {
                    if (payload.getTrigID().equals("cos")) //flips the cos into sin
                        payload.setTrigID("sin");
                    else //flips the sin into cos and multiplies the coefficient by -1
                    {
                        payload.setCoefficientNum(payload.getCoefficientNum() * -1);
                        payload.setTrigID("cos");
                    }

                    payload.setCoefficientDen(payload.getInnerCoefficient()); //sets the denominator of the outer coefficient to the inner coefficient
                }
                boolean simplified;
                do //do while loop that will simplify the fraction that was just made
                {
                    simplified = false;

                    if (payload.getCoefficientDen() % 2 == 0 && payload.getCoefficientNum() % 2 == 0) //if both the top and bottom of the fraction are divisible by 2
                    {
                        simplified = true;
                        payload.setCoefficientDen(payload.getCoefficientDen() / 2);
                        payload.setCoefficientNum(payload.getCoefficientNum() / 2);
                    }
                    if (payload.getCoefficientDen() % 3 == 0 && payload.getCoefficientNum() % 3 == 0) //if both the top and bottom of the fraction are divisible by 3  
                    {
                        simplified = true;
                        payload.setCoefficientDen(payload.getCoefficientDen() / 3);
                        payload.setCoefficientNum(payload.getCoefficientNum() / 3);
                    }
                    if (payload.getCoefficientDen() % 5 == 0 && payload.getCoefficientNum() % 5 == 0) //if both the top and bottom of the fraction are divisible by 5
                    {
                        simplified = true;
                        payload.setCoefficientDen(payload.getCoefficientDen() / 5);
                        payload.setCoefficientNum(payload.getCoefficientNum() / 5);
                    }
                    if (payload.getCoefficientDen() % 7 == 0 && payload.getCoefficientNum() % 7 == 0) //if both the top and bottom of the fraction are divisible by 7
                    {
                        simplified = true;
                        payload.setCoefficientDen(payload.getCoefficientDen() / 7);
                        payload.setCoefficientNum(payload.getCoefficientNum() / 7);
                    }
                    if(payload.getCoefficientDen() % 11 == 0 && payload.getCoefficientNum() % 11 == 0) //if both the top and bottom of the fraction are divisible by 11
                    {
                        simplified = true;
                        payload.setCoefficientDen(payload.getCoefficientDen() / 11);
                        payload.setCoefficientNum(payload.getCoefficientNum() / 11);
                    }
                } while (simplified);


                if (BinaryTree.Search(payload) != null) //checks to see if there is a match of the current payload
                {
                    Payload temp = BinaryTree.Search(payload).getData(); //gets a temporary access to the payload in the binary tree
                    //if else statement below used to combine the fractions of the two payloads
                    if (temp.getCoefficientDen() == payload.getCoefficientDen()) //checks to see if the denominators match
                    {
                        temp.setCoefficientNum(temp.getCoefficientNum() + payload.getCoefficientNum());
                        if(temp.getCoefficientNum()==0) 
                            BinaryTree.Delete(BinaryTree.Search(payload));
                    }
                    else //used for when the denominators does not match
                    {
                        temp.setCoefficientNum((temp.getCoefficientNum() * payload.getCoefficientDen()) + (temp.getCoefficientDen() * payload.getCoefficientNum()));
                        temp.setCoefficientDen(temp.getCoefficientDen() * payload.getCoefficientDen());
                    }
                    //same do while loop as above used to simplify the new fraction
                    do
                    {
                        simplified = false;

                        if (temp.getCoefficientDen() % 2 == 0 && temp.getCoefficientNum() % 2 == 0) //if both the top and bottom of the fraction are divisible by 2
                        {
                            simplified = true;
                            temp.setCoefficientDen(temp.getCoefficientDen() / 2);
                            temp.setCoefficientNum(temp.getCoefficientNum() / 2);
                        }
                        if (temp.getCoefficientDen() % 3 == 0 && temp.getCoefficientNum() % 3 == 0) //if both the top and bottom of the fraction are divisible by 3
                        {
                            simplified = true;
                            temp.setCoefficientDen(temp.getCoefficientDen() / 3);
                            temp.setCoefficientNum(temp.getCoefficientNum() / 3);
                        }
                        if (temp.getCoefficientDen() % 5 == 0 && temp.getCoefficientNum() % 5 == 0) //if both the top and bottom of the fraction are divisible by 5
                        {
                            simplified = true;
                            temp.setCoefficientDen(temp.getCoefficientDen() / 5);
                            temp.setCoefficientNum(temp.getCoefficientNum() / 5);
                        }
                        if (temp.getCoefficientDen() % 7 == 0 && temp.getCoefficientNum() % 7 == 0) //if both the top and bottom of the fraction are divisible by 7
                        {
                            simplified = true;
                            temp.setCoefficientDen(temp.getCoefficientDen() / 7);
                            temp.setCoefficientNum(temp.getCoefficientNum() / 7);
                        }
                        if(payload.getCoefficientDen() % 11 == 0 && payload.getCoefficientNum() % 11 == 0) //if both the top and bottom of the fraction are divisible by 11
                        {
                            simplified = true;
                            payload.setCoefficientDen(payload.getCoefficientDen() / 11);
                            payload.setCoefficientNum(payload.getCoefficientNum() / 11);
                        }
                    } while (simplified);
                }
                else //if there is no match in the binary tree then we insert the new payload into the binary tree
                {
                    BinaryTree.insert(payload);
                }
                if(UpperBounds !=0 || LowerBounds != 0) //if there are values for the bounds from earlier
                {
                    UpperBoundsValue += payload.toNum(UpperBounds); //finds the upper bounds limit
                    LowerBoundsValue += payload.toNum(LowerBounds); //finds the lower bounds limit
                    BoundsValue= UpperBoundsValue-LowerBoundsValue; //finds the difference between the upper and lower bounds
                }
            }
            
            
            String equation = BinaryTree.Print(); //gets the new equation from the binary tree

            if(equation.length() != 0) //if the string is not empty
            {
                while (equation.charAt(0) == ' ' || equation.charAt(0) == '+') //removes spaces and +s at the front of the string
                    equation = equation.substring(1);
                if (equation.charAt(0) == '-' && equation.charAt(1) == ' ') //removes the first  - if the first part of the equation is negative and not a fraction
                    equation = "-" + equation.substring(2);
                if (equation.charAt(0) == '-' && equation.charAt(1) == '(') //removes the first - if the first part of the equation is negative and a fraction
                    equation = "(-" + equation.substring(2);
            }
            
            if (UpperBounds == 0 && LowerBounds == 0) //if there are no bounds then prints the equation with + C
            {
                if (equation.equals(""))
                    equation = "0";
                equation = equation + " + C";
            }
            else //if there are bounds then prints the equation as well as the value of the given bounds under the curve
                equation = equation +", " +LowerBounds + "|" + UpperBounds + " = " + String.format("%.3f",BoundsValue);
            System.out.println(equation);

        }
        //closes the two scanners used for the program
        input.close();
        fileScanner.close();
    }

}