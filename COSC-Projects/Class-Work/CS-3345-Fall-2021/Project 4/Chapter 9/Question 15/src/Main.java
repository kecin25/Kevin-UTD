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
        final int  SIZE  = 10;
        
        
        
        /*
                            Prim's Algorithm 
        */


        int[][] matrix = new int[SIZE][SIZE];
        //A through J in figure 9.84 represented as 0 through 9 in the matrix
        matrix[0][1] = 3;
        matrix[0][3] = 4;
        matrix[0][4] = 4;
        matrix[1][0] = 3;
        matrix[1][2] = 10;
        matrix[1][4] = 2;
        matrix[1][5] = 3;
        matrix[2][1] = 10;
        matrix[2][5] = 6;
        matrix[2][6] = 1;
        matrix[3][0] = 4;
        matrix[3][4] = 5;
        matrix[3][7] = 6;
        matrix[4][0] = 4;
        matrix[4][1] = 2;
        matrix[4][3] = 5;
        matrix[4][5] = 11;
        matrix[4][7] = 2;
        matrix[4][8] = 1;
        matrix[5][1] = 3;
        matrix[5][2] = 6;
        matrix[5][4] = 11;
        matrix[5][6] = 2;
        matrix[5][8] = 3;
        matrix[5][9] = 11;
        matrix[6][2] = 1;
        matrix[6][5] = 2;
        matrix[6][9] = 8;
        matrix[7][3] = 6;
        matrix[7][4] = 2;
        matrix[7][8] = 4;
        matrix[8][4] = 1;
        matrix[8][5] = 3;
        matrix[8][7] = 4;
        matrix[8][9] = 7;
        matrix[9][5] = 11;
        matrix[9][6] = 8;
        matrix[9][8] = 7;
        
        

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
        System.out.println("Prim's Algorithm");
        System.out.println("Table:");
        System.out.println("Vectors: A, B, C, D, E, F, G, H, I, J");
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
                case 7 -> System.out.print("H ");
                case 8 -> System.out.print("I ");
                case 9 -> System.out.print("J ");
            }
        }
        
        
        /*
                        Kruskal's Algorithm
        */
        boolean[][] ConnectionList = new boolean[SIZE][SIZE];
        boolean[][] NameCheck = new boolean[SIZE][SIZE];
        boolean[][] Iteration = new boolean[SIZE][SIZE];
        //says each letter can reach itself
        for(int i = 0; i<SIZE; i++)
            NameCheck[i][i] = true;
        int edgeNumber = 0;
        while(edgeNumber != SIZE -1)
        {
            int xTemp = 0;
            int yTemp = 1;
            int MatrixHolder = 5000;
            //finds next unchecked smallest edge between two vertices
            for (int i = 0; i < SIZE; i++)
            {
                for (int j = 0; j < SIZE; j++)
                {

                    if (matrix[i][j] != 0 && matrix[i][j] < MatrixHolder && !ConnectionList[i][j] && !Iteration[i][j])
                    {
                        MatrixHolder = matrix[i][j];
                        xTemp = i;
                        yTemp = j;
                    }
                }
            }
            //Iteration is used to prevent being stuck in a single spot
            Iteration[xTemp][yTemp] = true;
            if (!NameCheck[xTemp][yTemp])
            {
                NameCheck[xTemp][yTemp] = true;
                NameCheck[yTemp][xTemp] = true;
                //updates what vertices each vertex can reach
                for(int i = 0; i < SIZE; i++)
                {
                    if (NameCheck[i][xTemp])
                    {
                        NameCheck[yTemp][i] = NameCheck[i][yTemp] = true;
                    }
                    if (NameCheck[yTemp][i])
                    {
                        NameCheck[i][xTemp] = NameCheck[xTemp][i] = true;
                    }
                }
                ConnectionList[xTemp][yTemp] = true;
                ConnectionList[yTemp][xTemp] = true;
                edgeNumber++;

            }
            
        }
        System.out.println("Kurskal's Algorithm");
        for(int i= 0; i < SIZE; i++)
        {
            switch(i)
            {
                case 0 -> System.out.print("\n A connects to: ");
                case 1 -> System.out.print("\n B connects to: ");
                case 2 -> System.out.print("\n C connects to: ");
                case 3 -> System.out.print("\n D connects to: ");
                case 4 -> System.out.print("\n E connects to: ");
                case 5 -> System.out.print("\n F connects to: ");
                case 6 -> System.out.print("\n G connects to: ");
                case 7 -> System.out.print("\n H connects to: ");
                case 8 -> System.out.print("\n I connects to: ");
                case 9 -> System.out.print("\n J connects to: ");
            }
            for(int j = 0; j < SIZE; j++)
            {
                if(ConnectionList[i][j])
                {
                    switch (j)
                    {
                        case 0 -> System.out.print("A ");
                        case 1 -> System.out.print("B ");
                        case 2 -> System.out.print("C ");
                        case 3 -> System.out.print("D ");
                        case 4 -> System.out.print("E ");
                        case 5 -> System.out.print("F ");
                        case 6 -> System.out.print("G ");
                        case 7 -> System.out.print("H ");
                        case 8 -> System.out.print("I ");
                        case 9 -> System.out.print("J ");
                    }
                }
            }
        }


        
    }
}