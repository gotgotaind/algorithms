import edu.princeton.cs.algs4.*;

public class BruteCollinearPoints {
    static Queue<LineSegment> segmentsQ=new Queue();

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
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

        for(int i=0;i<points.length;i++) {
            for(int j=0;j<points.length;j++) {
                if ( j != i ) {
                    for (int k = 0; k < points.length; k++) {
                        if ( k!=i && k!=j ) {
                            for (int l = 0; l < points.length; l++) {
                                if ( l!=i && l!=k && l!=j ) {
                                    StdOut.println(i+","+j+","+k+","+l);
                                    Point p = points[i];
                                    Point q = points[j];
                                    Point r = points[k];
                                    Point s = points[l];
                                    // if( s.compareTo(r) > 0 && r.compareTo(q) > 0 && q.compareTo(p) > 0 ) {
                                    if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                                        segmentsQ.enqueue(new LineSegment(p, s));
                                    }
                                    // }
                                }

                            }
                        }
                    }
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