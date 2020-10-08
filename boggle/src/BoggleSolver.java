import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoggleSolver
{
    private TrieSET t;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        t=new TrieSET();
        for( String w : dictionary ) {
            t.add(w.replace("QU","["));
        }
    }

    private static int[] v_to_ij(int v, int ncols, int nrows) {
        int[] r=new int[2];
        r[0]=v-v/ncols*ncols;
        r[1]=v/ncols;
        return r;
    }

    private static int ij_to_v(int i, int j, int ncols, int rows) {
        return i+j*ncols;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Graph g=new Graph(board.cols()*board.rows());
        int ncols=board.cols();
        int nrows=board.rows();
        for( int j=0; j<nrows; j++) {
            for(int i=0; i<ncols; i++) {
                for(int ii=-1; ii<=1; ii++ ) {
                    for(int jj=-1; jj<=1; jj++ ) {
                        if ( ( i+ii>0 ) && ( j+jj>0 ) && ( i+ii<ncols ) && ( j+jj<nrows ) && !( ii==0 && jj==0 ) ) {
                            g.addEdge(ij_to_v(i,j,ncols,nrows),ij_to_v(i+ii,j+jj,ncols,nrows));
                        }
                    }
                }
            }
        }

        String[] a = new String[] {"a"};
        List<String> list = Arrays.asList(a);
        return list;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        return 2;
    }

    public static void main(String[] args) {
        In in = new In("file://../dictionaries/mydic.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("file://../boards/board-aqua.txt");

        /*

        // test the ij_to_v and v_to_ij static methods
        int ncols=board.cols();
        int nrows=board.rows();
        for( int j=0; j<nrows; j++) {
            for(int i=0; i<ncols; i++) {
                StdOut.println("i: "+i+", j: "+j+", v: "+ij_to_v(i,j,ncols,nrows));
            }
        }

        for( int v=0; v<nrows*ncols; v++) {
            StdOut.println("v: "+v+", i,j: "+Arrays.toString(v_to_ij(v,nrows,ncols)));
        }

         */

        /*
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);*/
    }
}

