import edu.princeton.cs.algs4.*;

public class KdTree {
    private Node root;
    private int size;

    class Node {
        Point2D p;
        Node ld;
        Node ru;
        boolean lr; // if true left/right comparison, else up/down
        Node(Point2D p,boolean lr) {
            this.p=p;
            this.lr=lr;
        }
    }

    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        if (root.p == null) {
            return true;
        } else {
            return false;
        }
    }

    private int size()
    {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls insert() with a null point");
        root = insert(root, p,false);
        //assert check();
    }

    private Node insert(Node x, Point2D p,boolean lr) {

        if (x == null) {
            size=size+1;
            return new Node(p,! lr);
        }

        //int cmp = key.compareTo(x.key);
        //if      (cmp < 0) x.left  = put(x.left,  key, val);
        //else if (cmp > 0) x.right = put(x.right, key, val);
        //else              x.val   = val;
        //x.size = 1 + size(x.left) + size(x.right);
        //return x;
        if(( p.x() == x.p.x()) && (p.y() == x.p.y() ) ) return x;

        if ( x.lr ) {
            if(p.x() <= x.p.x() ) {
                x.ld=insert(x.ld,p,! lr);
            }
            else {
                x.ru=insert(x.ru,p,! lr);
            }
        }
        else {
            if(p.y() <= x.p.y() ) {
                x.ld=insert(x.ld,p,! lr);
            }
            else {
                x.ru=insert(x.ru,p,! lr);
            }
        }

        return x;
    }


    // does the tree contain point p?
    public           boolean contains(Point2D p)    {
        return contains(root,p,false);

    }

    private boolean contains(Node x,Point2D p, boolean lr) {
        if(p==null) throw new IllegalArgumentException("calls contains() with a null point");
        if( x==null ) return false;
        if(( p.x() == x.p.x()) && (p.y() == x.p.y() ) ) return true;

        if ( x.lr ) {
            if(p.x() <= x.p.x() ) {
                return contains(x.ld,p,! lr);
            }
            else {
                return contains(x.ru,p,! lr);
            }
        }
        else {
            if(p.y() <= x.p.y() ) {
                return contains(x.ld,p,! lr);
            }
            else {
                return contains(x.ru,p,! lr);
            }
        }

    }


    /*
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

     */

    // unit testing of the methods (optional)
    public static void main(String[] args)   {
        /*
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);

        StdDraw.enableDoubleBuffering();
        PointSET kdtree = new PointSET();
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.show();
                }
            }
            StdDraw.pause(20);
        }

         */

        KdTree kd=new KdTree();
        kd.insert(new Point2D(0.5,0.5));
        StdOut.println("kd contains 0.5,0.5? "+kd.contains(new Point2D(0.5,0.5)));
        kd.insert(new Point2D(0.4,0.4));
        StdOut.println("kd contains 0.4,0.4? "+kd.contains(new Point2D(0.4,0.4)));
        StdOut.println("kd contains 0.3,0.3? "+kd.contains(new Point2D(0.3,0.3)));
        kd.insert(new Point2D(0.3,0.3));
        StdOut.println("kd contains 0.3,0.3? "+kd.contains(new Point2D(0.3,0.3)));
        StdOut.println("kd contains 0.6,0.5? "+kd.contains(new Point2D(0.6,0.5)));
        kd.insert(new Point2D(0.6,0.5));
        StdOut.println("kd contains 0.6,0.5? "+kd.contains(new Point2D(0.6,0.5)));

    }
}
