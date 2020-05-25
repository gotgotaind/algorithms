import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private Boolean [][] grid;
    private WeightedQuickUnionUF union;
    private int size;
    private int open_sites;
    private boolean isPercolates;

    // get union id from row,col
    private int getUnionId(int row, int col) {
        // +1 because id 0 in the union is a pseudo top open row
        return row*size+col+1;
    }
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        grid = new Boolean[n][n];
        open_sites=0;
        isPercolates=false;

        // +2 because we have a pseaudo first and last row
        union = new WeightedQuickUnionUF((n*n)+2);
        size=n;

        // first row is pseudo open row
/*        for (int c = 0; c < n; c++)
        {
            union.union(0,c);
        }*/

        // last row is pseudo open row
/*        for (int c = 0; c < n; c++)
        {
            union.union((n+1)*n,(n+1)*n+c);
        }*/

        for (int r = 0; r < n; r++)
        {
            for (int c = 0; c < n; c++)
            {
                grid[r][c] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if ( ! isOpen(row,col) ) {
            open_sites=open_sites+1;
            grid[row][col]=true;

            // Union with bottom pseudo row
            if ( row == (size-1) ) {
                union.union(getUnionId(row,col),(size*size)+1);
            }

            if ( row < (size-1) ) {
                if (isOpen(row + 1, col)) {
                    union.union(getUnionId(row,col),getUnionId(row+1,col));
                }
            }

            // Union with top pseudo row
            if ( row == 0 ) {
                    union.union(getUnionId(row,col),0);
            }

            if ( row > 0 ) {
                if (isOpen(row - 1, col)) {
                    union.union(getUnionId(row,col),getUnionId(row-1,col));
                }
            }
            if ( col > 0 ) {
                if (isOpen(row , col-1)) {
                    union.union(getUnionId(row,col),getUnionId(row,col-1));
                }
            }
            if ( col < (size-1) ) {
                if (isOpen(row , col+1)) {
                    union.union(getUnionId(row,col),getUnionId(row,col+1));
                }
            }

            if ( isFull(row,col) ) {

                // does it connect to pseudo bottom row
                boolean connect_bottom=(union.find(getUnionId(row,col))==union.find(size*size+1));
                if ( connect_bottom ) {
                    isPercolates=true;
                }
            }


        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int current_unionid=union.find(getUnionId(row,col));
        int top_unionid=union.find(0);
/*        System.out.println(String.format("row,col: %d,%d",row,col));
        System.out.println(String.format("current_unionid is %d",current_unionid));
        System.out.println(String.format("top_unionid is %d",top_unionid));*/
        boolean connect_top=(current_unionid==top_unionid);
        return ( connect_top );
    }

    // returns the number of open sites
    public int numberOfOpenSites() {return open_sites;}

    // does the system percolate?
    public boolean percolates() {
        return isPercolates;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(0,0);
        p.open(1,1);
        p.open(1,0);
        p.open(2,2);
        System.out.println(p.percolates());
        p.open(2,1);
        System.out.println(p.percolates());


    }
}