/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[] board;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.board = new int[n * n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i * n + j % n] = tiles[i][j];
            }
        }
    }

    private Board(int n, int[] tiles) {
        this.n = n;
        this.board = tiles.clone();
    }

    //return tile at row, col
    private int tileAt(int row, int col) {
        return board[row * n + col % n];
    }

    private void setTileAt(int row, int col, int val) {
        board[row * n + col % n] = val;
    }

    private int rowOf(int index) {
        return index / n;
    }

    private int colOf(int index) {
        return index % n;
    }

    //exchange two tiles
    private void exch(int row1, int col1, int row2, int col2) {
        int temp = tileAt(row1, col1);
        setTileAt(row1, col1, tileAt(row2, col2));
        setTileAt(row2, col2, temp);
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n);
        for (int i = 0; i < board.length; i++) {
            if (i % n == 0) s.append("\n");
            else s.append(" ");
            s.append(board[i]);
        }
        return s.toString();
    }


    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    // TODO: use tileAt
    public int hamming() {
        int result = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] != 0 && board[i] - i - 1 != 0) result++;
        }
        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < board.length; i++) {
            int tile = board[i];
            if (tile != 0) {

                result = result + Math.abs(rowOf(i) - rowOf(tile - 1)) + Math
                        .abs(colOf(i) - colOf(tile - 1));
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    /*
    public boolean equals(Object y) {
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
    }
    */

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int tileIndex1, tileIndex2;
        if (board[0] == 0) {
            tileIndex1 = 1;
            tileIndex2 = 2;
        }
        else {
            tileIndex1 = 0;
            if (board[1] == 0) tileIndex2 = 2;
            else tileIndex2 = 1;
        }

        Board result = new Board(n, board);
        result.exch(rowOf(tileIndex1), colOf(tileIndex1), rowOf(tileIndex2), colOf(tileIndex2));
        return result;
    }


    // unit testing (not graded)
    public static void main(String[] args) {
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }
            Board b = new Board(tiles);
            StdOut.println(b.toString());
            StdOut.println(b.dimension());
            StdOut.println("h = " + b.hamming());
            StdOut.println("m = " + b.manhattan());
            StdOut.println(b.isGoal());

            StdOut.println();

            Board twin = b.twin();
            StdOut.println(twin.toString());
            //StdOut.println(b.equals(twin));
            StdOut.println("h = " + twin.hamming());
            StdOut.println("m = " + twin.manhattan());

            //TODO: iterable test
        }
    }

}
