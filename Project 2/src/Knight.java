// Written by Rohan Modalavalasa: modal005@umn.edu, and Aidan Math: math5600@umn.edu
public class Knight {
    private int row;
    private int col;
    private boolean isBlack;
    public Knight(int row, int col, boolean isBlack){
        // Initialize attributes
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol){
        // check to test all 8 possible moves the knight can make (assuming it is legal)
        return (board.verifySourceAndDestination(row, col, endRow, endCol, isBlack) && ((this.row + 1 == endRow && this.col - 2 == endCol) || (this.row - 1 == endRow && this.col -2 == endCol) || (this.row - 2 == endRow && this.col - 1 == endCol) || (this.row - 2 == endRow && this.col + 1 == endCol) || (this.row - 1 == endRow && this.col + 2 == endCol) || (this.row + 1 == endRow && this.col + 2 == endCol) || (this.row + 2 == endRow && this.col - 1 == endCol) || (this.row + 2 == endRow && this.col + 1 == endCol)));
    }
}
