package tictactoe;

import java.util.Vector;

public class TTTBoard {
    private char[] board;
    private final int[][] lines = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

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
    }

    public void printBoard() {
        String border = "---------";
        char edge = '|';

        System.out.println(border);
        System.out.printf("%s %s %s %s %s\n", edge, board[0], board[1], board[2], edge);
        System.out.printf("%s %s %s %s %s\n", edge, board[3], board[4], board[5], edge);
        System.out.printf("%s %s %s %s %s\n", edge, board[6], board[7], board[8], edge);
        System.out.println(border);
    }

    /**
     * Checks the state of the board and returns one of four status messages: NOT_DONE, DRAW, X_WON or O_WON.
     * @return current BoardStates
     */
    public BoardStates boardStatus() {
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

        return state;
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
    public boolean isCellEmpty(int move) {
        return board[move] == ' ';
    }

    public void setCell(int move, int player) {
        switch (player) {
            case 0:
                board[move] = 'X';
                break;
            case 1:
                board[move] = 'O';
                break;
            case -1:
                board[move] = ' ';
                break;
            default:
                break;
        }
    }

    /**
     * Calculates the weight of a line by adding or subtracting one if a cell has an X or O. A value of 3 or -3 indicates
     * that X or O (respectively) has won. A value of 2 or -2 indicates the presence of a winning move.
     *
     * @param line - the cells to check
     * @return the weight of the line
     */
    public int lineWeight(int[] line) {
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

    /**
     * Check for a line with two X's or O's and an empty space
     * @param player to check for (0 = X, 1 = O)
     * @return line number
     */
    public int checkForTwo(int player) {
        int line = 0;

        do {
            int lw = lineWeight(lines[line]);
            if (lw == 2 && player == 0) { return line; }
            if (lw == -2 && player == 1) { return line; }
            line++;
        } while (line < 8);

        return line;
    }

    /**
     * Finds the empty space in a line with two X's or O's
     * @param line number to check
     * @return index in board array of empty space
     */
    public int getEmptySpace(int line) {
        for (int cell : lines[line]) {
            if (board[cell] == ' ') {
                return cell;
            }
        }

        return -1;
    }

    public Vector<Integer> emptySpaces() {
        Vector<Integer> empty = new Vector<>();

        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                empty.add(i);
            }
        }

        return empty;
    }
}
