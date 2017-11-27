//package myshell;//

//AHAMED ABBAS//
//CMP 426//
//09/16/2016//
//MyShell: My program acts like a basic UNIX interactive command line. My program prompts the user to enter a UNIX command; executes it and prints the
//output of the child process. Moreover, my program can also run in bash mode where the user enters the command 'batch' followed by a file name which
//contains the UNIX commands. My program looks for the entered file name; reads it and executes the commands and returns to interactive mode again upon 
//completion.

import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyShell 
{
    public static void UNIXshell()
    {
         
      Scanner read = new Scanner(System.in);                        //Creating an scanner object called read//
      boolean x = true;
      
      ArrayList<Process> processes = new ArrayList<Process>();      //Creating an array list called processes to store all the processes//
      
      while(x)
      {
        System.out.println("prompt>");                              //Prompting the user to enter a command//
        String readCommand = read.nextLine(); 
        
        readCommand = handleWhitespaces(readCommand);               //This method handles extra white spaces//
          
        if(isExit(readCommand))                                     //This method handles the case 'exit'//
        {
            x = false;                                              //Program will be aborted upon entering 'exit'//
            printExit();                                            //This method prints the program exiting message to the screen//
        }        
        else
        	if(isEmptystring(readCommand))                          //This method handles the case where the user presses 'enter' without inputting any command//
        	{
        		doNothing();                                        //This method will do nothing in case if there is no command from the user//
        	}         
    	else 
    		if(isBatchcommand(readCommand))                         //This method looks for the 'batch' command//
    		{
    			try
    			{
    				String fileName = readCommand.substring(6);     //Getting the file name only//
    				File file = new File(fileName); 

    				BufferedReader br = new BufferedReader(new FileReader(file));
    				String line;
    				try
    				{
    					while((line = br.readLine()) != null)       //While there exits a string, go ahead and execute//
    					{
    						Process k = Runtime.getRuntime().exec(line); //Creating process called p where the command line argument is being executed by exec()//
    						processes.add(k);                       //Adding the current process to the array list//
    						printChildoutput(k);                    //This method prints the output of the child process that is being executed//
    					}
    					br.close();
    				}
    				catch(Exception e)
    				{
    					e.printStackTrace();
    				}
    			}

    			catch(FileNotFoundException e)                      //Catching the error if the file was not found//
    			{
    				System.out.println("No File is found");         //Printing the error message//
    			}

    		 }
    	
		else                                                        //Handling cases other than 'exit' and the user pressing 'enter' without inputting any command//
		{  
			try                                                     //Handling executable commands//
			{
				Process p = Runtime.getRuntime().exec(readCommand); //Creating process called p where the command line argument is being executed by exec()//
				processes.add(p);                                   //Adding the current process to the array list//
				printChildoutput(p);                                //This method prints the output of the child process that is being executed//
			}
			catch(Exception e)                                      //Handling non executable and/or nonsensical commands//
			{	
				errorExecutionmessage(readCommand);                 //This method prints a message informing the user that the command entered is not executable..
			}
		}
 
        for(Process p : processes)                                  //This for-loop goes through all the processes in the array list//
        {
     	    try
    	    {
    		   p.waitFor();                                         //This method waits until the child process has finished//
    	    }
	        catch(InterruptedException e)                           //Handing the Interrupted Exception error//
    	    {
               e.printStackTrace();                                 //Printing the error message//
    	    }
        }
      
      } 
      read.close();  
}  
    
     public static String handleWhitespaces(String command)
      {
        command = command.trim();                                   //Removing all the white spaces//
        command = command.replaceAll("\\s+", " ");
        return command;
      }
     
     public static boolean isExit(String readCommand)
     {
    	 return (readCommand.equals("exit"));
     }
     
     public static void printExit()
     {
         System.out.println("Exiting...");
     }
     
     public static boolean isEmptystring(String readCommand)
     {
    	 return (readCommand.equals(""));
     }
     
     public static void doNothing()
     {
         
     }
     
     public static boolean isBatchcommand(String readCommand)
     {
    	 return ((readCommand.length() >=5) && (readCommand.substring(0,5).equals("batch")));
     }
     
     public static void printChildoutput(Process k) throws IOException 
     {
         InputStream is = k.getInputStream();
         BufferedReader buff = new BufferedReader(new InputStreamReader(is)); //Reading text from the input stream//
         String line;
         
         while((line = buff.readLine()) != null)                         //While there exits a string, go ahead and print the 'line' which is the output of the executed command//
         {
        	System.out.println(line);
         }
     }
     
     public static void errorExecutionmessage(String command)
     {
         System.out.println("Error executing: " + command);
     }
     
     public static void main(String[] args)
     {
    	 MyShell.UNIXshell();
     }
}