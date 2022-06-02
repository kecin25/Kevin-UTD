#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#define MAXLINE 4096 /*max text line length*/
#define SERV_PORT 2386 /*port*/
#define LISTENQ 8 /*maximum number of client connections */
static int timer_expired=0;
static void alarm_handler(int sig){ timer_expired=1;}
int main (int argc, char **argv)
{
printf("\n\n ** echoServer1 is running ** \n\n");
system("date; hostname; whoami; ls *");
sigaction(SIGALRM, &(struct sigaction){.sa_handler = alarm_handler},NULL);
alarm(500);
int listenfd, connfd, n;
 socklen_t clilen;
 char buf[MAXLINE];
 struct sockaddr_in cliaddr, servaddr;
	
 //creation of the socket
 listenfd = socket (AF_INET, SOCK_STREAM, 0);
	
 //preparation of the socket address 
 servaddr.sin_family = AF_INET;
 servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
 servaddr.sin_port = htons(SERV_PORT);
	
 bind(listenfd, (struct sockaddr *) &servaddr, sizeof(servaddr));
	
 listen(listenfd, LISTENQ);
	
 printf("%s\n","Server running...waiting for connections.");
	
 for ( ; ; ) {
  clilen = sizeof(cliaddr);
  connfd = accept(listenfd, (struct sockaddr *) &cliaddr, &clilen);
  printf("%s\n","Received request...");
				
  while ( (n = recv(connfd, buf, MAXLINE,0)) > 0)  {
   printf("%s","String received from and resent to the client:");
   puts(buf);
   send(connfd, buf, n, 0);
  }
			
 if (n < 0) {
  perror("Read error"); 
  exit(1);
 }
 close(connfd);
 alarm(500);
 }
 //close listening socket
 close(listenfd); 
}
