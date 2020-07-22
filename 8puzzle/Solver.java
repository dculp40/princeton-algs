/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Node orig;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        MinPQ<Node> oPQ = new MinPQ<>();
        oPQ.insert(new Node(initial, 0, null));

        MinPQ<Node> tPQ = new MinPQ<>();
        tPQ.insert(new Node(initial.twin(), 0, null));

        orig = oPQ.delMin();
        Node twin = tPQ.delMin();
        while (!orig.b.isGoal() && !twin.b.isGoal()) {
            for (Board neighbor : orig.b.neighbors()) {
                if (orig.moves < 1 || !neighbor.equals(orig.prev.b))
                    oPQ.insert(new Node(neighbor, orig.moves + 1, orig));
            }
            orig = oPQ.delMin();

            for (Board neighbor : twin.b.neighbors()) {
                if (twin.moves < 1 || !neighbor.equals(twin.prev.b))
                    tPQ.insert(new Node(neighbor, twin.moves + 1, twin));
            }
            twin = tPQ.delMin();
        }


    }

    private class Node implements Comparable<Node> {
        Board b;
        int moves;
        Node prev;
        int distance;

        public Node(Board b, int moves, Node prev) {
            this.b = b;
            this.moves = moves;
            this.prev = prev;
            this.distance = b.manhattan();
        }

        public int compareTo(Node that) {
            return this.distance + this.moves - that.distance - that.moves;
        }
    }


    // is the initial board solvable?
    public boolean isSolvable() {
        return orig.b.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return orig.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> s = new Stack<>();
        Node i = orig;
        while (i != null) {
            s.push(i.b);
            i = i.prev;
        }
        return s;
    }


    // test client (see below)
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
            Solver s = new Solver(new Board(tiles));
        }
    }
}
