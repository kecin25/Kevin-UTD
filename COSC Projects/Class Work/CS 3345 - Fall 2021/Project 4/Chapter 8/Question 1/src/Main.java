import java.util.Arrays;

/*
Made by: Kevin Boudreaux
ID: KCB180002
Made on: 11/29/2021
 */
public class Main 
{
    public static void main(String[] args) 
    {
        int[] array = new int[17];
        System.out.println("Arbitrarily:");
        Arrays.fill(array, -1);
        ARUnion(array,1,2);
        ARUnion(array,3,4);
        ARUnion(array,3,5);
        ARUnion(array,1,7);
        ARUnion(array,3,6);
        ARUnion(array,8,9);
        ARUnion(array,1,8);
        ARUnion(array,3,10);
        ARUnion(array,3,11);
        ARUnion(array,3,12);
        ARUnion(array,3,13);
        ARUnion(array,14,15);
        ARUnion(array,16,0);
        ARUnion(array,14,16);
        ARUnion(array,1,3);
        ARUnion(array,1,14);
        System.out.println(Arrays.toString(array));

        System.out.println("by Height:");
        Arrays.fill(array, -1);
        HeightUnion(array,1,2);
        HeightUnion(array,3,4);
        HeightUnion(array,3,5);
        HeightUnion(array,1,7);
        HeightUnion(array,3,6);
        HeightUnion(array,8,9);
        HeightUnion(array,1,8);
        HeightUnion(array,3,10);
        HeightUnion(array,3,11);
        HeightUnion(array,3,12);
        HeightUnion(array,3,13);
        HeightUnion(array,14,15);
        HeightUnion(array,16,0);
        HeightUnion(array,14,16);
        HeightUnion(array,1,3);
        HeightUnion(array,1,14);
        System.out.println(Arrays.toString(array));

        System.out.println("by Size:");
        Arrays.fill(array, -1);
        SizeUnion(array,1,2);
        SizeUnion(array,3,4);
        SizeUnion(array,3,5);
        SizeUnion(array,1,7);
        SizeUnion(array,3,6);
        SizeUnion(array,8,9);
        SizeUnion(array,1,8);
        SizeUnion(array,3,10);
        SizeUnion(array,3,11);
        SizeUnion(array,3,12);
        SizeUnion(array,3,13);
        SizeUnion(array,14,15);
        SizeUnion(array,16,0);
        SizeUnion(array,14,16);
        SizeUnion(array,1,3);
        SizeUnion(array,1,14);
        System.out.println(Arrays.toString(array));
    }
    public static void ARUnion(int[] array, int x, int y)
    {
        array[y] = x;
    }
    public static void HeightUnion(int[] array, int x, int y)
    {
        if(array[y] < array[x])
            array[x] = y;
        else
        {
            if(array[x] == array[y])
                array[x]--;
            array[y] = x;
        }
    }
    public static void SizeUnion(int[] array, int x, int y)
    {
        while(array[x] >= 0)
            x = array[x];
        while(array[y] >= 0 )
            y = array[y];
        if(array[x] <= array[y])
        {
            array[x] += array[y];
            array[y] = x;
        }
        else
        {
            array[y] += array[x];
            array[x] = y;
        }
    }
}
