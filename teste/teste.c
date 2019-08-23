#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char *argv[]){

    char msg[10];
    scanf("%s",msg);
    printf("%s\n",msg);
    int x = strcmp(msg,"oi");
    if(x==0){
        printf("igual");
    }

    
}