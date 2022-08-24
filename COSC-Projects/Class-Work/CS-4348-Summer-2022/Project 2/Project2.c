// used https://www.youtube.com/watch?v=LtXEMwSG5-8 as a simple example on how to set up a server and client connection
// used old code from CS3377 for user input
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <netinet/in.h>
#include <pthread.h>
#include <unistd.h>
#include <string.h>

static pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
char* getinput(char *buffer, size_t buflen);
void* checkSocket(void *arg);
void* client(void *arg);
void* server(void *arg);

////////////////////////////////////////////////////////////////////////////////////////////////////////////
int main(int argc, char *argv[])
{
    if(argc != 2)
    {
        printf("only 1 argument is allowed and it must be a number\n %d",argc);
        return 1;
    }
    int given_argument = atoi(argv[1]);
    void *null1, *null2;
    pthread_t client_thread, server_thread;
    //client thread
    printf("Launching Client\n");
    pthread_create(&client_thread, NULL, client, &given_argument);
    
    //server thread   
    printf("Launching Server\n");
    pthread_create(&server_thread, NULL, server, &given_argument);
    
    //waiting for server thread to finish
    pthread_join(server_thread,NULL);
    
    
    printf("Closed server\n");
    //waiting for client thread to finish
    pthread_join(client_thread, NULL);
    printf("Closed client\n");
      
    return 0;
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////
char* getinput(char *buffer, size_t buflen)
{
    return fgets(buffer, buflen, stdin);
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////
void* checkSocket(void *arg) //connects to socekt and prints output from socket until it is closed
{
    int *network_socket = (int *)arg;
    char server_message[256];
    while(recv(*network_socket, &server_message, sizeof(*server_message),0))
        {
            pthread_mutex_lock(&mutex);
            printf("%s", server_message);
            memset(server_message, 0 ,256);
            pthread_mutex_unlock(&mutex);
        }
        close(network_socket);
        return NULL;

}
////////////////////////////////////////////////////////////////////////////////////////////////////////////
void* client(void *arg)
{   
  printf("Client launched\n");
  int *given_argument = (int *)arg;
  int network_socket;
  int network_socket2;
  int network_socket3;
  int network_socket4;
  char server_response[256];
  int socket_count = 3;
  //waits 10 seconds for servers to full set up
  sleep(10);
  if(*given_argument != 1)
  {
      //1st connection//
      network_socket = socket(AF_INET,SOCK_STREAM,0);
      struct sockaddr_in server_address;
      server_address.sin_family=AF_INET;
      server_address.sin_port=htons(6999 + *given_argument); //represents the client at each server

      server_address.sin_addr.s_addr=inet_addr("10.176.69.32");
      int connection_status= connect(network_socket, (struct sockaddr*) &server_address, sizeof(server_address));
          if(connection_status == -1)
            printf("error Client connecting to 10.176.69.32\n");
  }
  
  if(*given_argument != 2)
  {
      //2nd connection//
      network_socket2 = socket(AF_INET,SOCK_STREAM,0);
      struct sockaddr_in server_address2;
      server_address2.sin_family=AF_INET;
      server_address2.sin_port=htons(6999 + *given_argument); //represents the client at each server

      server_address2.sin_addr.s_addr=inet_addr("10.176.69.33");
      int connection_status2= connect(network_socket2, (struct sockaddr*) &server_address2, sizeof(server_address2));
          if(connection_status2 == -1)
            printf("error Client connecting to 10.176.69.33\n");
  }

  if(*given_argument != 3)
  {
      //3rd connection//
      network_socket3 = socket(AF_INET,SOCK_STREAM,0);
      struct sockaddr_in server_address3;
      server_address3.sin_family=AF_INET;
      server_address3.sin_port=htons(6999 + *given_argument); //represents the client at each server

      server_address3.sin_addr.s_addr=inet_addr("10.176.69.34");
      int connection_status3= connect(network_socket3, (struct sockaddr*) &server_address3, sizeof(server_address3));
          if(connection_status3 == -1)
            printf("error Client connecting to 10.176.69.34\n");
  }

  if(*given_argument != 4)
  {
      //4th connection//
      network_socket4 = socket(AF_INET,SOCK_STREAM,0);
      struct sockaddr_in server_address4;
      server_address4.sin_family=AF_INET;
      server_address4.sin_port=htons(6999 + *given_argument); //represents the client at each server

      server_address4.sin_addr.s_addr=inet_addr("10.176.69.35");
      int connection_status4= connect(network_socket4, (struct sockaddr*) &server_address4, sizeof(server_address4));
          if(connection_status4 == -1)
            printf("error Client connecting to 10.176.69.35\n");
  }

  //creates 4 more threads so all connections can be checked at the same time
  pthread_t socket1_thread, socket2_thread, socket3_thread, socket4_thread;
  if(*given_argument != 1)
    pthread_create(&socket1_thread, NULL, checkSocket,&network_socket);
  if(*given_argument != 2)
    pthread_create(&socket2_thread, NULL, checkSocket,&network_socket2);
  if(*given_argument != 3)
    pthread_create(&socket3_thread, NULL, checkSocket,&network_socket3);
  if(*given_argument != 4)
    pthread_create(&socket4_thread, NULL, checkSocket,&network_socket4);
  
  if(*given_argument != 1)
    pthread_join(socket1_thread, NULL);
  if(*given_argument != 2)
    pthread_join(socket2_thread, NULL);
  if(*given_argument != 3)
    pthread_join(socket3_thread, NULL);
  if(*given_argument != 4)  
    pthread_join(socket4_thread, NULL);
  
  //closes the sockets before exiting out
  printf("closing client\n");
  if(*given_argument != 1)
      close(network_socket);
  if(*given_argument != 2)
      close(network_socket2);
  if(*given_argument != 3)
      close(network_socket3);
  if(*given_argument != 4)
      close(network_socket4);
  return NULL;
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////

void* server(void *arg)
{   
    printf("Server launched\n");   
    int *given_argument = (int *)arg;
    int server_socket1;
    int server_socket2;
    int server_socket3;
    int server_socket4;
    int client_socket1;
    int client_socket2;
    int client_socket3;
    int client_socket4;
    char send_message[256];

    if(*given_argument != 1)
    {
        ///client 1 ///
        //create the server socket
        server_socket1 = socket(AF_INET, SOCK_STREAM,0);
        //define the server address
        struct sockaddr_in server_address1;
        server_address1.sin_family=AF_INET;
        server_address1.sin_port =htons(7000);
        server_address1.sin_addr.s_addr = INADDR_ANY;

        //bind the socket to our pecificed IP and port
        bind(server_socket1, (struct sockaddr*) &server_address1, sizeof(server_address1));

        listen(server_socket1,5);
    }

    if(*given_argument != 2)
    {
        ///client 2 ///
        //create the server socket
        server_socket2 = socket(AF_INET, SOCK_STREAM,0);
        //define the server address
        struct sockaddr_in server_address2;
        server_address2.sin_family=AF_INET;
        server_address2.sin_port =htons(7001);
        server_address2.sin_addr.s_addr = INADDR_ANY;

        //bind the socket to our pecificed IP and port
        bind(server_socket2, (struct sockaddr*) &server_address2, sizeof(server_address2));

        listen(server_socket2,5);
    }

    if(*given_argument != 3)
    {
        ///client 3 ///
        //create the server socket
        server_socket3 = socket(AF_INET, SOCK_STREAM,0);
        //define the server address
        struct sockaddr_in server_address3;
        server_address3.sin_family=AF_INET;
        server_address3.sin_port =htons(7002);
        server_address3.sin_addr.s_addr = INADDR_ANY;

        //bind the socket to our pecificed IP and port
        bind(server_socket3, (struct sockaddr*) &server_address3, sizeof(server_address3));

        listen(server_socket3,5);
    }


    if(*given_argument != 4)
    {
        ///client 4 ///
        //create the server socket
        server_socket4 = socket(AF_INET, SOCK_STREAM,0);
        //define the server address
        struct sockaddr_in server_address4;
        server_address4.sin_family=AF_INET;
        server_address4.sin_port =htons(7003);
        server_address4.sin_addr.s_addr = INADDR_ANY;

        //bind the socket to our pecificed IP and port
        bind(server_socket4, (struct sockaddr*) &server_address4, sizeof(server_address4));

        listen(server_socket4,5);
    }
    //waits for client side to connect to sockets
    if(*given_argument != 1)
        {
            client_socket1 = accept(server_socket1,NULL,NULL);
            printf("Accepted Client 1\n");
        }
    if(*given_argument != 2)
        {
            client_socket2 = accept(server_socket2,NULL,NULL);
            printf("Accepted Client 2\n");
        }
    if(*given_argument != 3)
        {
            client_socket3 = accept(server_socket3,NULL,NULL);
            printf("Accepted Client 3\n");
        }
    if(*given_argument != 4)
        {
            client_socket4 = accept(server_socket4,NULL,NULL);
            printf("Accepted Client 4\n");
        }

    ///getting input ///
    printf("Ready for Input\n");
    while(1 == 1)
    {
        getinput(send_message, sizeof(send_message));
        if(send_message[0] == 's' && send_message[1] == 't' && send_message[2] == 'o' && send_message[3] == 'p')
        {
            if(*given_argument != 1)
                close(client_socket1);
            if(*given_argument != 2)
                close(client_socket2);
            if(*given_argument != 3)
                close(client_socket3);
            if(*given_argument != 4)
                close(client_socket4);
            printf("closing server\n");
            return NULL;
        }
        else if(send_message[0] == 's' && send_message[1] == 'e' && send_message[2] == 'n' && send_message[3] == 'd' && send_message[4] == ' ')
        {
            if(send_message[5] == '0')
            {
              if(*given_argument != 1)
                   send(client_socket1, send_message, sizeof(send_message), 0);
              if(*given_argument != 2)
                   send(client_socket2, send_message, sizeof(send_message), 0);
              if(*given_argument != 3)
                   send(client_socket3, send_message, sizeof(send_message), 0);
              if(*given_argument != 4)
                   send(client_socket4, send_message, sizeof(send_message), 0);
            }
            else if(send_message[5] == '1')
                if(*given_argument != 1)
                    send(client_socket1, send_message, sizeof(send_message), 0);
                else   
                    printf("Can not send message to self.\n");
            else if(send_message[5] == '2')
                if(*given_argument != 2)
                    send(client_socket2, send_message, sizeof(send_message), 0);
                else   
                    printf("Can not send message to self.\n");
            else if(send_message[5] == '3')
                if(*given_argument != 3)
                    send(client_socket3, send_message, sizeof(send_message), 0);
                else   
                    printf("Can not send message to self.\n");
            else if(send_message[5] == '4')
                if(*given_argument != 4)
                    send(client_socket4, send_message, sizeof(send_message), 0);
                else   
                    printf("Can not send message to self.\n");
            else
                printf("Eneter a number between 0-4\n");
        }
        else
            printf("Incorrect command use exit or send [0-4]\n");
    
    memset(send_message, 0 ,256);
    }
}