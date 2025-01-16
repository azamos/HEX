import java.util.ArrayList;
import java.util.Scanner;

public class Hex {
    static final char vacant = '#';
    static final char playerTwo = 'B';
    static final char playerOne = 'W';
    final static int[][] directions = { { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 } };
    Node leftVirtualNode, rightVirtualNode, topVirtualNode, bottomVirtualNode;
    char[][] board;
    int N;
    final DisjointSet player1DisjointSet = new DisjointSet();
    final DisjointSet player2DisjointSet = new DisjointSet();

    public Hex(int n) {
        N = n;
        board = new char[N][N];
        leftVirtualNode = new Node(N / 2, -1);
        rightVirtualNode = new Node(N / 2, N);
        topVirtualNode = new Node(-1, N / 2);
        bottomVirtualNode = new Node(N, N / 2);
    }

    private void createVirtualNodeSets() {
        player1DisjointSet.CreateSingletonSet(N / 2, -1);
        player1DisjointSet.CreateSingletonSet(N / 2, N);
        player2DisjointSet.CreateSingletonSet(-1, N / 2);
        player2DisjointSet.CreateSingletonSet(N, N / 2);
    }

    public void initGame() {
        createVirtualNodeSets();
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

    private void connectToVirtualNode(int i, int j, char player) {
        if (player == playerOne) {
            if (j == 0)// left edge
                player1DisjointSet.Union(i, j, N / 2, -1);
            if (j == N - 1)// right edge
                player1DisjointSet.Union(i, j, N / 2, N);
        } else if (player == playerTwo) {
            if (i == 0)// top edge
                player2DisjointSet.Union(i, j, -1, N / 2);
            if (i == N - 1)// bottom edge
                player2DisjointSet.Union(i, j, N, N / 2);
        }
    }

    private boolean player1Won() {
        return player1DisjointSet.Search(N / 2, -1) == player1DisjointSet.Search(N / 2, N);
    }

    private boolean player2Won() {
        return player2DisjointSet.Search(-1, N / 2) == player2DisjointSet.Search(N, N / 2);
    }

    private void connectToNeighbours(int i, int j, char player) {
        DisjointSet dsu = player == playerOne ? player1DisjointSet : player2DisjointSet;
        for (int[] dir : directions) {
            int newRow = i + dir[0];
            int newCol = j + dir[1];
            if (newRow >= 0 && newRow < N && newCol >= 0 && newCol < N) {
                if (board[newRow][newCol] == player) {
                    dsu.Union(i, j, newRow, newCol);
                }
            }
        }
    }

    public static void main(String[] args) {
        Hex hex = new Hex(11);
        Scanner scanner = new Scanner(System.in);
        Boolean gameRunning = true;
        int turn = 0;// turn%2 == 0 ? player1 : player2.

        hex.initGame();
        hex.printBoard();
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
            char currPlayer = turn % 2 == 0 ? playerOne : playerTwo;
            hex.board[row][col] = currPlayer;
            hex.printBoard();
            DisjointSet currPlayerDSU = currPlayer == playerOne ? hex.player1DisjointSet : hex.player2DisjointSet;
            currPlayerDSU.CreateSingletonSet(row, col);
            if (row == 0 || col == 0 || row == hex.N - 1 || col == hex.N - 1)
                hex.connectToVirtualNode(row, col, currPlayer);
            hex.connectToNeighbours(row, col, currPlayer);
            if ((currPlayer == playerOne && hex.player1Won()) ||
                    (currPlayer == playerTwo && hex.player2Won())) {
                System.out.print("Congrats! Player " + (currPlayer == playerOne ? "1" : "2") + " WON!");
                gameRunning = false;
            }
            turn++;
            if (turn == hex.N * hex.N) {
                System.out.println("It's a draw! No more moves left.");
                gameRunning = false;
            }
        }
        scanner.close();
    }
}
