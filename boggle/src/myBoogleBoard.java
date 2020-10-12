import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class myBoogleBoard {
    public int nrows,ncols;
    public Digraph g;
    public BoggleBoard b;

    private void initG() {
        for( int j=0; j<nrows; j++) {
            for(int i=0; i<ncols; i++) {
                for(int ii=-1; ii<=1; ii++ ) {
                    for(int jj=-1; jj<=1; jj++ ) {
                        if ( ( i+ii>=0 ) && ( j+jj>=0 ) && ( i+ii<ncols ) && ( j+jj<nrows ) && !( ii==0 && jj==0 ) ) {
                            g.addEdge(ij_to_v(i,j),ij_to_v(i+ii,j+jj));
                        }
                    }
                }
            }
        }
    }
    public myBoogleBoard(BoggleBoard b) {

        nrows=b.rows();
        ncols=b.cols();
        this.b=b;
        g=new Digraph(ncols*nrows);
        initG();
    }

    public  int[] v_to_ij(int v) {
        int[] r=new int[2];
        r[0]=v-v/ncols*ncols;
        r[1]=v/ncols;
        return r;
    }

    public int ij_to_v(int i, int j) {
        return i+j*ncols;
    }


}
