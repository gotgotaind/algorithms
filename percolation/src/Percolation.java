import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private Boolean [][] grid;
    private WeightedQuickUnionUF union;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        grid = new Boolean[n][n];
        union = new WeightedQuickUnionUF((n+1)*n);

        // first row is pseudo open row
        for (int c = 0; c < n; c++)
        {
            grid[0][c] = True;
            union.union(0,c);
        }

        for (int r = 1; r < n; r++)
        {
            for (int c = 0; c < n; c++)
            {
                grid[r][c] = False;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ( ! isOpen(row+1,col) ) {
            grid[row+1][col]=True;
            if ( row < n ) {
                if (isOpen(row + 1, col)) {

                }
            }


        }

    };

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) { return grid[row][col]; };

    // is the site (row, col) full?
    public boolean isFull(int row, int col) { return False;};

    // returns the number of open sites
    public int numberOfOpenSites() {return 0;};

    // does the system percolate?
    public boolean percolates() {return False;};

    // test client (optional)
    public static void main(String[] args) {};
}