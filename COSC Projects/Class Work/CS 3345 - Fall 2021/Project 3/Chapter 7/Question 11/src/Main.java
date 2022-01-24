/*
    Made by: Kevin Boudreaux
    ID: KCB180002
    Made on: 11/18/2021
 */
public class Main {

    public static void main(String[] args) {
	int[] array = new int[]{142, 543, 123, 65, 453, 879, 572, 434, 111, 242, 811, 102};
    heap temp = new heap();
    temp.sort(array);
    temp.print(array);
    }
}
class heap
{
    public void print(int[] array)
    {
        for (int j : array) System.out.print(j + " ");
            System.out.println();
    }

    private void heapify(int[] array, int n, int i)
    {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && array[left] > array[largest]) 
            largest = left;
        if (right < n && array[right] > array[largest]) 
            largest = right;
        if (largest != i)
        {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            heapify(array, n, largest);
        }
    }
    public void sort(int[] array)
    {
        for (int i = array.length / 2 - 1; i >= 0; i--)
            heapify(array, array.length, i);
        for (int i = array.length - 1; i > 0; i--)
        {
            int swap = array[0];
            array[0] = array[i];
            array[i] = swap;
            heapify(array, i, 0);
        }
    }
}
    
    
    