#include<stdio.h>
void main()
{
	int count,n;
	float sum =0.0;
	print("ENTER THE VALUE OF n.\n");
	scanf("%d",&n);
	for(count=1;count<=n;count++)
	{
		sum =sum+1.0/count;
	}
	printf("\nsum=%f",sum);
}
