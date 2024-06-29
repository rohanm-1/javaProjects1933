// Written by Rohan Modalavalasa: modal005@umn.edu, and Aidan Math: math5600@umn.edu
import java.util.Scanner;
public class Game {
    static Board myBoard = new Board();
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        // Let's call odd numbers to be whites turn and even numbers to be blacks turn
        int count = 1;
        // declare player choices for starting/ending row and column
        int sRow;
        int sCol;
        int eRow;
        int eCol;
        Fen.load("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", myBoard);
        while(!myBoard.isGameOver()){
            System.out.println("Board: \n"+myBoard);
            if(count % 2 == 1){
                System.out.println("It is currently white's turn to play.");
                System.out.println("What is your move? (format: [start row] [start col] [end row] [end col])");
                sRow = s.nextInt();
                sCol = s.nextInt();
                eRow = s.nextInt();
                eCol = s.nextInt();
                // nested if statement to ensure only white pieces are moved
                if(myBoard.getPiece(sRow,sCol) != null && !myBoard.getPiece(sRow,sCol).getIsBlack()){
                    if(myBoard.movePiece(sRow, sCol, eRow, eCol)){
                        // Test if pawn promotion on every move
                        myBoard.getPiece(eRow,eCol).promotePawn(eRow, false);
                    }
                }
                else {
                    System.out.println("Illegal move. Skipping turn..");
                }
            }
            else{
                System.out.println("It is currently black's turn to play.");
                System.out.println("What is your move? (format: [start row] [start col] [end row] [end col])");
                sRow = s.nextInt();
                sCol = s.nextInt();
                eRow = s.nextInt();
                eCol = s.nextInt();
                // nested if statement to ensure only black pieces can move
                if(myBoard.getPiece(sRow,sCol) != null && myBoard.getPiece(sRow,sCol).getIsBlack()){
                    if(myBoard.movePiece(sRow, sCol, eRow, eCol)){
                        // Test if pawn promotion on every move
                        myBoard.getPiece(eRow,eCol).promotePawn(eRow, true);
                    }
                }
                else {
                    System.out.println("Illegal move. Skipping turn..");
                }
            }
            count++;
        }
        // Because we have exited the loop, we know the game is over
        // We will increment count one last time before we exit, so if count % 2 = 0, it was previously white's turn, meaning that white won, and vice versa
        if(count % 2 == 0){
            System.out.println("White has won the game!");
        }
        else{
            System.out.println("Black has won the game!");
        }
    }
}
