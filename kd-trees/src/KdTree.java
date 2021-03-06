import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class KdTree {
    private Node root;
    private int size;
    final static private RectHV rootRect=new RectHV(0.0, 0.0, 1.0, 1.0);

    private class Node {
        Point2D p;
        Node ld;
        Node ru;
        private final RectHV rect;  // the rectangle containing this node
        //boolean lr; // if true left/right comparison, else up/down
        //Node(Point2D p,boolean lr) {
        Node(Point2D p,RectHV rect) {
            this.p=p;
            this.rect=rect;
        }
    }

    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        if (root == null) {
            return true;
        } else {
            return false;
        }
    }

    public int size()
    {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls insert() with a null point");
        root = insert(root, p,true,rootRect);
        //assert check();
    }

    private Node insert(Node x, Point2D p,boolean lr,RectHV rect) {

        if (x == null) {
            size=size+1;
            //draw();
            Node niou=new Node(p,rect);
            return niou;
        }

        //int cmp = key.compareTo(x.key);
        //if      (cmp < 0) x.left  = put(x.left,  key, val);
        //else if (cmp > 0) x.right = put(x.right, key, val);
        //else              x.val   = val;
        //x.size = 1 + size(x.left) + size(x.right);
        //return x;
        if(( p.x() == x.p.x()) && (p.y() == x.p.y() ) ) return x;

        double rxmin=rect.xmin();
        double rxmax=rect.xmax();
        double rymin=rect.ymin();
        double rymax=rect.ymax();

        if ( lr ) {
            if(p.x() <= x.p.x() ) {
                // this if is to avoid recreating a new rectHV if the node already exists
                if( x.ld == null ) {
                    x.ld = insert(x.ld, p, !lr, new RectHV(rxmin, rymin, x.p.x(), rymax));
                }
                else
                {
                    x.ld = insert( x.ld, p, !lr, x.ld.rect );
                }
            }
            else {
                if( x.ru == null ) {
                    x.ru = insert(x.ru, p, !lr, new RectHV(x.p.x(), rymin, rxmax, rymax));
                }
                else
                {
                    x.ru = insert(x.ru, p, !lr, x.ru.rect);
                }
            }
        }
        else {
            if(p.y() <= x.p.y() ) {
                if ( x.ld == null ) {
                    x.ld = insert(x.ld, p, !lr, new RectHV(rxmin, rymin, rxmax, x.p.y()));
                }
                else
                {
                    x.ld = insert(x.ld, p, !lr, x.ld.rect );
                }
            }
            else {
                if( x.ru == null ) {
                    x.ru = insert(x.ru, p, !lr, new RectHV(rxmin, x.p.y(), rxmax, rymax));
                }
                else
                {
                    x.ru = insert(x.ru, p, !lr, x.ru.rect );
                }
            }
        }

        //throw new IllegalStateException("I don't think we should come here.");
        return x;
    }


    // does the tree contain point p?
    public           boolean contains(Point2D p)    {
        if (p == null) throw new IllegalArgumentException("calls contains() with a null point");
        return contains(root,p,true);

    }

    private boolean contains(Node x,Point2D p, boolean lr) {
        if(p==null) throw new IllegalArgumentException("calls contains() with a null point");
        if( x==null ) return false;
        if(( p.x() == x.p.x()) && (p.y() == x.p.y() ) ) return true;

        if ( lr ) {
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



    // draw all points to standard draw
    public              void draw()  {
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        Node n=root;


        if ( n!=null ) {

            StdDraw.setPenRadius(0.002);

            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(),rootRect.ymin(),n.p.x(),rootRect.ymax());
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(n.p.x(),n.p.y());
            draw_children(n,false);

        }
        StdDraw.show();
        StdDraw.pause(20);

    }
    private void draw_children(Node n,boolean lr) {
        if( n.ld != null ) {
            Node nn=n.ld;
            StdDraw.setPenRadius(0.001);

            if( lr ) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(nn.p.x(),nn.rect.ymin(),nn.p.x(),nn.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(nn.rect.xmin(),nn.p.y(),nn.rect.xmax(),nn.p.y());
            }
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(n.ld.p.x(),n.ld.p.y());
            draw_children(n.ld,! lr);
        }
        if( n.ru != null ) {
            Node nn=n.ru;
            StdDraw.setPenRadius(0.001);

            if( lr ) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(nn.p.x(),nn.rect.ymin(),nn.p.x(),nn.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(nn.rect.xmin(),nn.p.y(),nn.rect.xmax(),nn.p.y());
            }
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(n.ru.p.x(),n.ru.p.y());
            draw_children(n.ru,! lr);
        }
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)    {
        if (rect == null) throw new IllegalArgumentException("calls range() with a null rect");
        Queue<Point2D> q=new Queue<Point2D>();
        if( isEmpty() ) { return q; }
        in_rect(root,q,rect,true);
        /*
        for(Point2D p:s ) {
            if( p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y()>=rect.ymin() && p.y() <= rect.ymax() ) {
                q.enqueue(p);
            }
        }
        */


        return q;
    }

    private void in_rect(Node n, Queue<Point2D> q, RectHV rect, boolean lr) {
        if( in_rect(n.p,rect) ) { q.enqueue(n.p); }
        if ( lr ) {
            if ( n.ld != null && rect.xmin() <= n.p.x() ) {
                in_rect(n.ld,q,rect,! lr);
            }
            if ( n.ru != null && rect.xmax() >= n.p.x() ) {
                in_rect(n.ru,q,rect,! lr);
            }
        }
        else
        {
            if ( n.ld != null && rect.ymin() <= n.p.y() ) {
                in_rect(n.ld,q,rect,! lr);
            }
            if ( n.ru != null && rect.ymax() >= n.p.y() ) {
                in_rect(n.ru,q,rect,! lr);
            }
        }
        return;

    }

    private boolean in_rect(Point2D p,RectHV rect ) {
        if( p.x()>=rect.xmin() && p.x()<=rect.xmax() && p.y()>=rect.ymin() && p.y()<=rect.ymax() ) {
            return true;
        }
        else
        {
            return false;
        }
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public           Point2D nearest(Point2D p)   {
        if (p == null) throw new IllegalArgumentException("calls nearest() with a null point");
        if ( isEmpty() ) return null;
        return nearest(p,root,true,root.p);
    }

    private Point2D nearest(Point2D p,Node n, boolean lr, Point2D nearest ) {
        if( p.distanceSquaredTo(n.p) < p.distanceSquaredTo(nearest) ) { nearest=n.p; }
        if( lr ) {

            if( p.x() <= n.p.x() ) {
                if( n.ld != null ) nearest=nearest(p,n.ld,! lr, nearest);
                if ( n.ru != null  && n.ru.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest) ) {
                    nearest=nearest(p,n.ru,! lr, nearest);
                }
            }
            else
            {
                if ( n.ru != null )  nearest=nearest(p,n.ru,! lr, nearest);
                if( n.ld != null && n.ld.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest) )
                {
                     nearest=nearest(p,n.ld,! lr, nearest);
                }
            }
        }
        else {

            if (p.y() <= n.p.y()) {
                if( n.ld != null )  nearest = nearest(p, n.ld, !lr, nearest);
                if ( n.ru != null && n.ru.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest)) {
                    nearest = nearest(p, n.ru, !lr, nearest);
                }
            } else {
                if ( n.ru != null ) nearest = nearest(p, n.ru, !lr, nearest);
                if ( n.ld != null && n.ld.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearest)) {
                    nearest = nearest(p, n.ld, !lr, nearest);
                }
            }
        }
        return nearest;
    }



    // unit testing of the methods (optional)
    public static void main(String[] args) {

        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        //PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            //kdtree.isEmpty();
            //brute.insert(p);
        }
        kdtree.draw();

        Point2D nearest=kdtree.nearest(new Point2D(0.27, 0.77));
        StdOut.println("Nearest is  "+nearest.toString());
        /*
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



        Point2D nearest=kd.nearest(new Point2D(0.42,0.38));
        StdOut.println("Nearest is  "+nearest.toString());

        for( Point2D p : kd.range(new RectHV(0.2, 0.2, 0.42, 0.42))) {
            StdOut.println("rect contains  "+p.toString());
        }


*/
    }
}
