import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.TrieSET;
import edu.princeton.cs.algs4.TrieST;

import java.util.Arrays;

public class CircularSuffixArray {

    private int l;
    private char[] ss;
    private int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if( s==null ) throw new IllegalArgumentException();
        l=s.length();
        // making a char array of the original string copied twice
        // so that we can take slice of it to make the circular
        // char arrays
        this.ss=(s+s).toCharArray();
        index=new int[l];
        ics[] icsa=new ics[l];
        // StdOut.println("icsa before sorting :");
        for(Integer i=0; i<l; i++) {
            icsa[i]=new ics(ss,i);
            // StdOut.println(icsa[i].icss());
        }

        Quick.sort(icsa);

        // StdOut.println("icsa after sorting :");
        for(Integer i=0; i<l; i++) {
            index[i]=icsa[i].ith;
            // StdOut.println(icsa[i].icss());
        }
    }

    // ith circular suffix
    private class ics implements Comparable<ics> {
        Integer ith;
        char[] ss;

        public ics(char[] ss,int ith) {
            this.ss=ss;
            this.ith=ith;
        }

        public String icss() {;
            char[] icsa= Arrays.copyOfRange(ss,ith,ith+l);
            return new String(icsa);
        }

        public int compareTo(ics icst){
            // StdOut.println("A: "+this.icss()+" B: "+icst.icss()+" :"+this.icss().compareTo(icst.icss()));
            return this.icss().compareTo(icst.icss());
        }
    }

    // also add this ics method in the parent class in order to be able to print the ith circular string
    private String ics_debug(Integer ith) {
        char[] icsa= Arrays.copyOfRange(ss,ith,ith+l);
        return new String(icsa);
    }


    // length of s
    public int length() {
        return l;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if( i<0 || i>=l ) throw new IllegalArgumentException();
        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        StringBuilder sb=new StringBuilder();
        int l=10000;
        for(int i=0; i<l; i++) {
            sb.append((char) StdRandom.uniform(128));
        }

        Stopwatch timer = new Stopwatch();
        // random ascii
        CircularSuffixArray c=new CircularSuffixArray(sb.toString());
        // abra.txt
        // CircularSuffixArray c=new CircularSuffixArray("ABRACADABRA!");
        // cadabra.txt
        // CircularSuffixArray c=new CircularSuffixArray("CADABRA!ABRA");
        //CircularSuffixArray c=new CircularSuffixArray("ABABABABABAB");
        //CircularSuffixArray c=new CircularSuffixArray("ABAB");

        for(int i=0; i<c.length(); i++ ) {
            //StdOut.println(c.ics_debug(c.index[i])+"\t"+c.index[i]);
        }
        StdOut.println("Finished in "+timer.elapsedTime());

    }

}
