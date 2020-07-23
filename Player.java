package tictactoe;

import java.util.Random;
import java.util.Scanner;

public interface Player {
    Random num = new Random();

    static Player AI(String type) {
        switch (type) {
            case "user":
                return new Human();
            case "easy":
                return new Easy();
            case "medium":
                return new Medium();
//            case "hard":
//                return Hard();
        }
        return new Human();
    }

    default void randomMove() {
        int space;
        do {
            space = 6 + num.nextInt(3) - (3 * num.nextInt(3));
        } while (!TTTBoard.isCellEmpty(space));
        TTTBoard.setCell(space);
    }

    void move(TTTBoard board, int move);
}

class Human implements Player {
    public void move(TTTBoard board, int move) {
        Scanner input = new Scanner(System.in);
        boolean valid = false;
        int cell = 0;
        do {
            System.out.print("Enter the coordinates: ");

            // validate we get two integers input from user
            int col;
            if (input.hasNextInt()) {
                col = input.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                input.nextLine();
                continue;
            }

            int row;
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

            cell = 6 + (col - 1) - (3 * (row - 1));
            if (TTTBoard.isCellEmpty(cell)) {
                valid = true;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        } while (!valid);

        TTTBoard.setCell(cell);
    }
}

class Easy implements Player {
    public void move(TTTBoard board, int move) {
        System.out.println("Making move level \"easy\"");
        randomMove();
    }
}

class Medium implements Player {
    public void move(TTTBoard board, int move) {
        System.out.println("Making move level \"medium\"");
        int[] getMove = board.checkBoard();
        int win   = move == 0 ? getMove[0] : getMove[1];
        int block = move == 0 ? getMove[1] : getMove[0];

        if (win != 9) {
            TTTBoard.setCell(board.getEmptySpace(win));
        } else if (block != 9) {
            TTTBoard.setCell(board.getEmptySpace(block));
        } else {
            randomMove();
        }
    }
}