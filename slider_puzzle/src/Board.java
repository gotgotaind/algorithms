import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


import java.util.Iterator;

public class Board {

    private final int[][] tiles;
    private final int n;
    private final int[][] goal;
    private final tile[] goalij;

    private class tile {
        private int i;
        private int j;

    }
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles=tiles;
        n= (int) Math.sqrt(tiles.length);
        goal=new int[n][n];
        goalij=new tile[n];

        int k=1;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                goal[i][j]=k;
                goalij[k].i = i;
                goalij[k].j = j;
                if ( i==(n-1) && j==(n-2) ) {
                    goal[i][j]=0;
                    goalij[0].i=i;
                    goalij[0].j=j;
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
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                distance=distance+Math.abs(i-goalij[goal[i][j]].i)+Math.abs(j-goalij[goal[i][j]].j);
            }

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
            Board b=new Board(tiles);
            int v=b.tiles[i0+1][j0];
            b.tiles[i0+1][j0]=0;
            b.tiles[i0][j0]=v;
            s.push(b);
        }

        // move left?
        if ( i0 - 1 > 0 ) {
            Board b=new Board(tiles);
            int v=b.tiles[i0-1][j0];
            b.tiles[i0-1][j0]=0;
            b.tiles[i0][j0]=v;
            s.push(b);
        }

        // move down?
        if ( j0 + 1 < n ) {
            Board b=new Board(tiles);
            int v=b.tiles[i0][j0+1];
            b.tiles[i0][j0+1]=0;
            b.tiles[i0][j0]=v;
            s.push(b);
        }

        // move up?
        if ( j0 - 1 > 0 ) {
            Board b=new Board(tiles);
            int v=b.tiles[i0][j0-1];
            b.tiles[i0][j0-1]=0;
            b.tiles[i0][j0]=v;
            s.push(b);
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
    //public static void main(String[] args)


}