#include<stdio.h>
int main()
{
	int a,b,c;
	printf("Enter the numbers a,b and c: ");
	scanf("%d %d %d", &a,&b,&c);
	if(a>=b && a>=c)
	printf(" NUMBER A IS GREATER:",a);
	else if(b>=c && b>=a)
	printf("NUMBER B IS GREATER:",b);
	else
	printf("NUMBER C  IS GREATER:",c);
	return 0;
	
}


