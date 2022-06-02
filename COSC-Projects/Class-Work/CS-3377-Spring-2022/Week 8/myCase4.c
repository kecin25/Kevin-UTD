#include <unistd.h>
#include <stdio.h>
int main(void){
pid_t pid;
pid_t pid2;
printf("Origional program, pid=%d\n\n", getpid());
if((pid=fork())!=0 && (pid2 = fork())!=0){fork();}
if((pid=fork())!=0 && (pid2 = fork())!=0){fork();}
if(pid==0)
	printf("In the child process, pid=%d, ppid=%d\n\n", getpid(), getppid());
if(pid2==0)
	printf("In the child process, pid2=%d, ppid=%d\n\n", getpid(), getppid());
if(pid!=0 && pid2!=0)
{
sleep(1);
printf("In the parent process, pid=%d, forks returned=%d, %d\n\n", getpid(), pid, pid2);
}
}
	
