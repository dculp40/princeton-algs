/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PointSET {
    private SET<Point2D> s;

    // construct an empty set of points
    public PointSET() {
        s = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return s.isEmpty();
    }

    // number of points in the set
    public int size() {
        return s.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!contains(p)) s.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return s.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        for (Point2D p : s) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> result = new Stack<>();

        for (Point2D p : s) {
            if (rect.contains(p)) result.push(p);
        }

        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        Point2D result = null;
        double minDist = Integer.MAX_VALUE;

        for (Point2D q : s) {
            double newDist = p.distanceTo(q);
            if (newDist < minDist) {
                result = q;
                minDist = newDist;
            }
        }

        return result;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET pSet = new PointSET();

        for (int i = 0; i < 10; i++) {
            pSet.insert(new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0)));
        }

        pSet.draw();

        RectHV r = new RectHV(0.2, 0.2, 0.8, 0.8);
        r.draw();

        StdOut.println("In Rectangle:");
        for (Point2D p : pSet.range(r)) {
            StdOut.println(p.toString());
        }

        StdOut.println("Closest to (0.5, 0.5):");
        StdOut.println(pSet.nearest(new Point2D(0.5, 0.5)));

    }

}
