/*Name: Homework 4
Description:
Created by: Kevin Boudreaux
Created on: 5/7/2021
ID: KCB180002
*/

import java.util.Random;

public class Main
{
    private static int quickSortCompare;
    public static int quickSortSwap = 0;
    public static int insertionSortCompare = 0;
    public static int insertionSortSwap = 0;
    public static int mergeSortCompare = 0;
    
    public static void main(String[] args)
    {
        long bubbleCounter = 0;
        long selectionCounter = 0;
        long insertionCounter = 0;
        
        Random rand = new Random();
        int array1[] = new int[5000];
        int array2[] = new int[5000];
        int array3[] = new int[5000];
        int[] List1 = new int[5000];
        LinkedlistIS List2 = new LinkedlistIS();
        int[] List3 = new int[5000];
        
        
        for(int i=0;i<5000;i++)
        {
            int temp = rand.nextInt();
            array1[i] = array2[i] = array3[i] = temp;            
            List1[i] = List3[i] = temp;
            List2.push(temp);
            
            
        }
        
        bubbleSort(array1);
        selectionSort(array2);
        insertSort(array3);
        
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        
        quickSort(List1,0,4999);
        System.out.println("quick sort Comparison #: "+ quickSortCompare);
        System.out.println("quick sort item assignments #: " + quickSortSwap);

        List2.insertionSort(List2.head);
        System.out.println("insert sort Comparison #: "+ insertionSortCompare);
        System.out.println("insert sort item assignments #: " + insertionSortSwap);
        
        MergeSort temp = new MergeSort();
        temp.sort(List3, 0,4999);
        System.out.println("merge sort Comparison #: "+ mergeSortCompare);
    }

    static void bubbleSort(int arr[])
    {
        long bubbleCounter = 0;
        long bubbleSwap = 0;
        int n = arr.length;
        for (int i = 0; i < n-1; i++, bubbleCounter++)
            for (int j = 0; j < n-i-1; j++, bubbleCounter++)
                if (arr[j] > arr[j+1])
                {
                    bubbleCounter++;
                    bubbleSwap++;
                    // swap arr[j+1] and arr[j]
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
        System.out.println("bubble sort Comparison #: " + bubbleCounter);
        System.out.println("bubble sort item assignment #: " + bubbleSwap);
    }
    static void selectionSort(int arr[])
    {
        long selectionCounter = 0;
        long selectionSwap = 0;
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++, selectionCounter++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++, selectionCounter++)
                if (arr[j] < arr[min_idx])
                {
                    selectionCounter++;
                    min_idx = j;
                }

            // Swap the found minimum element with the first
            // element
            selectionSwap++;
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }

        System.out.println("selection sort Comparison #: " + selectionCounter);
        System.out.println("selection sort item assignments #: " + selectionSwap);
    }
    static  void insertSort(int arr[])
    {
        long insertCounter = 0;
        long insertSwap = 0;
        int n = arr.length;
        for (int i = 1; i < n; ++i, insertCounter++) {
            int key = arr[i];
            int j = i - 1;
 
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && arr[j] > key) {
                insertCounter+=2;
                insertSwap++;
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        System.out.println("insert sort Comparison #: "+ insertCounter);
        System.out.println("insert sort item assignments #: " + insertSwap);
    }

    static int partition (int []arr, int l, int h)
    {
        int x = arr[h];
        int i = (l - 1);

        for(int j = l; j <= h - 1; j++, quickSortCompare++)
        {
            if (arr[j] <= x)
            {
                quickSortCompare++;
                quickSortSwap++;
                i++;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        quickSortSwap++;
        int tmp = arr[i + 1];
        arr[i + 1] = arr[h];
        arr[h] = tmp;
        return(i + 1);
    }

    /* A[] --> Array to be sorted,
        l  --> Starting index,
        h  --> Ending index */
    static void quickSort(int []A, int l, int h)
    {
        if (l < h)
        {
            quickSortCompare++;
            quickSortSwap++;
            // Partitioning index
            int p = partition(A, l, h);
            quickSort(A, l, p - 1);
            quickSort(A, p + 1, h);
        }
    }



    public static class LinkedlistIS
    {
        node head;
        node sorted;

        class node
        {
            int val;
            node next;

            public node(int val)
            {
                this.val = val;
            }
        }

        void push(int val)
        {
            /* allocate node */
            node newnode = new node(val);
            /* link the old list off the new node */
            newnode.next = head;
            /* move the head to point to the new node */
            head = newnode;
        }

        // function to sort a singly linked list using insertion sort
        void insertionSort(node headref)
        {
            
            // Initialize sorted linked list
            sorted = null;
            node current = headref;
            // Traverse the given linked list and insert every
            // node to sorted
            while (current != null)
            {
                insertionSortCompare++;
                insertionSortSwap++;
                // Store next for next iteration
                insertionSortSwap++;
                node next = current.next;
                // insert current in sorted linked list
                sortedInsert(current);
                // Update current
                insertionSortSwap++;
                current = next;
            }
            // Update head_ref to point to sorted linked list
            insertionSortSwap++;
            head = sorted;
        }

        /*
         * function to insert a new_node in a list. Note that
         * this function expects a pointer to head_ref as this
         * can modify the head of the input linked list
         * (similar to push())
         */
        void sortedInsert(node newnode)
        {
            /* Special case for the head end */
            if (sorted == null || sorted.val >= newnode.val)
            {
                insertionSortCompare+=2;
                insertionSortSwap++;
                newnode.next = sorted;
                sorted = newnode;
            }
            else
            {
                insertionSortCompare+=2;
                node current = sorted;
                /* Locate the node before the point of insertion */
                while (current.next != null && current.next.val < newnode.val)
                {
                    insertionSortCompare+=2;
                    insertionSortSwap++;
                    current = current.next;
                }
                insertionSortSwap++;
                newnode.next = current.next;
                current.next = newnode;
            }
        }

        /* Function to print linked list */
        void printlist(node head)
        {
            while (head != null)
            {
                System.out.print(head.val + " ");
                head = head.next;
            }
        }
    }


    static class MergeSort
    {
        // Merges two subarrays of arr[].
        // First subarray is arr[l..m]
        // Second subarray is arr[m+1..r]
        void merge(int arr[], int l, int m, int r)
        {
            // Find sizes of two subarrays to be merged
            int n1 = m - l + 1;
            int n2 = r - m;

            /* Create temp arrays */
            int L[] = new int[n1];
            int R[] = new int[n2];

            /*Copy data to temp arrays*/
            for (int i = 0; i < n1; ++i, mergeSortCompare++)
                L[i] = arr[l + i];
            for (int j = 0; j < n2; ++j, mergeSortCompare++)
                R[j] = arr[m + 1 + j];

            /* Merge the temp arrays */

            // Initial indexes of first and second subarrays
            int i = 0, j = 0;

            // Initial index of merged subarry array
            int k = l;
            while (i < n1 && j < n2) {
                mergeSortCompare++;
                if (L[i] <= R[j]) {
                    mergeSortCompare++;
                    arr[k] = L[i];
                    i++;
                }
                else {
                    mergeSortCompare++;
                    arr[k] = R[j];
                    j++;
                }
                k++;
            }

            /* Copy remaining elements of L[] if any */
            while (i < n1) {
                mergeSortCompare++;
                arr[k] = L[i];
                i++;
                k++;
            }

            /* Copy remaining elements of R[] if any */
            while (j < n2) {
                mergeSortCompare++;
                arr[k] = R[j];
                j++;
                k++;
            }
        }

        // Main function that sorts arr[l..r] using
        // merge()
        void sort(int arr[], int l, int r)
        {
            if (l < r) {
                mergeSortCompare++;
                // Find the middle point
                int m =l+ (r-l)/2;

                // Sort first and second halves
                sort(arr, l, m);
                sort(arr, m + 1, r);

                // Merge the sorted halves
                merge(arr, l, m, r);
            }
        }

        /* A utility function to print array of size n */
        static void printArray(int arr[])
        {
            int n = arr.length;
            for (int i = 0; i < n; ++i)
                System.out.print(arr[i] + " ");
            System.out.println();
        }
    }
}