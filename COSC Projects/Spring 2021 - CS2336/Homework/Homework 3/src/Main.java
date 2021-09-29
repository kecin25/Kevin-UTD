/*Name: Homework 3
Description:
Created by: Kevin Boudreaux
Created on: 4/29/2021
ID: KCB180002
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine();
        File file = new File(fileName);
        Scanner fileScanner = null;
        try
        {
            fileScanner = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        
        graph Graph = new graph();
        assert fileScanner != null;
        Graph.createGraph(fileScanner);
        
        if(Graph.isConnected())
            System.out.println("connected");
        else
            System.out.println("not connected");
        
    }
    private static class graph
    {
        private int currentSize;
        private final int maxSize;
        private final int[][] matrix;
        public graph()
        {
            maxSize = 10;
            matrix = new int[10][10];
        }
        public graph(int x)
        {
            maxSize = x;
            matrix = new int[x][x];
        }
        public boolean isEmpty()
        {
            return currentSize == 0;
        }
        public void createGraph(Scanner input)
        {
            String line = input.nextLine();
            currentSize =Integer.parseInt(line);
            for(int i=0;i<currentSize;i++)
            {
                line =input.nextLine();
                int row = Integer.parseInt(line.substring(0,line.indexOf(" ")));
                line = line.substring(line.indexOf(" ")+1);
                
                while(line.contains(" "))
                {
                    int connection = Integer.parseInt(line.substring(0,line.indexOf(" ")));
                    line = line.substring(0,line.indexOf(" "));
                    matrix[row][connection] = 1;
                }
                int connection = Integer.parseInt(line);
                matrix[row][connection] = 1;
            }
        }
        public boolean isConnected()
        {
            for(int i=0;i<currentSize;i++)
            {
                boolean[] visited = new boolean[currentSize];
                isConnectedHelper(i,visited);
                
                for(boolean j: visited)
                {
                    if(!j)
                        return false;
                }
            }
            return true;
        }
        private void isConnectedHelper(int loco, boolean[] visited)
        {
            visited[loco] = true;
            for(int i=0; i < currentSize;i++)
            {
                if(!visited[i] && matrix[loco][i] == 1)
                    isConnectedHelper(i,visited);
            }
        }

        public int getMaxSize()
        {
            return maxSize;
        }
    }
}
