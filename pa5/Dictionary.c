//-----------------------------------------------------------------------------
// Dictionary.c
// Binary Search Tree implementation of the Dictionary ADT
//-----------------------------------------------------------------------------

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "Dictionary.h"

const int tableSize =101;


// rotate_left()
// rotate the bits in an unsigned int
unsigned int rotate_left(unsigned int value, int shift) {
   int sizeInBits = 8*sizeof(unsigned int);
   shift = shift & (sizeInBits - 1);
   if ( shift == 0 )
      return value;
   return (value << shift) | (value >> (sizeInBits - shift));
}

// pre_hash()
// turn a string into an unsigned int
unsigned int pre_hash(char* input) { 
   unsigned int result = 0xBAE86554;
   while (*input) { 
      result ^= *input++;
      result = rotate_left(result, 5);
   }
   return result;
}

// hash()
// turns a string into an int in the range 0 to tableSize-1
int hash(char* key){
   return pre_hash(key)%tableSize;
}

// private types and functions ------------------------------------------------

// NodeObj
typedef struct NodeObj{
   char* key;
   char* value;
   struct NodeObj* next;
   //struct NodeObj* right;
} NodeObj;

// Node
typedef NodeObj* Node;

// newNode()
// constructor for private Node type
Node newNode(char* k, char* v) {
   Node N = malloc(sizeof(NodeObj));
   assert(N!=NULL);
   N->key = k;
   N->value = v;
   N->next = NULL;
   return(N);
}

// freeNode()
// destructor for private Node type
void freeNode(Node* pN){
   if( pN!=NULL && *pN!=NULL ){
      free(*pN);
      *pN = NULL;
   }
}

// deleteAll()
// deletes all Nodes in the List.
void deleteAll(Node N){
   if( N!=NULL ){
      deleteAll(N->next);
      freeNode(&N);
   }
}

// DictionaryObj
typedef struct DictionaryObj{
   Node* hashTable;
   int numPairs;
} DictionaryObj;

// findKey()
// returns the Node containing key k in the subtree rooted at R, or returns
// NULL if no such Node exists
Node findKey(Node R, char* k){
   if(R==NULL || strcmp(k, R->key)==0) 
      return R;
   else
      return findKey(R->next, k); 
}

// printInOrder()
// prints the (key, value) pairs belonging to the subtree rooted at R in order
// of increasing keys to file pointed to by out.
void printInOrder(FILE* out, Node R){
   if( R!=NULL ){
      fprintf(out, "%s %s\n", R->key, R->value);
      printInOrder(out, R->next);
   }
}


// public functions -----------------------------------------------------------

// newDictionary()
// constructor for the Dictionary type
Dictionary newDictionary(){
   Dictionary D = malloc(sizeof(DictionaryObj));
   assert(D!=NULL);
   D->hashTable = calloc(tableSize, (sizeof(Node))); 
   for(int i=0; i < tableSize; i++)
      D->hashTable[i] = NULL;
   D->numPairs = 0;
   return D;
}

// freeDictionary()
// destructor for the Dictionary type
void freeDictionary(Dictionary* pD){
   if( pD!=NULL && *pD!=NULL ){
      makeEmpty(*pD);
      free((*pD)->hashTable);
      free(*pD);
      *pD = NULL;
   }
}

// isEmpty()
// returns 1 (true) if D is empty, 0 (false) otherwise
// pre: none
int isEmpty(Dictionary D){
   if( D==NULL ){
      fprintf(stderr, 
         "Dictionary Error: calling isEmpty() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   return(D->numPairs==0);
}

// size()
// returns the number of (key, value) pairs in D
// pre: none
int size(Dictionary D){
   if( D==NULL ){
      fprintf(stderr, 
         "Dictionary Error: calling size() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   return(D->numPairs);
}

// lookup()
// returns the value v such that (k, v) is in D, or returns NULL if no 
// such value v exists.
// pre: none
char* lookup(Dictionary D, char* k){
   Node N = NULL;
   int hashValue;
   if( D==NULL ){
      fprintf(stderr, 
         "Dictionary Error: calling lookup() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   hashValue = hash(k);
   if(D->hashTable[hashValue] != NULL)
      N = findKey(D->hashTable[hashValue], k);
   
   return ( N==NULL ? NULL : N->value );
}

// insert()
// inserts new (key,value) pair into D
// pre: lookup(D, k)==NULL
void insert(Dictionary D, char* k, char* v){
   Node N;
   int hashValue;

   if( D==NULL ){
      fprintf(stderr, 
         "Dictionary Error: calling insert() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }

   hashValue = hash(k);
   if( findKey(D->hashTable[hashValue], k)!=NULL ){
      fprintf(stderr, 
         "Dictionary Error: cannot insert() duplicate key: \"%s\"\n", k);
      exit(EXIT_FAILURE);
   }
   N = newNode(k, v); 
   if( D->hashTable[hashValue] == NULL)
      D->hashTable[hashValue] = N;
   else{
      N->next = D->hashTable[hashValue];
      D->hashTable[hashValue] = N;
   }
   D->numPairs++;
}

// delete()
// deletes pair with the key k
// pre: lookup(D, k)!=NULL
void delete(Dictionary D, char* k){
   Node N, P;
   int hashValue;
   if( D==NULL ){
      fprintf(stderr, 
         "Dictionary Error: calling delete() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }

   hashValue = hash(k);
   N = findKey(D->hashTable[hashValue], k);
   if( N==NULL ){
      fprintf(stderr, 
         "Dictionary Error: cannot delete() non-existent key: \"%s\"\n", k);
      exit(EXIT_FAILURE);
   }
   P = D->hashTable[hashValue];
   if(strcmp(P->key,k)==0)
      D->hashTable[hashValue] = D->hashTable[hashValue]->next;
   else{
      while(strcmp(P->next->key,k)!=0) P = P->next;
      P->next = P->next->next;
   }
   freeNode(&N);
   D->numPairs--;
}

// makeEmpty()
// re-sets D to the empty state.
// pre: none
void makeEmpty(Dictionary D){
   for(int i=0; i<tableSize; i++){
      if(D->hashTable[i] != NULL){
         deleteAll(D->hashTable[i]);
         D->hashTable[i] = NULL;    
      } 
   }
   D->numPairs = 0;
}

// printDictionary()
// pre: none
// prints a text representation of D to the file pointed to by out
void printDictionary(FILE* out, Dictionary D){
   if( D==NULL ){
      fprintf(stderr, 
         "Dictionary Error: calling printDictionary() on NULL"
         " Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   for(int i = 0;i<tableSize; i++ ){
      if (D->hashTable[i] != NULL){
         printInOrder(out, D->hashTable[i]);
      }
   }  
}

