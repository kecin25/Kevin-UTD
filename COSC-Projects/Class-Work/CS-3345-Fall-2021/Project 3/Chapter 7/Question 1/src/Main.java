/*
    Made by: Kevin Boudreaux
    ID: KCB180002
    Made on: 11/18/2021
 */
public class Main {

    public static void main(String[] args) {
        
	int[] list = {3,1,4,1,5,9,2,6,5};
    InsertionSortMin(list);
        System.out.println("min to max:");
        for (int j : list) 
            System.out.print(j + " ");
        
    System.out.println("\nmax to min:");
    list = new int[]{3, 1, 4, 1, 5, 9, 2, 6, 5};
    InsertionSortMax(list);
        for (int j : list) 
            System.out.print(j + " ");
    }
    public static void InsertionSortMin(int[] list)
    {
        for(int i = 1; i<list.length;i++)
        {
            int k = i;
            for(int j = i-1; j>= 0 && list[j] > list[k]; j--,k--)
            {
                int swap = list[j];
                list[j] = list[k];
                list[k] = swap;
            }
        }
    }
    public static void InsertionSortMax(int[] list)
    {
        for(int i = 1; i<list.length;i++)
        {
            int k = i;
            for(int j = i-1; j>= 0 && list[j] < list[k]; j--,k--)
            {
                int swap = list[j];
                list[j] = list[k];
                list[k] = swap;
            }
        }
    }
}
    
