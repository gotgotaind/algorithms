import edu.princeton.cs.algs4.*;

public class PointSET {
    private SET<Point2D> s;

    // construct an empty set of points
    public PointSET() {
        s = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        if (s.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    // number of points in the set
    public               int size() {
        return s.size();
    }

    // add the point to the set (if it is not already in the set)
    public              void insert(Point2D p) {
        if( ! s.contains(p) ) {
            s.add(p);
        }
    }

    // does the set contain point p?
    public           boolean contains(Point2D p)    {
        if( s.contains(p) ) { return true; }
        return false;
    }

    // draw all points to standard draw
    public              void draw()  {
        for(Point2D p: s ) {
            StdDraw.point(p.x(),p.y());
        }

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)    {
        Queue<Point2D> q=new Queue<Point2D>();
        for(Point2D p:s ) {
            if( p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y()>=rect.ymin() && p.y() <= rect.ymax() ) {
                q.enqueue(p);
            }
        }
        return q;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public           Point2D nearest(Point2D p)   {
        if ( s.isEmpty() ) { return null; }
        Double d=Double.POSITIVE_INFINITY;
        Point2D nearest=null;
        for ( Point2D q:s ) {
            if( p!=q &&  p.distanceTo(q) < d ) {
                nearest=q;
            }

        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args)   {

    }
}
