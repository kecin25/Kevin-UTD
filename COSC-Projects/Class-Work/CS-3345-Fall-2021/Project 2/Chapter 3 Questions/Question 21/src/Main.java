//Name: Chapter 3, Question 21
//Question solved: "Write a program to check for balancing symbols in the following languages:
//    a. Pascal (begin/end, (), [], {}).
//    b. Java (/**/, (), [], {}).
//    c. Explain how to print out an error message that is likely to reflect the probable cause.
//Made by: Kevin Boudreaux
//ID: KCB 180002
//Made on: 10/20/2021


import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        //input is used to get input from the user
        Scanner input = new Scanner(System.in);

        System.out.println("is the language Pascal or Java?");

        String language = input.nextLine();

        //lan is used to see if a valid language was entered
        int lan = 0;
        if(language.equals("Pascal") || language.equals("pascal"))
            lan = 1;
        else if(language.equals("Java") || language.equals("java"))
            lan = 2;

        //if not a valid language then printout an error message
        if(lan == 0)
            System.out.println("Invalid input, please enter Pascal or Java");

        //file is used to get access to a given file
        File file = getFile();
        Scanner fileScanner = null;
        //try catch to see if given file is a valid file
        try
        {
            fileScanner = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        //numLines is used to count number of lines
        int numLines = 0;
        if(fileScanner !=null)
        {
            while (fileScanner.hasNextLine())
            {
                fileScanner.nextLine();
                numLines++;

            }
        }
        else
        {
            System.out.println("The file given is empty.");
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
        if( lan ==1)
            PascalCheck(fileScanner,numLines);
        else
            JavaCheck(fileScanner,numLines);

    }
    public static File getFile()
    {

        Scanner input = new Scanner(System.in);
        System.out.println("What is the name of the file?");
        String fileName = input.nextLine();
        System.out.println("File is: " + fileName);

        return new File(fileName);
    }
    public static void PascalCheck(Scanner fileScanner, int numLines)
    {

        //List is a stack used to keep track of the characters that we are looking for
        LinkedList List = new LinkedList();
        int lineNum = 1;
        String Line = null;
        if (fileScanner != null)
        {
            Line = fileScanner.nextLine();
        }

        //first for loop is used for each line in the file
        for(int j=0;j<numLines;j++)
        {
            //second for loop is used for the length of the current line
            for (int i = 0; i < Line.length(); i++)
            {
                //first if statement checks to see if what is currently being looked at is 'begin'
                if (!((i + 4) > Line.length()) && (Line.charAt(i) == 'b' && Line.charAt(i + 1) == 'e' && Line.charAt(i + 2) == 'g' && Line.charAt(i + 3) == 'i' && Line.charAt(i + 4) == 'n'))
                {
                    //if it is add it to the stack and increase the counter by 4 since we already looked at the next 4 spots
                    List.push(new Node("begin"));
                    i += 4;
                }
                //checks to see if current character is a '(' and if so pushes it to the stack
                else if (Line.charAt(i) == '(')
                {
                    List.push(new Node("(", lineNum));
                }
                //checks to see if current character is a '[' and if so pushes it to the stack
                else if (Line.charAt(i) == '[')
                {
                    List.push(new Node("[", lineNum));
                }
                //checks to see if current character is a '{' and if so pushes it to the stack
                else if (Line.charAt(i) == '{')
                {
                    List.push(new Node("{", lineNum));
                }
                //checks to see if current thing being looked at is an 'end'
                else if (!((i + 2) > Line.length()) &&
                        (Line.charAt(i) == 'e' && Line.charAt(i + 1) == 'n' && Line.charAt(i + 2) == 'd'))
                {
                    //if it is and the top of the stack is 'begin' then pop 'begin'
                    if (List.getHead().getInfo().equals("begin"))
                    {
                        List.pop();
                        i += 2;
                    }
                    else
                    {
                        //if it is not print out an error message
                        System.out.println("Error on line: " + List.getHead().getLineNum());
                        System.out.println("You need to have a 'begin' for this 'end'.");
                        break;
                    }
                }
                //checks to see if the current character is a ')'
                else if (Line.charAt(i) == ')')
                {
                    //if it is then checks to see if the top of the stack is a '(' and if it is pop it but if it is not then print an error message
                    if (List.getHead().getInfo().equals("("))
                    {
                        List.pop();
                    }
                    else
                    {
                        System.out.println("Error on line: " + List.getHead().getLineNum());
                        System.out.println("You need to have a '(' for this ')'.");
                        break;
                    }
                }
                //if it is then checks to see if the top of the stack is a ']' and if it is pop it but if it is not then print an error message
                else if (Line.charAt(i) == ']')
                {
                    if (List.getHead().getInfo().equals("["))
                    {
                        List.pop();
                    }
                    else
                    {
                        System.out.println("Error on line: " + List.getHead().getLineNum());
                        System.out.println("You need to have a '[' for this ']'.");
                        break;
                    }
                }
                //if it is then checks to see if the top of the stack is a '}' and if it is pop it but if it is not then print an error message
                else if (Line.charAt(i) == '}')
                {
                    if (List.getHead().getInfo().equals("{"))
                    {
                        List.pop();
                    }
                    else
                    {
                        System.out.println("Error on line: " + List.getHead().getLineNum());
                        System.out.println("You need to have a '{' for this '}'.");
                        break;
                    }
                }
            }
            //increments lineNum by one, used to help print out error messages that can use lineNum to help user find where the error is
            lineNum++;
            if (fileScanner.hasNextLine())
            {
                Line = fileScanner.nextLine();
            }
            else
                Line = "";
        }

    }
    public static void JavaCheck(Scanner fileScanner, int numLines)
    {
        LinkedList List = new LinkedList();
        int lineNum = 1;
        String Line = null;
        if (fileScanner != null)
        {
            Line = fileScanner.nextLine();
        }


        for(int j=0;j<numLines;j++)
        {
            for (int i = 0; i < Line.length(); i++)
            {
                //if the current object is a '/*' then pushes it to the top of the stack
                if (!((i + 1) > Line.length()) && (Line.charAt(i) == '/' && Line.charAt(i + 1) == '*'))
                {
                    List.push(new Node("/*"));
                    i += 1;
                }
                //if the current object is a '(' then pushes it to the top of the stack
                else if (Line.charAt(i) == '(')
                {
                    List.push(new Node("(", lineNum));
                }
                //if the current object is a '[' then pushes it to the top of the stack
                else if (Line.charAt(i) == '[')
                {
                    List.push(new Node("[", lineNum));
                }
                //if the current object is a '{' then pushes it to the top of the stack
                else if (Line.charAt(i) == '{')
                {
                    List.push(new Node("{", lineNum));
                }

                //checks to see if current object at the top of the stack is a '*/' and if it is then pops it off and if not it will print an error message
                else if (!((i + 1) > Line.length()) &&
                        (Line.charAt(i) == '*' && Line.charAt(i + 1) == '/'))
                {
                    if (List.getHead().getInfo().equals("/*"))
                    {
                        List.pop();
                        i += 1;
                    }
                    else
                    {
                        System.out.println("Error on line: " + List.getHead().getLineNum());
                        System.out.println("You need to have a '/*' for this '*/'.");
                        break;
                    }
                }
                //checks to see if current object at the top of the stack is a '(' and if it is then pops it off and if not it will print an error message
                else if (Line.charAt(i) == ')')
                {
                    if (List.getHead().getInfo().equals("("))
                    {
                        List.pop();
                    }
                    else
                    {
                        System.out.println("Error on line: " + List.getHead().getLineNum());
                        System.out.println("You need to have a '(' for this ')'.");
                        break;
                    }
                }
                //if it is then checks to see if the top of the stack is a ']' and if it is pop it but if it is not then print an error message
                else if (Line.charAt(i) == ']')
                {
                    if (List.getHead().getInfo().equals("["))
                    {
                        List.pop();
                    }
                    else
                    {
                        System.out.println("Error on line: " + List.getHead().getLineNum());
                        System.out.println("You need to have a '[' for this ']'.");
                        break;
                    }
                }
                //if it is then checks to see if the top of the stack is a '}' and if it is pop it but if it is not then print an error message
                else if (Line.charAt(i) == '}')
                {
                    if (List.getHead().getInfo().equals("{"))
                    {
                        List.pop();
                    }
                    else
                    {
                        System.out.println("Error on line: " + List.getHead().getLineNum());
                        System.out.println("You need to have a '{' for this '}'.");
                        break;
                    }
                }
            }
            //increments lineNum by one, used to help print out error messages that can use lineNum to help user find where the error is
            lineNum++;
            if (fileScanner.hasNextLine())
            {
                Line = fileScanner.nextLine();
            }
            else
                Line = "";
        }
    }
}
