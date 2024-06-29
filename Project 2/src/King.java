// Written by Rohan Modalavalasa: modal005@umn.edu, and Aidan Math: math5600@umn.edu
public class King {
    private int row;
    private int col;
    private boolean isBlack;
    public King(int row, int col, boolean isBlack){
        // Initialize attributes
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol){
        // check if its remotely a legal move and if it's a legal adjacent move
        return board.verifySourceAndDestination(this.row, this.col, endRow, endCol, isBlack) && board.verifyAdjacent(this.row, this.col, endRow, endCol);
    }
}
