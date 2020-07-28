/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node tree;
    private int size;

    // construct an empty tree of points
    public KdTree() {
        tree = new Node(new RectHV(0, 0, 1, 1));
    }

    private static class Node {
        private Point2D p;
        private RectHV r;
        private Node lb;
        private Node rt;

        private Node(Point2D p) {
            this.p = p;
        }

        private Node(RectHV r) {
            this.r = r;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return tree.p == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    /* / recursive tree size
    private int size(Node n) {
        if (n.p == null) return 0;
        return 1 + size(n.lb) + size(n.rt);
    }
    */

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!contains(p)) {
            tree = insert(p, tree, 0);
            size++;
        }
    }

    private Node insert(Point2D p, Node n, int level) {
        if (n.p == null) {
            n.p = p;
            if (level % 2 == 0) {
                n.lb = new Node(new RectHV(n.r.xmin(), n.r.ymin(), p.x(), n.r.ymax()));
                n.rt = new Node(new RectHV(p.x(), n.r.ymin(), n.r.xmax(), n.r.ymax()));
            }
            else {
                n.lb = new Node(new RectHV(n.r.xmin(), n.r.ymin(), n.r.xmax(), p.y()));
                n.rt = new Node(new RectHV(n.r.xmin(), p.y(), n.r.xmax(), n.r.ymax()));
            }
            return n;
        }

        if (n.lb.r.contains(p)) {
            n.lb = insert(p, n.lb, level + 1);
        }
        else {
            n.rt = insert(p, n.rt, level + 1);
        }

        return n;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(p, tree);
    }

    // recursive tree contains
    private boolean contains(Point2D p, Node n) {
        if (n.p == null) return false;
        if (n.p.equals(p)) return true;

        if (n.lb.r.contains(p)) return contains(p, n.lb);
        return contains(p, n.rt);
    }

    // draw all points to standard draw
    public void draw() {
        draw(tree, 0);
    }

    // recursive draw
    private void draw(Node n, int level) {
        if (n.p == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.p.draw();

        StdDraw.setPenRadius();
        if (level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.lb.r.xmax(), n.lb.r.ymin(), n.lb.r.xmax(), n.lb.r.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.lb.r.xmin(), n.lb.r.ymax(), n.lb.r.xmax(), n.lb.r.ymax());
        }

        draw(n.lb, level + 1);
        draw(n.rt, level + 1);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> result = new Stack<>();

        range(rect, tree, result);

        return result;
    }

    // recursive range
    private void range(RectHV rect, Node n, Stack<Point2D> s) {
        if (n.p == null) return;
        if (rect.contains(n.p)) s.push(n.p);

        if (rect.intersects(n.lb.r)) range(rect, n.lb, s);
        if (rect.intersects(n.rt.r)) range(rect, n.rt, s);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        // Point2D result = null;
        // double minDist = Integer.MAX_VALUE;

        return nearest(p, tree, tree.p);
    }

    // recursive nearest
    private Point2D nearest(Point2D p, Node n, Point2D champ) {
        if (n.p == null) return null;
        if (n.r.distanceSquaredTo(p) > p.distanceSquaredTo(champ))
            return null; // this subtree cannot contain champ
        if (n.p.distanceSquaredTo(p) < p.distanceSquaredTo(champ)) champ = n.p;

        Point2D trialPoint;
        // double trialDist;
        if (n.lb.r.distanceSquaredTo(p) < n.rt.r
                .distanceSquaredTo(p)) { // left side is closer to query point
            trialPoint = nearest(p, n.lb, champ); // get left champ
            if (trialPoint != null && trialPoint.distanceSquaredTo(p) < p
                    .distanceSquaredTo(champ)) {
                champ = trialPoint;
            }
            trialPoint = nearest(p, n.rt, champ); // get right champ
            if (trialPoint != null && trialPoint.distanceSquaredTo(p) < p
                    .distanceSquaredTo(champ)) {
                champ = trialPoint;
            }
        }
        else { // right side is closer
            trialPoint = nearest(p, n.rt, champ); // get right champ
            if (trialPoint != null && trialPoint.distanceSquaredTo(p) < p
                    .distanceSquaredTo(champ)) {
                champ = trialPoint;
            }
            trialPoint = nearest(p, n.lb, champ); // get left champ
            if (trialPoint != null && trialPoint.distanceSquaredTo(p) < p
                    .distanceSquaredTo(champ)) {
                champ = trialPoint;
            }
        }

        return champ;
    }


    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree pTree = new KdTree();

/*
        for (int i = 0; i < 10; i++) {
            pTree.insert(
                    new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0)));
        }

 */


        pTree.insert(new Point2D(.7, .2));
        pTree.insert(new Point2D(.5, .4));
        pTree.insert(new Point2D(.2, .3));
        pTree.insert(new Point2D(.4, .7));
        pTree.insert(new Point2D(.9, .6));

        pTree.draw();


        RectHV r = new RectHV(0.2, 0.2, 0.8, 0.8);
        StdDraw.setPenColor(StdDraw.BLACK);
        r.draw();

        StdOut.println("In Rectangle:");
        for (Point2D p : pTree.range(r)) {
            StdOut.println(p.toString());
        }


        StdOut.println("Closest to (0.82, 0.38):");
        Point2D nearest = pTree.nearest(new Point2D(0.82, 0.38));
        StdOut.println(nearest.toString());

        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.line(0.82, 0.38, nearest.x(), nearest.y());


    }

}
