/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        Point[] sorted = Arrays.copyOf(points, points.length);
        Arrays.sort(sorted, new ByPos());

        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i].compareTo(sorted[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        segments = new LineSegment[0];

        for (int i = 0; i < sorted.length; i++) {
            for (int j = i + 1; j < sorted.length; j++) {
                for (int k = j + 1; k < sorted.length; k++) {
                    for (int l = k + 1; l < sorted.length; l++) {
                        if (sorted[i].slopeTo(sorted[j]) == sorted[i].slopeTo(sorted[k])
                                && sorted[i].slopeTo(sorted[j]) == sorted[i].slopeTo(sorted[l])) {
                            addSegment(sorted[i], sorted[l]);
                        }
                    }
                }
            }
        }
    }

    private class ByPos implements Comparator<Point> {
        public int compare(Point a, Point b) {
            return a.compareTo(b);
        }
    }

    private void addSegment(Point p, Point q) {
        if (numberOfSegments() == 0) {
            LineSegment tmp = new LineSegment(p, q);
            segments = new LineSegment[] { tmp };
        }
        else {
            LineSegment[] result = new LineSegment[segments.length + 1];
            for (int i = 0; i < segments.length; i++) {
                result[i] = segments[i];
            }
            result[segments.length] = new LineSegment(p, q);
            segments = result;
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
