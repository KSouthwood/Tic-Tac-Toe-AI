package tictactoe;

public class TTTBoard {
    private static char[] board;
    private final static char EDGE = '|';
    private final static String BORDER = "---------";
    private final static int[][] lines = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private static int countX;
    private static int countO;

    public enum BoardStates {
        DRAW("Draw"),
        X_WON("X wins"),
        O_WON("O wins"),
        NOT_DONE("Game not finished");

        private final String message;

        BoardStates(String msg) {
            this.message = msg;
        }

        public String getMessage() {
            return message;
        }
    }

    TTTBoard() {
        initialState();
    }

    public void initialState() {
        board = "         ".toCharArray();
        countO = 0;
        countX = 0;
    }

    public void printBoard() {
        System.out.println(BORDER);
        System.out.printf("%s %s %s %s %s\n", EDGE, board[0], board[1], board[2], EDGE);
        System.out.printf("%s %s %s %s %s\n", EDGE, board[3], board[4], board[5], EDGE);
        System.out.printf("%s %s %s %s %s\n", EDGE, board[6], board[7], board[8], EDGE);
        System.out.println(BORDER);
    }

    /**
     * Checks the state of the board and returns one of four status messages: NOT_DONE, DRAW, X_WON or O_WON.
     * @return current BoardStates
     */
    public String boardStatus() {
        BoardStates state;
        int i = 0;

        do {
            state = checkForWinner(lines[i]);
            i++;
        } while (state == BoardStates.NOT_DONE && i < 8);

        if (state == BoardStates.NOT_DONE) {
            if (!String.valueOf(board).contains(" ")) {
                state = BoardStates.DRAW;
            }
        }

        return state.getMessage();
    }

    /**
     * Checks for three in a row and returns X_WON or O_WON when found.
     * @param line - the array cells to check
     * @return a BoardState indicating if a winner is found
     */
    private BoardStates checkForWinner(int[] line) {
        BoardStates state;

        switch (lineWeight(line)) {
            case 3:
                state = BoardStates.X_WON;
                break;
            case -3:
                state = BoardStates.O_WON;
                break;
            default:
                state = BoardStates.NOT_DONE;
        }

        return state;
    }

    /**
     * Checks if the space the player/AI want to play in is empty.
     * @param move - the space to play to
     * @return true if it's empty
     */
    public static boolean isCellEmpty(int move) {
        return board[move] == ' ';
    }

    public static void setCell(int move) {
        if (countO == countX) {
            board[move] = 'X';
            countX++;
        } else {
            board[move] = 'O';
            countO++;
        }
    }

    /**
     * Calculates the weight of a line by adding or subtracting one if a cell has an X or O. A value of 3 or -3 indicates
     * the X or O (respectively) has won. A value of 2 or -2 indicates the presence of a winning move.
     *
     * @param line - the cells to check
     * @return the weight of the line
     */
    public static int lineWeight(int[] line) {
        int weight = 0;
        for (int cell :
                line) {
            switch (board[cell]) {
                case 'X':
                    weight++;
                    break;
                case 'O':
                    weight--;
                    break;
                default:
                    break;
            }
        }
        return weight;
    }

    public int[] checkBoard() {
        int[] winOrBlock = {9, 9};

        for (int line = 0; line < 8; line++) {
            switch (lineWeight(lines[line])) {
                case 2:
                    winOrBlock[0] = line;
                    break;
                case -2:
                    winOrBlock[1] = line;
                    break;
                default:
                    break;
            }
        }

        return winOrBlock;
    }

    public int getEmptySpace(int line) {
        for (int cell : lines[line]) {
            if (board[cell] == ' ') {
                return cell;
            }
        }

        return -1;
    }
}
