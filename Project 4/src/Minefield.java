import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Minefield {
    /**
    Global Section
    */
    public static final String ANSI_YELLOW_BRIGHT = "\u001B[33;1m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_PURPLE = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001b[47m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001b[45m";
    public static final String ANSI_GREY_BACKGROUND = "\u001b[0m";

    /* 
     * Class Variable Section
     * 
    */
    private int flags;//num of flags
    private int mines;//num of mines
    private Cell[][] minefield;

    /*Things to Note:
     * Please review ALL files given before attempting to write these functions.
     * Understand the Cell.java class to know what object our array contains and what methods you can utilize
     * Understand the StackGen.java class to know what type of stack you will be working with and methods you can utilize
     * Understand the QGen.java class to know what type of queue you will be working with and methods you can utilize
     */
    
    /**
     * Minefield
     * 
     * Build a 2-d Cell array representing your minefield.
     * Constructor
     * @param rows       Number of rows.
     * @param columns    Number of columns.
     * @param flags      Number of flags, should be equal to mines
     */
    public Minefield(int rows, int columns, int flags) {
        this.mines=flags;
        this.flags=flags;
        this.minefield= new Cell[rows][columns];
        //let's populate the minefield with Cell objects
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                minefield[i][j] = new Cell(false, "0"); 
            }
        }
    }
    // getter methods for minefield and flags
    public Cell[][] getMinefield(){
        return minefield;
    }

    public int getFlags(){
        return flags;
    }

    /**
     * evaluateField
     * 
     *
     * @function:
     * Evaluate entire array.
     * When a mine is found check the surrounding adjacent tiles. If another mine is found during this check, increment adjacent cells status by 1.
     * 
     */
    public void evaluateField() {
        boolean[][] checked = new boolean[minefield.length][minefield[0].length];//we use this to make sure we dont double count mines 
        for(int i = 0; i < minefield.length; i++) {
            for (int j = 0; j < minefield[0].length; j++) {
                if (minefield[i][j].getStatus().equals("M") && !checked[i][j]) {//checking for mines throughout the array
                    checked[i][j] = true;
                    //if we find a mine, we have to now loop through all adjacent and increment accordingly 
                    for (int rowDiff=-1; rowDiff<=1; rowDiff++) {
                        for (int collDiff= -1; collDiff <= 1; collDiff++) {//used help from Stack Overflow to figure out how to loop over adjacent tiles efficently: https://stackoverflow.com/questions/2035522/get-adjacent-elements-in-a-two-dimensional-array
                            if (inBounds(i+rowDiff, j+collDiff) &&!minefield[i+rowDiff][j+collDiff].getStatus().equals("M")) {

                                //if adjacent isnt a mine, convert to int, increment status, convert to string
                                Cell adjacent = minefield[i+rowDiff][j+collDiff];//temp variable that we change the status
                                int status = Integer.parseInt(adjacent.getStatus());
                                status++;//our increment
                                adjacent.setStatus(String.valueOf(status));
                            }
                        }
                        
                    }
                    
                }
            }
        }
    }
    
        public boolean inBounds(int row, int col){
            // A helper function that will check if desired coords are in bounds
            if((row <0 || col <0) || (row>=minefield.length || col>=minefield[0].length)){
                return false;
            }
            return true;
        }

    /**
     * createMines
     * 
     * Randomly generate coordinates for possible mine locations.
     * If the coordinate has not already been generated and is not equal to the starting cell set the cell to be a mine.
     * utilize rand.nextInt()
     * 
     * @param x       Start x, avoid placing on this square.
     * @param y        Start y, avoid placing on this square.
     * @param mines      Number of mines to place.
     */
    public void createMines(int x, int y, int mines) {
        for (int i=0; i<mines;){
            Random rand = new Random();
            int randX = rand.nextInt(minefield.length);//a random int assigned and bounded by the length 
            int randY = rand.nextInt(minefield[0].length);
            if(randX!=x && randY!=y && !minefield[randX][randY].getStatus().equals("M")){
                minefield[randX][randY].setStatus("M");//if the random cell isnt already a mine, we make it a mine and continue the loop
                i++;
            }
        }
    }

    /**
     * guess
     * 
     * Check if the guessed cell is inbounds (if not done in the Main class). 
     * Either place a flag on the designated cell if the flag boolean is true or clear it.
     * If the cell has a 0 call the revealZeroes() method or if the cell has a mine end the game.
     * At the end reveal the cell to the user.
     * 
     * 
     * @param x       The x value the user entered.
     * @param y       The y value the user entered.
     * @param flag    A boolean value that allows the user to place a flag on the corresponding square.
     * @return boolean Return false if guess did not hit mine or if flag was placed, true if mine found.
     */
    public boolean guess(int x, int y, boolean flag) {
        if(!inBounds(x, y)){//checks if in bounds
            return false;
        }
        Cell current = minefield[x][y];
        //if the user picks a flag, set to a flag and decrement flag count
        if(flag ==true && current.getRevealed()!=true){
                current.setStatus("F");//set to flag
                current.setRevealed(true);
                this.flags--;
            }
        if(flag ==false){
            if(current.getStatus().equals("M")){//if we arent flagging and is mine return true
                current.setRevealed(true);
                return true;
            }
            if(current.getStatus().equals("0")){//if it is zero, we call revela zeroes
                revealZeroes(x,y);
            }
            else{
                current.setRevealed(true);//else we set revealed to true and return false
            }
        }
        return false;

    }

    /**
     * gameOver
     * 
     * Ways a game of Minesweeper ends:
     * 1. player guesses a cell with a mine: game over -> player loses
     * 2. player has revealed the last cell without revealing any mines -> player wins
     * 
     * @return boolean Return false if game is not over and squares have yet to be revealed, otheriwse return true.
     */
    public boolean gameOver() {
        int mineCount=0;//can easily change this if we want
        //loop through the entire array, if a mine is revealed, we return true 
        for(int i =0; i < minefield.length; i++){
            for(int j = 0; j < minefield[0].length; j++){
                if(minefield[i][j].getStatus().equals("M") && minefield[i][j].getRevealed()){
                    System.out.println("User has hit a mine.");
                    return true;
                }
                
            }
        }
        int totalRevealed = (minefield.length * minefield[0].length) - mines;//total number of cells that are not mines in the minefield
        int revealedCells = 0;
        for(int i =0; i < minefield.length; i++){
            for(int j = 0; j < minefield[0].length; j++){//if we find  a cell that is revealed, we increment until it matches to what the ending totatl revealed shoudl be
                if(minefield[i][j].getRevealed()){
                    revealedCells++;//add to revealed cells
                }
            }
        }
        if(revealedCells == totalRevealed)//if the total revealed cells is equal to the total mines, we know the game is over and can end 
            return true;
        return false;
    }

    /**
     * Reveal the cells that contain zeroes that surround the inputted cell.
     * Continue revealing 0-cells in every direction until no more 0-cells are found in any direction.
     * Utilize a STACK to accomplish this.
     *
     * This method should follow the psuedocode given in the lab writeup.
     * Why might a stack be useful here rather than a queue?
     *
     * @param x      The x value the user entered.
     * @param y      The y value the user entered.
     */
    public void revealZeroes(int x, int y) {
        Stack1Gen<int[]> s = new Stack1Gen<>();
        int[] addto=new int[2];
        addto[0]=x;
        addto[1]=y;
        s.push(addto);
        while(!s.isEmpty()){
            int []coords=s.pop();//this will update to our new values each time
            int row= coords[0];//represents our y
            int col= coords[1];//represents our x
            Cell current=minefield[row][col];
            current.setRevealed(true);

            //we check each direction and then add to the Stack accordingly.
            //it will go in each direction until it cant anymore, beacuse it is a stack

            //checks top
            if (inBounds(row-1, col) && minefield[row-1][col].getStatus().equals("0") && !minefield[row-1][col].getRevealed() ) {
                        int []stackAdd=new int[2];//create a new array with the coords and push it to stack
                        stackAdd[0]=row-1;
                        stackAdd[1]=col;
                        s.push(stackAdd);
            }
            //checks bottom
            if (inBounds(row+1, col) && minefield[row+1][col].getStatus().equals("0") && !minefield[row+1][col].getRevealed() ) {
                        int []stackAdd=new int[2];//create a new array with the coords and push it to stack
                        stackAdd[0]=row+1;
                        stackAdd[1]=col;
                        s.push(stackAdd);
            }
            //checks rigth
            if (inBounds(row, col+1) && minefield[row][col+1].getStatus().equals("0") && !minefield[row][col+1].getRevealed() ) {
                        int []stackAdd=new int[2];//create a new array with the coords and push it to stack
                        stackAdd[0]=row;
                        stackAdd[1]=col+1;
                        s.push(stackAdd);
            }
            //checks left
            if (inBounds(row, col-1) && minefield[row][col-1].getStatus().equals("0") && !minefield[row][col-1].getRevealed() ) {
                        int []stackAdd=new int[2];//create a new array with the coords and push it to stack
                        stackAdd[0]=row;
                        stackAdd[1]=col-1;
                        s.push(stackAdd);
            }
        }
    }
    /**
     * revealStartingArea
     *
     * On the starting move only reveal the neighboring cells of the inital cell and continue revealing the surrounding concealed cells until a mine is found.
     * Utilize a QUEUE to accomplish this.
     * 
     * This method should follow the psuedocode given in the lab writeup.
     * Why might a queue be useful for this function?
     *
     * @param x     The x value the user entered.
     * @param y     The y value the user entered.
     */
    public void revealStartingArea(int x, int y) {
        Q1Gen<int[]> q = new Q1Gen<>(); 
        int[] addTo=new int[2];
        addTo[0]=x;
        addTo[1]=y;
        if(minefield[x][y].getStatus().equals("M"))//if the begining is a mine, we call the guess method which will reveal mine and end game
        guess(x, y, false);//calling guess
        q.add(addTo);
        while(q.length() != 0){
            int[] coords = q.remove();
            int row =coords[0];//represents our x
            int col =coords[1];//represents our y
            Cell current= minefield[row][col];
            //we first check for a mine so we can end as soon as we see one 
            if(current.getStatus().equals("M")){
                return;
            }
            //we add all the directions to the queue, unlike our stack where we do one direction at a time
            //works almost identically as our stack method in reveal zeros 

            current.setRevealed(true);
            if(inBounds(row-1, col) && minefield[row-1][col].getRevealed()!=true ){
                int[] addQ= new int[2];//new array that hold our coordinate vals
                addQ[0]=row-1;
                addQ[1]=col;
                q.add(addQ);  
            }
            //check bottom
            if(inBounds(row+1, col) && minefield[row+1][col].getRevealed()!=true){
                int[] addQ= new int[2];//new array that hold our coordinate vals
                addQ[0]=row+1;
                addQ[1]=col;
                q.add(addQ);
        
            }
            //check left
            if(inBounds(row, col-1) && minefield[row][col-1].getRevealed()!=true){
                int[] addQ= new int[2];//new array that hold our coordinate vals
                addQ[0]=row;
                addQ[1]=col-1;
                q.add(addQ);
            }
            //check right 
            if(inBounds(row, col+1) && minefield[row][col+1].getRevealed()!=true){
                int[] addQ= new int[2];//new array that hold our coordinate vals
                addQ[0]=row;
                addQ[1]=col+1;
                q.add(addQ);
            }
             

        }
    }


    /**
     * For both printing methods utilize the ANSI colour codes provided! 
     * 
     * 
     * 
     * 
     * 
     * debug
     *
     * @function This method should print the entire minefield, regardless if the user has guessed a square.
     * *This method should print out when debug mode has been selected. 
     */
    public void debug() {//one array dedicated to debug mode, one for the main game, fix the debig mode so that it doesnt end game right away
        Minefield mcopy = new Minefield(minefield.length, minefield[0].length, flags);
        // we need to create a deepcopy of the object- for each Cell in the minefield, create a new Cell with the same status and reveal boolean 
        // However, the debug method wants the status of all the Cells to be revealed, so we can just have true in the first parameter
        for(int i = 0; i < mcopy.minefield.length; i++){
            for(int j = 0; j < mcopy.minefield[0].length; j++){
                mcopy.minefield[i][j] = new Cell(true, this.minefield[i][j].getStatus());
            }
        }
        System.out.println(mcopy);
    }

    /**
     * toString
     *
     * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
     */
    public String toString() {
        // initialize resultant string with a couple spaces to match spacing of the minefield
        String result = "    ";
        // add numbers of the columns with the correct spacing
        for(int i = 0; i < minefield[0].length; i++){
            result += i + "   ";
        }
        result += "\n";
        // adding rows
        for(int i = 0; i < minefield.length; i++){
            // start by adding row num
            result += i + "   "; 
            // then iterate through each Cell in the minefield
            // if it's revealed, we show the status to the player and color code it
            // if it's not revealed, simply print out a - to the resultant String
            for(int j = 0; j < minefield[0].length; j++){
                if(minefield[i][j].getRevealed()){
                    switch(minefield[i][j].getStatus()){
                        case "-":
                            result += ANSI_GREY_BACKGROUND+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "F":
                            result += ANSI_RED+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "M":
                            result += ANSI_RED_BRIGHT+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "0":
                            result += ANSI_YELLOW+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "1":
                            result += ANSI_BLUE+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "2":
                            result += ANSI_GREEN+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "3":
                            result += ANSI_PURPLE+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "4":
                            result += ANSI_CYAN+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "5":
                            result += ANSI_BLUE_BRIGHT+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "6":
                            result += ANSI_RED_BRIGHT+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "7":
                            result += ANSI_YELLOW+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "8":
                            result += ANSI_CYAN+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                        case "9":
                            result += ANSI_PURPLE+minefield[i][j].getStatus()+ANSI_GREY_BACKGROUND+"   ";
                            break;
                    }
                }
                else{
                    result += "-   ";
                }
            }
            // \n\n for spacing reasons
            result += "\n\n";
        }
        return result;
    }
}
