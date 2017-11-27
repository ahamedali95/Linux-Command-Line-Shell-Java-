//package myshell;//

//AHAMED ABBAS//
//CMP 426//
//09/16/2016//
//This class will be run on the prompt of the MyShell program. This program takes in one mandatory command line argument and two optional 
//command line arguments. Given the fact that the optional command line arguments should be able to be entered in any order, the program prints out
//certain lines according to the arguments.

public class CommandLineTest
{
    public static void main(String[] args)
    {
        int startFlag = 0;
        int endFlag = 0;
        int middleFlag = 0;
        
        for(int i = 0; i < args.length; i++)                             //This for-loop goes through the string array//
        {
           if(args[i].equals("-e"))                                      //If there is a string '-e' as an argument//
           {
               endFlag = 1;                                              //Set the variable endFlag to 1//
           }
           if(args[i].equals("-s"))                                      //If there is a string '-s' as an argument//
           {
               startFlag = 1;                                            //Set the variable startFlag to 1//
           }
           if(i == args.length-1)                                        //
           {
               middleFlag = Integer.parseInt(args[i]);                   //Getting a string digit from the array and converting it to an integer//
           }
        }
        
        //printing the appropriate lines
        
        if(startFlag > 0)
        {
            System.out.println("Starting...");
        }
        
        if(middleFlag > 0)
        {
            for(int i = 0; i < middleFlag; i++)
            {
                System.out.println("middle");
            }
        }
        
        if(endFlag > 0)
        {
            System.out.println("Ending...");
        }  
    } 
} 