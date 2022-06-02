/*
Made by: Kevin Boudreaux
ID: KCB180002
Made on: 11/29/2021
 */
import java.util.Arrays;
public class Main
{
    public static void main(String[] args)
    {
        //size used to help easily resize size of the matrix,k only would have to edit the switch statement
        final int  SIZE  = 7;
        
        
        
        /*
                            Part A
        */   
        
        
        int[][] matrix = new int[SIZE][SIZE];
        //A through G in figure 9.82 represented as 0 through 6 in the matrix
        matrix[0][1] = 5;
        matrix[0][2] = 3;
        matrix[1][2] = 2;
        matrix[1][4] = 3;
        matrix[1][6] = 1;
        matrix[2][3] = 7;
        matrix[2][4] = 7;
        matrix[3][0] = 2;
        matrix[3][5] = 6;
        matrix[4][3] = 2;
        matrix[4][5] = 1;
        matrix[6][4] = 1;

        boolean[] known = new boolean[SIZE];
        int[] distance = new int[SIZE];
        Arrays.fill(distance, 50000);
        //starting at A
        distance[0] = 0;
        int[] previous = new int[SIZE];
        previous[0] = -1;
        boolean allKnown = false;
        while(!allKnown)
        {
            int position = 0;
            int numKnown = 0;
            //looking for smallest
            for(int i = 0; i<SIZE; i++)
            {
                if(distance[position] > distance[i] && !known[i] || known[position])
                    position = i;
            }
            known[position] = true;
            //checking connections to other vertexes
            for(int i = 0; i < SIZE; i++)
            {
                if(matrix[position][i] != 0)
                {
                    if(distance[i] > (distance[position] + matrix[position][i]))
                    {
                        distance[i] = distance[position] + matrix[position][i];
                        previous[i] = position;
                    }
                }
            }
            //checking to see if all have been reached
            for(int i = 0; i<SIZE; i++)
            {
                if(known[i])
                    numKnown++;
                else
                    break;
            }
            if(numKnown == SIZE)
                allKnown = true;

            
        }
        //printing out table for visual aid
        System.out.println("Part A");
        System.out.println("Table:");
        System.out.println("Vectors: A, B, C, D, E, F, G");
        System.out.print("known: ");
        for(int i= 0; i< SIZE; i++)
        {
            if(known[i])
                System.out.print("T ");
            else
                System.out.print("F ");
        }
        System.out.print("\nDistance: ");
        for(int i = 0; i < SIZE; i++)
            System.out.print(distance[i] + " ");
        System.out.print("\nPrevious: ");
        for(int i = 0; i < SIZE; i++)
        {
            switch (previous[i])
            {
                case -1 -> System.out.print("NA ");
                case 0 -> System.out.print("A ");
                case 1 -> System.out.print("B ");
                case 2 -> System.out.print("C ");
                case 3 -> System.out.print("D ");
                case 4 -> System.out.print("E ");
                case 5 -> System.out.print("F ");
                case 6 -> System.out.print("G ");
            }
        }
        
        /*
                        Part B
        */



        matrix = new int[SIZE][SIZE];
        //A through G in figure 9.82 represented as 0 through 6 in the matrix
        matrix[0][1] = 1;
        matrix[0][2] = 1;
        matrix[1][2] = 1;
        matrix[1][4] = 1;
        matrix[1][6] = 1;
        matrix[2][3] = 1;
        matrix[2][4] = 1;
        matrix[3][0] = 1;
        matrix[3][5] = 1;
        matrix[4][3] = 1;
        matrix[4][5] = 1;
        matrix[6][4] = 1;

        known = new boolean[SIZE];
        distance = new int[SIZE];
        Arrays.fill(distance, 50000);
        //starting at B
        distance[1] = 0;
        previous = new int[SIZE];
        previous[1] = -1;
        allKnown = false;
        while(!allKnown)
        {
            int position = 0;
            int numKnown = 0;
            //looking for smallest
            for(int i = 0; i<SIZE; i++)
            {
                if(distance[position] > distance[i] && !known[i] || known[position])
                    position = i;
            }
            known[position] = true;
            //checking connections to other vertexes
            for(int i = 0; i < SIZE; i++)
            {
                if(matrix[position][i] != 0)
                {
                    if(distance[i] > (distance[position] + matrix[position][i]))
                    {
                        distance[i] = distance[position] + matrix[position][i];
                        previous[i] = position;
                    }
                }
            }
            //checking to see if all have been reached
            for(int i = 0; i<SIZE; i++)
            {
                if(known[i])
                    numKnown++;
                else
                    break;
            }
            if(numKnown == SIZE)
                allKnown = true;


        }
        //printing out table for visual aid
        System.out.println("\n\nPart B");
        System.out.println("Table:");
        System.out.println("Vectors: A, B, C, D, E, F, G");
        System.out.print("known: ");
        for(int i= 0; i< SIZE; i++)
        {
            if(known[i])
                System.out.print("T ");
            else
                System.out.print("F ");
        }
        System.out.print("\nDistance: ");
        for(int i = 0; i < SIZE; i++)
            System.out.print(distance[i] + " ");
        System.out.print("\nPrevious: ");
        for(int i = 0; i < SIZE; i++)
        {
            switch (previous[i])
            {
                case -1 -> System.out.print("NA ");
                case 0 -> System.out.print("A ");
                case 1 -> System.out.print("B ");
                case 2 -> System.out.print("C ");
                case 3 -> System.out.print("D ");
                case 4 -> System.out.print("E ");
                case 5 -> System.out.print("F ");
                case 6 -> System.out.print("G ");
            }
        }
    }
}
