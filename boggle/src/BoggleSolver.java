import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoggleSolver
{
    private myTrieSET t;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        t=new myTrieSET();
        for( String w : dictionary ) {
            t.add(w.replace("QU","["));
        }
    }

    private char char_at_v(int v, myBoogleBoard mb) {
        int[] ij=mb.v_to_ij(v);
        char c=mb.b.getLetter(ij[0],ij[1]);
        if( c=='Q' ) c='[';
        return c;
    }

    private String p_to_s(ArrayList<Integer> p, myBoogleBoard mb) {
        StringBuilder sb = new StringBuilder();
        for(int v:p) {
            sb.append(char_at_v(v,mb));
        }
        return sb.toString();
    }

    private void sol_search(myTrieSET.Node cn, ArrayList<Integer> cp,ArrayList<ArrayList<Integer>> sol,myBoogleBoard mb) {
        if ( cn.isString == true ) {
            sol.add(cp);
            StdOut.println(p_to_s(cp,mb));
        }
        for(int v: mb.g.adj(cp.get(cp.size()-1))) {
            if( ! cp.contains(v) ) {
                for (char c = 0; c < myTrieSET.R; c++) {
                    if( cn.next[c] != null ) {
                        cp.add(v);
                        sol_search( cn.next[c],cp,sol,mb );
                    }
                }
            }
        }

    }


    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        myBoogleBoard mb=new myBoogleBoard(board);

        ArrayList<ArrayList<Integer>> sol = new ArrayList<>();

        for( int v=0;  v<mb.g.V(); v++ ) {
            myTrieSET.Node cn=t.root.next[char_at_v(v,mb)];
            if( cn != null ) {
                ArrayList<Integer> cp = new ArrayList<Integer>();
                cp.add(v);
                sol_search(cn, cp, sol, mb);
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
        // test the ij_to_v and v_to_ij  methods
        int ncols=board.cols();
        int nrows=board.rows();

        myBoogleBoard mb = new myBoogleBoard(board);
        for( int j=0; j<nrows; j++) {
            for(int i=0; i<ncols; i++) {
                StdOut.println("i: "+i+", j: "+j+", v: "+mb.ij_to_v(i,j));
            }
        }

        for( int v=0; v<nrows*ncols; v++) {
            StdOut.println("v: "+v+", i,j: "+Arrays.toString(mb.v_to_ij(v)));
        }

        //  print the adjacent nodes
        for( int v=0; v<nrows*ncols; v++) {
            StdOut.println("v: "+v+", i,j: "+Arrays.toString(mb.v_to_ij(v)));
            for(int vv:mb.g.adj(v)) {
                StdOut.println("neib : "+Arrays.toString(mb.v_to_ij(vv)));
            }
        }

         */

        // test the p_to_s method
        ArrayList<Integer> p=new ArrayList<>();
        p.add(2);
        p.add(3);
        p.add(7);
        myBoogleBoard mb = new myBoogleBoard(board);
        StdOut.println(solver.p_to_s(p,mb));

        solver.getAllValidWords(board);

        /*
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);*/
    }
}

