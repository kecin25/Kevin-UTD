#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <time.h> 
#include <signal.h>


int Wargc = 1;
char **Wargv;
void sig_int(int signo)
{
        if(signo == SIGALRM)
	                        printf("%s is terminated after 30 seconds of no activity\n", Wargv[0]);
exit(0);
}



int main(int argc, char *argv[])
{

   system(" date; hostname; whoami ");
   Wargc = argc;
   Wargv = argv;
   signal(SIGALRM, sig_int);
   alarm(30);




    int listenfd = 0, connfd = 0;
    struct sockaddr_in serv_addr; 

    char sendBuff[1025];
    time_t ticks; 

    listenfd = socket(AF_INET, SOCK_STREAM, 0);
    memset(&serv_addr, '0', sizeof(serv_addr));
    memset(sendBuff, '0', sizeof(sendBuff)); 

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    serv_addr.sin_port = htons(34567); 

    bind(listenfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)); 

    listen(listenfd, 10); 

    system(" date; hostname; whoami ");
    system(" netstat -aont | grep \"`hostname -i`:34567 \" ");
    system("ps ");

    printf("\n timeServer: listening at IP=127.0.0.1 Port=34567 \n");

    while(1)
    {
        connfd = accept(listenfd, (struct sockaddr*)NULL, NULL); 
        printf("\n timeServer: timeClient got connected. \n");
        printf("\n timeServer: successful & conglaturation \n");

   // system("netstat -aont | grep \" `hostname -i\`:5000 \" "); 	
        ticks = time(NULL);
        snprintf(sendBuff, sizeof(sendBuff), "%.24s\r\n", ctime(&ticks));
        write(connfd, sendBuff, strlen(sendBuff)); 

        close(connfd);
	   break;
     }

    printf("\n timeServer: now terminating. \n\n");
    sleep(1);
    system("ps ");
    exit(0);   // let the server be terminated now.
}

