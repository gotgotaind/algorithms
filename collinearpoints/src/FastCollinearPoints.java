
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Merge;

//import java.lang.reflect.Array;
import java.util.Arrays;

//import static java.lang.Double.compare;

public class FastCollinearPoints {
    private final Queue<LineSegment> segmentsQ=new Queue<LineSegment>();
    private final static boolean debug=false;

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

        // If there is only one point, no need to go further
        if ( points.length == 1 ) { return ; }

        //Arrays.sort(points, points[0].slopeOrder());
        Merge.sort(points);

        for(int i=0;i<points.length;i++) {
            //    int i=0;

            Point[] op = new Point[points.length - 1];
            int k = 0;
            for (int j = 0; j < points.length; j++) {
                if (j != i) {
                    op[k] = points[j];
                    k = k + 1;
                }
            }
            Point pi = points[i];
            if ( debug ) { StdOut.println("Pivot  point : "+pi); }
            //Merge.sort(op);
            Arrays.sort(op, pi.slopeOrder());


            Point start_segment = op[0];
            double slope = pi.slopeTo(start_segment);
            int seglen = 2;
            if ( debug ) { StdOut.println("first slope ordered slope compare :"+pi+" "+op[0]+" : "+slope); }
            boolean backflow=false;
            if( pi.compareTo(op[0])>0 ) {
                if ( debug ) { StdOut.println("Backflow!"); }
                backflow=true;
            }

            for (int j = 1; j < points.length - 1; j++) {
                if (pi.slopeTo(op[j])==slope) {
                    seglen = seglen + 1;
                    if ( debug ) {  StdOut.println("segment continued "+pi+" "+op[j]+" : "+slope+" seglen:"+seglen); }
                    if( pi.compareTo(op[j])>0 ) {
                        if ( debug ) {  StdOut.println("Backflow!"); }
                        backflow=true;
                    }
                    // if it's the last point of op and a segment was formed add it now.
                    // instead of checking if the next point in op is in the same segment
                    if ( ( j == ( points.length -2 ) ) && seglen >= 4 && backflow==false ) {
                        segmentsQ.enqueue(new LineSegment(pi, op[j]));
                        if ( debug ) {  StdOut.println("added segment with slope ( this segment ends at the last point of op ) : "+slope); }
                    }

                } else {
                    //if ( cocount >= 3 && compare(slope,0.0)>=0 ) {
                    if ( debug ) {  StdOut.println("segment break "+pi+" "+op[j]+" : "+slope); }
                    if ( seglen >= 4 && backflow==false ) {
                            segmentsQ.enqueue(new LineSegment(pi, op[j - 1]));
                        if ( debug ) {  StdOut.println("added segment with slope : "+slope); }
                    }
                    backflow=false;
                    start_segment = op[j];
                    slope = pi.slopeTo(op[j]);
                    if( pi.compareTo(op[j])>0 ) {
                        if ( debug ) { StdOut.println("Backflow!"); }
                        backflow=true;
                    }
                    seglen = 2;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println("Found "+collinear.numberOfSegments()+" segments.");
        StdOut.println("Showing segments :");
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println("StdDraw.show();");
        StdDraw.show();
        StdOut.println("ZOBA");
    }

}
