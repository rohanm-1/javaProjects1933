//Import Section
import java.util.Random;
import java.util.Scanner;

/*
 * Provided in this class is the neccessary code to get started with your game's implementation
 * You will find a while loop that should take your minefield's gameOver() method as its conditional
 * Then you will prompt the user with input and manipulate the data as before in project 2
 * 
 * Things to Note:
 * 1. Think back to project 1 when we asked our user to give a shape. In this project we will be asking the user to provide a mode. Then create a minefield accordingly
 * 2. You must implement a way to check if we are playing in debug mode or not.
 * 3. When working inside your while loop think about what happens each turn. We get input, user our methods, check their return values. repeat.
 * 4. Once while loop is complete figure out how to determine if the user won or lost. Print appropriate statement.
 */

public class main{
    //NEED DEBUG TO PRINT AND PLAY THE GAME
    //NEED TO DISPLAY TOTAL NUMBER OF FLAGS
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = " ";
        Random rand = new Random();
        //Keep looping until the user enters a valid difficulty level
        while (input.equals("Easy")!= true && input.equals("Normal")!= true && input.equals("Hard")!= true) {
            System.out.println("Please select a difficulty: 'Easy' 'Normal' 'Hard'");
            input = s.nextLine();//If user enters an invalid input, prompt again
    
            if (!input.equals("Easy")&&!input.equals("Normal") && !input.equals("Hard")) {
                System.out.println("Invalid input. Please enter 'Easy' 'Normal' 'Hard'.");
            }
    
        }
        Minefield m;
        if (input.equals("Easy")) {
            m = new Minefield(5, 5, 5);
            m.createMines(rand.nextInt(5), rand.nextInt(5), 5);
        }else if (input.equals("Normal")) {
            m = new Minefield(8, 8, 10);
            m.createMines(rand.nextInt(8), rand.nextInt(8), 10);
        }else{
            m = new Minefield (10,10,20);
            m.createMines(rand.nextInt(10), rand.nextInt(10), 20);
        }
        m.evaluateField();
        System.out.println(m);
        int x;
        int y;
        System.out.println("Would you like to enter debug mode? type 1: yes, any other number: no");
        int debug = s.nextInt();
        // we only call debug once to save memory usage from creating more deepcopies of the same board
        if(debug == 1){
            m.debug();
        }
        System.out.println("Enter an x and y coordinate to reveal the starting area (Remaining: "+ m.getFlags()+"): [x] [y] [f (-1, else)]");
        x = s.nextInt();
        y = s.nextInt();
        // not accounting for if the guess is a mine, NEED FIX (change revealStartingArea?)
        m.revealStartingArea(x, y);
    
        while(!m.gameOver()){
            System.out.println(m);
            System.out.println("Enter a coordinate (Remaining: "+ m.getFlags()+"): [x] [y]");
            int xcor = 0;
            int ycor = 0;
            xcor = s.nextInt();
            ycor = s.nextInt();
            System.out.println("Would you like to place a flag? Enter 1 for yes, -1 for no: ");
            int flag = s.nextInt();
            if(flag == 1){
                m.guess(xcor,ycor,true);
            }
            else if(flag == -1){
                m.guess(xcor, ycor, false);
            }
            else{
                //assumption
                System.out.println("Because incorrect input, no flag");
                m.guess(xcor, ycor, false);
            }
            System.out.println("Would you like to place a flag? Enter 1 for yes, -1 for no: ");

        }
        System.out.println(m);
    }
}

