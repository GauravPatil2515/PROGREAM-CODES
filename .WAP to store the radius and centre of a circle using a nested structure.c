#include<stdio.h>
struct circle
{
	float radius;
	struct 
	{
		float x,y;
	}centre;
};

void main()
{
	struct circle c;
	printf("enter the radius and x and y co ordintes of centre of circle:");
	scanf("%f%f%f",&c.radius,&c.centre.x,&c.centre.y);
	printf("circle info \n radius=%f\ncentre coordinte:%f,%f",c.radius,c.centre.x,c.centre.y);
	
}
