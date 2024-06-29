// Written by Rohan Modalavalasa: modal005, and Aidan Math: math5600@umn.edu
public class Bishop {
    private int row;
    private int col;
    private boolean isBlack;
    public Bishop(int row, int col, boolean isBlack){
        // Initialize attributes
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol){
        // check if it's remotely a legal move and check if it's a legal diagonal move
        return board.verifySourceAndDestination(this.row, this.col, endRow, endCol, isBlack) && board.verifyDiagonal(this.row, this.col, endRow, endCol);
    }

}
