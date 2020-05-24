import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private Boolean [][] grid;
    private WeightedQuickUnionUF union;
    private int size;
    private int open_sites;
    private boolean isPercolates;

    // get union id from row,col
    private int getUnionId(int row, int col) {
        // row+1 because row 0 in the union is a pseudo open row
        return (row+1)*size+col;
    }
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        grid = new Boolean[n][n];
        open_sites=0;
        isPercolates=false;

        // n+2 because we have a pseaudo first and last row ( both open )
        union = new WeightedQuickUnionUF((n+2)*n);
        size=n;

        // first row is pseudo open row
        for (int c = 0; c < n; c++)
        {
            union.union(0,c);
        }

        // last row is pseudo open row
        for (int c = 0; c < n; c++)
        {
            union.union((n+1)*n,(n+1)*n+c);
        }

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
            grid[row+1][col]=true;
            if ( row <= size ) {
                if (isOpen(row + 1, col)) {
                    union.union(getUnionId(row,col),getUnionId(row+1,col));
                }
            }
            if ( row >= 0 ) {
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

                boolean connect_bottom=(union.find(getUnionId(row,col))==union.find(size*(size+2)));
                if ( connect_bottom ) {
                    isPercolates=true;
                }
            }


        }

    };

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ( row == 0 ) return true;
        if ( row== (size+1) ) return true;
        return grid[row][col];
    };

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        boolean connect_top=(union.find(getUnionId(row,col))==union.find(0));
        return ( connect_top );
    };

    // returns the number of open sites
    public int numberOfOpenSites() {return open_sites;};

    // does the system percolate?
    public boolean percolates() {
        return isPercolates;
    };

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(0,0);


        System.out.println(p.isFull(0,0));



    };
}