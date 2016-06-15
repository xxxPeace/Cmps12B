//-----------------------------------------------------------------------------
// DictionaryTest.c
// Test client for the Dictionary ADT
//-----------------------------------------------------------------------------

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"Dictionary.h"

int main(int argc, char* argv[]){
	Dictionary A = newDictionary();
	char* k;
	char* v;
	char* array1[] = {"one","two","three","four","five","six","seven","eight","nine","ten"};
	char* array2[] = {"difference","always","memory","galumph","happy","sad","results","assignment","storing", "strncat"};
	printf("Insert one key and value:\n");
    insert(A,array1[0],array2[0]);
    printf("A is empty? %s\n", (isEmpty(A)?"true":"false"));
    printf("Size of A is: %d\n", size(A));
    printDictionary(stdout, A);
    makeEmpty(A);
    printf("Make empty A\n");
    printf("A is empty? %s\n", (isEmpty(A)?"true":"false"));
    printf("Size of A is: %d\n", size(A));

    printf("Insert 10 keys and values\n");
    for(int i=0; i<10; i++){
      insert(A, array1[i], array2[i]);
    }
    printf("A is empty? %s\n", (isEmpty(A)?"true":"false"));
    printf("Size of A is: %d\n", size(A));

    for(int i=0; i<10; i++){
      k = array1[i];
      v = lookup(A, k);
      printf("key=\"%s\" %s\"%s\"\n", k, (v==NULL?"not found ":"value="), v);
    }

    delete(A, "one");
    delete(A, "five");
    delete(A, "ten");
    // delete(A, "one");  // error: key not found
    printf("Delete the keys one, five and ten...\n");
    printf("A is empty? %s\n", (isEmpty(A)?"true":"false"));
    printf("Size of A is: %d\n", size(A));
    for(int i=0; i<10; i++){
      k = array1[i];
      v = lookup(A, k);
      printf("key=\"%s\" %s\"%s\"\n", k, (v==NULL?"not found ":"value="), v);
    }
    
    makeEmpty(A);
    printf("Make empty A\n");
    printf("A is empty? %s\n", (isEmpty(A)?"true":"false"));
    printf("Size of A is: %d\n", size(A));

    freeDictionary(&A);
    return(EXIT_SUCCESS);



}