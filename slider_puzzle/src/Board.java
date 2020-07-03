import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


import java.util.Iterator;

public class Board {

    private final int[][] tiles;
    private final int n;
    private final int[][] goal;
    private final tile[] goalij;
    private final tile[] tileij;

    private class tile {
        private int i;
        private int j;
        tile(int i,int j) {
            this.i=i;
            this.j=j;
        }

    }
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {


        n= tiles.length;
        this.tiles=new int[n][n];
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
        goal=new int[n][n];
        goalij=new tile[n*n];
        tileij=new tile[n*n];

        int k=1;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {

                tileij[tiles[i][j]]=new tile(i,j);
                if( tiles[i][j]==6 ) {
                    StdOut.println("tiles[i][j]=6,i="+i+",j="+j);
                }

                if ( i==(n-1) && j==(n-1) ) {
                    goal[i][j]=0;
                    goalij[0]=new tile(i,j);
                }
                else {
                    goal[i][j]=k;
                    goalij[k]=new tile(i,j);
                }
                k=k+1;
            }

        }



    }

    // string representation of this board
    public String toString() {
        String out=new String();
        out=""+n+"\n";
        for(int i=0;i<n;i++) {
            //cout=out+"\t";
            for(int j=0;j<n;j++) {
                out=out+"\t"+tiles[i][j];
            }
            out=out+"\n";
        }
        return out;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int distance=0;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if( tiles[i][j]!=goal[i][j] ) {
                    distance=distance+1;
                }
            }

        }
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance=0;
        for(int k=1;k<n*n;k++) {
                StdOut.println("k :"+k+",tileij[k].i :"+tileij[k].i);
                distance=distance+Math.abs(tileij[k].i-goalij[k].i)+Math.abs(tileij[k].j-goalij[k].j);
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if ( tiles[i][j]!=goal[i][j] ) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board goal() {
        Board b=new Board(tiles);
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                b.tiles[i][j]=goal[i][j];
            }
        }
        return b;
    }

    // does this board equal y?
    public boolean equals(Object y) {

        // must test that size are equals !
        
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
        Stack<Board> s=new Stack<Board>();

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if ( tiles[i][j]==0 ) {
                    i0=i;
                    j0=j;
                }
            }
        }

        // move right?
        if ( i0 + 1 < n ) {
            Board br=new Board(tiles);
            int v=br.tiles[i0+1][j0];
            br.tiles[i0+1][j0]=0;
            br.tiles[i0][j0]=v;
            s.push(br);
        }

        // move left?
        if ( i0 - 1 > 0 ) {
            Board bl=new Board(tiles);
            int v=bl.tiles[i0-1][j0];
            bl.tiles[i0-1][j0]=0;
            bl.tiles[i0][j0]=v;
            s.push(bl);
        }

        // move down?
        if ( j0 + 1 < n ) {
            Board bd=new Board(tiles);
            int v=bd.tiles[i0][j0+1];
            bd.tiles[i0][j0+1]=0;
            bd.tiles[i0][j0]=v;
            s.push(bd);
        }

        // move up?
        if ( j0 - 1 > 0 ) {
            Board bu=new Board(tiles);
            int v=bu.tiles[i0][j0-1];
            bu.tiles[i0][j0-1]=0;
            bu.tiles[i0][j0]=v;
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
                if ( tiles[i][j]!=0 && i!=i0 && j!=j0) {
                    i1=i;
                    j1=j;
                    break;
                }
            }
            if ( tiles[i][j1]!=0 && i!=i0 && j1!=j0) {
                break;
            }
        }

        Board b=new Board(tiles);
        int v=b.tiles[i0][j0];
        b.tiles[i0][j0]=b.tiles[i1][j1];
        b.tiles[i1][j1]=v;
        return b;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int n=3;
        int[][] tiles=new int[n][n+1];

        int k=0;
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = k;
                k=k+1;
            }
        }

        StdOut.println("initial board :");
        Board b=new Board(tiles);
        StdOut.println(b.toString());
        StdOut.println("Goal board :");
        b=b.goal();
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