 
 #include <stdio.h>
#include <stdlib.h>

#include <sys/types.h>
#include <sys/socket.h>

#include <netinet/in.h>

#include <strings.h>
char *getinput(char *buffer, size_t buflen)
{
        printf(">> ");
        return fgets(buffer, buflen, stdin);
}

 //Server side
int main(int argc, char *argv[])
{
    int const MESSAGE_SIZE = 256;

    char server_message[MESSAGE_SIZE] = "";
    char buf[MESSAGE_SIZE];
    //creating server socket
    int server_socket;
    server_socket = socket(AF_INET, SOCK_STREAM, 0);

    //defining the server address
    struct sockaddr_in server_address[4];
    server_address[0].sin_family = AF_INET;
    server_address[1].sin_family = AF_INET;
    server_address[2].sin_family = AF_INET;
    server_address[3].sin_family = AF_INET;

    server_address[0].sin_port = htons(9000); //9000 = port #
    server_address[1].sin_port = htons(9000); 
    server_address[2].sin_port = htons(9000);
    server_address[3].sin_port = htons(9000);

    switch(argv[1])
    {
        case 1:
            //server_address[0].sin_addr.s_addr = "10.176.69.32"//TODO put in address logic 
            server_address[1].sin_addr.s_addr = "10.176.69.33"//TODO put in address logic
            server_address[2].sin_addr.s_addr = "10.176.69.34"//TODO put in address logic 
            server_address[3].sin_addr.s_addr = "10.176.69.35"//TODO put in address logic 
            break;
        
        case 2:
            server_address[0].sin_addr.s_addr = "10.176.69.32"//TODO put in address logic
            //server_address[1].sin_addr.s_addr = "10.176.69.33"//TODO put in address logic
            server_address[2].sin_addr.s_addr = "10.176.69.34"//TODO put in address logic 
            server_address[3].sin_addr.s_addr = "10.176.69.35"//TODO put in address logic 
            break;
            
        case 3:
            server_address[0].sin_addr.s_addr = "10.176.69.32"//TODO put in address logic
            server_address[1].sin_addr.s_addr = "10.176.69.33"//TODO put in address logic
            //server_address[2].sin_addr.s_addr = "10.176.69.34"//TODO put in address logic 
            server_address[3].sin_addr.s_addr = "10.176.69.35"//TODO put in address logic 
            break;
        case 4:
            server_address[0].sin_addr.s_addr = "10.176.69.32"//TODO put in address logic
            server_address[1].sin_addr.s_addr = "10.176.69.33"//TODO put in address logic
            server_address[2].sin_addr.s_addr = "10.176.69.34"//TODO put in address logic 
            //server_address[3].sin_addr.s_addr = "10.176.69.35"//TODO put in address logic 
            break;
            
        default:
        printf("Incorrect input number\n");
        exit(1);
    }
    while(getinput(buf, sizeof(buf)))
    {
        buf[strlen(buf)-1] = '\0';
        if(buf[0] == 's' && buf[1] == 't' && buf[2] == 'o' && buf[3] == 'p')
        {
            //closing socket
            close(server_socket);
            reaturn 0;
        }
        else if (buf[0] == 's' && buf[1] == 'e' && buf[2] == 'n' && buf[3] == 'd'&& buf[4] == ' ')
        {
            if(!(1 <= (int)buf[5] <= 4))
                printf("The number must be between 1 and 4.\n");
            elif(&& buf[5] == argv[1])
                printf("Current process cannot send message to itself.\n");
            else
            {
                switch ((int)buf[5])
                {
                case 1:
                    //binding the socket to specified IP and port
                    bind(server_socket, (struct sockaddr*) &server_address[0], sizeof(server_address));

                    listen(server_socket, 3); //3 is # of connections that can listen in at once

                    int client_socket = accept(server_socket, NULL, NULL);
                    //sending message to client 
                    send(client_socket, server_message, sizeof(server_message), 0); //TODO update to be in loop that will activate every time user inputs a message
                    break;

                case 2:
                    //binding the socket to specified IP and port
                    bind(server_socket, (struct sockaddr*) &server_address[1], sizeof(server_address));

                    listen(server_socket, 3); //3 is # of connections that can listen in at once

                    int client_socket = accept(server_socket, NULL, NULL);
                    //sending message to client 
                    send(client_socket, server_message, sizeof(server_message), 0); //TODO update to be in loop that will activate every time user inputs a message
                    break;
                case 3:
                    //binding the socket to specified IP and port
                    bind(server_socket, (struct sockaddr*) &server_address[2], sizeof(server_address));

                    listen(server_socket, 3); //3 is # of connections that can listen in at once

                    int client_socket = accept(server_socket, NULL, NULL);
                    //sending message to client 
                    send(client_socket, server_message, sizeof(server_message), 0); //TODO update to be in loop that will activate every time user inputs a message
                    break;
                case 4:
                    //binding the socket to specified IP and port
                    bind(server_socket, (struct sockaddr*) &server_address[3], sizeof(server_address));

                    listen(server_socket, 3); //3 is # of connections that can listen in at once

                    int client_socket = accept(server_socket, NULL, NULL);
                    //sending message to client 
                    send(client_socket, server_message, sizeof(server_message), 0); //TODO update to be in loop that will activate every time user inputs a message
                    break;
                default:
                    printf("Something went wrong in break switch statement.\n");
                    break;
                }
            }
        }
        
        
    }
    return 0;
}