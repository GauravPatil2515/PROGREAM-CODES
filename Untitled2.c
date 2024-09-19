#include<stdio.h>
#include<conio.h>
void main()
{
 float celsius,fahrenheit; 
 printf("\n Enter the Temparature in Celsius : "); 
 scanf("%f",&celsius);
 fahrenheit = (1.8 * celsius) + 32;
 printf("\n Temperature in Fahrenheit : %f ",fahrenheit);
 getch();
}
