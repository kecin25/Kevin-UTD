/*
    Made by: Kevin Boudreaux
    ID: KCB180002
    Made on: 11/18/2021
 */
public class Main {

    public static void main(String[] args) {
	int[] array = new int[] {3,1,4,1,5,9,2,6,5,3,5};
    quicksort(array);
        for (int j : array) 
            System.out.print(j + " ");
    
    }
    public static void quicksort(int[] array)
    {
        QuickSort(array, 0, array.length-1);
    }
    private static void QuickSort(int[] array, int left, int right)
    {
        if(right - left + 1 <= 3)
            manual(array, left, right);
        else
        {
            int median = median3(array, left, right);
            int temp = partition(array, left, right, median);
            QuickSort(array, left, temp - 1);
            QuickSort(array, temp + 1, right);
        }
    }
    private static int median3(int[] array, int left, int right)
    {
        int center = (left+right)/2;
        if(array[left] > array[center])
            swap(array, left, center);
        if(array[left] > array[right])
            swap(array, left, right);
        if(array[center] > array[right])
            swap(array, center, right);

        swap(array, center, right-1);
        return array[right-1];
    }
    
    
    private static int partition(int[] array, int left, int right, int pivot)
    {
        int leftPtr = left;
        int rightPtr = right-1;
        while(true)
        {
            while(array[++leftPtr] < pivot);
            while(array[--rightPtr] > pivot);
            if(leftPtr >= rightPtr)
                break;
            else
                swap(array, leftPtr, rightPtr);
        }
        swap(array, leftPtr, right-1);
        return leftPtr;
    }
    private static void manual(int[] array, int left, int right)
    {
        int size = right-left+1;
        if(size <= 1)
            return;
        if(size == 2)
        {
            if (array[left] > array[right]) 
                swap(array, left, right);
        }
        else
        {
            if(array[left] > array[right-1])
                swap(array, left, right-1);
            if(array[left] > array[right])
                swap(array, left, right);
            if(array[right-1] > array[right])
                swap(array, right-1, right);
        }
        
    }
    private static void swap(int[] array, int num1, int num2)
    {
        int temp = array[num1];
        array[num1] = array[num2];
        array[num2] = temp;
    }
}
