//used https://www.youtube.com/watch?v=LtXEMwSG5-8 as a simple example on how to set up a server and client connection
//used old code from CS3377 for user input
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


int main(int argc, char *argv[]){
    int const MESSAGE_SIZE = 256;

   //Clinet side
   {
        //creating a socket
        //List<Socket> SocketList = new List<Socket>();  //list for 3 socket connections for each server
        int network_socket;
        if((network_socket = socket(AF_INET, SOCK_STREAM, 0);) == 0)
        {
            perror("socket failed");
            exit(EXIT_FAILURE);
        }

        //giving address for socket
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
        
        //starting connection to other servers
        if((connect(network_socket,(struct sockaddr *) &server_address[0],sizeof(server_address[0]))) != 0)
        {
            printf("Unable to connect to %d\n",server_address[0].sin_addr.s_addr);
            exit(1);
        }
        if((connect(network_socket,(struct sockaddr *) &server_address[1],sizeof(server_address[1]))) != 0)
        {
            printf("Unable to connect to %d\n",server_address[1].sin_addr.s_addr);
            exit(1);
        }
        if((connect(network_socket,(struct sockaddr *) &server_address[2],sizeof(server_address[2]))) != 0)
        {
            printf("Unable to connect to %d\n",server_address[2].sin_addr.s_addr);
            exit(1);
        }

        //recieve data from servers
        while(true)
        {
            char server_message[MESSAGE_SIZE];
            if(network_socket != NULL)
            {
                recv(network_socket, &server_message, sizeof(server_message), 0);

                if(strcasecmp(server_message,"stop") === 0)
                {
                    printf("Message Recieved: Stop\n Closing socket connection...\n");
                    //closing socket
                    close(network_socket);
                    return 0;
                }

                //printing data from server
                if(server_message != NULL)
                    printf("Message Recieved: %s\n", server_message);
            }
        }

   }
    return 0;
}