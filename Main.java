package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter cells: ");
        String board = input.next();
        TTTBoard gameBoard = new TTTBoard(board);
        gameBoard.printBoard();
        while (true) {
            if (gameBoard.play(getMove())) {
                break;
            }
        }
        gameBoard.printBoard();
        System.out.println(gameBoard.boardStatus());
    }

    private static int getMove() {
        Scanner input = new Scanner(System.in);
        int col = 0;
        int row = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter the coordinates: ");
            col = 0;
            row = 0;

            // validate we get two integers input from user
            if (input.hasNextInt()) {
                col = input.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                input.nextLine();
                continue;
            }

            if (input.hasNextInt()) {
                row = input.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                input.nextLine();
                continue;
            }

            // we have two integers, now validate the range
            if (col < 1 || col > 3 || row < 1 || row > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            valid = true;
        }

        return 6 + (col - 1) - (3 * (row - 1));
    }
}

class TTTBoard {
    private final char[] board;
    private final static char EDGE = '|';
    private final static String BORDER = "---------";
    private final static int[][] lines = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private int countX;
    private int countO;

    private enum BoardStates {
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

//    TTTBoard() {
//        new TTTBoard("_________");
//    }

    TTTBoard(String board) {
        this.board = board.toCharArray();

        for (int i = 0; i < board.length(); i++) {
            if (this.board[i] == 'X') {
                countX++;
            }
            if (this.board[i] == 'O') {
                countO++;
            }
        }
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
            if (!String.valueOf(board).contains("_")) {
                state = BoardStates.DRAW;
            }
        }

        return state.getMessage();
    }

    private BoardStates checkForWinner(int[] line) {
        BoardStates state = BoardStates.NOT_DONE;

        if ((board[line[0]] == board[line[1]]) && (board[line[1]] == board[line[2]])) {
            if (board[line[0]] != '_') {
                state = (board[line[0]] == 'X') ? BoardStates.XWON : BoardStates.OWON;
            }
        }

        return state;
    }

    public boolean play(int move) {
        if (board[move] == '_') {
            if (countO == countX) {
                board[move] = 'X';
                countX++;
            } else {
                board[move] = 'O';
                countO++;
            }
            return true;
        }

        System.out.println("This cell is occupied! Choose another one!");
        return false;
    }
}