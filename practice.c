#include<stdio.h>
void main()
{
	int i, t,n,fact=1;
	printf("enter the :");
	scanf("%d",&n); 
    for(i=1;i<=n;i++) 
    { 
    fact=fact*i;
    }
	printf("Factorial of %d is %d",n,fact);
	
}

