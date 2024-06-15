#include<stdio.h>

int arm(int num)
{
    int rem , sum=0;
    while(num!=0)
    {
        rem=num%10;
        sum=sum+rem*rem*rem;
        num =num/10;
    }
    return sum;
}

void main()
{
    int num ,sum;
    printf("enter the number:");
    scanf("%d",&num);
    sum=arm(num);
    if(num==sum)
    {
        printf("%d is arm", num);
    }
    else
    {
        printf("%d is not arm", num);
    }
}

