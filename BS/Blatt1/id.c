#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>


int main(void)
{
    uid_t U_ID= getuid();
    gid_t G_ID= getgid();
    printf("user: %d \ngroup: %d\n",U_ID,G_ID);
    return 0;
}
