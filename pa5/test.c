#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
typedef struct test {
	int a;	
} testObject;
typedef testObject* list;
int main()
{
    printf("Hello, World!\n");
    
    list* b = calloc(5, (sizeof(testObject)));
    //int* a = malloc(4,sizeof(double))
    
    list c = malloc(sizeof(testObject));
    
	b[4] = c;
    return 0;
}