#include <windows.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <dirent.h>
#include <fcntl.h>
#include <sys/ipc.h>
#include <sys/shm.h>

int main(int argc, char **argv)
{
    //creating shared memory for the two kids
    key_t key1 = ftok("shmfile1",65);
    key_t key2 = ftok("shmfile2",65);
    key_t key3 = ftok("shmfile3",65);
    key_t key4 = ftok("shmfile4",65);
    int shmid1 = shmget(key1,2*sizeof(int),0666|IPC_CREAT);
    int shmid2 = shmget(key2,2*sizeof(int),0666|IPC_CREAT);
    int shmid3 = shmget(key3,2*sizeof(int),0666|IPC_CREAT);
    int shmid4 = shmget(key4,2*sizeof(int),0666|IPC_CREAT);

    int *child1FinishedWriting = (int*)shmat(shmid1,(void*)0,0);
    int *child2FinishedWriting = (int*)shmat(shmid2,(void*)0,0);
    int *child1Finished = (int*)shmat(shmid3,(void*)0,0);
    int *child2Finished = (int*)shmat(shmid4,(void*)0,0);



    const int fileSize = 1024;
    //finding larger directory
    char cwd[260];
    int NumFiles = 0;
    DIR *dr1, *dr2;
    struct dirent *cur;
    int count1 = 0;
    int count2 = 0;
    if(argc != 2)
    {


        dr1 = opendir("dir1");
        if (dr1) {
            while ((cur = readdir(dr1)) != NULL)
            {
                if(strcmp(cur->d_name, ".") == 0 || strcmp(cur->d_name, "..") == 0)
                        continue;
                count1++;
            }
            closedir(dr1);
        }
        dr2 = opendir("dir2");
        if (dr2) {
            while ((cur = readdir(dr2)) != NULL)
            {
                if(strcmp(cur->d_name, ".") == 0 || strcmp(cur->d_name, "..") == 0)
                        continue;
                count2++;
            }
            closedir(dr2);
        }
        if(count1>count2)
            NumFiles = count1;
        else
            NumFiles = count2;
    }
    printf("Parent: Number of files in larger directory is: %d",NumFiles);
    int pipeLength = 2*NumFiles*fileSize+1;
//Creating the two pipes
    int Pipe1[2];
    int Pipe2[2];
//opening pipes
     if(pipe(Pipe1) == -1)
     {
         printf("Parent: Error opening pipe 1\n");
         exit(1);
     }

     if(pipe(Pipe2) == -1)
     {
         printf("Parent: Error opening pipe 2\n");
         exit(1);
     }
//     printf("Parent: both Pipes opened. Forking...\n");


//child 1 getting file names in dir1, sorting it as file1, file1contents, ....
//sending that through pipe1 to child 2
//reading pipe2 from child 2
//make a new file and add its contents in same format
//exit child
    switch(fork())
    {
        case -1:
            printf("Child 1: Error forking Child 1...\n");
            exit(1);
        case 0:
            //closing reading end of first pipe
            if(close(Pipe1[0]) == -1)
            {
                printf("Child 1: Error closing reading end of pipe 1...\n");
                exit(1);
            }
            printf("child 1: Closed reading end of pipe 1...\n");
            //closing writing end of second pipe
            if(close(Pipe2[1]) == -1)
            {
                printf("Child 1: Error closing writing end of pipe 2...\n");
                exit(1);
            }
            printf("Child 1: Closed writing end of pipe 2...\n");

            //opening dir1
            dr1=opendir("./dir1");

            char final_list[2*NumFiles][fileSize]; //used as final submission to other child
            char fileName_list[NumFiles][fileSize]; //used to get file names
            char contents_list[NumFiles][fileSize]; //used to keep track of contents

            if(dr1)
            {
                printf("Child 1: dir1 directory opened...\n");
                printf("Child 1: Number of files is %d\n",NumFiles);
                int i=0;
                while ((cur = readdir(dr1)) != NULL)
                {
                    printf("Child 1: current File being looked at: %s\n",cur->d_name);
                    if(strcmp(cur->d_name, ".") == 0 || strcmp(cur->d_name, "..") == 0)
                        continue;
                    strcpy(fileName_list[i], cur->d_name);

                    //opening current file to get contents
                    char Tstring[32];
                    memset(Tstring, 0, 32);
                    strcat(Tstring,"dir1/");
                    strcat(Tstring,cur->d_name);
                    FILE *ptr = fopen(Tstring,"r");
                    
                    char ch[1];
                    char sNumFiles[fileSize];
                    memset(sNumFiles, 0, fileSize);
                    if(ptr == NULL)
                    {
                        printf("Child 1: Error opening file %s, %s\n",cur->d_name, strerror(errno));
                        exit(1);
                    }
                    printf("Child 1: Opened file %s\n",cur->d_name);
                    do{
                        ch[0] = fgetc(ptr);
                        strcat(sNumFiles,ch);
                    }while(ch[0]!=EOF);
                    //close file
                    fclose(ptr);
                    strcpy(contents_list[i], sNumFiles);
                    i++;
                }
                //combining the two lists into one list
                i = 0;
                int k = 0;
                printf("Child1: combining information to send to Child 2...\nChild1: while loop will run %d times...\n",NumFiles);
                while(i<NumFiles)
                {
                    strcpy(final_list[k], fileName_list[i]);
                    k++;
                    strcpy(final_list[k], contents_list[i]);
                    k++;
                    i++;
                }
                //writing to pipe 1
                printf("Child1: Starting to write to pipe 1...\n");
                if(write(Pipe1[1], final_list, pipeLength) != pipeLength)
                {
                    printf("Child 1: Error writing to pipe 1...\n");
                    exit(1);
                }
                printf("Child 1: Wrote to pipe 1...\n");
                //closing writing to pipe 1
                printf("Child1: Starting to close writing side of pipe 1...\n");
                if(close(Pipe1[1]) == -1)
                {
                    printf("Child 1: Error closing writing to pipe 1...\n");
                    exit(1);
                }
                printf("Child 1: Closed writing to pipe 1...\n");
                *child1FinishedWriting = 1;
                while(*child2FinishedWriting != 1);
                //starting the reading part of the program for child 1
                char incoming[2*NumFiles][fileSize];
                printf("Child 1: Starting to read from pipe 2...\n");
                if(read(Pipe2[0], incoming, pipeLength) == -1)
                {
                    printf("Child 1: Error reading from pipe 2...\n");
                    exit(1);
                }
                printf("Child 1:Read from pipe 2...\n");
                printf("Child 1: Starting to close the reading end of pipe 2...\n");
                if (close(Pipe2[0]) == -1)
                {
                    printf("Child 1: Error closing reading end of pipe 2...\n");
                    _exit(1);
                }
                printf("Child 1: Closed reading end of pipe 2...\n");
                //making the files and adding the contents into the current directory
                for(int i = 0; i < 2*NumFiles;i++)
                {
                    char Tstring[32];
                    memset(Tstring, 0, 32);
                    strcat(Tstring,"dir1/");
                    strcat(Tstring,incoming[i]);
                    FILE *fileName = fopen(Tstring,"w");
                    printf("Child 1: Making file: %s\n",incoming[i]);
                    i++;
                    fprintf(fileName, incoming[i]);
                    fclose(fileName);
                }

                printf("Child 1: Finish copying files from Child 2...\n");
                *child1Finished = 1;
                _exit(0);
            }
    }



    //child 2 getting file names in dir2, sorting it as file1, file1contents, ....
    //sending through pipe2 to child 1
    //reading pipe1 from child 1
    //make a new file and add its contents in same format
    //exit child
    switch(fork())
    {
        case -1:
            printf("Child 2: Error forking Child 2...\n");
            exit(1);
        case 0:
            //closing reading end of second pipe
            printf("Child 2: Starting to close the reading end of pipe 2...\n");
            if(close(Pipe2[0]) == -1)
            {
                printf("Child 2: Error closing reading end of pipe 2...\n");
                exit(1);
            }
            printf("Child 2: Closed reading end of pipe 2...\n");
            //closing writing end of second pipe
            printf("Child 2: Starting to close the writing end of pipe 1...\n");
            if(close(Pipe1[1]) == -1)
            {
                printf("Child 2: Error closing writing end of pipe 1...\n");
                exit(1);
            }
            printf("Child 2: Closed writing end of pipe 1...\n");
            //opening dir2
            dr2=opendir("./dir2");

            char final_list[2*NumFiles][fileSize]; //used as final submission to other child
            char fileName_list[NumFiles][fileSize]; //used to get file names
            char contents_list[NumFiles][fileSize]; //used to keep track of contents

            if(dr2)
            {
                printf("Child 2: dir2 directory opened...\n");
                printf("Child 2: Number of files is %d\n",NumFiles);
                int i=0;
                while ((cur = readdir(dr2)) != NULL)
                {
                    if(strcmp(cur->d_name, ".") == 0 || strcmp(cur->d_name, "..") == 0)
                        continue;
                    strcpy(fileName_list[i], cur->d_name);

                    //opening current file to get contents
                    char Tstring[32];
                    memset(Tstring, 0, 32);
                    strcat(Tstring,"dir2/");
                    strcat(Tstring,cur->d_name);
                    FILE *ptr = fopen(Tstring,"r");
                    
                    char ch[1];
                    char sNumFiles[fileSize];
                    memset(sNumFiles, 0, fileSize);
                    if(ptr == NULL)
                    {
                        printf("Child 2: Error opening file %s, %s\n",cur->d_name, strerror(errno));
                        exit(1);
                    }
                    printf("Child 2: Opened file %s\n",cur->d_name);
                    do{
                        ch[0] = fgetc(ptr);
                        strcat(sNumFiles,ch);
                    }while(ch[0]!=EOF);
                    //close file
                    fclose(ptr);
                    strcpy(contents_list[i], sNumFiles);
                    i++;
                }
                //combining the two lists into one list
                i = 0;
                int k = 0;
                printf("child2: combining information to send to Child 1...\nchild2: while loop will run %d times...\n",NumFiles);
                while(i<NumFiles)
                {
                    strcpy(final_list[k], fileName_list[i]);
                    k++;
                    strcpy(final_list[k], contents_list[i]);
                    k++;
                    i++;
                }
                //writing to pipe 2
                printf("child2: Starting to write to pipe 2...\n");
                if(write(Pipe2[1], final_list, pipeLength) != pipeLength)
                {
                    printf("Child 2: Error writing to pipe 2...\n");
                    exit(1);
                }
                printf("Child 2: Wrote to pipe 2...\n");
                //closing writing to pipe 1
                if(close(Pipe2[1]) == -1)
                {
                    printf("Child 2: Error closing writing to pipe 2...\n");
                    exit(1);
                }
                printf("Child 2: Closed writing to pipe 2...\n");
                *child2FinishedWriting = 1;
                while(*child1FinishedWriting != 1);
                //starting the reading part of the program for Child 2
                char incoming[2*NumFiles][fileSize];
                printf("child2: Starting to read from pipe 1...\n");
                if(read(Pipe1[0], incoming, pipeLength) == -1)
                {
                    printf("Child 2: Error reading from pipe 1...\n");
                    exit(1);
                }
                printf("Child 2:Read from pipe 1...\n");
                printf("child2: Starting to close the writing end to pipe 1...\n");
                if (close(Pipe1[0]) == -1)
                {
                    printf("Child 2: Error closing reading end of pipe 1...\n");
                    _exit(1);
                }
                printf("Child 2: Closed reading end of pipe 1...\n");
                //making the files and adding the contents into the current directory
                for(int i = 0; i < 2*NumFiles;i++)
                {
                    char Tstring[32];
                    memset(Tstring, 0, 32);
                    strcat(Tstring,"dir2/");
                    strcat(Tstring,incoming[i]);
                    FILE *fileName = fopen(Tstring,"w");
                    printf("Child 2: Making file: %s\n",incoming[i]);
                    i++;
                    fprintf(fileName, incoming[i]);
                    fclose(fileName);
                }

                printf("Child 2: Finish copying files from Child 1...\n");
                *child2Finished = 1;
                _exit(0);
            }
    }
    if(wait(NULL) == -1)
    {
        printf("Parent: Error Waiting...\n");
        exit(EXIT_FAILURE);
    }
    do{}while(*child1Finished != 1 && *child2Finished != 1);
    //print message from parent about finishing
    printf("Parent: Closing pipes...\n");
    if (close(Pipe2[1]) == -1)
    {
        printf("Parent: Error closing writing of pipe 1...\n");
        exit(EXIT_FAILURE);
    }
    if (close(Pipe2[0]) == -1)
    {
        printf("Parent: Error closing reading of pipe 2...\n");
        exit(EXIT_FAILURE);
    }
    if (close(Pipe1[1]) == -1)
    {
        printf("Parent: Error closing writing of pipe 1...\n");
        exit(EXIT_FAILURE);
    }
    if (close(Pipe2[1]) == -1)
    {
        printf("Parent: Error closing writing of pipe 2...\n");
        exit(EXIT_FAILURE);
    }

    printf("Parent: Finished\n");
    exit(EXIT_SUCCESS);

}