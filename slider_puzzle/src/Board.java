import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


import java.util.Iterator;

public class Board {

    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {


        n= tiles.length;
        this.tiles=new int[n][n];

        //StdOut.println("In tiles initialization loop");
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                // StdOut.println("tiles[i][j]=v,i="+i+",j="+j+",v="+tiles[i][j]+",this.v="+this.tiles[i][j]);
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder out
                = new StringBuilder();

        //str.append("GFG");
        //String out=n+"\n";
        //out=""+n+"\n";
        for(int i=0;i<n;i++) {
            //cout=out+"\t";
            for(int j=0;j<n;j++) {
                out=out.append("\t");
                out=out.append(tiles[i][j]);
            }
            out=out.append("\n");
        }
        return out.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int distance=0;
        int k=1;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if( tiles[i][j]!=k && tiles[i][j]!=0 ) {
                    distance=distance+1;
                }
                k=k+1;
            }

        }
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance=0;

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                int k=1;
                for(int l=0;l<n;l++) {
                    for (int m = 0; m < n; m++) {
                        if (tiles[i][j] == k && tiles[i][j] != 0) {
                            distance = distance + Math.abs(l-i)+Math.abs(m-j);
                        }
                        k=k+1;
                        if ( k == n*n ) { k=0; }
                    }
                }
            }

        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int k=1;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if ( tiles[i][j]!=k ) {
                    return false;
                }
                k=k+1;
                if ( k== n*n ) { k=0; }
            }
        }
        return true;
    }


    // does this board equal y?
    public boolean equals(Object y) {

        // must test that size are equals !
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board by=(Board) y;
        if( by.dimension() != this.dimension() ) {
            return false;
        }
        
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if ( tiles[i][j]!=((Board) y).tiles[i][j] ) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int i0=0,j0=0;
        int[][] ntiles = new int[n][n];
        Stack<Board> s=new Stack<Board>();

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                ntiles[i][j]=tiles[i][j];
                if ( tiles[i][j]==0 ) {
                    i0=i;
                    j0=j;
                }
            }
        }

        // move right?
        if ( i0 + 1 < n ) {

            int v=ntiles[i0+1][j0];
            ntiles[i0+1][j0]=0;
            ntiles[i0][j0]=v;
            Board br=new Board(ntiles);
            s.push(br);
        }


        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                ntiles[i][j]=tiles[i][j];
            }
        }

        // move left?
        if ( i0 - 1 >= 0 ) {
            int v=ntiles[i0-1][j0];
            ntiles[i0-1][j0]=0;
            ntiles[i0][j0]=v;
            Board bl=new Board(ntiles);
            s.push(bl);
        }

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                ntiles[i][j]=tiles[i][j];
            }
        }

        // move down?
        if ( j0 + 1 < n ) {

            int v=ntiles[i0][j0+1];
            ntiles[i0][j0+1]=0;
            ntiles[i0][j0]=v;
            Board bd=new Board(ntiles);
            s.push(bd);
        }

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                ntiles[i][j]=tiles[i][j];
            }
        }

        // move up?
        if ( j0 - 1 >= 0 ) {

            int v=ntiles[i0][j0-1];
            ntiles[i0][j0-1]=0;
            ntiles[i0][j0]=v;
            Board bu=new Board(ntiles);
            s.push(bu);
        }


        return s;
    }



    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int i0=0;
        int j0=0;
        int i1=0;
        int j1=0;

        int[][] ttiles=new int[n][n];
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                ttiles[i][j] = tiles[i][j];
            }
        }
        // find the first non zero tile
        // true we could use this label thing to break out of the double loop using only one break
        // not sure what would be the most readable
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if ( tiles[i][j]!=0 ) {
                    i0=i;
                    j0=j;
                    break;
                }
            }
            if ( tiles[i][j0]!=0 ) {
                break;
            }
        }

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if ( tiles[i][j]!=0 && ! (i==i0 && j==j0) ) {
                    i1=i;
                    j1=j;
                    break;
                }
            }
            if ( tiles[i][j1]!=0 && ! (i==i0 && j1==j0) ) {
                break;
            }
        }

        int v=ttiles[i0][j0];
        ttiles[i0][j0]=ttiles[i1][j1];
        ttiles[i1][j1]=v;
        Board b=new Board(ttiles);
        return b;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int n=3;
        int[][] tiles=new int[n][n];
        int[][] goal=new int[n][n];

        int k=0;
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = k;
                if ( k==n*n-1 ) {
                    goal[i][j]=0;

                }
                else
                {
                    goal[i][j]=k+1;
                }
                k=k+1;
            }
        }

        StdOut.println("initial board :");
        Board b=new Board(tiles);
        StdOut.println(b.toString());
        StdOut.println("Goal board :");
        b=new Board(goal);
        StdOut.println(b.toString());

        StdOut.println("Neighbors :");
        int neib=0;
        for ( Board bb : b.neighbors() ) {
            StdOut.println("Neigbor "+neib);
            StdOut.println(bb.toString());
            StdOut.println("manatan :"+bb.manhattan());
            StdOut.println("hamming :"+bb.hamming());
            StdOut.println("equal to b :"+bb.equals(b));
            StdOut.println("equal to itself :"+bb.equals(bb));
            neib++;
        }
        StdOut.println("Twin :");
        StdOut.println(b.twin());
    }


}