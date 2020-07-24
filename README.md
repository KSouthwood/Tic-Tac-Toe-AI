# Tic-Tac-Toe-AI

Tic-Tac-Toe game written in Java with three levels of AI playing ability. Part of the Java Developer curriculum from JetBrains/Hyperskill.
Was the first medium level project tackled and to use multiple files for classes. Introduces abstract and interface classes.

### Stage 1
Take a string representing a board state and output it in a formatted state. Then ask the user for a position to play after which the
board is output again followed by a message of the board state: X or O win, tie, or unfinished. The user input should be checked so
they only input valid positions.

### Stage 2
Start with an empty board and the user going first playing against the computer. Implement an Easy AI to play against that just makes random
moves. Print the outcome of the game when someone wins or it's a tie, then exits.

### Stage 3
Make it possible to play the game with any combination of human and AI players. If playing against the AI, you can go first or second.
Implement a command system at the beginning of the game to accept "exit" to quit, or "start" followed by two choices for the players,
one of "user" for human, or "easy" for the computer AI. Error check the input for correctness as well. After each game, come back to the
command system until "exit" is entered.

### Stage 4
Implement a medium AI that will look to win first, block second, otherwise moves randomly. At this stage, it should be possible for to have
the medium AI play the easy AI as well.

### Stage 5
Implement the last AI, hard. This one uses the minimax algorithm to determine all of its moves so it should never lose.
