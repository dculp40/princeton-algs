/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> r = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            r.enqueue(StdIn.readString());
        }
        Iterator<String> i = r.iterator();
        for (int j = 0; j < Integer.parseInt(args[0]); j++) {
            StdOut.println(i.next());
        }
    }
}
