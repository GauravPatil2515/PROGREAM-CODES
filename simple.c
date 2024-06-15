#include<stdio.h>
#include<math.h>

float simpleinterest(float principal,float time ,float rate)
{
	return(principal * time*rate)/100;

}
float compoundinterest(float principal,float time,float rate)
{
	//return principal* (pow((1+rate /100), time )) - principal);
	return principal* (pow((1 + rate / 100), time)) - principal;
}

int main()
{
	float principal ,rate,time;
	
	printf("enter the principal amount:");
	scanf("%f",&principal);
	
	printf("enter the time:");
	scanf("%f",&time);
	
	printf("enter the rate:");
	scanf("%f",&rate);
	
	
	float simple_interest = simpleinterest(principal ,time,rate);
	float compound_interestv= compoundinterest(principal,time,rate);
	
	printf("\n simple interest: %.2f ",simpleinterest);
	printf("\n compound interest: %.2f ",compoundinterest);
}
