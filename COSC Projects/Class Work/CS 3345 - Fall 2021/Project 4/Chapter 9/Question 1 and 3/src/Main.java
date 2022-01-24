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
        final int  SIZE  = 11;
        int[][] matrix = new int[SIZE][SIZE];
        //A through I in figure 9.81 represented as 1 through 9 in the matrix, s and t are 0 and 10
        matrix[0][1] = 1;
        matrix[0][4] = 4;
        matrix[0][7] = 6;
        matrix[1][2] = 2;
        matrix[1][5] = 2;
        matrix[2][3] = 2;
        matrix[3][10] = 4;
        matrix[4][1] = 3;
        matrix[4][5] = 3;
        matrix[5][3] = 2;
        matrix[5][6] = 3;
        matrix[5][9] = 3;
        matrix[6][3] = 1;
        matrix[6][10] = 3;
        matrix[7][4] = 2;
        matrix[7][5] = 1;
        matrix[7][8] = 6;
        matrix[8][5] = 2;
        matrix[8][9] = 6;
        matrix[9][6] = 1;
        matrix[9][10] = 4;

        boolean[] known = new boolean[SIZE];
        int[] distance = new int[SIZE];
        Arrays.fill(distance, 50000);
        distance[0] = 0;
        int[] previous = new int[SIZE];

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

            //printing out table for visual aid
            System.out.println("\n\nTable:");
            System.out.println("Vectors: s, A, B, C, D, E, F, G, H, I, t");
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
                    case 0 -> System.out.print("s ");
                    case 1 -> System.out.print("A ");
                    case 2 -> System.out.print("B ");
                    case 3 -> System.out.print("C ");
                    case 4 -> System.out.print("D ");
                    case 5 -> System.out.print("E ");
                    case 6 -> System.out.print("F ");
                    case 7 -> System.out.print("G ");
                    case 8 -> System.out.print("H ");
                    case 9 -> System.out.print("I ");
                    case 10 -> System.out.print("t ");
                }
            }
        }
    }
}
