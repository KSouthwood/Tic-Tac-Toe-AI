package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

abstract public class Player {
    protected int player;

    Player(int player) {
        this.player = player;
    }

    static Player AI(String type, int player) {
        switch (type) {
            case "user":
                return new Human(player);
            case "easy":
                return new Easy(player);
            case "medium":
                return new Medium(player);
            case "hard":
                return new Hard(player);
        }
        return new Human(player);
    }

    abstract void move(TTTBoard board);
}

class Human extends Player {
    public Human(int player) {
        super(player);
    }

    public void move(TTTBoard board) {
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
            if (board.isCellEmpty(cell)) {
                valid = true;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        } while (!valid);

        board.setCell(cell, player);
    }
}

class Easy extends Player {
    Random num = new Random();

    Easy(int player) {
        super(player);
    }

    protected void randomMove(TTTBoard board) {
        int space;
        do {
            space = 6 + num.nextInt(3) - (3 * num.nextInt(3));
        } while (!board.isCellEmpty(space));
        board.setCell(space, player);
    }

    public void move(TTTBoard board) {
        System.out.println("Making move level \"easy\"");
        randomMove(board);
    }
}

class Medium extends Easy {
    Medium(int player) {
        super(player);
    }

    @Override
    public void move(TTTBoard board) {
        System.out.println("Making move level \"medium\"");

        // see if we can play to win
        int play = board.checkForTwo(player);
        if (play < 8) {
            board.setCell(board.getEmptySpace(play), player);
            return;
        }

        // see if we have to block
        play = board.checkForTwo((player + 1) % 2);
        if (play < 8) {
            board.setCell(board.getEmptySpace(play), player);
            return;
        }

        // no winning or blocking move, so move randomly
        randomMove(board);
    }
}

class Hard extends Player {
    static class Result {
        int play;
        int score;

        Result(int play, int score) {
            this.play = play;
            this.score = score;
        }
    }

    Hard(int player) {
        super(player);
    }

    @Override
    public void move(TTTBoard board) {
        System.out.println("Making move level \"hard\"");

        int bestScore = Integer.MIN_VALUE;
        int bestMove  = -1;
        List<Integer> empties = board.emptySpaces();
        for (int empty : empties) {
            board.setCell(empty, player);
            int score = minimax(board, (player + 1) % 2);
            board.setCell(empty, -1);

            if (score > bestScore) {
                bestScore = score;
                bestMove = empty;
            }
        }
//        int playToMake = minimax(aiBoard, player);
        board.setCell(bestMove, player);
    }

    private int minimax(TTTBoard board, int move) {
        // check if there's a winner or draw, and return a score
        TTTBoard.BoardStates status = board.boardStatus();
        switch (status) {
            case X_WON:
                return player == 0 ? 10 : -10;
            case O_WON:
                return player == 1 ? 10 : -10;
            case DRAW:
                return 0;
        }

        List<Result> results = new ArrayList<>();
        List<Integer> emptySpaces = board.emptySpaces();    // get all empty spaces...

        for (Integer emptySpace : emptySpaces) {      // ... and loop through them
            board.setCell(emptySpace, move);
            results.add(new Result(emptySpace, minimax(board, ((move + 1) % 2)))); // collect scores from all plays
            board.setCell(emptySpace, -1);
        }

        if (move == player) {
            int max = Integer.MIN_VALUE;
            for (Result r : results) {
                if (r.score > max) {
                    max = r.score;
                }
            }
            return max;
        } else {
            int min = Integer.MAX_VALUE;
            for (Result r : results) {
                if (r.score < min) {
                    min = r.score;
                }
            }
            return min;
        }
    }
}