import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
        char c=mb.b.getLetter(ij[1],ij[0]);
        if( c=='Q' ) c='[';
        return c;
    }

    private String p_to_s(List<Integer> p, myBoogleBoard mb) {
        StringBuilder sb = new StringBuilder();
        for(int v:p) {
            char c=char_at_v(v,mb);
            if( c=='[' ) {
                sb.append("Q");
                sb.append("U");
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private void sol_search(myTrieSET.Node cn, ArrayList<Integer> cp,int wl, List<List<Integer>> sol,myBoogleBoard mb ) {
        if ( cn.isString == true && wl >2 ) {

            sol.add(new ArrayList<Integer>(cp));
            //StdOut.println(cp.toString());
            //StdOut.println(p_to_s(cp,mb));
        }
        for(int v: mb.g.adj(cp.get(cp.size()-1))) {
            if( ! cp.contains(v) ) {
                    char c=char_at_v(v,mb);

                    if( cn.next[c-myTrieSET.o] != null ) {
                        //ArrayList<Integer> ncp=new ArrayList<>(cp);
                        cp.add(v);
                        wl=wl+1;
                        if ( c == '[' ) {
                            wl=wl+1;
                        }
                        sol_search(cn.next[c-myTrieSET.o], cp, wl, sol, mb);

                        cp.remove(cp.size()-1);
                        wl=wl-1;
                        if ( c == '[' ) {
                            wl=wl-1;
                        }
                    }
            }
        }

    }


    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        myBoogleBoard mb=new myBoogleBoard(board);

        List<List<Integer>> sol = new ArrayList<>();

        for( int v=0;  v<mb.g.V(); v++ ) {
            char c=char_at_v(v,mb);

            // we have to keep track of word length use to check if the word is a
            // valid word of at least 3 letters. Taking into account that Qu counts
            // as 2 letters
            int wl=1;
            if ( c == '[' ) {
                wl=2;
            }

            myTrieSET.Node cn=t.root.next[c-myTrieSET.o];
            if( cn != null ) {
                ArrayList<Integer> cp = new ArrayList<Integer>();
                cp.add(v);
                sol_search(cn, cp, wl, sol, mb);
            }
        }

        // convert sol ( list of solution as path in the graph ) to a string set
        SET<String> sol_s=new SET<String>();
        //StdOut.println("Solutions :");
        for( List<Integer> s:sol ) {
            //StdOut.println(s.toString());
            sol_s.add(p_to_s(s,mb));
        }
        return sol_s;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if ( ! t.contains(word.replace("QU","[")) ) {
            return 0;
        }
        //StdOut.println(word);
        int len=word.length();

        if( len == 3 || len == 4 ) {
            return 1;
        }
        if( len == 5 ) return 2;
        if( len == 6 ) return 3;
        if( len == 7 ) return 5;
        if( len >= 8 ) return 11;
        return 0;
    }

    public static void main(String[] args) {
        In in = new In("file://../dictionaries/dictionary-16q.txt");
        //In in = new In("file://../dictionaries/mydic.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("file://../boards/board-16q.txt");
        //BoggleBoard board = new BoggleBoard("file://../boards/board-aqua.txt");

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
        */
/*
        //  print the adjacent nodes
        myBoogleBoard mb = new myBoogleBoard(board);
        for( int v=0; v<mb.nrows*mb.ncols; v++) {
            StdOut.println("v: "+v+", i,j: "+Arrays.toString(mb.v_to_ij(v)));
            for(int vv:mb.g.adj(v)) {
                StdOut.println("neib : "+Arrays.toString(mb.v_to_ij(vv)));
            }
        }
*/

        /*
        // test the p_to_s method
        ArrayList<Integer> p=new ArrayList<>();
        p.add(2);
        p.add(3);
        p.add(7);
        myBoogleBoard mb = new myBoogleBoard(board);
        StdOut.println(solver.p_to_s(p,mb));
        */

/*
        for( String s:solver.getAllValidWords(board)) {
            StdOut.println(s);
        }
*/
        StdOut.println(solver.scoreOf("AZEDD"));
        StdOut.println(solver.scoreOf("QUQU"));
        /*
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);*/
    }
}

