import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private final double mean,dev,clow,chigh;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){

        if ( n < 1 ) {
            throw new IllegalArgumentException("n must be > 0");
        }
        if ( trials < 1 ) {
            // this fails grader tests : StdOut.println("trials = " + trials);
            throw new IllegalArgumentException("triaals : "+trials+" must be > 0");
        }
        int row=-1;
        int col=-1;
        double[] t=new double[trials];

        for( int trial=0;  trial<trials ; trial++ ) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {

                boolean already_open = true;

                while (already_open) {
                    row = StdRandom.uniform(n) + 1;
                    col = StdRandom.uniform(n) + 1;
                    already_open = p.isOpen(row, col);
/*                System.out.println("row = " + row);
                System.out.println("col = " + col);
                System.out.println(already_open);*/
                }
                p.open(row, col);
            }

            t[trial] = (double) (p.numberOfOpenSites()) / n / n;
        }

        mean=StdStats.mean(t);
        dev=StdStats.stddev(t);
        clow=mean- 1.96*dev/Math.sqrt(trials);
        chigh=mean+ 1.96*dev/Math.sqrt(trials);


    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return dev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return clow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return chigh;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps=new PercolationStats(n,T);
        StdOut.println("ps.mean() = " + ps.mean());
        StdOut.println("ps.stddev() = " + ps.stddev());
        StdOut.println("ps.confidenceLo() = " + ps.confidenceLo());
        StdOut.println("ps.confidenceHi() = " + ps.confidenceHi());
    }

}