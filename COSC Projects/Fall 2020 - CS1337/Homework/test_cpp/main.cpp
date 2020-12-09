#include "stdio.h"
#include "stdlib.h"
// Create a linked list node struct to hold an integer value
// Name the struct Node
// Name the integer num
// Name the node pointer next
// Create a bubble sort function that will sort the values in a linked list using the bubble sort algorithm
// You can swap contents of the nodes rather than swapping the order of the nodes
// After each pass of the bubble sort, print the list to the console
// Print a space between each integer and a new line after the last integer
// Create a selection sort function that will sort the values in a linked list using the selection sort algorithm
// You can swap contents of the nodes rather than swapping the order of the nodes
// After each swap of the selection sort, print the list to the console
// Print a space between each integer and a new line after the last integer
// Create  a driver to test the functions
// Create two linked lists
// The linked lists will hold the same data as entered from the user
// Prompt the user to enter 8 integers.
// Store each of the values in both linked lists
// Display the contents of the first linked list
// Print a space between each integer and a new line after the last integer
// Call the bubble sort function with the first linked list
// Display the contents of the second linked list
// Print a space between each integer and a new line after the last integer
// Call the selection sort function with the second linked list

struct Node
{
    int num;
    struct Node *next;
};
void bubblesort(struct Node *head, int x)
{
    struct Node *temp = head;
    struct Node *temp2=head;
    int n;
    for (int i = 0; i < x-1; i++)
    {
        n = 0;
        for (int j = 0; j < x-1; j++)
        {
            if ((temp->num) > (temp->next->num))
            {
                int number = temp->num;
                temp->num = temp->next->num;
                temp->next->num = number;
                n = 1;
                temp2=head;
            }
            temp = temp->next;
        }
        if (n == 0)
        {
           i=x;
        }


        temp2 = head;
        for(int k =0; k <8; k++)
        {
            printf("%d ", temp2->num);
            temp2 = temp2->next;
        }
        printf("\n");
        temp=head;

    }
}
void selectionsort(struct Node *head)
{
    struct Node *temp = head;
    struct Node *temp2=head;
    int z=7;
    int breaker=0;
    while (temp)
    {

        struct Node *h = temp;
        struct Node *x = temp->next;
        for(int i=0;i<z;i++)
        {
            if ((h->num) > x->num)
            {
                h = x;
            }
            x = x->next;
        }
        int tem = temp->num;
        temp->num = h->num;
        h->num = tem;
        temp = temp->next;
        temp2=head;
        for(int i =0; i <8; i++)
        {
            printf("%d ", temp2->num);
            temp2 = temp2->next;
        }
        printf("\n");
        z--;
        breaker++;
        if(breaker>8)
            break;
    }
}


int main()
{
    struct Node *t, *y;

    int num2;
    struct Node *temp = (struct Node *)malloc(sizeof(struct Node));
    t = temp;
    struct Node *tem = (struct Node *)malloc(sizeof(struct Node));
    y = tem;
    for(int i =0; i<8; i++){
        scanf("%d",&num2);
        temp->num = num2;
        temp->next = (struct Node *)malloc(sizeof(struct Node));
        temp=temp->next;
        tem->num = num2;
        tem->next = (struct Node *)malloc(sizeof(struct Node));
        tem=tem->next;

    }
    temp = t;
    tem = y;
    printf("Before bubble: \n");
    for(int i =0; i <8; i++)
        {
            printf("%d ", temp->num);
            temp = temp->next;
        }
    temp = t;
    printf("\nAfter : \n");
    bubblesort(t,8);
    printf("\nBefore selection: \n");
    for(int i =0; i <8; i++)
        {
            printf("%d ", tem->num);
            tem = tem->next;
        }
    tem=y;
    printf("\nAfer: \n");
    selectionsort(y);
    return 0;


}
