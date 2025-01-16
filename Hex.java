import java.util.ArrayList;
import java.util.Scanner;

public class Hex {
    static final char vacant = '#';
    static final char playerTwo = 'B';
    static final char playerOne = 'W';
    DisjointSet player1 = new DisjointSet();
    /*
     * Invariant: player1 ∩ player2 = ∅ .
     * That's why we have the search function in class DisjointSet
     */
    DisjointSet player2 = new DisjointSet();
    char[][] board;
    int N;

    public Hex(int n) {
        N = n;
        board = new char[N][N];
    }

    public void initGame() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = vacant;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < N; i++) {
            System.out.print(" ".repeat(i));
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Hex hex = new Hex(11);
        hex.initGame();
        hex.printBoard();
        Scanner scanner = new Scanner(System.in);
        Boolean gameRunning = true;
        int turn = 0;// turn%2 == 0 ? player1 : player2.
        while (gameRunning) {
            int row = -1;
            int col = -1;
            boolean validMove = false;
            while (!validMove) {
                System.out.print("Turn " + turn + " , Player " + (turn % 2 == 0 ? playerOne
                        : playerTwo) + " Enter row and column (0 to " + (hex.N - 1) + "): ");
                if (scanner.hasNextInt()) {
                    row = scanner.nextInt();
                    if (scanner.hasNextInt()) {
                        col = scanner.nextInt();
                        if (row >= 0 && row < hex.N && col >= 0 && col < hex.N) {
                            if (hex.board[row][col] == vacant) {
                                validMove = true; // Move is valid and spot is empty
                            } else {
                                System.out.println("Spot already taken. Try again.");
                            }
                        } else {
                            System.out.println("Coordinates out of bounds. Try again.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter integers.");
                        scanner.next(); // Clear invalid input
                    }
                } else {
                    System.out.println("Invalid input. Please enter integers.");
                    scanner.next(); // Clear invalid input
                }
            }
            hex.board[row][col] = turn % 2 == 0 ? playerOne : playerTwo;
            hex.printBoard();
            turn++;
            if (turn > 121) {
                gameRunning = false;
                scanner.close();
            }
        }
    }
}
