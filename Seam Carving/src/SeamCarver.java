import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class SeamCarver {

    private Picture picture;
    private double[][] energy;
    int w,h;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null ) throw new IllegalArgumentException();
        this.picture=new Picture(picture);
        w=picture.width();
        h=picture.height();

        energy=new double[w][h];



        for(int col=0; col < w; col++) {
            for(int row=0; row < h; row++) {
                if ( row == 0 || col == 0 || row == h-1 || col == w-1 ) {
                    energy[col][row]=1000;
                }
                else
                {
                    float[] rgb0;
                    float[] rgb1;

                    rgb0=picture.get(col+1,row).getRGBColorComponents(null);
                    rgb1=picture.get(col-1,row).getRGBColorComponents(null);
                    double dx2=0;
                    for(int i=0; i<3; i++) {
                        dx2=dx2+(rgb1[i]-rgb0[i])*(rgb1[i]-rgb0[i])*255*255;
                    }

                    rgb0=picture.get(col,row+1).getRGBColorComponents(null);
                    rgb1=picture.get(col,row-1).getRGBColorComponents(null);
                    double dy2=0;
                    for(int i=0; i<3; i++) {
                        dy2=dy2+(rgb1[i]-rgb0[i])*(rgb1[i]-rgb0[i])*255*255;
                    }

                    energy[col][row]=Math.sqrt(dx2+dy2);
                }
            }
        }



    }

    // current picture
    public Picture picture() {
        return picture;
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
        return energy[x][y];
    }

    // sequence of indices for horizontal seam
    //public int[] findHorizontalSeam()

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
                    if ( c + parent_pix_delta > 0 && c + parent_pix_delta < w ) {
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
    //public void removeHorizontalSeam(int[] seam)

    // remove vertical seam from current picture
    //public void removeVerticalSeam(int[] seam)

    //  unit testing (optional)
    public static void main(String[] args) {
        // test energy method
        Picture p=new Picture("6x5.png");
        SeamCarver sc=new SeamCarver(p);
        StdOut.println(sc.energy(1,2));
        StdOut.println( Arrays.toString(sc.findVerticalSeam() ) );
    }

}