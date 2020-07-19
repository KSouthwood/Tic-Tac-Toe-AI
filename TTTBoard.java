package tictactoe;

public class TTTBoard {
    private char[] board;
    private final static char EDGE = '|';
    private final static String BORDER = "---------";
    private final static int[][] lines = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private int countX;
    private int countO;

    public enum BoardStates {
        DRAW("Draw"),
        XWON("X wins"),
        OWON("O wins"),
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

    private BoardStates checkForWinner(int[] line) {
        BoardStates state = BoardStates.NOT_DONE;

        if ((board[line[0]] == board[line[1]]) && (board[line[1]] == board[line[2]])) {
            if (board[line[0]] != ' ') {
                state = (board[line[0]] == 'X') ? BoardStates.XWON : BoardStates.OWON;
            }
        }

        return state;
    }

    public boolean play(int move) {
        if (board[move] == ' ') {
            if (countO == countX) {
                board[move] = 'X';
                countX++;
            } else {
                board[move] = 'O';
                countO++;
            }
            return true;
        }

        return false;
    }
}
