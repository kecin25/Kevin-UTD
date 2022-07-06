// used https://www.youtube.com/watch?v=LtXEMwSG5-8 as a simple example on how to set up a server and client connection
// used old code from CS3377 for user input
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <netinet/in.h>

char *getinput(char *buffer, size_t buflen)
{
    printf(">> ");
    return fgets(buffer, buflen, stdin);
}

int main(int argc, char *argv[])
{
    int const MESSAGE_SIZE = 256;
    int network_socket;
    int server_socket;
    int client_socket;
    int connection_status;
    struct sockaddr_in server_address;
    char server_response[256];
    char server_message[256];
    
    // Server side
    switch (fork())
    {
    case -1:
        printf("Server Side: Error forking...\n");
        exit(1);
    case 0:
    
        //create the server socket
        server_socket = socket(AF_INET, SOCK_STREAM,0);
        //define the server address
        server_address.sin_family=AF_INET;
        server_address.sin_port =htons(9000);
        server_address.sin_addr.s_addr = inet_addr("10.176.69.33");
        
        //bind the socket to our pecificed IP and port
        bind(server_socket, (struct sockaddr*) &server_address, sizeof(server_address));
                
        listen(server_socket,5);	
        
        client_socket = accept(server_socket,NULL,NULL);
        while(getinput(server_message, sizeof(server_message))){
            send(client_socket, server_message, sizeof(server_message), 0);
        }close(server_socket);
        return 0;
    }

    // Clinet side
    switch (fork())
    {
    case -1:
        printf("Child 2: Error forking Child 2...\n");
        exit(1);
    case 0:
        wait(1000);
        network_socket = socket(AF_INET,SOCK_STREAM,0);
        server_address.sin_family=AF_INET;
        server_address.sin_port=htons(9000);
        server_address.sin_addr.s_addr=INADDR_ANY;
        connection_status= connect(network_socket, (struct sockaddr*) &server_address, sizeof(server_address));
        if(connection_status == -1)
        {
            printf("Connection status error\n");
            return 1;
        }
        
        while(recv(network_socket, &server_response, sizeof(server_response),0))
        {
        printf("The server sent the data: %s\n",server_response);
        }
        close(network_socket);
        return 0;
    }

    return 0;
}