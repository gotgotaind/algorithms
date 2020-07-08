import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private boolean isSolvable=false;
    private int moves=-1;
    private Stack<Board> s=new Stack<Board>();

    private class SearchNode implements Comparable<SearchNode>{
        Board b;
        int moves;
        SearchNode previous;

        public int compareTo(SearchNode b){
            int pa=this.moves+this.b.manhattan();
            int pb=b.moves+b.b.manhattan();
            if( pa==pb) {
                return 0;
            }
            else if ( pa > pb ) {
                return 1;
            }
            else if ( pa < pb ) {
                return -1;
            }
            return 0;

        };

    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if ( initial == null ) {
            throw new IllegalArgumentException("initial board is null");
        }

        SearchNode sn0=new SearchNode();
        sn0.b=initial;
        sn0.moves=0;
        sn0.previous=null;
        MinPQ<SearchNode> pq0=new MinPQ<SearchNode>();
        pq0.insert(sn0);

        SearchNode sn1=new SearchNode();
        sn1.b=initial.twin();
        sn1.moves=0;
        sn1.previous=null;
        MinPQ<SearchNode> pq1=new MinPQ<SearchNode>();
        pq1.insert(sn1);
        // StdOut.println("Initial board :"+sn0.b.toString());



        SearchNode end=null;

        while ( true ) {
            sn0=pq0.delMin();
            sn1=pq1.delMin();
            if( sn0.b.isGoal() ) {
                end=sn0;
                break;
            }
            if( sn1.b.isGoal() ) {
                end=null;
                break;
            }
            for ( Board bb : sn0.b.neighbors() ) {
                SearchNode sn=new SearchNode();
                sn.b=bb;
                sn.moves=sn0.moves+1;
                sn.previous=sn0;
                pq0.insert(sn);
            }
            for ( Board bb : sn1.b.neighbors() ) {
                SearchNode sn=new SearchNode();
                sn.b=bb;
                sn.moves=sn1.moves+1;
                sn.previous=sn1;
                pq1.insert(sn);
            }

        }

        if ( end == null ) {
            moves=-1;
            s=null;
            isSolvable=false;
        }
        else
        {
            moves=end.moves;
            isSolvable=true;
            SearchNode next=end;
            while ( next != null ) {
                s.push(next.b);
                next = next.previous;
            }
        }


    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return s;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}