
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Merge;

import java.lang.reflect.Array;
import java.util.Arrays;

public class FastCollinearPoints {
    static Queue<LineSegment> segmentsQ=new Queue();

    public FastCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if ( points == null ) {
            throw new IllegalArgumentException("points is null");
        }

        // Just checks that all points are different
        for(int i=0;i<points.length;i++) {
            if( points[i] == null ) {
                throw new IllegalArgumentException("points["+i+"] is null");
            }
            for(int j=0;j<points.length;j++) {
                if ( i != j ) {
                    if (points[i].equals(points[j])) {
                        throw new IllegalArgumentException("points[" + i + "] is equal to points[" + j + "]");
                    }
                }
            }
        }

        Merge.sort(points);
        for(int i=0;i<points.length;i++) {
            Point[] op=new Point[points.length-1];
            int k=0;
            for(int j=0;j<points.length;j++) {
                if ( j != i ) {
                    op[k]=points[j];
                    k=k+1;
                }
            }
            Point pi=points[i];
            Arrays.sort(op,pi.slopeOrder());

            Point start_segment=op[0];
            double slope=pi.slopeTo(start_segment);
            int cocount=1;

            for(int j=1;j<points.length-1;j++) {
                if( pi.slopeTo(op[j]) == slope ) {
                    cocount=cocount+1;
                }
                else {
                    if ( cocount >= 4 ) {
                        segmentsQ.enqueue(new LineSegment(start_segment, op[j-1]));
                    }
                    start_segment=op[j];
                    slope=pi.slopeTo(op[j]);
                    cocount=1;
                }

            }

        }
    }
    public           int numberOfSegments()        // the number of line segments
    {
        return segmentsQ.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] lsa=new LineSegment[segmentsQ.size()];
        int i=0;
        for ( LineSegment ls : segmentsQ ) {
            lsa[i]=ls;
            i=i+1;
        }
        return lsa;
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
        StdOut.println("Found "+collinear.numberOfSegments()+" segments.");
        StdOut.println("Showing segments :");
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println("StdDraw.show();");
        StdDraw.show();
        StdOut.println("ZOBI");
    }

}
