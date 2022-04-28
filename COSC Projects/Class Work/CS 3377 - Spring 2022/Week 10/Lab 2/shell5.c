/*
 *  shell1.c 
 *  simplest shell. 
 *     running in loop to read input string (command) to be processed
 *     To exit, type EOF (ctlr+D) or ctlr+C 
 */
#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sysexits.h>
#include <unistd.h>


int Wargc = 1;
char **Wargv;
char *getinput(char *buffer, size_t buflen, char **argv) 
{
	printf("$$%s> ",argv[0]);
	return fgets(buffer, buflen, stdin);
}
void sig_int(int signo)
{
        if(signo== SIGALRM)
                printf("%s is terminated after %d  second(s) of no activity\n", Wargv[0], atoi(Wargv[1]));
	if(signo == SIGINT)
	        printf("\nCaught SIGINT!\n");
}

int main(int argc, char **argv) 
{
	char buf[1024];
	pid_t pid;
	int status;
	
	Wargc = argc;
	Wargv = argv;

	signal(SIGALRM, sig_int);
	if(argc >= 2)
	{
		printf("shell timer = %c seconds\n",*argv[1]);
		alarm(atoi(argv[1]));
	}	
	else
	{
		printf("shell timer = 10 seconds\n");
		alarm(10);
	}
	while (getinput(buf, sizeof(buf), argv)) {
		if(argc>=2)
			alarm(atoi(argv[1]));
		else
			alarm(10);
		buf[strlen(buf) - 1] = '\0';
		if(buf[0] == 'e' && buf[1] == 'x' && buf[2] == 'i' && buf[3] == 't')
			exit(EX_OK);
		if(signal(SIGINT, sig_int) == SIG_ERR)
			printf("error");
		if((pid=fork()) == -1) {
			fprintf(stderr, "shell: can't fork: %s\n",
					strerror(errno));
			continue;
		}
		else if (pid == 0) {
			/* child */
			execlp(buf, buf, (char *)0);
			fprintf(stderr, "shell: couldn't exec %s: %s\n", buf,
					strerror(errno));
			exit(EX_DATAERR);
		}

		if ((pid=waitpid(pid, &status, 0)) < 0)
			fprintf(stderr, "shell: waitpid error: %s\n",
					strerror(errno));
	}
	exit(EX_OK);
}
