import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

public class Solver {

    class SearchNode {
        Board b;
        int moves;
        SearchNode previous;

        public Comparator<SearchNode> priority() {
            return this::comparePriority;
        };

        private int comparePriority(SearchNode a,SearchNode b){
            int pa=a.moves+a.b.manhattan();
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

    MinPQ<SearchNode> pq=new MinPQ<SearchNode>(SearchNode::priority);
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        SearchNode sn0=new SearchNode();
        sn0.b=initial;
        sn0.moves=0;
        sn0.previous=null;

        pq.insert(sn0);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable()

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()

    // test client (see below)
    public static void main(String[] args)

}