public class BruteCollinearPoints {
    static LineSegment[] segments;
    static int num_segemnts=0;

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
            for(int j=1;j<points.length;j++) {
                if (points[i].equals(points[j])) {
                    throw new IllegalArgumentException("points[" + i + "] is equal to points[" + j + "]");
                }
            }
        }

        for(int i=0;i<points.length;i++) {
            for(int j=1;j<points.length;j++) {
                for(int k=2;k<points.length;k++) {
                    for(int l=3;l<points.length;l++) {
                        Point p = points[i];
                        Point q= points[j];
                        Point r = points[k];
                        Point s = points[l];
                        if( s.compareTo(r) > 0 && r.compareTo(q) > 0 && q.compareTo(p) > 0 ) {
                            if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {

                            }
                        }
                    }
                }
            }
        }
    }
    public           int numberOfSegments()        // the number of line segments
    public LineSegment[] segments()                // the line segments
}