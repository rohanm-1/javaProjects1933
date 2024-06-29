// Written by Rohan Modalavalasa, modal005 and Aidan Math math5600
import java.util.Scanner;
public class Piece {
    // Create Instance Variables (char not private to access in other classes)
    char character;
    private int row;
    private int col;
    private boolean isBlack;
    /**
     * Constructor.
     * @param character     The character representing the piece.
     * @param row           The row on the board the piece occupies.
     * @param col           The column on the board the piece occupies.
     * @param isBlack       The color of the piece.
     */
    public Piece(char character, int row, int col, boolean isBlack) {
        this.character = character;
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    /**
     * Determines if moving this piece is legal.
     * @param board     The current state of the board.
     * @param endRow    The destination row of the move.
     * @param endCol    The destination column of the move.
     * @return If the piece can legally move to the provided destination on the board.
     */
    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        switch (this.character) {
            case '\u2659':
            case '\u265f':
                Pawn pawn = new Pawn(row, col, isBlack);
                return pawn.isMoveLegal(board, endRow, endCol);
            case '\u2656':
            case '\u265c':
                Rook rook = new Rook(row, col, isBlack);
                return rook.isMoveLegal(board, endRow, endCol);
            case '\u265e':
            case '\u2658':
                Knight knight = new Knight(row, col, isBlack);
                return knight.isMoveLegal(board, endRow, endCol);
            case '\u265d':
            case '\u2657':
                Bishop bishop = new Bishop(row, col, isBlack);
                return bishop.isMoveLegal(board, endRow, endCol);
            case '\u265b':
            case '\u2655':
                Queen queen = new Queen(row, col, isBlack);
                return queen.isMoveLegal(board, endRow, endCol);
            case '\u265a':
            case '\u2654':
                King king = new King(row, col, isBlack);
                return king.isMoveLegal(board, endRow, endCol);
            default:
                return false;
        }
    }

    /**
     * Sets the position of the piece.
     * @param row   The row to move the piece to.
     * @param col   The column to move the piece to.
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Return the color of the piece.
     * @return  The color of the piece.
     */
    public boolean getIsBlack() {
        return isBlack;
    }

    /**
     * Handle promotion of a pawn.
     * @param row Current row of the pawn
     * @param isBlack Color of the pawn
     */
    public void promotePawn(int row, boolean isBlack) {
        Scanner s = new Scanner(System.in);
        String piece = "";
        // Only 1 iteration because if the pawn can promote row can only be 2 values
        for(int i = 0; i < 7; i++){
            // if row = 0, the piece that must be there is a white pawn
            // This nested if statement accounts for the white piece promotion
            if(Game.myBoard.getPiece(row,i) != null){
                if(row == 0 && Game.myBoard.getPiece(row, i).character == '\u2659' && !isBlack){
                    System.out.println("Promote by typing knight, bishop, queen, or rook");
                    piece = s.nextLine();
                    if(piece.equals("knight")){
                        Game.myBoard.setPiece(row, i, new Piece('\u2658', row, i, isBlack));
                    }
                    else if(piece.equals("bishop")){
                        Game.myBoard.setPiece(row, i, new Piece('\u2657', row, i, isBlack));
                    }
                    else if(piece.equals("rook")){
                        Game.myBoard.setPiece(row, i, new Piece('\u2656', row, i, isBlack));
                    }
                    else if(piece.equals("queen")){
                        Game.myBoard.setPiece(row, i, new Piece('\u2655', row, i, isBlack));
                    }
                    else{
                        System.out.println("Enter a valid piece to promote to.");
                        promotePawn(row, isBlack);
                    }
                }
            }
            // if row = 7, the piece that must be there is a black pawn
            // This nested if statement accounts for the black piece promotion
            if(Game.myBoard.getPiece(row,i) != null){
                if(row == 7 && Game.myBoard.getPiece(row, i).character == '\u265f' && isBlack){
                    System.out.println("Promote by typing knight, bishop, queen, or rook");
                    piece = s.nextLine();
                    if(piece.equals("knight")){
                        Game.myBoard.setPiece(row, i, new Piece('\u265e', row, i, isBlack));
                    }
                    else if(piece.equals("bishop")){
                        Game.myBoard.setPiece(row, i, new Piece('\u265d', row, i, isBlack));
                    }
                    else if(piece.equals("rook")){
                        Game.myBoard.setPiece(row, i, new Piece('\u265c', row, i, isBlack));
                    }
                    else if(piece.equals("queen")){
                        Game.myBoard.setPiece(row, i, new Piece('\u265b', row, i, isBlack));
                    }
                    else{
                        System.out.println("Enter a valid piece to promote to.");
                        promotePawn(row, isBlack);
                    }
                }
            }
        }
    }


    /**
     * Returns a string representation of the piece.
     * @return  A string representation of the piece.
     */
    public String toString() {
        String result = "";
        result = result + this.character;
        return result;
    }
}
