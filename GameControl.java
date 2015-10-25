public class GameControl{
    // default states of any new game
    private int generations = 0;
    private int delayTime = 0;

    // constructors
    public GameControl(){}

    public GameControl(int numGenerations, int newDelay){
        generations = numGenerations;
        delayTime = newDelay;
    }

    // methods
    public Cell [][] makeBoard(int boardWidth, int boardHeight){
        /*
        inititializes the gameboard and sets the entire board to blank
        */
        Cell [][] board = new Cell[boardWidth][boardHeight];

        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board[row].length; col++){
                board[row][col] = new Cell();
                board[row][col].setPosition(col, row);
            }
        }
        return board;
    }

    void insertGlider(Cell [][] destBoard, int xPos, int yPos){
        // init glider pattern and inserts it into game board at (xPos, yPos)
        String [][] pattern = {{" ", "*", " "}, {" ", " ", "*"}, {"*","*","*"}}; // " " is dead, "*" is living

        int xIndex = xPos-1;
        int yIndex = yPos-1;

        for (int row = 0; row < pattern.length; row++){
            for (int col = 0; col < pattern[row].length; col++){
                String currentVal = pattern[row][col];
                if (currentVal == "*"){
                    Cell thisCell = destBoard[xIndex+row][yIndex+col];
                    thisCell.live();
                }
            }
        }
    }

    void printBoard(Cell [][] gameBoard){
        // prints the entire gameboard for a single generation
        for (int row = 0; row < gameBoard.length; row++){
            for (int col = 0; col < gameBoard[row].length; col++){
                    Cell thisCell = gameBoard[row][col];
                    if (thisCell.hasLife()){
                        System.out.print("*");
                    } else {
                        System.out.print(" ");
                    }
            }
            System.out.println();
        }
    }

    void setMaxGenerations(int numGenerations){
        // set the max number of generations to run the game
        generations = numGenerations;
    }

    public int getMaxGenerations(){
        // returns the number of max generations in game instance
        return generations;
    }

    void setDelay(int newDelay){
        // set the delay between each generation in miliseconds
        delayTime = newDelay;
    }

    public int getDelay(){
        // get the delay between generations in miliseconds
        return delayTime;
    }

    void delayGame(int delayFor){
        try{
            Thread.sleep(delayFor);
        } catch (InterruptedException e) {
            System.out.println("Delay Exception");
        }
    }

    void evaluateBoard(Cell [][] currentBoard){
        // make new empty board to store the status of next board
        Cell [][] nextBoard = new Cell [currentBoard.length][currentBoard[0].length];
        
        // loop through current board & set life status of every cell
        for (int row = 0; row < currentBoard.length; row++){
            for (int col = 0; col < currentBoard[row].length; col++){
                    Cell cellThisGen = currentBoard[row][col]; // the cell during this generation
                    Cell cellNextGen = new Cell(); // the cell during next generation
                    int liveNeighborCount = cellThisGen.countLiveNeighbors();
                    if (cellThisGen.hasLife()){
                        if(liveNeighborCount < 2 || liveNeighborCount > 3){
                            cellNextGen.die();
                        } else {
                            cellNextGen.live();
                        }
                    }else{
                        if(liveNeighborCount == 3){
                            cellNextGen.live();
                        }
                    }
                    nextBoard[row][col] = cellNextGen; // insert new cell status into next generation board
            }    
        }

        currentBoard = nextBoard; // the contents of the current board will be replaced with the next generation
        nextBoard = null; // delete this instance of the next board
    }
} // end GameControl class