import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean [][] grid;
    private final WeightedQuickUnionUF union_ispercolates;
    private final WeightedQuickUnionUF union_isfull;
    private final int size;
    private int open_sites;
    private boolean isPercolates;


    // get union id from row,col
    private int getUnionId(int row, int col) {
        // +1 because id 0 in the union is a pseudo top open row
        // -1 to col and row because indexes have to start at 1
        return (row-1)*size+(col-1)+1;
    }


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if ( n <= 0 ) {
            throw new IllegalArgumentException("grid size must be positive");
        }

        grid = new boolean[n][n];
        open_sites=0;
        isPercolates=false;

        // +2 because we have a pseudo first and bottom row
        union_ispercolates = new WeightedQuickUnionUF((n*n)+2);
        // +1 because we only have a pseudo first row
        union_isfull = new WeightedQuickUnionUF((n*n)+1);
        size=n;

        // first row is pseudo open row
/*        for (int c = 0; c < n; c++)
        {
            union.union(0,c);
        }*/

        // last row is pseudo open row
/*
        for (int c = 0; c < n; c++)
        {
            union.union((n+1)*n,(n+1)*n+c);
        }
*/

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


        if ( (row < 1) || ( row > size) ) {
            throw new IllegalArgumentException("row must be between 1 and n");
        }
        if ( (col < 1) || (col >size) ) {
            throw new IllegalArgumentException("col must be between 1 and n");
        }

        // row and col start at 1, while our grid array is indexed starting at 0
        // so wew define a grid row and col as this
        int grow=row-1;
        int gcol=col-1;

        if ( ! isOpen(row,col) ) {
            open_sites=open_sites+1;
            grid[grow][gcol]=true;

            // Union with bottom pseudo row
            if ( grow == (size-1) ) {
                union_ispercolates.union(getUnionId(row,col),(size*size)+1);
            }

            // Union with down
            if ( grow < (size-1) ) {
                if (isOpen(row + 1, col)) {
                    union_ispercolates.union(getUnionId(row,col),getUnionId(row+1,col));
                    union_isfull.union(getUnionId(row,col),getUnionId(row+1,col));
                }
            }

            // Union with top pseudo row
            if ( grow == 0 ) {
                union_ispercolates.union(getUnionId(row,col),0);
                union_isfull.union(getUnionId(row,col),0);
            }

            // union with above
            if ( grow > 0 ) {
                if (isOpen(row - 1, col)) {
                    union_ispercolates.union(getUnionId(row,col),getUnionId(row-1,col));
                    union_isfull.union(getUnionId(row,col),getUnionId(row-1,col));
                }
            }

            // union with left
            if ( gcol > 0 ) {
                if (isOpen(row , col-1)) {
                    union_ispercolates.union(getUnionId(row,col),getUnionId(row,col-1));
                    union_isfull.union(getUnionId(row,col),getUnionId(row,col-1));
                }
            }

            // union with right
            if ( gcol < (size-1) ) {
                if (isOpen(row , col+1)) {
                    union_ispercolates.union(getUnionId(row,col),getUnionId(row,col+1));
                    union_isfull.union(getUnionId(row,col),getUnionId(row,col+1));
                }
            }




        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ( (row < 1) || ( row > size) ) {
            throw new IllegalArgumentException("row must be between 1 and n");
        }
        if ( (col < 1) || (col >size) ) {
            throw new IllegalArgumentException("col must be between 1 and n");
        }

        return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ( (row < 1) || ( row > size) ) {
            throw new IllegalArgumentException("row must be between 1 and n");
        }
        if ( (col < 1) || (col >size) ) {
            throw new IllegalArgumentException("col must be between 1 and n");
        }

        int current_unionid=union_isfull.find(getUnionId(row,col));
        int top_unionid=union_isfull.find(0);
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

        // does pseaudo top row connect to pseudo bottom row

            boolean connect_bottom = (union_ispercolates.find(0) == union_ispercolates.find(size * size + 1 ));
            if ( connect_bottom ) {
                isPercolates=true;
            }

        return isPercolates;

    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1,1);
        p.open(2,2);
        p.open(2,1);
        p.open(3,3);
        System.out.println(p.percolates());
        System.out.println(p.numberOfOpenSites());
        p.open(3,2);
        System.out.println(p.percolates());
        System.out.println(p.numberOfOpenSites());

    }
}