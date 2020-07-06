/* *****************************************************************************
 *  Name:    Ada Lovelace
 *  NetID:   alovelace
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] open;
    private int rowSize;
    private WeightedQuickUnionUF perc;
    private WeightedQuickUnionUF fill;
    private int openCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.perc = new WeightedQuickUnionUF(n * n + 2);
        this.fill = new WeightedQuickUnionUF(n * n + 1);
        this.rowSize = n;
        this.open = new boolean[n * n];
        this.openCount = 0;

        int virtTop = n * n;
        int virtBot = n * n + 1;

        for (int i = 0; i < n; i++) { //virtual sites
            perc.union(i, virtTop);
            fill.union(i, virtTop);
            perc.union(n * (n - 1) + i, virtBot);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        inBoundsCheck(row, col);
        if (!isOpen(row, col)) {
            int indexToOpen = index(row, col);
            open[indexToOpen] = true;
            openCount++;

            int[] surrounding = {
                    index(row - 1, col),
                    index(row + 1, col),
                    index(row, col - 1),
                    index(row, col + 1)
            };

            for (int target : surrounding) {
                int[] tRowCol = rowCol(target);
                if (target >= 0 && isOpen(tRowCol[0], tRowCol[1])) {
                    perc.union(indexToOpen, target);
                    fill.union(indexToOpen, target);
                }
            }
        }
    }

    private void inBoundsCheck(int row, int col) {
        if (row < 1 || row > rowSize || col > rowSize || col < 1) {
            throw new IllegalArgumentException(
                    "Grid n is " + rowSize + ", you asked for: [" + row + ", " + col + "]");
        }
    }

    // covert row/col to index
    private int index(int row, int col) {
        if (row < 1 || row > rowSize || col > rowSize || col < 1) {
            return -1;
        }
        return (row - 1) * rowSize + col - 1;
    }

    // convert index to row/col
    private int[] rowCol(int i) {
        int[] r = { i / rowSize + 1, i % rowSize + 1 };
        return r;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        inBoundsCheck(row, col);
        return open[index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        inBoundsCheck(row, col);
        return isOpen(row, col) && fill.find(index(row, col)) == fill.find(rowSize * rowSize);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        if (rowSize == 1) {
            return open[0];
        }
        int topIndex = rowSize * rowSize;
        int botIndex = topIndex + 1;
        return perc.find(topIndex) == perc.find(botIndex);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(6);
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 7; j++) {
                perc.open(i, j);
                StdOut.println(
                        "" + perc.isOpen(i, j) + perc.percolates() + perc.numberOfOpenSites() + perc
                                .isFull(i, j));
            }
        }
    }
}
