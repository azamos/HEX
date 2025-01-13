import java.util.ArrayList;

public class Hex {
    static final char vacant = 'G';
    static final char playerTwo = 'B';
    static final char playerOne = 'W';
    DisjointSet player1 = new DisjointSet();
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
        board[N / 2][0] = playerOne;
        board[0][N / 2] = playerTwo;
    }

    /* Given location XY and moves, specifies valid moves */
    public ArrayList<Coordinates> availableMoveXY(Coordinates p) {
        ArrayList<Coordinates> validMoves = new ArrayList<>();
        int i = p.i;
        int j = p.j;
        if (i > 0)
            validMoves.add(new Coordinates(i - 1, j));
        if (i < N - 1)
            validMoves.add(new Coordinates(i + 1, j));
        if (j > 0)
            validMoves.add(new Coordinates(i, j - 1));
        if (j < N - 1)
            validMoves.add(new Coordinates(i, j + 1));

        /* in even indexes, can move up,down,left,right, and left+UP, right+UP */
        if (j % 2 == 0) {
            if (i > 0) {
                if (j > 1)
                    validMoves.add(new Coordinates(i - 1, j - 1));
                if (j < N - 1)
                    validMoves.add(new Coordinates(i - 1, j + 1));
            }
        }
        /* in even indexes, can move up,down,left,right, and left+DOWN, right+DOWN */
        else {

            if (i < N - 1) {
                if (j > 1)
                    validMoves.add(new Coordinates(i + 1, j - 1));
                if (j < N - 1)
                    validMoves.add(new Coordinates(i + 1, j + 1));
            }
        }
        return validMoves;
    }
}
