#include <stdio.h>
#include <math.h>

// Function to calculate simple interest
float simpleInterest(float principal, float rate, int time) 
{
    return (principal * rate * time) / 100;
}

// Function to calculate compound interest
float compoundInterest(float principal, float rate, int time) 
{
    return principal * (pow((1 + rate / 100), time)) - principal;
}

int main() 
{
    float principal, rate;
    int time;

    // Input principal amount, rate of interest, and time period
    printf("Enter principal amount: ");
    scanf("%f", &principal);

    printf("Enter rate of interest (in percentage): ");
    scanf("%f", &rate);

    printf("Enter time period (in years): ");
    scanf("%d", &time);

    // Calculate simple interest and compound interest
    float simple_interest = simpleInterest(principal, rate, time);
    float compound_interest = compoundInterest(principal, rate, time);

    // Print the results
    printf("\nSimple Interest: %.2f\n", simple_interest);
    printf("Compound Interest: %.2f\n", compound_interest);

    return 0;
}

