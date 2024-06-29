// Written by Rohan Modalavalasa: modal005, and Aidan Math: math5600@umn.edu
public class Queen {
    private int row;
    private int col;
    private boolean isBlack;
    public Queen(int row, int col, boolean isBlack){
        // Initialize attributes
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol){
        // check if it's remotely a legal move as well as the diagonal, horizontal, and vertical
        return board.verifySourceAndDestination(this.row, this.col, endRow, endCol, isBlack) && (board.verifyHorizontal(this.row, this.col, endRow,endCol) || board.verifyVertical(this.row, this.col, endRow,endCol) || board.verifyDiagonal(this.row, this.col, endRow, endCol));
    }
}
