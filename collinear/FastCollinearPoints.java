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

public class FastCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
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

        if (points.length < 4) return;

        //cycle through points
        for (Point p : points) {
            Arrays.sort(sorted, p.slopeOrder());

            int colStartIndex = 1;
            int currIndex = 2;
            Point q = sorted[colStartIndex]; //update endpoint
            Boolean segIsIncreasing = p.compareTo(q) < 0; //check for mid-segment

            while (currIndex < sorted.length) {
                //segIsIncreasing = p.compareTo(sorted[currIndex]) < 0;
                if (p.slopeTo(sorted[colStartIndex]) != p
                        .slopeTo(sorted[currIndex])) { //found segment end
                    if (segIsIncreasing && currIndex - colStartIndex > 2) addSegment(p, q);
                    colStartIndex = currIndex;
                    q = sorted[colStartIndex]; //update endpoint
                    segIsIncreasing = p.compareTo(q) < 0; //check for mid-segment
                }
                else { //still in segment
                    segIsIncreasing = segIsIncreasing
                            && p.compareTo(sorted[currIndex]) < 0; //check for mid-segment
                    if (q.compareTo(sorted[currIndex]) < 0) q = sorted[currIndex]; //update endpoint
                }
                currIndex++;
            }
            if (segIsIncreasing && currIndex - colStartIndex > 2) addSegment(p, q);
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
        StdDraw.setXscale(-100, 32768);
        StdDraw.setYscale(-100, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
