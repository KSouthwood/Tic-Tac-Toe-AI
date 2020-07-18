package tictactoe;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        TTTBoard gameBoard = new TTTBoard();
        gameBoard.printBoard();
        int move = 0;
        do {
            switch (move) {
                case 0:
                if (gameBoard.play(humanPlayer())) {
                    move = (move + 1) % 2;
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
                break;
                case 1:
                    if (gameBoard.play(computerPlayer())) {
                        System.out.println("Making move level \"easy\"");
                        move = (move + 1) % 2;
                    }
            }
            gameBoard.printBoard();
        } while (gameBoard.boardStatus().equals(TTTBoard.BoardStates.NOT_DONE.getMessage()));

        System.out.println(gameBoard.boardStatus());
    }

    private static int humanPlayer() {
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

    private static int computerPlayer() {
        Random row = new Random();
        Random col = new Random();

//        do {
//            int r = row(3) + 1;
//            int c = col(3) + 1;
//        }
        return 6 + col.nextInt(3) - (3 * (row.nextInt(3)));
    }
}
