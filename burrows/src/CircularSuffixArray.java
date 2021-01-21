import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.Quick3way;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.SuffixArray;
import edu.princeton.cs.algs4.TrieSET;
import edu.princeton.cs.algs4.TrieST;

import java.util.Arrays;

public class CircularSuffixArray {

    private int l;
    private char[] ca;
    private int[] index;
    private static final int CUTOFF =  5;   // cutoff to insertion sort (any value between 0 and 12)

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        String method = "3quick_fromsuffixarrayx";
        //String method = "my_own";
        if (s == null) throw new IllegalArgumentException();
        l = s.length();
        index = new int[l];
        // making a char array of the original string copied twice
        // so that we can take slice of it to make the circular
        // char arrays
        this.ca = (s + s).toCharArray();

        if (method == "my_own") {
            // my method gets 100% completion on the grader but misses 3 performance points


            ics[] icsa = new ics[l];
            // StdOut.println("icsa before sorting :");
            for (Integer i = 0; i < l; i++) {
                icsa[i] = new ics(i);
                // StdOut.println(icsa[i].icss());
            }


            Merge.sort(icsa);

            // StdOut.println("icsa after sorting :");
            for (Integer i = 0; i < l; i++) {
                index[i] = icsa[i].ith;
                // StdOut.println(icsa[i].icss());
            }
        }

        if (method == "SuffixArray") {
            SuffixArray sa = new SuffixArray(s);
            for (Integer i = 0; i < l; i++) {
                index[i] = sa.index(i);
                // StdOut.println(icsa[i].icss());
            }
        }

        if (method == "3quick_fromsuffixarrayx") {
            // 3-way string quicksort lo..hi starting at dth character
            for (int i = 0; i < l; i++)
                index[i] = i;

            sort(0, l-1, 0);

            /*
            // check the sort

            for (int i = 0; i < l - 1; i++)
                if( ! less(index[i],index[i+1],0 ) ) {
                    StdOut.println(i);
                    StdOut.println(ics_debug(index[i]));
                    StdOut.println(ics_debug(index[i+1]));
                    throw new IllegalStateException("");
                }
            */
            }


    }

        // 3-way string quicksort lo..hi starting at dth character ( fromsuffixarrayx )
        private void sort(int lo, int hi, int d) {

            // cutoff to insertion sort for small subarrays
            if (hi <= lo + CUTOFF) {
                insertion(lo, hi, d);
                return;
            }

            int lt = lo, gt = hi;
            char v = ca[index[lo] + d];
            int i = lo + 1;
            while (i <= gt) {
                char t = ca[index[i] + d];
                if      (t < v) exch(lt++, i++);
                else if (t > v) exch(i, gt--);
                else            i++;
            }

            // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
            sort(lo, lt-1, d);
            if (v >= 0  && d<l ) sort(lt, gt, d+1);
            sort(gt+1, hi, d);
        }

        // sort from a[lo] to a[hi], starting at the dth character
        private void insertion(int lo, int hi, int d) {
            for (int i = lo; i <= hi; i++)
                for (int j = i; j > lo && less(index[j], index[j-1], d); j--)
                    exch(j, j-1);
        }

        // is text[i+d..n) < text[j+d..n) ?
        private boolean less(int i, int j, int d) {
            if (i == j) return false;
            i = i + d;
            j = j + d;
            int k = 0;
            // in suffixarrayx was this, because only the suffixes are compared
            //while (i < l && j < l) {
            // but wwe want to compare the whole circularsuffix so we may need to compare l characters
            while (k < l && i < 2*l && j < 2*l ) {
                if (ca[i] < ca[j]) return true;
                if (ca[i] > ca[j]) return false;
                i++;
                j++;
                k++;
            }
            // in suffixarrayx was this
            // return i > j;
            return false;
        }

        // exchange index[i] and index[j]
        private void exch(int i, int j) {
            int swap = index[i];
            index[i] = index[j];
            index[j] = swap;
        }

    // ith circular suffix
    private class ics implements Comparable<ics> {
        Integer ith;

        public ics(int ith) {
            this.ith=ith;
        }

        public int compareTo(ics icst){
            // StdOut.println("A: "+this.icss()+" B: "+icst.icss()+" :"+this.icss().compareTo(icst.icss()));
            // return this.icss().compareTo(icst.icss());
            for(int i=0; i<l; i++) {
                int result = ca[i+ith] - ca[i+icst.ith];
                if( result != 0 ) return result;
            }
            return 0;
        }
    }

    // also add this ics method in the parent class in order to be able to print the ith circular string
    private String ics_debug(Integer ith) {
        char[] icsa= Arrays.copyOfRange(ca,ith,ith+l);
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
            sb.append((char) (StdRandom.uniform(25) ) );
        }

        Stopwatch timer = new Stopwatch();
        // random ascii
        // CircularSuffixArray c=new CircularSuffixArray(sb.toString());
        // abra.txt
        CircularSuffixArray c=new CircularSuffixArray("ABRACADABRA!");
        // ABABBABABBABABB
        //CircularSuffixArray c=new CircularSuffixArray("ABABBABABBABABB");
        // stars.txt
        // CircularSuffixArray c=new CircularSuffixArray("**");
        // BAA
        //CircularSuffixArray c=new CircularSuffixArray("BAA");
        // cadabra.txt
        // CircularSuffixArray c=new CircularSuffixArray("CADABRA!ABRA");
        //CircularSuffixArray c=new CircularSuffixArray("ABABABABABAB");
        //CircularSuffixArray c=new CircularSuffixArray("ABAB");

        for(int i=0; i<c.length(); i++ ) {
            StdOut.println(c.ics_debug(i)+"\t"+i+"\t"+c.ics_debug(c.index[i])+"\t"+c.index[i]);
        }
        StdOut.println("Finished in "+timer.elapsedTime());

    }

}