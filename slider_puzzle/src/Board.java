import edu.princeton.cs.algs4.StdOut;

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
    public Iterable<Board> neighbors()

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()

    // unit testing (not graded)
    public static void main(String[] args)

}