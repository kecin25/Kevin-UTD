#include <unistd.h>
#include <stdio.h>
int main(void)
{
pid_t p;
printf("Origional program, pid=%d\n\n", getpid());
p=fork();
p=fork();
p=fork();
if(p==0)
{
printf("In the child process, pid = %d, ppid = %d\n\n", getpid(), getppid());
}
else{
sleep(1);
printf("In the parent process, pid=%d, fork returned = %d\n\n", getpid(), p);
}
}
