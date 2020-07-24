package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TTTBoard gameBoard = new TTTBoard();
        boolean playGame = true;
        String[] gameplay;

        do {
            gameBoard.initialState();
            gameplay = menu();
            if (gameplay[0].equals("exit")) {
                playGame = false;
                continue;
            }
            gameLoop(gameBoard, gameplay[1], gameplay[2]);
        } while (playGame);
    }

    private static void gameLoop(TTTBoard gameBoard, String player1, String player2) {
        Player[] players = new Player[2];
        players[0] = Player.AI(player1, 0);
        players[1] = Player.AI(player2, 1);
        gameBoard.printBoard();

        int move = 0;
        do {
            players[move].move(gameBoard);
            move = (move + 1) % 2;
            gameBoard.printBoard();
        } while (gameBoard.boardStatus().equals(TTTBoard.BoardStates.NOT_DONE));

        System.out.println(gameBoard.boardStatus().getMessage() + '\n');
    }

    private static String getInput() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    /**
     * Handle the menu input at the beginning of each game. Validates the command and player
     * choices.
     *
     * @return a String[] with the command and player choices
     */
    private static String[] menu() {
        String[] command;

        while (true) {
            System.out.print("Input command: ");
            command = getInput().split(" ");
            if ((command.length == 1) && command[0].equals("exit")) {
                break;
            }
            if ((command.length == 3) && command[0].equals("start")) {
                if (command[1].equals("user") || command[1].equals("easy") ||
                command[1].equals("medium") || command[1].equals("hard")) {
                    if (command[2].equals("user") || command[2].equals("easy") ||
                            command[2].equals("medium") || command[2].equals("hard")) {
                        break;
                    }
                }
            }
            System.out.println("Bad parameters!");
        }

        return command;
    }

}
