import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph G;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        this.G=new Digraph(G.V());

        // make this.G a copy of G so that it can be an immutable private attribute of DAG
        // as requested in the assignment
        for( int v=0;v<G.V();v++ ) {
            for( int a:G.adj(v) ) {
                this.G.addEdge(v,a);
            }
        }
    }

    private int[] shortest_ancestral_path(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);

        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);

        int length = Integer.MAX_VALUE;
        int sca = -1;
        boolean has_path = false;

        for (int x = 0; x < G.V(); x++) {
            if (bfsv.hasPathTo(x) && bfsw.hasPathTo(x)) {
                has_path = true;
                int sum = bfsv.distTo(x) + bfsw.distTo(x);
                if (sum < length) {
                    length = sum;
                    sca = x;

                }
            }
        }

        if (has_path) {
            return new int[]{sca,length};
        } else {
            return new int[]{-1,-1};
        }
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        // Just make an Iterable structure containing just one element of v and w to pass as
        // argument to shortest_ancestral_path
        Bag<Integer> vv=new Bag<Integer>();
        vv.add(v);
        Bag<Integer> ww=new Bag<Integer>();
        ww.add(w);

        return shortest_ancestral_path(vv, ww)[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        // Just make an Iterable structure containing just one element of v and w to pass as
        // argument to shortest_ancestral_path
        Bag<Integer> vv=new Bag<Integer>();
        vv.add(v);
        Bag<Integer> ww=new Bag<Integer>();
        ww.add(w);

        return shortest_ancestral_path(vv, ww)[0];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if ( v == null || w == null ) {
            throw new IllegalArgumentException("argument is null");
        }
        if ( ! v.iterator().hasNext() || ! w.iterator().hasNext() ) {
            return -1;
        }
        return shortest_ancestral_path(v, w)[1];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if ( v == null || w == null ) {
            throw new IllegalArgumentException("argument is null");
        }

        if ( ! v.iterator().hasNext() || ! w.iterator().hasNext() ) {
            return -1;
        }
        return shortest_ancestral_path(v, w)[0];
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = G.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }


        for (Integer v : vertices) {
            if (v == null) {
                throw new IllegalArgumentException("vertex is null");
            }
            validateVertex(v);
        }


    }


    // do unit testing of this class
    public static void main(String[] args) {
        Digraph G=new Digraph(new In("digraph1.txt"));
        StdOut.println(G);

        SAP sap=new SAP(G);
        StdOut.println(sap.length(10,7));

    }
}