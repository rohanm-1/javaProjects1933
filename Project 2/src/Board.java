// Written by Rohan Modalavalasa, modal005@umn.edu and Aidan Math math5600@umn.edu
public class Board {

    // Instance variables
    private Piece[][] board;

    // Construct an object of type Board using given arguments.
    public Board() {
        board = new Piece[8][8];
    }

    // Accessor Methods

    // Return the Piece object stored at a given row and column
    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    // Update a single cell of the board to the new piece.
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    // Game functionality methods

    // Moves a Piece object from one cell in the board to another, provided that
    // this movement is legal. A constraint of a legal move is:
    // - there exists a Piece at (startRow, startCol) and the destination square is seizable.
    // Returns a boolean to signify success or failure.
    // This method calls all necessary helper functions to determine if a move is legal,
    // and to execute the move if it is.
    // Your Game class should not directly call any other method of this class.
    // Hint: this method should call isMoveLegal() on the starting piece. 
    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        // If there is a piece in the cell of the board and the move is legal, move the piece
        if(board[startRow][startCol] != null && board[startRow][startCol].isMoveLegal(Game.myBoard, endRow, endCol)){
            // update the location of the piece
            board[startRow][startCol].setPosition(endRow, endCol);
            // Sets destination cell to the starting Piece and starting cell blank
           board[endRow][endCol] = board[startRow][startCol];
           board[startRow][startCol] = null;
           return true;
        }
        return false;
    }


    // Determines whether the game has been ended, i.e., if one player's King
    // has been captured.
    public boolean isGameOver() {
        int wking = 0;
        int bking = 0;
        // iterate through chess board
        for(int i = 0; i < Game.myBoard.board.length; i++){
            for(int j = 0; j < Game.myBoard.board.length; j++){
                // increment white king(wking) or black king(bking) if we see a black or white king in a cell of the board
                if(Game.myBoard.board[i][j] != null){
                    if(Game.myBoard.board[i][j].character == '\u2654')
                        wking++;
                    else if(Game.myBoard.board[i][j].character == '\u265A')
                        bking++;
                }
            }
        }
        //Therefore, if both wking and bking are not equal to 1, we know the game is over
        if(wking == 1 && bking == 1){
            return false;
        }
        return true;
    }

    // Constructs a String that represents the Board object's 2D array.
    // Returns the fully constructed String.
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(" ");
        for(int i = 0; i < 8; i++){
            out.append(" ");
            out.append(i);
        }
        out.append('\n');
        for(int i = 0; i < board.length; i++) {
            out.append(i);
            out.append("|");
            for(int j = 0; j < board[0].length; j++) {
                out.append(board[i][j] == null ? "\u2001|" : board[i][j] + "|");
            }
            out.append("\n");
        }
        return out.toString();
    }

    // Sets every cell of the array to null. For debugging and grading purposes.
    public void clear() {
        //iterate through the chess board and set every cell to null
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                Game.myBoard.board[i][j] = null;
            }
        }
    }

    // Movement helper functions

    // Ensure that the player's chosen move is even remotely legal.
    // Returns a boolean to signify whether:
    // - 'start' and 'end' fall within the array's bounds.
    // - 'start' contains a Piece object, i.e., not null.
    // - Player's color and color of 'start' Piece match.
    // - 'end' contains either no Piece or a Piece of the opposite color.
    // - where 'start' = (startRow, startCol) and 'end' = (endRow, endCol)
    public boolean verifySourceAndDestination(int startRow, int startCol, int endRow, int endCol, boolean isBlack) {
        // Account for no change in position
        if(startRow == endRow && startCol == endCol)
            return false;
        // use nested if statements to satisfy the aforementioned conditions, only return true if everything is satisfied.
        if((startRow >= 0 && startRow <= 7) && (startCol >= 0 && startCol <= 7) && (endRow >= 0 && endRow <= 7) && (endCol >= 0 && endCol <= 7)){
            if(this.board[startRow][startCol] != null){
                if (isBlack == this.board[startRow][startCol].getIsBlack()) {
                    if(this.board[endRow][endCol] == null || this.board[endRow][endCol].getIsBlack() != isBlack){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Check whether the 'start' position and 'end' position are adjacent to each other
    public boolean verifyAdjacent(int startRow, int startCol, int endRow, int endCol) {
        //if the absolute values of startRow-endRow and startCol-endCol are 1, that means that
        //the distance between the 2 cells is only 1 cell apart, making them adjacent
        if((Math.abs(startRow-endRow) == 1 || startRow == endRow) && (Math.abs(startCol-endCol) == 1 || startCol == endCol))
            return true;
        return false;
    }

    // Checks whether a given 'start' and 'end' position are a valid horizontal move.
    // Returns a boolean to signify whether:
    // - The entire move takes place on one row.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyHorizontal(int startRow, int startCol, int endRow, int endCol) {
        //only use 1 loop to iterate through the board because endRow must be equal to startRow
        if(endRow != startRow)
            return false;
        //account for no change in position
        if(startCol == endCol)
            return true;
        // if startCol is less than endCol, analyze the cells to the right of the Piece
        if(startCol < endCol){
            for(int i = startCol; i < endCol; i++){
                if(i == startCol); // if i is equal to the column where the piece is, don't check if a piece doesn't exist there
                else if(this.board[startRow][i] != null)
                    return false;
            }
        }
        // if startCol is greater than endCol, analyze the cells to the left of the Piece
        if(startCol > endCol){
            for(int i = startCol; i > endCol; i--){
                if(i == startCol);
                else if(this.board[startRow][i] != null)
                    return false;
            }
        }
        // if the move is able to get past all previous checks, it is a legal horizontal move
        return true;
    }

    // Checks whether a given 'start' and 'end' position are a valid vertical move.
    // Returns a boolean to signify whether:
    // - The entire move takes place on one column.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyVertical(int startRow, int startCol, int endRow, int endCol) {
        //the structure very similar to verifyHorizontal except this time we keep the column constant
        if(endCol != startCol)
            return false;
        if(startRow == endRow)
            return true;
        // if startRow is less than endRow, analyze the cells below the Piece
        if(startRow < endRow){
            for(int i = startRow; i < endRow; i++){
                if(i == startRow); // if i is equal to the row where the piece is, don't check if a piece doesn't exist there
                else if(this.board[i][startCol] != null)
                    return false;
            }
        }
        // if startRow is greater than endRow, analyze the cells above the Piece
        if(startRow > endRow){
            for(int i = startRow; i > endRow; i--){
                if(i == startRow); // if i is equal to the row where the piece is, don't check if a piece doesn't exist there
                else if(this.board[i][startCol] != null)
                    return false;
            }
        }
        // if the move is able to get past all previous checks, it is a legal vertical move
        return true;
    }

    // Checks whether a given 'start' and 'end' position are a valid diagonal move.
    // Returns a boolean to signify whether:
    // - The path from 'start' to 'end' is diagonal... change in row and col.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyDiagonal(int startRow, int startCol, int endRow, int endCol) {
        //if the absolute values of the differences between the rows and columns are equal, it is a diagonal move
        if(Math.abs(startRow-endRow) != Math.abs(startCol-endCol))
            return false;
        //account for no change in position
        if(startCol == endCol && startRow == endRow)
            return true;
        // iterate through the entire board: There are 4 cases to consider as the bishop can move in 4 directions
        // we also only want to iterate for each direction once (no nested loop)
        // so we create column counters for each respective iteration based on their direction
        //bottom left
        int colCount1 = startCol-1;
        for(int i = startRow+1; i < endRow; i++) {
            if(colCount1 < endCol)
                break;
            if (this.board[i][colCount1] != null)
                return false;
            colCount1--;
        }
        // bottom right
        int colCount2 = startCol+1;
        for(int i = startRow+1; i < endRow; i++) {
            if(colCount2 > endCol)
                break;
            if (this.board[i][colCount2] != null)
                return false;
            colCount2++;
        }
        // top left
        int colCount3 = startCol-1;
        for(int i = startRow-1; i > endRow; i--) {
            if(colCount3 < endCol)
                break;
            if (this.board[i][colCount3] != null)
                return false;
            colCount3--;
        }
        // top right
        int colCount4 = startCol+1;
        for(int i = startRow-1; i > endRow; i--) {
            if(colCount4 > endCol)
                break;
            if (this.board[i][colCount4] != null)
                return false;
            colCount4++;
        }
        // If the provided move can get through all of these checks, then it is a legal diagonal move
        return true;
    }
}
