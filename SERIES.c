#include <stdio.h>

int main() 
{
    int rows, i, j;

    printf("Enter the number of rows: ");
    scanf("%d", &rows);

    for (i = 1; i <= rows; ++i)
	 {
        for (j = 1; j <= i; ++j)
		 {
            printf("%d ", i);
        }
        printf("\n");
    }

    return 0;
}

