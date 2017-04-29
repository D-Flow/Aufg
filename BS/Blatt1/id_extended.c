#include <unistd.h>
#include <sys/types.h>
#include <stdio.h>
#include <sys/types.h>
#include <pwd.h>


int main(int n,char* c[])   //c array of char*; *c ist programm name
{
    uid_t U_ID= getuid();
    gid_t G_ID= getgid();
    if(n==1){
        U_ID= getuid();
        G_ID= getgid();
    }
    if(n==2){
        //printf("Checking user : %s\n",c[1]);      //Testmethode
        struct passwd* STR_PWD=getpwnam(c[1]);
        U_ID=STR_PWD->pw_uid;
        G_ID=STR_PWD->pw_gid;
        // n=2 also ist min 1 parameter gegeben
    }
    printf("user: %d \ngroup: %d\n",U_ID,G_ID);
    return 0;
}
