#include <stdio.h>
#include <stdlib.h>

struct Node
{
    int num;
    struct Node * next;
};

void BubbleSort(struct Node*);
void SelectionSort(struct Node*);
int main()
{
    int var=0;
    struct Node *Head1=(struct Node*)malloc(sizeof(struct Node));
    struct Node *Head2=(struct Node*)malloc(sizeof(struct Node));
    struct Node *temp1=Head1;
    struct Node *temp2=Head2;

    for(int i=0; i<8; i++)
    {
        printf("Enter a number\n");
        scanf("%d",&var);

        temp1->num=var;
        temp2->num=var;
        temp1->next=(struct Node*)malloc(sizeof(struct Node));
        temp2->next=(struct Node*)malloc(sizeof(struct Node));
        temp1=temp1->next;
        temp2=temp2->next;

    }
    temp1=Head1;
    temp2=Head2;

    for(int i=0; i<8; i++)
    {
        printf("%d ",temp1->num);
        temp1=temp1->next;
    }
    printf("\n");

    BubbleSort(Head1);
    printf("\n\n\n");

    for(int i=0; i<8; i++)
    {
        printf("%d ",temp2->num);
        temp2=temp2->next;
    }
    printf("\n");
    SelectionSort(Head2);

    return 0;
}
void BubbleSort(struct Node *Head1)
{
    int moved;
    struct Node* temp=Head1;
    struct Node* temp2=Head1;
    int numtemp;

    do
    {
        moved=0;
        for(int i=0;i<7;i++)
        {
            if(temp->num>temp->next->num)
            {
                numtemp=temp->num;
                temp->num=temp->next->num;
                temp->next->num=numtemp;
                moved++;

                temp2=Head1;

            }
            temp=temp->next;
        }
        temp2=Head1;
        for(int j=0;j<8;j++)
        {
            printf("%d ",temp2->num);
            temp2=temp2->next;
        }
        printf("\n");
        temp=Head1;
    }while(moved>0);
}
void SelectionSort(struct Node *Head2)
{
    struct Node *temp=Head2;
    struct Node *temp2=Head2;
    struct Node *anc=Head2;
    for(int i=0; i<7; i++)
    {
        for(int k=0; k<i; k++)
            temp=temp->next;
        int min=temp->num;
        int minloco=i;
        for(int j=i+1; j<8; j++)
        {
            temp=temp->next;
            if(temp->num<min)
            {
                min=temp->num;
                minloco=j;
            }
        }
        temp2=Head2;

        for(int j=0; j<(minloco-i); j++)
            temp2=temp2->next;

        int numtemp=Head2->num;

        Head2->num=min;

        temp2->num=numtemp;

        temp=anc;
        temp2=anc;

        for(int n=0; n<8; n++)
        {
            printf("%d ",temp->num);
            temp=temp->next;
        }

        printf("\n");

        Head2=Head2->next;

        temp=anc;

    }

}
