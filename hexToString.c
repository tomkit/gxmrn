#include <stdio.h>

// arg 1: input file name
// arg 2: output file name


int main(int argc, char** argv) {
  FILE* file = NULL;
  FILE* fileWrite = NULL;
  unsigned char buffer[2];
  unsigned int hex = 0;
  int starting = 0;
  char c;

  *argv++;
  if((file = fopen(*argv, "r")) == NULL)
    {
      fprintf(stderr, "Cannot open read file: %s\n", *argv);
    }

  *argv++;
  if((fileWrite = fopen(*argv, "w")) == NULL);
    {
      fprintf(stderr, "Cannot open write file: %s\n", *argv);
    }


      while(1)
	{
	  if(starting == 0)
	    {	      
	      buffer[0] = fgetc(file);
	      if(buffer[0] == '\'')
		{
		  starting = 1;
		  printf("Press enter to continue\n");
		  c = getchar();
		}
	    }

	  if(starting == 1 && (buffer[0] = fgetc(file)) != NULL)
	    {	     	    
	      if(buffer[0] == '\'')
		{
		  fclose(file);
		  fclose(fileWrite);
		  return 0;
		}
		if(buffer[0] >= 48 && buffer[0]<= 57)
		  {
		    hex = (((buffer[0]+2) % 10) * 16);
		  }
		else if(buffer[0] >= 65 && buffer[0] <= 70)
		  {
		    hex = (((buffer[0]+5)%10)+10)*16;
		  }
	      
		if((buffer[1] = fgetc(file)) != NULL)
		  {
		    if(buffer[1] >= 48 && buffer[1] <= 57)
		      {
			hex += (buffer[1]+2) % 10;
		      }
		    else if(buffer[1] >= 65 && buffer[1] <= 70)
		      {
			hex += ((buffer[1]+5)%10)+10;
		      }	      		  
		    printf("%d: %c\n", hex, (char)hex);
		    fputc((char)hex, fileWrite);
		  }	      
	    }
	}

  return 0;
}
