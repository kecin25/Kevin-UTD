/*
    By: Kevin Boudreaux 
    ID: KCB180002
    Made on: 11/17/2021

 */
public class Main
{

    public static void main(String[] args)
    {
        int[] array = {10};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14, 6};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14, 6, 5};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14, 6, 5, 8};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14, 6, 5, 8, 15};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14, 6, 5, 8, 15, 3};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14, 6, 5, 8, 15, 3, 9};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14, 6, 5, 8, 15, 3, 9, 7};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13};
        buildHeap(array);
        printHeap(array);
        array = new int[]{10,12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2};
        buildHeap(array);
        printHeap(array);
        System.out.println("using delete min 3 times:");
        array= delMin(array);
        printHeap(array);
        array = delMin(array);
        printHeap(array);
        array = delMin(array);
        printHeap(array);
    }
    
    static void makeHeap(int[] array, int size, int cur)
    {
        int smallest = cur;
        int left = 2 * smallest + 1;
        int right = 2 * smallest + 2;

        if (left < size && array[left] < array[smallest])
        {
            smallest = left;
        }
        if (right < size && array[right] < array[smallest])
        {
            smallest = right;
        }
        if (smallest != cur)
        {
            int temp = array[cur];
            array[cur] = array[smallest];
            array[smallest] = temp;
            makeHeap(array, size, smallest);
        }
        

    }

    static void buildHeap(int[] array)
    {
        for (int i = (array.length / 2) + 1; i >= 0; i--)
            makeHeap(array, array.length, i);
    }

    static void printHeap(int[] array)
    {
        System.out.println("Array representation of the heap: ");
        for (int j : array) System.out.print(j + " ");
        System.out.println();
    }
    static int[] delMin(int[] array)
    {
        array[0] = array[array.length-1];
        int[] temp = new int[array.length-1];
        for(int i=0; i<temp.length;i++)
        {
            temp[i] = array[i];
        }
        buildHeap(temp);
        return temp;
    }
}