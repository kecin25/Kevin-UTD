#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
char *getinput(char *buffer, size_t buflen)
{
        printf(">> ");
        return fgets(buffer, buflen, stdin);
}
int main(){
    char server_message[256] = "You have reached the server!";
    //create the server socket
    int server_socket1;
    int server_socket2;
    int server_socket3;
    int server_socket4;
    server_socket1 = socket(AF_INET, SOCK_STREAM,0);
    server_socket2 = socket(AF_INET, SOCK_STREAM,0);
    server_socket3 = socket(AF_INET, SOCK_STREAM,0);
    server_socket4 = socket(AF_INET, SOCK_STREAM,0);
    //define the server address
    struct sockaddr_in server_address1;
    struct sockaddr_in server_address2;
    struct sockaddr_in server_address3;
    struct sockaddr_in server_address4;
    server_address1.sin_family=AF_INET;
    server_address2.sin_family=AF_INET;
    server_address3.sin_family=AF_INET;
    server_address4.sin_family=AF_INET;
    server_address1.sin_port =htons(9000);
    server_address2.sin_port =htons(9000);
    server_address3.sin_port =htons(9000);
    server_address4.sin_port =htons(9000);
    server_address1.sin_addr.s_addr = inet_addr("10.176.69.32");
    server_address2.sin_addr.s_addr = inet_addr("10.176.69.33");
    server_address3.sin_addr.s_addr = inet_addr("10.176.69.34");
    server_address4.sin_addr.s_addr = inet_addr("10.176.69.35");
	
    //bind the socket to our pecificed IP and port
    bind(server_socket1, (struct sockaddr*) &server_address1, sizeof(server_address1));
			
    listen(server_socket1,5);
    listen(server_socket2,5);
    listen(server_socket3,5);
    listen(server_socket4,5);

			
    int client_socket1;
    int client_socket2;
    int client_socket3;
    int client_socket4;
    //client_socket1 = accept(server_socket1,NULL,NULL);
    client_socket2 = accept(server_socket2,NULL,NULL);
    //client_socket3 = accept(server_socket3,NULL,NULL);
    //client_socket4 = accept(server_socket4,NULL,NULL);
    while(getinput(server_message, sizeof(server_message))){
        if(server_message[0] == 'e' && server_message[1] == 'x' && server_message[2] == 'i' && server_message[3] == 't')
            break;
        else if(server_message[0] == 's' && server_message[1] == 'e' && server_message[2] == 'n' && server_message[3] == 'd' && server_message[4] == ' ')
        {
            if(server_message[6] == '1')
                send(client_socket1, server_message, sizeof(server_message), 0);
            else if(server_message[6] == '2')
                send(client_socket2, server_message, sizeof(server_message), 0);
            else if(server_message[6] == '3')
                send(client_socket3, server_message, sizeof(server_message), 0);
            else if(server_message[6] == '4')
                send(client_socket4, server_message, sizeof(server_message), 0);
            else
                printf("Incorrect location, use a number 1-4.\n");
        }
        else
            printf("incorrect command, use exit or send [1-4] msg.\n");
	    
    }
    close(server_socket1);
    close(server_socket2);
    close(server_socket3);
    close(server_socket4);
    return 0;
}
