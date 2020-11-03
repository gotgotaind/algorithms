import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class SeamCarver {

    private Picture picture;
    private double[][] energy;
    private int w,h;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null ) throw new IllegalArgumentException();
        this.picture=new Picture(picture);
        w=picture.width();
        h=picture.height();

        energy=new double[w][h];

        for(int col=0; col < w; col++) {
            for(int row=0; row < h; row++) {
                    energy[col][row]=compute_energy(col, row);
                }
        }
    }

    private double compute_energy(int col, int row) {
        if (row == 0 || col == 0 || row == h - 1 || col == w - 1) {
            return(1000);
        } else {

            int c0 = picture.getRGB(col + 1, row);
            int c1 = picture.getRGB(col - 1, row);
            double dx2 = compute_delta_square(c0,c1);
            c0 = picture.getRGB(col, row + 1);
            c1 = picture.getRGB(col, row - 1);
            double dy2 = compute_delta_square(c0,c1);
            return Math.sqrt(dx2+dy2);
        }

    }

    private static int compute_delta_square(int c1, int c2) {
        int r1 = (c1 >> 16) & 0xFF;
        int g1 = (c1 >>  8) & 0xFF;
        int b1 = (c1 >>  0) & 0xFF;
        int r2 = (c2 >> 16) & 0xFF;
        int g2 = (c2 >>  8) & 0xFF;
        int b2 = (c2 >>  0) & 0xFF;        
        return (r1-r2)*(r1-r2)+(g1-g2)*(g1-g2)+(b1-b2)*(b1-b2);
    }


    // current picture
    public Picture picture() {
        Picture np = new Picture(picture);
        return np;
    }

    // width of current picture
    public int width() {
        return w;
    }

    // height of current picture
    public int height() {
        return h;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if( x < 0 || x > w -1 || y < 0 || y > h - 1 ) throw new IllegalArgumentException();
        return energy[x][y];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[][] parent=new int[w][h];
        double path_energy[][]=new double[w][h];
        int[] possible_parent={-1,0,1};
        for (int r = 0; r < h; r++) {
            path_energy[0][r]=energy[0][r];
        }
        for(int c = 1; c < w; c++) {
            for (int r = 0; r < h; r++) {
                double min_parent_nrg=Double.POSITIVE_INFINITY;
                int best_min_parent_delta=10;
                for(int parent_pix_delta:possible_parent) {
                    if ( r + parent_pix_delta >= 0 && r + parent_pix_delta < h ) {
                        double parent_pix_nrg = path_energy[c - 1][r + parent_pix_delta];
                        if (parent_pix_nrg < min_parent_nrg) {
                            best_min_parent_delta = parent_pix_delta;
                            min_parent_nrg = parent_pix_nrg;
                        }
                    }
                }
                path_energy[c][r]=min_parent_nrg+energy(c,r);

                if( best_min_parent_delta == 10 ) throw new IllegalStateException();
                parent[c][r]=r+best_min_parent_delta;
            }
        }

        double min_path_nrg=Double.POSITIVE_INFINITY;
        int min_path_row=-1;
        for(int r = 0; r < h; r++) {
            double c_path_energy = path_energy[w-1][r];
            if ( c_path_energy < min_path_nrg ) {
                min_path_nrg = c_path_energy;
                min_path_row = r;
            }
        }
        if( min_path_row == -1 )  throw new IllegalStateException();

        Stack<Integer> s = new Stack<Integer>();
        int last=min_path_row;
        s.push(last);
        for( int c = w-1; c > 0; c-- ) {
            last=parent[c][last];
            s.push(last);
        }

        int[] result=new int[w];
        int c=0;
        for( int r:s) {
            result[c]=r;
            c++;
        }
        return result;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[][] parent=new int[w][h];
        double path_energy[][]=new double[w][h];
        int[] possible_parent={-1,0,1};
        for (int c = 0; c < w; c++) {
            path_energy[c][0]=energy[c][0];
        }
        for(int r = 1; r < h; r++) {
            for (int c = 0; c < w; c++) {
                double min_parent_nrg=Double.POSITIVE_INFINITY;
                int best_min_parent_delta=10;
                for(int parent_pix_delta:possible_parent) {
                    if ( c + parent_pix_delta >= 0 && c + parent_pix_delta < w ) {
                        double parent_pix_nrg = path_energy[c + parent_pix_delta][r - 1];
                        if (parent_pix_nrg < min_parent_nrg) {
                            best_min_parent_delta = parent_pix_delta;
                            min_parent_nrg = parent_pix_nrg;
                        }
                    }
                }
                path_energy[c][r]=min_parent_nrg+energy(c,r);

                if( best_min_parent_delta == 10 ) throw new IllegalStateException();
                parent[c][r]=c+best_min_parent_delta;
            }
        }

        double min_path_nrg=Double.POSITIVE_INFINITY;
        int min_path_col=-1;
        for(int c = 0; c < w; c++) {
            double c_path_energy = path_energy[c][h-1];
            if ( c_path_energy < min_path_nrg ) {
                min_path_nrg = c_path_energy;
                min_path_col = c;
            }
        }
        if( min_path_col == -1 )  throw new IllegalStateException();

        Stack<Integer> s = new Stack<Integer>();
        int last=min_path_col;
        s.push(last);
        for( int r = h-1; r > 0; r-- ) {
            last=parent[last][r];
            s.push(last);
        }

        int[] result=new int[h];
        int r=0;
        for( int c:s) {
            result[r]=c;
            r++;
        }
        return result;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if(seam == null) throw new IllegalArgumentException();
        if(seam.length != w) throw new IllegalArgumentException();

        for(int c = 1; c < w; c++) {
            if( Math.abs(seam[c] - seam[c - 1]) > 1 ) throw new IllegalArgumentException();
        }

        for(int c = 0; c < w; c++) {
            if( seam[c] < 0 || seam[c] > h - 1 ) throw new IllegalArgumentException();
        }

        if ( h <= 1 ) throw new IllegalArgumentException();

        Picture np=new Picture(w,h-1);
        double[][] ne=new double[w][h-1];

        for(int c = 0; c < w ; c++) {
            int jump=0;
            for(int r = 0; r < h - 1; r++) {
                if( r == seam[c] ) {
                    jump = 1;
                }
                np.set(c,r,picture.get(c,r+jump));
                ne[c][r]=energy[c][r+jump];
            }
        }
        picture=np;
        h=h-1;

        // recompute enery around the seam
        for(int c = 0; c < w ; c++) {
            if ( seam[c] < h  ) ne[c][seam[c]]=compute_energy(c,seam[c]);
            if ( seam[c] > 0 ) ne[c][seam[c]-1]=compute_energy(c,seam[c]-1);
            if ( seam[c] < h - 1  ) ne[c][seam[c]+1]=compute_energy(c,seam[c]+1);
        }
        energy=ne;

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null ) throw new IllegalArgumentException();
        if(seam.length != h) throw new IllegalArgumentException();

        for(int r = 1; r < h; r++) {
            if( Math.abs(seam[r] - seam[r - 1]) > 1 ) throw new IllegalArgumentException();
        }

        for(int r = 0; r < h; r++) {
            if( seam[r] < 0 || seam[r] > w - 1 ) throw new IllegalArgumentException();
        }

        if ( w <= 1 ) throw new IllegalArgumentException();

        Picture np=new Picture(w-1,h);
        double[][] ne=new double[w-1][h];
        for(int c = 0; c < w - 1; c++) {
            int jump=0;
            for(int r = 0; r < h ; r++) {
                if( c >= seam[r] ) {
                    jump = 1;
                }
                else
                {
                    jump = 0;
                }
                np.set(c,r,picture.get(c+jump,r));
                ne[c][r]=energy[c+jump][r];
            }
        }

        picture=np;
        w=w-1;

        // recompute enery around the seam
        for(int r = 0; r < h ; r++) {
            if( seam[r] < w ) ne[seam[r]][r]=compute_energy(seam[r],r);
            if( seam[r] > 0 ) ne[seam[r]-1][r]=compute_energy(seam[r]-1,r);
            if( seam[r] < w - 1 ) ne[seam[r]+1][r]=compute_energy(seam[r]+1,r);
        }
        energy=ne;
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        // test energy method
        Picture p=new Picture("6x5.png");
        SeamCarver sc=new SeamCarver(p);
        StdOut.println(sc.energy(2,3));
        StdOut.println( Arrays.toString(sc.findVerticalSeam() ) );
        StdOut.println( Arrays.toString(sc.findHorizontalSeam() ) );
        sc.removeHorizontalSeam(sc.findHorizontalSeam());
        sc.removeVerticalSeam(sc.findVerticalSeam());
    }

}